/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scadalts.modbuspal2.master;

import org.scadalts.modbuspal2.main.ModbusConst;
import org.scadalts.modbuspal2.toolkit.NumericTextField;

/**
 * @author jmc15
 */
public class ModbusRequestDialog
        extends javax.swing.JDialog {
    private boolean isOK = false;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane actionSelectorTabbedPane;
    private javax.swing.JButton cancelButton;
    private javax.swing.JPanel delayPanel;
    private NumericTextField delayTextField;
    private javax.swing.JComboBox implementationComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JSpinner jSpinner2;
    private javax.swing.JButton okButton;
    private NumericTextField x01_quantityToReadTextField;
    private javax.swing.JPanel x01_readCoilsPanel;
    private NumericTextField x01_startingAddressTextField;
    private NumericTextField x02_quantityToReadTextField;
    private javax.swing.JPanel x02_readDiscreteInputsPanel;
    private NumericTextField x02_startingAddressTextField;
    private NumericTextField x03_quantityToReadTextField;
    private javax.swing.JPanel x03_readHoldingRegistersPanel;
    private NumericTextField x03_startingAddressTextField;
    private NumericTextField x05_outputAddressTextField;
    private javax.swing.JPanel x05_writeSingleCoilPanel;
    private NumericTextField x06_registerAddressTextField;
    private javax.swing.JPanel x06_writeSingleRegisterPanel;
    private NumericTextField x0F_quantityOfOutputsTextField;
    private NumericTextField x0F_startingAddressTextField;
    private javax.swing.JPanel x0F_writeMultipleCoilsPanel;
    private NumericTextField x10_quantityToReadTextField;
    private NumericTextField x10_startingAddressTextField;
    private javax.swing.JPanel x10_writeHoldingRegistersPanel;
    private NumericTextField x17_quantityToReadTextField;
    private NumericTextField x17_quantityToWriteTextField;
    private NumericTextField x17_readStartingAddressTextField;
    private javax.swing.JPanel x17_readWriteMultipleRegistersPanel;
    private NumericTextField x17_writeStartingAddressTextField;
    /**
     * Creates new form ModbusRequestDialog
     */
    public ModbusRequestDialog() {
        setModalityType(ModalityType.DOCUMENT_MODAL);
        initComponents();
    }

    ModbusMasterRequest getRequest() {
        if (actionSelectorTabbedPane.getSelectedComponent() == x01_readCoilsPanel) {
            int startingAddress = x01_startingAddressTextField.getInteger();
            int quantityToRead = x01_quantityToReadTextField.getInteger();
            return ModbusMasterRequest.getReadCoilsRequest(startingAddress, quantityToRead);
        } else if (actionSelectorTabbedPane.getSelectedComponent() == x02_readDiscreteInputsPanel) {
            int startingAddress = x02_startingAddressTextField.getInteger();
            int quantityToRead = x02_quantityToReadTextField.getInteger();
            return ModbusMasterRequest.getReadDiscreteInputsRequest(startingAddress, quantityToRead);
        } else if (actionSelectorTabbedPane.getSelectedComponent() == x03_readHoldingRegistersPanel) {
            int startingAddress = x03_startingAddressTextField.getInteger();
            int quantityOfRegisters = x03_quantityToReadTextField.getInteger();
            return ModbusMasterRequest.getReadHoldingRegistersRequest(startingAddress, quantityOfRegisters);
        } else if (actionSelectorTabbedPane.getSelectedComponent() == x05_writeSingleCoilPanel) {
            int outputAddress = x05_outputAddressTextField.getInteger();
            return ModbusMasterRequest.getWriteSingleCoilRequest(outputAddress);
        } else if (actionSelectorTabbedPane.getSelectedComponent() == x06_writeSingleRegisterPanel) {
            int registerAddress = x06_registerAddressTextField.getInteger();
            return ModbusMasterRequest.getWriteSingleRegisterRequest(registerAddress);
        } else if (actionSelectorTabbedPane.getSelectedComponent() == x0F_writeMultipleCoilsPanel) {
            int startingAddress = x0F_startingAddressTextField.getInteger();
            int quantityOfOutputs = x0F_quantityOfOutputsTextField.getInteger();
            return ModbusMasterRequest.getWriteMultipleCoilsRequest(startingAddress, quantityOfOutputs);
        } else if (actionSelectorTabbedPane.getSelectedComponent() == x10_writeHoldingRegistersPanel) {
            int startingAddress = x10_startingAddressTextField.getInteger();
            int quantityOfRegisters = x10_quantityToReadTextField.getInteger();
            return ModbusMasterRequest.getWriteMultipleRegistersRequest(startingAddress, quantityOfRegisters);
        } else if (actionSelectorTabbedPane.getSelectedComponent() == x17_readWriteMultipleRegistersPanel) {
            int readStartingAddress = x17_readStartingAddressTextField.getInteger();
            int quantityToRead = x17_quantityToReadTextField.getInteger();
            int writeStartingAddress = x17_writeStartingAddressTextField.getInteger();
            int quantityToWrite = x17_quantityToWriteTextField.getInteger();
            return ModbusMasterRequest.getReadWriteMultipleRegistersRequest(
                    readStartingAddress, quantityToRead,
                    writeStartingAddress, quantityToWrite);
        } else if (actionSelectorTabbedPane.getSelectedComponent() == delayPanel) {
            int delayInMilliseconds = delayTextField.getInteger();
            return ModbusMasterDelay.getDelay(delayInMilliseconds);
        }

        return null;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        actionSelectorTabbedPane = new javax.swing.JTabbedPane();
        x01_readCoilsPanel = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        x01_startingAddressTextField = new NumericTextField();
        jLabel11 = new javax.swing.JLabel();
        x01_quantityToReadTextField = new NumericTextField();
        x02_readDiscreteInputsPanel = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        x02_startingAddressTextField = new NumericTextField();
        jLabel13 = new javax.swing.JLabel();
        x02_quantityToReadTextField = new NumericTextField();
        x03_readHoldingRegistersPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        x03_startingAddressTextField = new NumericTextField();
        jLabel1 = new javax.swing.JLabel();
        x03_quantityToReadTextField = new NumericTextField();
        x05_writeSingleCoilPanel = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        x05_outputAddressTextField = new NumericTextField();
        x06_writeSingleRegisterPanel = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        x06_registerAddressTextField = new NumericTextField();
        x0F_writeMultipleCoilsPanel = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        x0F_startingAddressTextField = new NumericTextField();
        jLabel17 = new javax.swing.JLabel();
        x0F_quantityOfOutputsTextField = new NumericTextField();
        x10_writeHoldingRegistersPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        x10_startingAddressTextField = new NumericTextField();
        jLabel4 = new javax.swing.JLabel();
        x10_quantityToReadTextField = new NumericTextField();
        x17_readWriteMultipleRegistersPanel = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        x17_readStartingAddressTextField = new NumericTextField();
        jLabel19 = new javax.swing.JLabel();
        x17_quantityToReadTextField = new NumericTextField();
        jLabel20 = new javax.swing.JLabel();
        x17_writeStartingAddressTextField = new NumericTextField();
        jLabel21 = new javax.swing.JLabel();
        x17_quantityToWriteTextField = new NumericTextField();
        delayPanel = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        delayTextField = new NumericTextField();
        jLabel10 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        implementationComboBox = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jSpinner2 = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Master Request");
        setMinimumSize(new java.awt.Dimension(300, 300));

        x01_readCoilsPanel.setLayout(new java.awt.GridBagLayout());

        jLabel9.setText("Starting address:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 2, 2);
        x01_readCoilsPanel.add(jLabel9, gridBagConstraints);

        x01_startingAddressTextField.setColumns(5);
        x01_startingAddressTextField.setText("numericTextField1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 2, 5);
        x01_readCoilsPanel.add(x01_startingAddressTextField, gridBagConstraints);

        jLabel11.setText("Quantity of coils :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 5, 2);
        x01_readCoilsPanel.add(jLabel11, gridBagConstraints);
        jLabel11.getAccessibleContext().setAccessibleName("Quantity of coils :");

        x01_quantityToReadTextField.setColumns(5);
        x01_quantityToReadTextField.setText("numericTextField1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 5, 5);
        x01_readCoilsPanel.add(x01_quantityToReadTextField, gridBagConstraints);

        actionSelectorTabbedPane.addTab("Read Coils", x01_readCoilsPanel);

        x02_readDiscreteInputsPanel.setLayout(new java.awt.GridBagLayout());

        jLabel12.setText("Starting address:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 2, 2);
        x02_readDiscreteInputsPanel.add(jLabel12, gridBagConstraints);

        x02_startingAddressTextField.setColumns(5);
        x02_startingAddressTextField.setText("numericTextField1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 2, 5);
        x02_readDiscreteInputsPanel.add(x02_startingAddressTextField, gridBagConstraints);

        jLabel13.setText("Quantity of inputs :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 5, 2);
        x02_readDiscreteInputsPanel.add(jLabel13, gridBagConstraints);

        x02_quantityToReadTextField.setColumns(5);
        x02_quantityToReadTextField.setText("numericTextField1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 5, 5);
        x02_readDiscreteInputsPanel.add(x02_quantityToReadTextField, gridBagConstraints);

        actionSelectorTabbedPane.addTab("Read Discrete Inputs", x02_readDiscreteInputsPanel);

        x03_readHoldingRegistersPanel.setLayout(new java.awt.GridBagLayout());

        jLabel2.setText("Starting address:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 2, 2);
        x03_readHoldingRegistersPanel.add(jLabel2, gridBagConstraints);

        x03_startingAddressTextField.setColumns(5);
        x03_startingAddressTextField.setText("numericTextField1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 2, 5);
        x03_readHoldingRegistersPanel.add(x03_startingAddressTextField, gridBagConstraints);

        jLabel1.setText("Quantity of registers :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 5, 2);
        x03_readHoldingRegistersPanel.add(jLabel1, gridBagConstraints);

        x03_quantityToReadTextField.setColumns(5);
        x03_quantityToReadTextField.setText("numericTextField1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 5, 5);
        x03_readHoldingRegistersPanel.add(x03_quantityToReadTextField, gridBagConstraints);

        actionSelectorTabbedPane.addTab("Read Holding registers", x03_readHoldingRegistersPanel);

        x05_writeSingleCoilPanel.setLayout(new java.awt.GridBagLayout());

        jLabel14.setText("Output address:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 2, 2);
        x05_writeSingleCoilPanel.add(jLabel14, gridBagConstraints);

        x05_outputAddressTextField.setColumns(5);
        x05_outputAddressTextField.setText("numericTextField1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 2, 5);
        x05_writeSingleCoilPanel.add(x05_outputAddressTextField, gridBagConstraints);

        actionSelectorTabbedPane.addTab("Write Single Coil", x05_writeSingleCoilPanel);

        x06_writeSingleRegisterPanel.setLayout(new java.awt.GridBagLayout());

        jLabel15.setText("Register address:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 2, 2);
        x06_writeSingleRegisterPanel.add(jLabel15, gridBagConstraints);

        x06_registerAddressTextField.setColumns(5);
        x06_registerAddressTextField.setText("numericTextField1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 2, 5);
        x06_writeSingleRegisterPanel.add(x06_registerAddressTextField, gridBagConstraints);

        actionSelectorTabbedPane.addTab("Write Single Register", x06_writeSingleRegisterPanel);

        x0F_writeMultipleCoilsPanel.setLayout(new java.awt.GridBagLayout());

        jLabel16.setText("Starting address:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 2, 2);
        x0F_writeMultipleCoilsPanel.add(jLabel16, gridBagConstraints);

        x0F_startingAddressTextField.setColumns(5);
        x0F_startingAddressTextField.setText("numericTextField1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 2, 5);
        x0F_writeMultipleCoilsPanel.add(x0F_startingAddressTextField, gridBagConstraints);

        jLabel17.setText("Quantity of outputs :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 5, 2);
        x0F_writeMultipleCoilsPanel.add(jLabel17, gridBagConstraints);

        x0F_quantityOfOutputsTextField.setColumns(5);
        x0F_quantityOfOutputsTextField.setText("numericTextField1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 5, 5);
        x0F_writeMultipleCoilsPanel.add(x0F_quantityOfOutputsTextField, gridBagConstraints);

        actionSelectorTabbedPane.addTab("Write Multiple Coils", x0F_writeMultipleCoilsPanel);

        x10_writeHoldingRegistersPanel.setLayout(new java.awt.GridBagLayout());

        jLabel3.setText("Starting address:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 2, 2);
        x10_writeHoldingRegistersPanel.add(jLabel3, gridBagConstraints);

        x10_startingAddressTextField.setColumns(5);
        x10_startingAddressTextField.setText("numericTextField1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 2, 5);
        x10_writeHoldingRegistersPanel.add(x10_startingAddressTextField, gridBagConstraints);

        jLabel4.setText("Quantity of registers :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 5, 2);
        x10_writeHoldingRegistersPanel.add(jLabel4, gridBagConstraints);

        x10_quantityToReadTextField.setColumns(5);
        x10_quantityToReadTextField.setText("numericTextField1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 5, 5);
        x10_writeHoldingRegistersPanel.add(x10_quantityToReadTextField, gridBagConstraints);

        actionSelectorTabbedPane.addTab("Write Holding registers", x10_writeHoldingRegistersPanel);

        x17_readWriteMultipleRegistersPanel.setLayout(new java.awt.GridBagLayout());

        jLabel18.setText("Read starting address:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 2, 2);
        x17_readWriteMultipleRegistersPanel.add(jLabel18, gridBagConstraints);

        x17_readStartingAddressTextField.setColumns(5);
        x17_readStartingAddressTextField.setText("numericTextField1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 2, 5);
        x17_readWriteMultipleRegistersPanel.add(x17_readStartingAddressTextField, gridBagConstraints);

        jLabel19.setText("Quantity to read:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 5, 2);
        x17_readWriteMultipleRegistersPanel.add(jLabel19, gridBagConstraints);

        x17_quantityToReadTextField.setColumns(5);
        x17_quantityToReadTextField.setText("numericTextField1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 5, 5);
        x17_readWriteMultipleRegistersPanel.add(x17_quantityToReadTextField, gridBagConstraints);

        jLabel20.setText("Write starting address:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        x17_readWriteMultipleRegistersPanel.add(jLabel20, gridBagConstraints);

        x17_writeStartingAddressTextField.setColumns(5);
        x17_writeStartingAddressTextField.setText("numericTextField1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 2, 5);
        x17_readWriteMultipleRegistersPanel.add(x17_writeStartingAddressTextField, gridBagConstraints);

        jLabel21.setText("Quantity to write:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 5, 2);
        x17_readWriteMultipleRegistersPanel.add(jLabel21, gridBagConstraints);

        x17_quantityToWriteTextField.setColumns(5);
        x17_quantityToWriteTextField.setText("numericTextField1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 5, 5);
        x17_readWriteMultipleRegistersPanel.add(x17_quantityToWriteTextField, gridBagConstraints);

        actionSelectorTabbedPane.addTab("Read/Write Multiple Registers", x17_readWriteMultipleRegistersPanel);

        delayPanel.setLayout(new java.awt.GridBagLayout());

        jLabel8.setText("Delay:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 2, 2);
        delayPanel.add(jLabel8, gridBagConstraints);

        delayTextField.setColumns(5);
        delayTextField.setText("numericTextField1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 2, 5);
        delayPanel.add(delayTextField, gridBagConstraints);

        jLabel10.setText("ms");
        delayPanel.add(jLabel10, new java.awt.GridBagConstraints());

        actionSelectorTabbedPane.addTab("Delay", delayPanel);

        getContentPane().add(actionSelectorTabbedPane, java.awt.BorderLayout.CENTER);

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });
        jPanel1.add(okButton);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        jPanel1.add(cancelButton);

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        jLabel5.setText("Retries:");
        jPanel2.add(jLabel5);

        jSpinner1.setModel(new javax.swing.SpinnerNumberModel(0, 0, 10, 1));
        jPanel2.add(jSpinner1);

        implementationComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Modbus", "J-Bus"}));
        implementationComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                implementationComboBoxActionPerformed(evt);
            }
        });
        jPanel2.add(implementationComboBox);

        jLabel6.setText("Receive timeout:");
        jPanel2.add(jLabel6);

        jSpinner2.setModel(new javax.swing.SpinnerNumberModel(0, 0, 60000, 1000));
        jPanel2.add(jSpinner2);

        jLabel7.setText("ms");
        jPanel2.add(jLabel7);

        getContentPane().add(jPanel2, java.awt.BorderLayout.NORTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        isOK = false;
        setVisible(false);
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        isOK = true;
        setVisible(false);
    }//GEN-LAST:event_okButtonActionPerformed

    private void implementationComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_implementationComboBoxActionPerformed

        /*switch( implementationComboBox.getSelectedIndex() )
        {
            default:
            case 0: // modbus
            modbusSlave.setImplementation(IMPLEMENTATION_MODBUS);
            break;
            case 1: // J-Bus
            modbusSlave.setImplementation(IMPLEMENTATION_JBUS);
            break;
        }*/
    }//GEN-LAST:event_implementationComboBoxActionPerformed

    public boolean isOK() {
        return isOK;
    }
    // End of variables declaration//GEN-END:variables

    void initializeWith(ModbusMasterRequest mmr) {
        if (mmr instanceof ModbusMasterDelay) {
            ModbusMasterDelay mmd = (ModbusMasterDelay) mmr;
            actionSelectorTabbedPane.setSelectedComponent(delayPanel);
            delayTextField.setValue(mmd.getDelay());
            return;
        }

        switch (mmr.getFunctionCode()) {
            case ModbusConst.FC_READ_COILS:
                actionSelectorTabbedPane.setSelectedComponent(x01_readCoilsPanel);
                x01_startingAddressTextField.setValue(mmr.getReadAddress());
                x01_quantityToReadTextField.setValue(mmr.getReadQuantity());
                break;

            case ModbusConst.FC_READ_DISCRETE_INPUTS:
                actionSelectorTabbedPane.setSelectedComponent(x02_readDiscreteInputsPanel);
                x02_startingAddressTextField.setValue(mmr.getReadAddress());
                x02_quantityToReadTextField.setValue(mmr.getReadQuantity());
                break;

            case ModbusConst.FC_READ_HOLDING_REGISTERS:
                actionSelectorTabbedPane.setSelectedComponent(x03_readHoldingRegistersPanel);
                x03_startingAddressTextField.setValue(mmr.getReadAddress());
                x03_quantityToReadTextField.setValue(mmr.getReadQuantity());
                break;

            case ModbusConst.FC_WRITE_SINGLE_COIL:
                actionSelectorTabbedPane.setSelectedComponent(x05_writeSingleCoilPanel);
                x05_outputAddressTextField.setValue(mmr.getWriteAddress());
                break;

            case ModbusConst.FC_WRITE_SINGLE_REGISTER:
                actionSelectorTabbedPane.setSelectedComponent(x06_writeSingleRegisterPanel);
                x06_registerAddressTextField.setValue(mmr.getWriteAddress());
                break;

            case ModbusConst.FC_WRITE_MULTIPLE_COILS:
                actionSelectorTabbedPane.setSelectedComponent(x0F_writeMultipleCoilsPanel);
                x0F_startingAddressTextField.setValue(mmr.getWriteAddress());
                x0F_quantityOfOutputsTextField.setValue(mmr.getWriteQuantity());
                break;

            case ModbusConst.FC_WRITE_MULTIPLE_REGISTERS:
                actionSelectorTabbedPane.setSelectedComponent(x10_writeHoldingRegistersPanel);
                x10_startingAddressTextField.setValue(mmr.getWriteAddress());
                x10_quantityToReadTextField.setValue(mmr.getWriteQuantity());
                break;

            case ModbusConst.FC_READ_WRITE_MULTIPLE_REGISTERS:
                x17_readStartingAddressTextField.setValue(mmr.getReadAddress());
                x17_quantityToReadTextField.setValue(mmr.getReadQuantity());
                x17_writeStartingAddressTextField.setValue(mmr.getWriteAddress());
                x17_quantityToWriteTextField.setValue(mmr.getWriteQuantity());
                break;

        }
    }
}
