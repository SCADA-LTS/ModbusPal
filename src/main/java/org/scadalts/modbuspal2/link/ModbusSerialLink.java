/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.scadalts.modbuspal2.link;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.scadalts.modbuspal2.main.ModbusPalProject;
import org.scadalts.modbuspal2.master.ModbusMasterRequest;
import org.scadalts.modbuspal2.serial.SerialPortException;
import org.scadalts.modbuspal2.serial.SerialPortParameters;
import org.scadalts.modbuspal2.serial.SerialPortService;
import org.scadalts.modbuspal2.serial.SerialPortUtils;
import org.scadalts.modbuspal2.slave.ModbusSlaveAddress;
import org.scadalts.modbuspal2.toolkit.ModbusTools;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The serial link waits for incoming requests from a COM port
 *
 * @author nnovic
 */
public class ModbusSerialLink
        extends ModbusSlaveProcessor
        implements ModbusLink, SerialPortDataListener {

    /**
     * identifier to specify that there is no parity for the serial communication
     */
    public static final int PARITY_NONE = 0;
    /**
     * identifier to specify that the odd parity must be used for the serial communication
     */
    public static final int PARITY_ODD = 1;
    /**
     * identifier to specify that the even parity must be used for the serial communication
     */
    public static final int PARITY_EVEN = 2;
    public static final int STOP_BITS_1 = 0;
    public static final int STOP_BITS_1_5 = 1;
    public static final int STOP_BITS_2 = 2;
    private static final Logger LOG = LogManager.getLogger(ModbusSerialLink.class);
    private static ArrayList<SerialPort> commPorts = new ArrayList<>();
    private int serialStopBits;
    private SerialPortService serialPort;
    private int baudrate;
    private InputStream input;
    private OutputStream output;
    private boolean executeThread = false;
    private Thread serverThread;
    private int serialParity;
    private int flowControl;
    private ModbusLinkListener listener = null;
    /**
     * Creates a new instance of ModbusSerialLink.
     *
     * @param mpp     the modbuspal project that holds the slaves information
     * @param index   index of the COM port to sue for communication
     * @param speed   baudrate of the COM port
     * @param parity  parity of the communication
     * @param xonxoff enables or disables XON/XOFF
     * @param rtscts  enables or disables RTS/CTS
     * @throws ClassCastException
     */
    public ModbusSerialLink(ModbusPalProject mpp, SerialPortParameters serialPortParameters)
            throws ClassCastException {
        super(mpp);
        baudrate = serialPortParameters.getBaudRate();
        serialParity = serialPortParameters.getParity();
        serialStopBits = serialPortParameters.getStopBits();
        flowControl = SerialPort.FLOW_CONTROL_DISABLED | serialPortParameters.getFlowControlIn() | serialPortParameters.getFlowControlOut();
        serialPort = SerialPortService.newService(serialPortParameters);
    }

    /**
     * This method will check that the specified com port actually exists.
     *
     * @param comId a string containing a COM port name.
     * @return true if the COM port exists.
     */
    public static boolean exists(String comId) {
        for (SerialPort commPort : commPorts) {
            if (commPort.getSystemPortName().equals(comId)) {
                return true;
            }
        }
        return false;
    }

    public static List<SerialPort> getSerialPorts() {
        return Stream.of(SerialPortUtils.getCommPorts()).collect(Collectors.toList());
    }

    /**
     * Returns the list of available COM ports on the host system.
     *
     * @return list of available COM ports
     */
    public static CommPortList getListOfCommPorts() {
        return new CommPortList();
    }

    /**
     * Setup the RXTX library and scan the available COM ports
     */
    public static boolean enumerate() {
        try {
            SerialPort[] portList = SerialPortUtils.getCommPorts();
            for (SerialPort port : portList) {
                LOG.info("Found: " + port.getSystemPortName());
                commPorts.add(port);
            }
            return true;
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            return false;
        }
    }

    public static SerialPortParameters create(String cmmName, int speed, int parity,
                                              int stopBits, boolean xonxoff, boolean rtscts) throws ClassCastException {
        int baudrate = speed;
        int serialParity;
        int serialStopBits;
        int flowControl = SerialPort.FLOW_CONTROL_DISABLED;

        switch (parity) {
            case PARITY_NONE:
                serialParity = SerialPort.NO_PARITY;
                break;
            case PARITY_ODD:
                serialParity = SerialPort.ODD_PARITY;
                break;
            default:
            case PARITY_EVEN:
                serialParity = SerialPort.EVEN_PARITY;
                break;
        }

        switch (stopBits) {
            default:
            case STOP_BITS_1:
                serialStopBits = SerialPort.ONE_STOP_BIT;
                break;
            case STOP_BITS_1_5:
                serialStopBits = SerialPort.ONE_POINT_FIVE_STOP_BITS;
                break;
            case STOP_BITS_2:
                serialStopBits = SerialPort.TWO_STOP_BITS;
                break;
        }

        //if( rtscts )
        //{
        //    flowControl |= SerialPort.FLOW_CONTROL_RTS_ENABLED;
        //     flowControl |= SerialPort.FLOW_CONTROL_CTS_ENABLED;
        //}

        if (xonxoff) {
            return SerialPortParameters.newParameters("MODBUSPAL", cmmName, baudrate,
                    SerialPort.FLOW_CONTROL_XONXOFF_IN_ENABLED,
                    SerialPort.FLOW_CONTROL_XONXOFF_OUT_ENABLED,
                    8, serialStopBits, serialParity, 3000);
        } else {
            return SerialPortParameters.newParameters("MODBUSPAL", cmmName, baudrate,
                    SerialPort.FLOW_CONTROL_DISABLED,
                    SerialPort.FLOW_CONTROL_DISABLED,
                    8, serialStopBits, serialParity, 3000);
        }
    }

    static int computeCRC(byte[] buffer, int offset, int length) {
        // Load a 16–bit register with FFFF hex (all 1’s). This is the CRC
        // register.
        int CRC = 0xFFFF;

        for (int i = 0; i < length; i++) {
            // Exclusive OR the first 8–bit byte of the message with the
            // low–order byte of the 16–bit CRC register, putting the result
            // in the CRC register.
            int b = buffer[offset + i] & 0xFF;
            CRC = (CRC ^ b) & 0xFFFF;

            for (int j = 0; j < 8; j++) {
                int LSB = CRC & 1;
                CRC = (CRC >> 1);
                if (LSB == 1) {
                    CRC = (CRC ^ 0xA001) & 0xFFFF;
                }
            }
        }
        return CRC;
    }

    @Override
    public int getListeningEvents() {
        return -1;
    }

    @Override
    public void start(ModbusLinkListener l)
            throws IOException {
        listener = l;


        try {
            serialPort.open();
        } catch (SerialPortException e) {
            throw new RuntimeException(e);
        }
        input = serialPort.getInputStream();
        output = serialPort.getOutputStream();
        serialPort.addDataListener(this);
        LOG.info("Connected to com port");
        executeThread = true;
        serverThread = new Thread(this, "serial link");
        serverThread.start();
    }


    @Override
    public void stop() {
        executeThread = false;
        serverThread.interrupt();

        try {
            input.close();
        } catch (IOException ex) {
            LOG.error(ex.getMessage(), ex);
        }

        try {
            output.close();
        } catch (IOException ex) {
            LOG.error(ex.getMessage(), ex);
        }

        serialPort.close();

        try {
            serverThread.join();
        } catch (InterruptedException ex) {
            LOG.error(ex.getMessage(), ex);
        } finally {
            serverThread = null;
        }
    }

    @Override
    public void serialEvent(SerialPortEvent arg0) {
        synchronized (input) {
            input.notify();
        }
    }

    @Override
    public void run() {
        byte buffer[] = new byte[256];
        System.out.println("Start ModbusSerialLink");

        while (executeThread == true) {
            try {
                // wait until a notification is issued by the SerialEvent
                // callback
                synchronized (input) {
                    input.wait(1000);
                }

                // if some data is available then:
                if (input.available() >= 1) {
                    // read all available data
                    int totalLen = input.read(buffer);

                    // read slave address (it is the first byte)
                    int slaveID = ModbusTools.getUint8(buffer, 0);

                    // read crc value (located in the last two bytes
                    int crcLSB = ModbusTools.getUint8(buffer, totalLen - 2);
                    int crcMSB = ModbusTools.getUint8(buffer, totalLen - 1);
                    int receivedCRC = crcMSB * 256 + crcLSB;

                    // compute crc between slave address (included) and crc (excluded)
                    int computedCRC = computeCRC(buffer, 0, totalLen - 2);

                    int pduLength = totalLen - 3; // 1 for slave address, and 2 for CRC

                    // if CRC are ok, then process the pdu
                    if (receivedCRC == computedCRC) {
                        //System.out.println("read "+ totalLen + " bytes");
                        pduLength = processPDU(new ModbusSlaveAddress(slaveID), buffer, 1, pduLength);
                    } else {
                        // handle CRC error with exception code
                        pduLength = makeExceptionResponse(XC_SLAVE_DEVICE_FAILURE, buffer, 1);
                    }

                    // if the output pdu length is positive, then send the content
                    // of the buffer
                    if (pduLength > 0) {
                        totalLen = 1 + pduLength + 2; // 1 for slave address, and 2 for CRC

                        // compute crc of outgoing reply
                        int outputCRC = computeCRC(buffer, 0, totalLen - 2);

                        // low order byte of the CRC must be transmitted first
                        buffer[totalLen - 2] = (byte) (outputCRC & 0xFF);
                        buffer[totalLen - 1] = (byte) ((outputCRC >> 8) & 0xFF);

                        // write content of buffer into the output stream
                        output.write(buffer, 0, totalLen);
                        output.flush();
                    }
                }
            } catch (InterruptedException ex) {
                // not an error
            } catch (IOException ex) {
                LOG.error(ex.getMessage(), ex);
                if (Thread.interrupted()) {
                    //LOG.error(ex.getMessage(), ex);
                }
            }
        }

        LOG.info("Stop ModbusSerialLink");
        listener.linkBroken();
        listener = null;
    }

    @Override
    public void startMaster(ModbusLinkListener l) throws IOException {
        start(l);
    }

    @Override
    public void stopMaster() {
        try {
            input.close();
        } catch (IOException ex) {
            LOG.error(ex.getMessage(), ex);
        }

        try {
            output.close();
        } catch (IOException ex) {
            LOG.error(ex.getMessage(), ex);
        }

        serialPort.close();
    }

    @Override
    public void execute(ModbusSlaveAddress dst, ModbusMasterRequest req, int timeout)
            throws IOException {
        byte buffer[] = new byte[2048];

        // genete PDU of the request, start at offset 1
        // (leave room for header and footer).
        int length = buildPDU(req, dst, buffer, 1);

        // prepend slave address
        ModbusTools.setUint8(buffer, 0, dst.getRtuAddress());

        // compute CRC
        int totalLen = 1 + length + 2; // 1 for slave address, and 2 for CRC
        int outputCRC = computeCRC(buffer, 0, totalLen - 2);
        buffer[totalLen - 2] = (byte) (outputCRC & 0xFF);
        buffer[totalLen - 1] = (byte) ((outputCRC >> 8) & 0xFF);

        // send request
        output.write(buffer, 0, totalLen);
        output.flush();

        // wait for reply
        synchronized (input) {
            try {
                input.wait(timeout);
            } catch (InterruptedException ex) {
                LOG.error(ex.getMessage(), ex);
            }
        }

        // if some data is available then:
        if (input.available() >= 3) {
            // read all available data
            totalLen = input.read(buffer);

            // read slave address (it is the first byte)
            int slaveID = ModbusTools.getUint8(buffer, 0);

            // read crc value (located in the last two bytes
            int crcLSB = ModbusTools.getUint8(buffer, totalLen - 2);
            int crcMSB = ModbusTools.getUint8(buffer, totalLen - 1);
            int receivedCRC = crcMSB * 256 + crcLSB;

            // compute crc between slave address (included) and crc (excluded)
            int computedCRC = computeCRC(buffer, 0, totalLen - 2);

            int pduLength = totalLen - 3; // 1 for slave address, and 2 for CRC

            // if CRC are ok, then process the pdu
            if (receivedCRC == computedCRC) {
                //System.out.println("read "+ totalLen + " bytes");
                //pduLength = processPDU(new ModbusSlaveAddress(slaveID), buffer, 1, pduLength);
                processPDU(req, dst, buffer, 1, totalLen - 3);
            } else {
                // handle CRC error with exception code
                //pduLength = makeExceptionResponse(XC_SLAVE_DEVICE_FAILURE, buffer, 1);
                LOG.error("CRC Error");
            }
        }
    }

    /**
     *
     */
    public static class CommPortList
            implements ComboBoxModel {
        private Object selectedItem;

        CommPortList() {
            if (commPorts.size() >= 1) {
                selectedItem = commPorts.get(0).getSystemPortName();
            }
        }

        @Override
        public int getSize() {
            return commPorts.size();
        }

        @Override
        public Object getElementAt(int index) {
            return commPorts.get(index).getSystemPortName();
        }

        @Override
        public void addListDataListener(ListDataListener l) {
        }

        @Override
        public void removeListDataListener(ListDataListener l) {
        }

        @Override
        public Object getSelectedItem() {
            return selectedItem;
        }

        @Override
        public void setSelectedItem(Object anItem) {
            selectedItem = anItem;
        }
    }
}
