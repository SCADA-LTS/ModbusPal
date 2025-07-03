package org.scadalts.modbuspal2.cli;

import picocli.CommandLine;

import java.io.File;

public class FileConverter implements CommandLine.ITypeConverter<File> {
    @Override
    public File convert(String browser) throws Exception {
        return new File(browser);
    }
}
