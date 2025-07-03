package org.scadalts.modbuspal2.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.scadalts.modbuspal2.automation.Automation;
import org.scadalts.modbuspal2.master.ModbusMasterTask;
import org.scadalts.modbuspal2.slave.ModbusSlave;
import org.scadalts.modbuspal2.slave.ModbusSlaveAddress;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModbusPalListenerImpl implements ModbusPalListener {

    private static final Logger LOG = LogManager.getLogger(ModbusPalListenerImpl.class);
    Map<ModbusSlaveAddress, ModbusSlave> slaveList = new HashMap<>();
    List<Automation> automations = new ArrayList<>();

    ModbusPalProject modbusPalProject;

    public void setProject(ModbusPalProject project) {
        LOG.info("Set project " + project.getName() + "\r\n");
        //- - - - - - - - - - - - - - -
        // Clear existing project
        //- - - - - - - - - - - - - - -

        if (modbusPalProject != null) {
            modbusPalProject.removeModbusPalListener(this);
        }


        //- - - - - - - - - - - - - -
        // Register new project
        //- - - - - - - - - - - - - -

        ModbusPalProject old = modbusPalProject;
        modbusPalProject = project;
        modbusPalProject.addModbusPalListener(this);

        //- - - - - - - - - - - - - -

        for (ModbusSlave s : project.getModbusSlaves()) {
            modbusSlaveAdded(s);
        }

        //- - - - - - - - - - - - - -
        // Refresh list of automations
        //- - - - - - - - - - - - - -

        Automation[] automations = project.getAutomations();
        for (int i = 0; i < automations.length; i++) {
            automationAdded(automations[i], i);
        }
        LOG.info("[" + modbusPalProject.getName() + "] Project set\r\n");
    }

    @Override
    public void modbusSlaveAdded(ModbusSlave slave) {
        slaveList.put(slave.getSlaveId(), slave);
        slave.addModbusSlaveListener(new ModbusSlaveListenerImpl());
    }

    @Override
    public void modbusSlaveRemoved(ModbusSlave slave) {
        ModbusSlaveAddress slaveID = slave.getSlaveId();
        ModbusSlave panel = slaveList.get(slaveID);
        if (panel != null)
            slaveList.remove(panel.getSlaveId());
    }

    @Override
    public void automationAdded(Automation automation, int index) {
        automations.add(index, automation);
        automation.start();
    }

    @Override
    public void automationRemoved(Automation automation) {
        automation.stop();
        automations.remove(automation);
    }

    @Override
    public void pduProcessed() {

    }

    @Override
    public void pduException() {

    }

    @Override
    public void pduNotServiced() {

    }

    @Override
    public void modbusMasterTaskRemoved(ModbusMasterTask mmt) {

    }

    @Override
    public void modbusMasterTaskAdded(ModbusMasterTask mmt) {

    }
}
