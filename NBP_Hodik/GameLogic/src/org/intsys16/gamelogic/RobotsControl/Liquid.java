/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.RobotsControl;

import org.intsys16.gamelogic.FieldControl.Coordinate;
import org.intsys16.gamelogic.FieldControl.Field;

/**
 *
 * @author Lenus1k
 */
public class Liquid extends Obstacles{
    public Liquid (Field a, Coordinate coord){
        //actually this thing should kill a robot instantly,
        //but it should appear less
        super (a,coord,100);   
    }
}
