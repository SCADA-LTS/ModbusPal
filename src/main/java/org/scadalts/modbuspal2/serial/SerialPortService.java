package org.scadalts.modbuspal2.serial;


import com.fazecast.jSerialComm.SerialPortDataListener;

import java.io.InputStream;
import java.io.OutputStream;

public interface SerialPortService extends AutoCloseable {

    static SerialPortService newService(SerialPortParameters serialParameters) {
        return new SerialPortServiceImpl(serialParameters);
    }

    @Override
    void close();

    void open() throws SerialPortException;

    InputStream getInputStream();

    OutputStream getOutputStream();

    boolean addDataListener(SerialPortDataListener listener);

    boolean isOpen();

    SerialPortParameters getParameters();
}
