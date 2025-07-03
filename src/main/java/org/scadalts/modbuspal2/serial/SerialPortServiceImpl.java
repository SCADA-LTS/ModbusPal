package org.scadalts.modbuspal2.serial;


import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class SerialPortServiceImpl implements SerialPortService {

    public static final String SERIAL_PORT_NO_INITIALIZED = "SerialPort no initialized!";
    private static final Logger LOG = LogManager.getLogger(SerialPortServiceImpl.class);
    private final SerialPortParameters serialPortParameters;
    private final ReentrantReadWriteLock lock;
    private SerialPort serialPort;

    SerialPortServiceImpl(SerialPortParameters serialParameters) {
        this.serialPortParameters = serialParameters;
        this.lock = new ReentrantReadWriteLock();
    }

    @Override
    public void open() throws SerialPortException {
        this.lock.writeLock().lock();
        try {
            if (getSerialPort() == null || !getSerialPort().isOpen()) {
                SerialPort serialPortOpened = SerialPortUtils.openSerialPort(serialPortParameters);
                setSerialPort(serialPortOpened);
            }
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            throw new SerialPortException(ex);
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    @Override
    public void close() {
        this.lock.writeLock().lock();
        try {
            if (getSerialPort() == null) {
                LOG.warn(SERIAL_PORT_NO_INITIALIZED);
                return;
            }
            SerialPortUtils.close(getSerialPort());
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    @Override
    public InputStream getInputStream() {
        this.lock.readLock().lock();
        try {
            if (getSerialPort() == null) {
                LOG.warn(SERIAL_PORT_NO_INITIALIZED);
                return null;
            }
            return getSerialPort().getInputStream();
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            return null;
        } finally {
            this.lock.readLock().unlock();
        }
    }

    @Override
    public OutputStream getOutputStream() {
        this.lock.readLock().lock();
        try {
            if (getSerialPort() == null) {
                LOG.warn(SERIAL_PORT_NO_INITIALIZED);
                return null;
            }
            OutputStream outputStream = getSerialPort().getOutputStream();
            outputStream.flush();
            return outputStream;
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            return null;
        } finally {
            this.lock.readLock().unlock();
        }
    }

    @Override
    public boolean isOpen() {
        this.lock.readLock().lock();
        try {
            if (getSerialPort() == null) {
                LOG.warn(SERIAL_PORT_NO_INITIALIZED);
                return false;
            }
            return getSerialPort().isOpen();
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            return false;
        } finally {
            this.lock.readLock().unlock();
        }
    }

    @Override
    public boolean addDataListener(SerialPortDataListener listener) {
        return serialPort.addDataListener(listener);
    }

    @Override
    public SerialPortParameters getParameters() {
        return serialPortParameters;
    }

    private SerialPort getSerialPort() {
        return this.serialPort;
    }

    private void setSerialPort(SerialPort serialPort) {
        this.serialPort = serialPort;
    }
}
