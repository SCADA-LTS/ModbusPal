package org.scadalts.modbuspal2.cli;

import org.scadalts.modbuspal2.link.ModbusLink;
import org.scadalts.modbuspal2.link.ModbusReplayLink;
import org.scadalts.modbuspal2.link.ModbusSerialLink;
import org.scadalts.modbuspal2.link.ModbusTcpIpLink;
import org.scadalts.modbuspal2.main.ModbusPalProject;
import org.scadalts.modbuspal2.serial.SerialPortParameters;

import java.io.File;
import java.util.function.BiFunction;

public enum ModeType {

    SERIAL {
        @Override
        public BiFunction<ModbusPalProject, SerialPortParameters, ModbusLink> createLink() {
            return (a, b) -> {
                try {
                    return new ModbusSerialLink(a, b);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            };
        }

        @Override
        public SerialPortParameters getArg(ModbusPal2Options modbusPalOptions) {
            return ModbusSerialLink.create(modbusPalOptions.getSerialPortId(), modbusPalOptions.getSerialBaudRate().getRate(),
                    modbusPalOptions.getSerialParity().getCode(), modbusPalOptions.getSerialStopBits().getCode(),
                    modbusPalOptions.isSerialXonXoff(), modbusPalOptions.isSerialRtsCts());
        }

    }, TCP {
        @Override
        public BiFunction<ModbusPalProject, Integer, ModbusLink> createLink() {
            return (a, b) -> {
                try {
                    return new ModbusTcpIpLink(a, b);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            };
        }

        @Override
        public Integer getArg(ModbusPal2Options modbusPalOptions) {
            return modbusPalOptions.getTcpPortNumber();
        }

    }, REPLAY {
        @Override
        public BiFunction<ModbusPalProject, File, ModbusLink> createLink() {
            return (a, b) -> {
                try {
                    return new ModbusReplayLink(a, b);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            };
        }

        @Override
        public File getArg(ModbusPal2Options modbusPalOptions) {
            return modbusPalOptions.getRecordFile();
        }
    };

    public abstract <T> BiFunction<ModbusPalProject, T, ModbusLink> createLink();

    public abstract <T> T getArg(ModbusPal2Options modbusPalOptions);
}
