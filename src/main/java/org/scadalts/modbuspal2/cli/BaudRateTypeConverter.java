package org.scadalts.modbuspal2.cli;

import picocli.CommandLine;

public class BaudRateTypeConverter implements CommandLine.ITypeConverter<BaudRateType> {
    @Override
    public BaudRateType convert(String browser) throws Exception {
        return BaudRateType.valueOf(browser.toUpperCase());
    }
}