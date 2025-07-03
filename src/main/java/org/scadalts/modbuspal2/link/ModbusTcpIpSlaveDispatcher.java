/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.scadalts.modbuspal2.link;

import org.apache.logging.log4j.LogManager;
import org.scadalts.modbuspal2.main.ModbusPalProject;
import org.scadalts.modbuspal2.slave.ModbusSlaveAddress;
import org.scadalts.modbuspal2.toolkit.ModbusTools;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Processes an incoming tcp/ip connection received by the ModbusTcpIpLink
 *
 * @author nnovic
 */
public class ModbusTcpIpSlaveDispatcher
        extends ModbusSlaveProcessor
        implements Runnable {
    private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger(ModbusTcpIpSlaveDispatcher.class);

    private static ArrayList<ModbusTcpIpSlaveDispatcher> dispatchers = new ArrayList<ModbusTcpIpSlaveDispatcher>();
    private Thread slaveThread;
    private Socket slaveSocket;
    private InputStream slaveInput;
    private OutputStream slaveOutput;
    /**
     * Creates a new instance of ModbusTcpIpSlaveDispatcher
     *
     * @param mpp  the modbuspal project that holds MODBUS slaves information
     * @param sock the socket to use to communicate with the master
     * @throws IOException
     */
    public ModbusTcpIpSlaveDispatcher(ModbusPalProject mpp, Socket sock)
            throws IOException {
        super(mpp);
        slaveSocket = sock;
        slaveInput = sock.getInputStream();
        slaveOutput = sock.getOutputStream();
        LOG.info("SlaveInput available: {}", slaveInput.available());
    }

    private static void register(ModbusTcpIpSlaveDispatcher dispatcher) {
        if (dispatchers.contains(dispatcher) == false) {
            dispatchers.add(dispatcher);
        }
    }

    private static void unregister(ModbusTcpIpSlaveDispatcher dispatcher) {
        if (dispatchers.contains(dispatcher) == true) {
            dispatchers.remove(dispatcher);
        }
    }

    static void stopAll() {
        Iterator<ModbusTcpIpSlaveDispatcher> iter = dispatchers.iterator();
        while (iter.hasNext()) {
            ModbusTcpIpSlaveDispatcher dispatcher = iter.next();
            dispatcher.stop();
        }
    }

    /**
     * starts the thread that processes the incoming requests
     */
    public void start() {
        slaveThread = new Thread(this, "tcp/ip dispatcher");
        slaveThread.start();
    }

    /**
     * stops the thread that processes  the incoming requests
     */
    public void stop() {
        try {
            slaveInput.close();
            slaveOutput.close();
            slaveThread = null;
        } catch (IOException ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    @Override
    public void run() {
        LOG.debug("ModbusTcpIpSlaveDispatcher starting..");
        register(this);

        int recv = 0;
        byte[] buffer = new byte[2048];

        try {
            while (recv != -1) {
                // read MBAP header:
                LOG.debug("ModbusTcpIpSlaveDispatcher.. before read len 7");
                recv = slaveInput.read(buffer, 0, 7);
                LOG.debug("ModbusTcpIpSlaveDispatcher.. after read len 7");
                if (recv == -1) {
                    continue;
                } else if (recv != 7) {
                    throw new IOException();
                }

                // interpret MBAP header:
                int transactionIdentifier = ModbusTools.getUint16(buffer, 0);
                int protocolIdentifier = ModbusTools.getUint16(buffer, 2);
                int length = ModbusTools.getUint16(buffer, 4);
                int uID = ModbusTools.getUint8(buffer, 6);
                //System.out.println("tID="+transactionIdentifier+" pID="+protocolIdentifier+" L="+length+" uID="+uID);

                // receive PDU
                int pduLength = length - 1;
                LOG.debug("ModbusTcpIpSlaveDispatcher.. before read len {}", pduLength);
                recv = slaveInput.read(buffer, 7, pduLength);
                LOG.debug("ModbusTcpIpSlaveDispatcher.. after read len {}", pduLength);
                if (recv == -1) {
                    continue;
                } else if (recv != pduLength) {
                    throw new IOException("received " + recv + " bytes instead of " + pduLength);
                }
                // interpret PDU and get result:
                pduLength = processPDU(new ModbusSlaveAddress(slaveSocket.getInetAddress(), uID), buffer, 7, pduLength);
                if (pduLength > 0) {
                    // change length in MBAP
                    ModbusTools.setUint16(buffer, 4, pduLength + 1);
                    LOG.debug("ModbusTcpIpSlaveDispatcher.. before write");
                    // send all
                    slaveOutput.write(buffer, 0, 7 + pduLength);
                    slaveOutput.flush();
                    LOG.debug("ModbusTcpIpSlaveDispatcher.. after write");
                }
            }
        } catch (Exception ex) {
            LOG.error("ModbusTcpIpSlaveDispatcher exception " + ex.getMessage(), ex);
        }

        // close input stream before exiting
        try {
            slaveInput.close();
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }

        try {
            // close output stream before exiting
            slaveOutput.close();
        } catch (IOException ex) {
            LOG.error(ex.getMessage(), ex);
        }

        LOG.debug("ModbusTcpIpSlaveDispatcher stopped.");
        unregister(this);
    }
}
