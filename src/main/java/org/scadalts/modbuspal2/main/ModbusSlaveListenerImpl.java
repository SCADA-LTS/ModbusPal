package org.scadalts.modbuspal2.main;

import org.scadalts.modbuspal2.slave.ModbusPduProcessor;
import org.scadalts.modbuspal2.slave.ModbusSlave;
import org.scadalts.modbuspal2.slave.ModbusSlaveListener;

public class ModbusSlaveListenerImpl implements ModbusSlaveListener {

    @Override
    public void modbusSlaveEnabled(ModbusSlave slave, boolean enabled) {
        slave.setEnabled(enabled);
    }

    @Override
    public void modbusSlaveImplChanged(ModbusSlave slave, int impl) {
        slave.setImplementation(impl);
    }

    @Override
    public void modbusSlaveNameChanged(ModbusSlave slave, String newName) {
        slave.setName(newName);
    }

    @Override
    public void modbusSlavePduProcessorChanged(ModbusSlave slave, byte functionCode, ModbusPduProcessor old, ModbusPduProcessor mspp) {
        slave.setPduProcessor(functionCode, mspp);
    }

    @Override
    public void modbusSlaveReplyDelayChanged(ModbusSlave slave, long min, long max) {
        slave.setReplyDelay(min, max);
    }

    @Override
    public void modbusSlaveErrorRatesChanged(ModbusSlave slave, float noReplyRate) {
        slave.setErrorRates(noReplyRate);
    }
}
