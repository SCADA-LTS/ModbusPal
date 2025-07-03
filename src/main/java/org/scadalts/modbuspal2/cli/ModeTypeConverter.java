package org.scadalts.modbuspal2.cli;

import picocli.CommandLine;

public class ModeTypeConverter implements CommandLine.ITypeConverter<ModeType> {
    @Override
    public ModeType convert(String browser) throws Exception {
        return ModeType.valueOf(browser.toUpperCase());
    }
}