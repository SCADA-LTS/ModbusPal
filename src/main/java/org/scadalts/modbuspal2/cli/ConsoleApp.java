package org.scadalts.modbuspal2.cli;


import org.scadalts.modbuspal2.main.ModbusPalGui;
import picocli.CommandLine;

public class ConsoleApp {

    public static void main(String[] args) {

        CommandLine testCmd = new CommandLine(new ModbusPalGui());
        testCmd.execute(args);
    }
}
