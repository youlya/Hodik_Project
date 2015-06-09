/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.FieldControl;
import org.intsys16.gamelogic.Interpretator.Interpretator;
import org.intsys16.integrator.api.Integrator;
import org.intsys16.gamelogic.XMLParser.XMLobject;
import org.intsys16.gamelogic.RobotsControl.*;
/**
 *
 * @author jbenua
 */
public abstract class Field_object {
    protected Coordinate c;
    protected Field field;
    protected Integrator integr = Integrator.getIntegrator();
    protected Interpretator interp;
    /*
    aргументы:
    good_robot(поле, интерпретатор, координата, 100, robot), где 100 - xp, robot - объект класса Unit
    bad_robot(поле, интерпретатор, координата, "act_type")
    */
    public Coordinate getCoord()
    {
        return c;
    }
    abstract public String getType();
    public void setCoord(Coordinate coord)
    {
        c.x=coord.x;
        c.y=coord.y;
    }
    public Field getField()
    {
        return field;
    }
    /*public int getLevel()
    {
        return field.level;
    }*/
    public Interpretator getInterpr()
    {
        return interp;
    }
    public Field_object(Field a, Interpretator in, Coordinate coord)
    {
        interp=in;
        field=a;
        c=coord;
    };
    
    public void show_info()
    {
        System.out.println("Hey! I'm on the field! Coords: " + c);
    }
    abstract public String getActtype();
    abstract public int getDamage();
    abstract public XMLobject toXML(XMLobject obj);
    
    public void interact(good_robot gr){};
}
