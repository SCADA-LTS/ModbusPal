package org.scadalts.modbuspal2.cli;

import picocli.CommandLine;

public class DefaultOptions {

    @CommandLine.Option(names = {"-v", "--version"}, versionHelp = true)
    private boolean version;

    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true)
    private boolean help;
}