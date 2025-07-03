/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.scadalts.modbuspal2.link;

import org.scadalts.modbuspal2.main.ModbusPalProject;
import org.scadalts.modbuspal2.master.ModbusMasterRequest;
import org.scadalts.modbuspal2.recorder.ModbusPalRecord;
import org.scadalts.modbuspal2.slave.ModbusSlaveAddress;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The replay link reproduces the incoming requests from a previously recorded
 * session
 *
 * @author nnovic
 */
public class ModbusReplayLink
        extends ModbusSlaveProcessor
        implements ModbusLink {
    private File recordFile = null;
    private boolean executeThread = false;
    private Thread serverThread;
    private ModbusLinkListener listener = null;

    /**
     * Creates a new instance of ModbusReplayLink.
     *
     * @param mpp    the project this link should run
     * @param source the file where the data to replay was recorded.
     */
    public ModbusReplayLink(ModbusPalProject mpp, File source) {
        super(mpp);
        recordFile = source;
    }

    @Override
    public void start(ModbusLinkListener l)
            throws IOException {
        executeThread = true;
        serverThread = new Thread(this, "replay");
        listener = l;
        serverThread.start();
    }


    public void stop() {
        executeThread = false;
        serverThread.interrupt();
        
       /* try
        {
            input.close();
        } 
        catch (IOException ex)
        {
            Logger.getLogger(ModbusReplayLink.class.getName()).log(Level.SEVERE, null, ex);
        }*/

        try {
            serverThread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(ModbusTcpIpLink.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            serverThread = null;
        }
    }

    public void run() {
        System.out.println("Start ModbusReplayLink");

        BufferedReader input = null;
        try {
            FileReader reader = new FileReader(recordFile);
            input = new BufferedReader(reader);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ModbusReplayLink.class.getName()).log(Level.SEVERE, null, ex);
        }

        long prev_timestamp = 0;

        while (executeThread == true) {
            try {
                String line = input.readLine();

                // if some data is available then parse it:
                if (line == null) {
                    executeThread = false;
                } else {
                    ModbusPalRecord record = new ModbusPalRecord(line);

                    if (record.getType() == ModbusPalRecord.RECORD_IN) {
                        long timestamp = record.getTimestamp();
                        System.out.println("sleep " + (timestamp - prev_timestamp) + " ms");
                        if (prev_timestamp != 0) {
                            Thread.sleep(timestamp - prev_timestamp);
                        }
                        prev_timestamp = timestamp;

                        ModbusSlaveAddress slaveId = record.getSlaveID();
                        byte[] data = record.getData();
                        int pduLength = processPDU(slaveId, data, 0, record.getDataLength());
                    }
                }
            } catch (Exception ex) {
                if (Thread.interrupted() == false) {
                    Logger.getLogger(ModbusReplayLink.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        try {
            // close the file reader
            input.close();
        } catch (IOException ex) {
            Logger.getLogger(ModbusReplayLink.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Stop ModbusReplayLink");
        listener.linkBroken();
        listener = null;
    }


    @Override
    public void startMaster(ModbusLinkListener l)
            throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void stopMaster() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void execute(ModbusSlaveAddress dst, ModbusMasterRequest req, int timeout) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
