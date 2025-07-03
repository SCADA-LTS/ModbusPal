package org.scadalts.modbuspal2.cli;

import picocli.CommandLine;

public class ParityTypeConverter implements CommandLine.ITypeConverter<ParityType> {
    @Override
    public ParityType convert(String browser) throws Exception {
        return ParityType.valueOf(browser.toUpperCase());
    }
}