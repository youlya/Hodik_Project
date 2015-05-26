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
public class Pit extends Obstacles{
    public Pit (Field a, Coordinate coord){
        super (a,coord,25);
    }
}
