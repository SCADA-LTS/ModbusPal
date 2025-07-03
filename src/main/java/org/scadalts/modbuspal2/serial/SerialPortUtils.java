package org.scadalts.modbuspal2.serial;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortIOException;
import com.fazecast.jSerialComm.SerialPortInvalidPortException;


public class SerialPortUtils {

    private static final String TIMEOUT_READ_KEY = "comm.serial.timeout.read";
    private static final String TIMEOUT_WRITE_KEY = "comm.serial.timeout.write";
    private static final String TIMEOUT_MODE_KEY = "comm.serial.timeout.mode";
    private static final String SLEEP_KEY = "comm.serial.sleep";

    private SerialPortUtils() {
    }

    public static SerialPort[] getCommPorts() {
        return SerialPort.getCommPorts();
    }

    public static SerialPort openSerialPort(SerialPortParameters serialPortParameters) throws SerialPortException {
        SerialPort serialPort = null;
        try {
            serialPort = getCommPort(serialPortParameters.getCommPortId());
            serialPort.setComPortParameters(serialPortParameters.getBaudRate(), serialPortParameters.getDataBits(),
                    serialPortParameters.getStopBits(), serialPortParameters.getParity());
            serialPort.setFlowControl(serialPortParameters.getFlowControlIn() | serialPortParameters.getFlowControlOut());
            serialPort.setComPortTimeouts(getTimeoutMode(), getTimeoutRead(serialPortParameters),
                    getTimeoutWrite(serialPortParameters));
            if (serialPort.openPort(getSleepMs()))
                return serialPort;
            throw new SerialPortIOException("Failed open port: " + serialPortParameters);
        } catch (Exception var4) {
            close(serialPort);
            throw new SerialPortException(var4);
        }
    }

    public static void close(SerialPort serialPort) {
        if (serialPort != null) {
            serialPort.closePort();
        }

    }

    static SerialPort getCommPort(String port) {
        SerialPort[] serialPorts = SerialPort.getCommPorts();
        for (SerialPort serialPort : serialPorts) {
            String portName = serialPort.getSystemPortName();
            if (portName.equals(port))
                return serialPort;
        }
        throw new SerialPortInvalidPortException("Invalid port: " + port, null);
    }

    static int getTimeoutRead(SerialPortParameters serialPortParameters) {
        return Math.max(getTimeoutRead(), serialPortParameters.getTimeout());
    }

    static int getTimeoutWrite(SerialPortParameters serialPortParameters) {
        return Math.max(getTimeoutWrite(), serialPortParameters.getTimeout());
    }

    private static int getTimeoutRead() {
        return 500;
    }

    private static int getTimeoutWrite() {
        return 500;
    }

    private static int getTimeoutMode() {
        return 0;
    }

    private static int getSleepMs() {
        return 0;
    }
}