/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modbuspal.master;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import modbuspal.slave.ModbusSlaveAddress;

/**
 *
 * @author JMC15
 */
public class ModbusMasterTarget
extends DefaultMutableTreeNode
{
    private String targetName;
    private ModbusSlaveAddress[] targetList;
    private String targetListText;
    
    void setTargetName(String s) 
    {
        targetName = s;
        setUserObject(targetName);
    }

    public String getTargetName()
    {
        return targetName;
    }
    
    void setTargetList(ModbusSlaveAddress[] a) 
    {
        targetList = a;
    }
    
    public List<ModbusSlaveAddress> getTargetList()
    {
        ArrayList<ModbusSlaveAddress> output = new ArrayList<ModbusSlaveAddress>();
        Collections.addAll(output, targetList);
        return output;
    }

    void setTargetListAsText(String targetsAsString) 
    {
        targetListText = targetsAsString;
    }
    
    public String getTargetListAsText()
    {
        return targetListText;
    }
}
