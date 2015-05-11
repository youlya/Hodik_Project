/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.RobotsControl;

import hodikgit.Coordinate;
import hodikgit.Direction;
import hodikgit.Field;
import hodikgit.Field_object;
import hodikgit.Integrator;
import interpretator.Interpretator;

/**
 *
 * @author jbenua
 */
public class good_robot extends Field_object{
    public int xp;
    Unit robot;
    public Direction dir;
    
    good_robot(Field a, Integrator i, Interpretator in, Coordinate coord, int x, Direction d, Unit r)
    {
        super(a, i, in, coord);
        xp=x;
        dir=d;
        robot=r;
    }
    
    public void setCoords(Coordinate newC){
        c=newC;
    }
    
    @Override
    public void show_info()
    {
        super.show_info();
        System.out.println("Name: "+ robot.name);
        System.out.println("XP: " + xp);
    }
}
