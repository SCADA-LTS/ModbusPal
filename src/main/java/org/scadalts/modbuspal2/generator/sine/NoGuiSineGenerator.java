/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.scadalts.modbuspal2.generator.sine;

import org.scadalts.modbuspal2.generator.Generator;
import org.scadalts.modbuspal2.toolkit.XMLTools;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.OutputStream;

/**
 * the sine generator
 *
 * @author nnovic
 */
public class NoGuiSineGenerator extends Generator {
    double amplitude = 1.0;
    double period = 1.0;
    boolean catchup = false;
    double offset = 0.0;
    private double initialAngle = 0.0;

    /**
     * Creates a new instance of SineGenerator.
     */
    public NoGuiSineGenerator() {

    }

    @Override
    public double getValue(double time) {
        double angle = (2 * Math.PI) / period;
        return offset + amplitude * Math.sin(initialAngle + time * angle);
    }

    @Override
    public void setInitialValue(double value) {
        super.setInitialValue(value);
        if (catchup == true) {
            value -= offset;
            if (value > amplitude) {
                initialAngle = 2 * Math.PI;
            } else if (value < -amplitude) {
                initialAngle = -2 * Math.PI;
            } else {
                initialAngle = Math.asin(value / amplitude);
            }
        }
    }

    @Override
    public void saveGeneratorSettings(OutputStream out) throws IOException {
        StringBuilder amp = new StringBuilder("<amplitude");
        amp.append(" value=\"").append(amplitude).append("\" />\r\n");
        out.write(amp.toString().getBytes());

        StringBuilder per = new StringBuilder("<period");
        per.append(" value=\"").append(period).append("\" />\r\n");
        out.write(per.toString().getBytes());

        StringBuilder off = new StringBuilder("<offset");
        off.append(" value=\"").append(offset).append("\" />\r\n");
        out.write(off.toString().getBytes());

        StringBuilder cu = new StringBuilder("<catchup");
        cu.append(" enabled=\"").append(catchup).append("\" />\r\n");
        out.write(cu.toString().getBytes());

    }


    @Override
    public void loadGeneratorSettings(NodeList childNodes) {
        Node ampNode = XMLTools.getNode(childNodes, "amplitude");
        if (ampNode != null) {
            String amp = XMLTools.getAttribute("value", ampNode);
            amplitude = Double.parseDouble(amp);
        }

        Node perNode = XMLTools.getNode(childNodes, "period");
        if (perNode != null) {
            period = Double.parseDouble(XMLTools.getAttribute("value", perNode));
        }

        Node offNode = XMLTools.getNode(childNodes, "offset");
        if (offNode != null) {
            offset = Double.parseDouble(XMLTools.getAttribute("value", offNode));
        }

        Node cuNode = XMLTools.getNode(childNodes, "catchup");
        if (cuNode != null) {
            catchup = Boolean.parseBoolean(XMLTools.getAttribute("enabled", cuNode));
        }
    }

}
