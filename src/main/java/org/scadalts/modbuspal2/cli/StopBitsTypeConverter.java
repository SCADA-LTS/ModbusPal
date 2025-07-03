package org.scadalts.modbuspal2.cli;

import picocli.CommandLine;

public class StopBitsTypeConverter implements CommandLine.ITypeConverter<StopBitsType> {
    @Override
    public StopBitsType convert(String browser) throws Exception {
        return StopBitsType.valueOf(browser.toUpperCase());
    }
}