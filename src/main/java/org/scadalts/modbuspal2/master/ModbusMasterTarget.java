/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scadalts.modbuspal2.master;

import org.scadalts.modbuspal2.slave.ModbusSlaveAddress;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author JMC15
 */
public class ModbusMasterTarget
        extends DefaultMutableTreeNode {
    private String targetName;
    private ModbusSlaveAddress[] targetList;
    private String targetListText;

    public String getTargetName() {
        return targetName;
    }

    void setTargetName(String s) {
        targetName = s;
        setUserObject(targetName);
    }

    public List<ModbusSlaveAddress> getTargetList() {
        ArrayList<ModbusSlaveAddress> output = new ArrayList<ModbusSlaveAddress>();
        Collections.addAll(output, targetList);
        return output;
    }

    void setTargetList(ModbusSlaveAddress[] a) {
        targetList = a;
    }

    public String getTargetListAsText() {
        return targetListText;
    }

    void setTargetListAsText(String targetsAsString) {
        targetListText = targetsAsString;
    }
}
