/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.scadalts.modbuspal2.script;

import org.scadalts.modbuspal2.binding.Binding;

/**
 * the class that should be used in python script to create new bindings
 *
 * @author nnovic
 */
public class PythonBinding
        extends Binding {
    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public int getRegister(int order, double value) {
        return 0;
    }

    @Override
    public Binding newInstance()
            throws InstantiationException, IllegalAccessException {
        PythonBinding pb = (PythonBinding) super.newInstance();
        pb.init();
        return pb;
    }


}
