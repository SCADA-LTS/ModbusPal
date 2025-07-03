/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.scadalts.modbuspal2.link;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.scadalts.modbuspal2.main.ModbusPalProject;
import org.scadalts.modbuspal2.master.ModbusMasterRequest;
import org.scadalts.modbuspal2.slave.ModbusSlaveAddress;
import org.scadalts.modbuspal2.toolkit.ModbusTools;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * This link waits for incoming requests on a particular TCP port
 *
 * @author nnovic
 */
public class ModbusTcpIpLink extends ModbusSlaveProcessor implements ModbusLink {

    private static final Logger LOG = LogManager.getLogger(ModbusTcpIpLink.class);
    private final ModbusPalProject modbusPalProject;
    private ServerSocket serverSocket;
    private Thread serverThread;
    private boolean executeThread;
    private ModbusLinkListener listener = null;
    private HashMap<ModbusSlaveAddress, Socket> clientSockets;
    private int tcpPort;

    /**
     * Transcation identifier for the master's requests.
     */
    private int clientTI;

    /**
     * Creates a new instance of ModbusTcpIpLink
     *
     * @param mpp  the modbuspal project that holds MODBUS slaves information
     * @param port the TCP port to listen to for incoming connections
     * @throws IOException
     */
    public ModbusTcpIpLink(ModbusPalProject mpp, int port) throws IOException {
        super(mpp);
        modbusPalProject = mpp;
        tcpPort = port;
        clientSockets = new HashMap<ModbusSlaveAddress, Socket>();
    }

    @Override
    public void start(ModbusLinkListener l) {
        executeThread = true;
        serverThread = new Thread(this, "tcp/ip link");
        listener = l;
        serverThread.start();
    }

    @Override
    public void stop() {
        executeThread = false;
        serverThread.interrupt();
        try {
            serverSocket.close();
        } catch (IOException ex) {
            LOG.error(ex.getMessage(), ex);
        }

        try {
            serverThread.join();
        } catch (InterruptedException ex) {
            LOG.error(ex.getMessage(), ex);
        }
        serverThread = null;
        ModbusTcpIpSlaveDispatcher.stopAll();
    }

    @Override
    public void run() {
        LOG.info("ModbusTcpIpLink starting... ");
        try {
            executeThread = true;
            serverSocket = new ServerSocket(tcpPort);
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            executeThread = false;
            LOG.error("ModbusTcpIpLink failed start.");
        }

        LOG.info("ModbusTcpIpLink Started: {}", serverSocket != null);

        if (serverSocket != null) {
            while (executeThread) {
                // create client socket
                try {
                    Socket socket = serverSocket.accept();
                    ModbusTcpIpSlaveDispatcher slave = new ModbusTcpIpSlaveDispatcher(modbusPalProject, socket);
                    slave.start();
                } catch (Exception ex) {
                    LOG.error(ex.getMessage(), ex);
                    if (Thread.interrupted()) {
                    }
                }
            }

            if (serverSocket.isClosed()) {
                try {
                    serverSocket.close();
                } catch (IOException ex) {
                    LOG.error(ex.getMessage(), ex);
                }
            }
            serverSocket = null;
        }

        LOG.info("ModbusTcpIpLink stopped.");
        listener.linkBroken();
        listener = null;
    }


    @Override
    public void startMaster(ModbusLinkListener l) throws IOException {
        listener = l;
    }


    @Override
    public void stopMaster() {
        for (Socket sock : clientSockets.values()) {
            if (sock != null) {
                try {
                    sock.close();
                } catch (IOException ex) {
                    LOG.error(ex.getMessage(), ex);
                }
            }
        }

        clientSockets.clear();
    }


    @Override
    public void execute(ModbusSlaveAddress dst, ModbusMasterRequest req, int timeout)
            throws IOException {
        // did I already open a connection for this slave ?
        Socket sock = clientSockets.get(dst);
        if (sock == null) {
            sock = new Socket(dst.getIpAddress(), tcpPort);
            clientSockets.put(dst, sock);
        }

        byte buffer[] = new byte[2048];

        // genete PDU of the request, start at offset 7
        // (leave room for MBAP header).
        int length = buildPDU(req, dst, buffer, 7);

        // create MBAP header for the request
        ModbusTools.setUint16(buffer, 0, clientTI); // transaction identifier on 2 bytes
        ModbusTools.setUint16(buffer, 2, 0); // protocol identifier on 2 bytes
        ModbusTools.setUint16(buffer, 4, 1 + length); //1 + PDU length
        ModbusTools.setUint8(buffer, 6, dst.getRtuAddress());

        // send request 
        sock.getOutputStream().write(buffer, 0, 7 + length);

        sock.setSoTimeout(timeout);

        // wait for reply
        int recv = sock.getInputStream().read(buffer);

        // rip MBAP header off
        processPDU(req, dst, buffer, 7, recv);
    }
}
