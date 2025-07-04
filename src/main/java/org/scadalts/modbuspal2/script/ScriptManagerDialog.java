/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ScriptManagerDialog.java
 *
 * Created on 24 mars 2009, 18:25:32
 */

package org.scadalts.modbuspal2.script;

import org.scadalts.modbuspal2.main.ListLayout;
import org.scadalts.modbuspal2.main.ModbusPalPane;
import org.scadalts.modbuspal2.main.ModbusPalProject;
import org.scadalts.modbuspal2.toolkit.FileTransferHandler;
import org.scadalts.modbuspal2.utils.ResourceUtils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.dnd.DropTarget;
import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;


/**
 * the dialog displaying the list of scripts defined in the project
 *
 * @author nnovic
 */
public class ScriptManagerDialog
        extends javax.swing.JDialog
        implements ScriptListener, FileTransferHandler.FileTransferTarget {
    /**
     * identifies the tab where the "generator scripts" used to be
     *
     * @deprecated "generator scripts" do not exist anymore. check documentation.
     */
    @Deprecated
    public static final int TAB_GENERATORS = 2;
    /**
     * identifies the tab where the "binding scripts" used to be
     *
     * @deprecated "binding scripts" do not exist anymore. check documentation.
     */
    @Deprecated
    public static final int TAB_BINDINGS = 3;
    private static final String REGISTRY_KEY = ModbusPalPane.BASE_REGISTRY_KEY + "/instanciators";
    private ModbusPalProject modbusPalProject;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addScriptButton;
    private javax.swing.JTabbedPane jTabbedPane1;
    private JPanel scriptsButtons;
    private JPanel scriptsList;
    private javax.swing.JScrollPane scriptsScrollPane;
    private JPanel scriptsTab;
    private javax.swing.JLabel statusLabel;
    private JPanel statusPanel;
    /**
     * Creates new form ScriptManagerDialog
     */
    public ScriptManagerDialog() {
        super();
        initComponents();
        Image img = Toolkit.getDefaultToolkit().createImage(ResourceUtils.getResource("img/icon32.png"));
        setIconImage(img);
        scriptsList.setDropTarget(new DropTarget(this, new FileTransferHandler(this)));
    }

    /**
     * Displays a FileChoose to let the user select a script file.
     * The directory is saved in the registry. The dialog in centered
     * by reference to the component given in argument
     *
     * @param parent component that will give hints for centering the dialog
     * @return the selected script file, or null
     */
    public static File chooseScriptFile(Component parent) {
        // get last used directory
        Preferences prefs = Preferences.userRoot();
        Preferences appPrefs = prefs.node(REGISTRY_KEY);
        String prev_dir = appPrefs.get("prev_dir", null);

        // newInstance the dialog
        JFileChooser fileChooser = new JFileChooser();

        // setup current directory if available
        if (prev_dir != null) {
            File cwd = new File(prev_dir);
            if ((cwd.isDirectory() == true) && (cwd.exists() == true)) {
                fileChooser.setCurrentDirectory(cwd);
            }
        }

        // newInstance a Python/Jython extension filter
        FileNameExtensionFilter pythonFilter = new FileNameExtensionFilter("Python file", "py");
        fileChooser.setFileFilter(pythonFilter);

        // display file chooser
        int choice = fileChooser.showDialog(parent, "Add");
        if (choice == JFileChooser.APPROVE_OPTION) {
            // get the directory that has been chosen
            File chosen = fileChooser.getCurrentDirectory();
            appPrefs.put("prev_dir", chosen.getPath());
            try {
                appPrefs.flush();
            } catch (BackingStoreException ex) {
                Logger.getLogger(ScriptManagerDialog.class.getName()).log(Level.SEVERE, null, ex);
            }
            return fileChooser.getSelectedFile();
        }

        return null;
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        scriptsTab = new JPanel();
        scriptsScrollPane = new javax.swing.JScrollPane();
        scriptsList = new JPanel();
        scriptsButtons = new JPanel();
        addScriptButton = new javax.swing.JButton();
        statusPanel = new JPanel();
        statusLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Script Manager");
        setMinimumSize(new java.awt.Dimension(400, 250));

        scriptsTab.setLayout(new java.awt.BorderLayout());

        scriptsList.setBackground(javax.swing.UIManager.getDefaults().getColor("List.background"));
        scriptsList.setLayout(null);
        scriptsList.setLayout(new ListLayout());
        scriptsScrollPane.setViewportView(scriptsList);

        scriptsTab.add(scriptsScrollPane, java.awt.BorderLayout.CENTER);

        scriptsButtons.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        addScriptButton.setText("Add");
        addScriptButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addScriptButtonActionPerformed(evt);
            }
        });
        scriptsButtons.add(addScriptButton);

        scriptsTab.add(scriptsButtons, java.awt.BorderLayout.NORTH);

        jTabbedPane1.addTab("Scripts", scriptsTab);

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        statusPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        statusLabel.setText(".");
        statusPanel.add(statusLabel);

        getContentPane().add(statusPanel, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addScriptButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addScriptButtonActionPerformed

        // get the selected file, in any.
        File scriptFile = chooseScriptFile(this);
        if (scriptFile == null) {
            setStatus("Cancelled by user.");
            return;
        }

        // add startup script:
        modbusPalProject.addScript(scriptFile);
    }//GEN-LAST:event_addScriptButtonActionPerformed
    // End of variables declaration//GEN-END:variables

    private void setStatus(String status) {
        statusLabel.setText(status);
    }

    @Override
    public void scriptAdded(ScriptRunner runner) {
        // create a new panel and add it
        ScriptRunnerPanel panel = new ScriptRunnerPanel(modbusPalProject, runner);
        scriptsList.add(panel);
        validate();
        repaint();
    }

    private ScriptRunnerPanel findPanel(JPanel panel, ScriptRunner runner) {
        for (int i = 0; i < panel.getComponentCount(); i++) {
            Component comp = panel.getComponent(i);
            if (comp instanceof ScriptRunnerPanel) {
                ScriptRunnerPanel srp = (ScriptRunnerPanel) comp;
                if (srp.contains(runner) == true) {
                    return srp;
                }
            }
        }
        return null;
    }


    @Override
    public void scriptRemoved(ScriptRunner runner) {
        ScriptRunnerPanel panel = findPanel(scriptsList, runner);
        scriptsList.remove(panel);
        validate();
        repaint();
    }

    @Override
    public boolean importFiles(Component target, List<File> files) {
        for (int i = 0; i < files.size(); i++) {
            File scriptFile = files.get(i);

            if (target == scriptsList) {
                modbusPalProject.addScript(scriptFile);
            } else {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }

        return true;
    }

    /**
     * TODO: get rid of this function. this class shall implement
     * ModbusPalProjectListener instead
     *
     * @param mpp
     */
    public void setProject(ModbusPalProject mpp) {
        if (modbusPalProject != null) {
            modbusPalProject.removeScriptListener(this);
        }
        modbusPalProject = mpp;
        modbusPalProject.addScriptListener(this);

        // update list of scripts:
        scriptsList.removeAll();
        for (ScriptRunner runner : modbusPalProject.getScripts(ScriptRunner.SCRIPT_TYPE_ANY)) {
            scriptAdded(runner);
        }

    }

}
