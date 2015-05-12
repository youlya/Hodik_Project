/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.FieldControl;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.intsys16.gamelogic.Interpretator.Interpretator;
import org.intsys16.integrator.api.Integrator;
/**
 *
 * @author jbenua
 */
public abstract class Field_object {
    protected Coordinate c;
    protected Field field;
    protected Integrator integrator = Integrator.getIntegrator();
    private static final Logger logger = Logger.getLogger(Field_object.class.getName());
    protected Interpretator interp;
    /*
    aргументы:
    good_robot(поле, интегратор, интерпретатор, координата, 100, robot), где 100 - xp, robot - объект класса Unit
    bad_robot(поле, интегратор, интерпретатор, координата, 10, 5, "act_type"), где 10 - xp, 1 - тип поведения, 5 - урон
    */
    public Coordinate getCoord()
    {
        return c;
    }
    
    public Interpretator getInterpr()
    {
        return interp;
    }

    public Field getField() {
        return field;
    }

    public Field_object(/** @debug Field a, Interpretator in, Coordinate coord*/)
    {
        /** @debug integrator works correctly when is called from here */
        logger.log(Level.INFO, integrator.getRobotsNames().toString());
        /** @debug end */
        
        /** @debug
        * interp=in;
        * field=a;
        * c=coord;
        */
    };
    
    public void show_info()
    {
        System.out.println("Hey! I'm on the field! Coords: " + c);
    }
}
