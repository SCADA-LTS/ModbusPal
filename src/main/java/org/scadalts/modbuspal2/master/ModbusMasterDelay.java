/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scadalts.modbuspal2.master;

/**
 * @author JMC15
 */
public class ModbusMasterDelay
        extends ModbusMasterRequest {
    private int delayMs = 0;

    public static ModbusMasterDelay getDelay(int milliseconds) {
        ModbusMasterDelay output = new ModbusMasterDelay();
        output.delayMs = milliseconds;

        String caption = String.format("Delay (%d milliseconds)", milliseconds);
        output.setUserObject(caption);
        return output;
    }

    public int getDelay() {
        return delayMs;
    }
}
