/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ScriptRunnerPanel.java
 *
 * Created on 30 mars 2009, 13:51:06
 */

package org.scadalts.modbuspal2.script;

import org.scadalts.modbuspal2.main.ModbusPalProject;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * the component representing a script in the script manager dialog
 *
 * @author nnovic
 */
public class ScriptRunnerPanel
        extends JPanel
        implements AncestorListener {
    private final ScriptRunner runner;
    private final ModbusPalProject project;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel classnameLabel;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton executeButton;
    private javax.swing.JButton openButton;
    private javax.swing.JComboBox typeComboBox;

    /**
     * Creates new form ScriptRunnerPanel
     *
     * @param mpp the current project
     * @param def the script
     */
    public ScriptRunnerPanel(ModbusPalProject mpp, ScriptRunner def) {
        runner = def;
        project = mpp;
        initComponents();
        typeComboBox.setSelectedIndex(runner.getType());
    }

    /**
     * Returns the script runner object that represents the script displayed
     * by this panel.
     *
     * @return the script object
     */
    public ScriptRunner getScriptRunner() {
        return runner;
    }

    /**
     * Removes this script from the project.
     */
    public void deleteScript() {
        System.out.println("Deleting script " + runner.getName() + "...");
        project.removeScript(runner);

    }

    /**
     * Checks if this panel is the front-end for the specified script
     *
     * @param sr the script to test
     * @return true if this panel is the front-end for the specified script
     */
    boolean contains(ScriptRunner sr) {
        return (sr == runner);
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        classnameLabel = new javax.swing.JLabel();
        typeComboBox = new javax.swing.JComboBox();
        executeButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        openButton = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createEtchedBorder());
        setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        classnameLabel.setText(runner.getName());
        classnameLabel.setToolTipText(runner.getPath());
        add(classnameLabel);

        typeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"On demand", "After init", "Before init"}));
        typeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeComboBoxActionPerformed(evt);
            }
        });
        add(typeComboBox);

        executeButton.setText("Execute");
        executeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                executeButtonActionPerformed(evt);
            }
        });
        add(executeButton);

        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });
        add(deleteButton);

        openButton.setText("Open");
        openButton.setEnabled(Desktop.isDesktopSupported());
        openButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openButtonActionPerformed(evt);
            }
        });
        add(openButton);
    }// </editor-fold>//GEN-END:initComponents

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        deleteScript();
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void executeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_executeButtonActionPerformed
        runner.execute();
    }//GEN-LAST:event_executeButtonActionPerformed

    private void openButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openButtonActionPerformed
        try {
            Desktop.getDesktop().open(runner.getScriptFile());
        } catch (IOException ex) {
            Logger.getLogger(ScriptRunnerPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_openButtonActionPerformed

    private void typeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeComboBoxActionPerformed
        runner.setType(typeComboBox.getSelectedIndex());
    }//GEN-LAST:event_typeComboBoxActionPerformed
    // End of variables declaration//GEN-END:variables

    @Override
    public void ancestorAdded(AncestorEvent event) {
    }

    @Override
    public void ancestorMoved(AncestorEvent event) {
        runner.interrupt();
    }

    @Override
    public void ancestorRemoved(AncestorEvent event) {
    }

}
