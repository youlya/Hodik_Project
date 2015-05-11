/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.RobotsControl;

import org.intsys16.gamelogic.FieldControl.Coordinate;
import org.intsys16.gamelogic.FieldControl.Field;
import org.intsys16.gamelogic.FieldControl.Field_object;
import org.intsys16.gamelogic.Interpretator.Interpretator;

/**
 *
 * @author jbenua
 */
public class bad_robot extends Field_object{
    int xp;
    String act_type;
    int damage;
    bad_robot(Field f, Integrator i, Interpretator in, Coordinate coord, int x, int d, String type)
    {
        super(f, i, in, coord);
        xp=x;
        damage=d;
        act_type=type;
    }
    
    @Override
    public void show_info()
    {
        super.show_info();
        System.out.println("XP: " + xp);
        System.out.println("damage: " + damage);
    }
}
