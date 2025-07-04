/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.scadalts.modbuspal2.generator;

/**
 * Specifies the method that a class must implement in order to receive notifications
 * from generators.
 *
 * @author nnovic
 */
public interface GeneratorListener {
    /**
     * A generator will trigger this method when it has finished generating
     * values.
     *
     * @param gen the generator that has just ended
     */
    void generatorHasEnded(Generator gen);

    /**
     * A generator will trigger this method when it has started generating
     * values.
     *
     * @param gen the generator that has just started
     */
    void generatorHasStarted(Generator gen);

}
