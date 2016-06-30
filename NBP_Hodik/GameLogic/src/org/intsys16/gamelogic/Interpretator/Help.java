/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.Interpretator;

import org.intsys16.gamelogic.FieldControl.Coordinate;
import org.intsys16.gamelogic.FieldControl.Direction;
import org.intsys16.gamelogic.RobotsControl.robot;
import org.intsys16.gamelogic.RobotsControl.Scores;
import org.intsys16.GraphicMapAPI.GraphicMapAPI;

/**
 *
 * @author StBiuRay
 */
public class Help implements CMD{
    robot currRobot;
    @Override
    public String toString()
    {
        return "Help command";
    }
    @Override
    public String Run(){
        return "Help";
    }
    @Override
    public String Help()
    {
        return "%Help% - returns list of possible commands";
    }

}
    
