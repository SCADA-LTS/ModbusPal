/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ModbusPalFrame.java
 *
 * Created on 22 oct. 2010, 10:48:15
 */

package org.scadalts.modbuspal2.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.scadalts.modbuspal2.cli.ModbusPal2Options;
import org.scadalts.modbuspal2.cli.ModeType;
import org.scadalts.modbuspal2.link.ModbusLink;
import org.scadalts.modbuspal2.utils.ResourceUtils;

import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

/**
 * Utilitary methods for creating new instances of ModbusPal
 *
 * @author nnovic
 */
public class ModbusPalGui extends ModbusPal2Options implements Runnable {
    public static final int MAX_PORT_NUMBER = 65536;
    private static final Logger LOG = LogManager.getLogger(ModbusPalGui.class);
    private static final HashMap<Object, ModbusPalPane> instances = new HashMap<Object, ModbusPalPane>();
    private static String initialLoadFilePath = "";
    private static int initialPortNumber = -1;


    /**
     * This method gets the initial load file path to load specified by the user in the command line arguments.
     *
     * @return {String} The absolute initial load file path. Returns "" if no argument was given.
     */
    public static String getInitialLoadFilePath() {
        return initialLoadFilePath;
    }

    /**
     * This method gets the initial port number to load specified by the user in the command line arguments.
     *
     * @return {int} The initial port number for TCP/IP connections. Returns -1 if no port number was given.
     */
    public static int getInitialPortNumber() {
        return initialPortNumber;
    }

    /**
     * this method will try to change the Look and Feel of the application,
     * using the system l&f. It means that the application will get the Windows
     * l&f on Windows, etc...
     */
    private static void setNativeLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            LOG.error("Error setting native LAF: " + e.getMessage(), e);
        }
    }

    /**
     * Creates a ModbusPalFrame. The internal
     * console of ModbusPal is enabled.
     *
     * @return a new ModbusPalFrame
     */
    public static JFrame newFrame() {
        ModbusPalFrame frame = new ModbusPalFrame();
        return frame;
    }

    /**
     * Creates a ModbusPalInternalFrame. The internal
     * console of ModbusPal is enabled.
     *
     * @return a new ModbusPalInternalFrame
     */
    public static JInternalFrame newInternalFrame() {
        ModbusPalInternalFrame iframe = new ModbusPalInternalFrame();
        return iframe;
    }

    /**
     * Creates a ModbusPalPane instance. The internal
     * console of ModbusPal is disabled.
     *
     * @return a new ModbusPalPane instance
     */
    public static ModbusPalPane newInstance() {
        return new ModbusPalPane(false);
    }

    /**
     * Returns a ModbusPalPane instance that is associated with
     * the specified key, as in a HashMap. If there is no ModusPalPane
     * associated with that key, a new one is created. Otherwise, the existing
     * one is returned
     *
     * @param key any object that can be used to uniquely identified a particular
     *            ModbusPalPane instance. usually a String.
     * @return The ModbusPalPane instance identified by the key
     */
    public static ModbusPalPane getInstance(Object key) {
        if (key == null) {
            throw new NullPointerException();
        }

        if (instances.containsKey(key) == false) {
            instances.put(key, new ModbusPalPane(false));
        }

        return instances.get(key);
    }

    /**
     * @param {String[]} args The command line arguments
     */
    public void run() {
        boolean noGui = isNoGui();
        File projectFile = getProjectFile();
        ModeType modeType = getModeType();
        boolean masterMode = isMasterMode();
        System.setProperty("java.awt.headless", String.valueOf(noGui));

        if (projectFile != null) {
            initialLoadFilePath = projectFile.getAbsolutePath();
        }

        if (!noGui) {
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    setNativeLookAndFeel();
                    JFrame frame = newFrame();
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.pack();
                    frame.setVisible(true);
                }
            });
        } else {
            try {

                ModbusPalProject modbusPalProject;
                try {
                    modbusPalProject = ModbusPalProject.load(projectFile, true);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                ModbusPalListenerImpl modbusPalListener = new ModbusPalListenerImpl();
                modbusPalListener.setProject(modbusPalProject);

                ModbusLink modbusLink = ModbusPalPane.startLink(modbusPalProject, modeType.getArg(this), masterMode, () -> {
                }, modeType.createLink());
                Runtime.getRuntime().addShutdownHook(new Thread() {
                    ModbusLink modbusLink;

                    @Override
                    public void run() {
                        if (modbusLink != null)
                            ModbusPalPane.stopLink(masterMode, modbusLink);
                    }

                    public Thread setModbusLink(ModbusLink modbusLink) {
                        this.modbusLink = modbusLink;
                        return this;
                    }
                }.setModbusLink(modbusLink));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }
    }

    /**
     * A JinternalFrame that contains a ModbusPalPane.
     */
    public static class ModbusPalInternalFrame
            extends JInternalFrame
            implements InternalFrameListener {
        final ModbusPalPane modbusPal;

        /**
         * Creates a new instance of ModbusPalInternalFrame
         */
        public ModbusPalInternalFrame() {
            setTitle(ModbusPalPane.APP_STRING);
            setIconImage();
            setLayout(new BorderLayout());
            modbusPal = new ModbusPalPane(false);
            add(modbusPal, BorderLayout.CENTER);
            pack();
            addInternalFrameListener(this);
        }

        private void setIconImage() {
            URL url2 = ResourceUtils.getResource("img/icon.png");
            Image image2 = getToolkit().createImage(url2);
            setFrameIcon(new ImageIcon(image2));
        }

        @Override
        public void internalFrameOpened(InternalFrameEvent e) {
        }

        @Override
        public void internalFrameClosing(InternalFrameEvent e) {
            modbusPal.exit();
        }

        @Override
        public void internalFrameClosed(InternalFrameEvent e) {
        }

        @Override
        public void internalFrameIconified(InternalFrameEvent e) {
        }

        @Override
        public void internalFrameDeiconified(InternalFrameEvent e) {
        }

        @Override
        public void internalFrameActivated(InternalFrameEvent e) {
        }

        @Override
        public void internalFrameDeactivated(InternalFrameEvent e) {
        }
    }

    /**
     * A JFrame with a ModbusPalPane inside
     */
    public static class ModbusPalFrame
            extends JFrame {
        final ModbusPalPane modbusPal;

        /**
         * Creates a new instance of ModbusPalFrame
         */
        public ModbusPalFrame() {
            setTitle(ModbusPalPane.APP_STRING);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setIconImage();
            setLayout(new BorderLayout());
            modbusPal = new ModbusPalPane(true);
            add(modbusPal, BorderLayout.CENTER);
            pack();
        }

        private void setIconImage() {
            URL url2 = ResourceUtils.getResource("img/icon.png");
            Image image2 = getToolkit().createImage(url2);
            setIconImage(image2);
        }
    }


}
