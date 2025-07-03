package org.scadalts.modbuspal2.cli;

import lombok.Getter;
import lombok.ToString;
import picocli.CommandLine;

import java.io.File;


@Getter
@ToString
public class ModbusPal2Options extends DefaultOptions {

    @CommandLine.Option(names = {"-f", "--project-file", "-loadFile"}, converter = FileConverter.class, defaultValue = "modbuspal_config.xmpp")
    private File projectFile;

    @CommandLine.Option(names = {"-rf", "--record-file"}, converter = FileConverter.class, defaultValue = "record_file.txt")
    private File recordFile;

    @CommandLine.Option(names = {"-sx", "--serial-xon-xoff"}, defaultValue = "false")
    private boolean serialXonXoff;

    @CommandLine.Option(names = {"-sc", "--serial-rts-cts"}, defaultValue = "false")
    private boolean serialRtsCts;

    @CommandLine.Option(names = {"-si", "--serial-port-id"}, defaultValue = "ttyS0")
    private String serialPortId;

    @CommandLine.Option(names = {"-sr", "--serial-baud-rate"}, converter = BaudRateTypeConverter.class, defaultValue = "_9600")
    private BaudRateType serialBaudRate;

    @CommandLine.Option(names = {"-sp", "--serial-parity"}, converter = ParityTypeConverter.class, defaultValue = "NONE")
    private ParityType serialParity;

    @CommandLine.Option(names = {"-sb", "--serial-stop-bits"}, converter = StopBitsTypeConverter.class, defaultValue = "ONE")
    private StopBitsType serialStopBits;

    @CommandLine.Option(names = {"-tp", "--tcp-port-number"}, defaultValue = "502")
    private int tcpPortNumber;

    @CommandLine.Option(names = {"-ng", "--no-gui", "-noGui"}, defaultValue = "false")
    private boolean noGui;

    @CommandLine.Option(names = {"-m", "--mode-type"}, converter = ModeTypeConverter.class, defaultValue = "TCP")
    private ModeType modeType;

    @CommandLine.Option(names = {"-mm", "--master-mode"}, defaultValue = "false")
    private boolean masterMode;
}
