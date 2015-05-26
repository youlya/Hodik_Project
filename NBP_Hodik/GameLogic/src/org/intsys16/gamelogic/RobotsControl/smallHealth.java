/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.RobotsControl;

import org.intsys16.gamelogic.FieldControl.Coordinate;
import org.intsys16.gamelogic.FieldControl.Field;
import org.intsys16.gamelogic.Interpretator.Interpretator;

/**
 *
 * @author Lenus1k
 */
public class smallHealth extends healthBonus{
    public smallHealth (Field a, Interpretator in, Coordinate coord){
        super (a,in,coord,5);
    }
}
