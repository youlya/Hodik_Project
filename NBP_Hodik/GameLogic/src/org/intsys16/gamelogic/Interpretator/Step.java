/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.Interpretator;

import org.intsys16.gamelogic.FieldControl.Coordinate;
import org.intsys16.gamelogic.FieldControl.Direction;
import org.intsys16.gamelogic.RobotsControl.good_robot;

/**
 *
 * @author micen
 */
public class Step implements CMD {

    Coordinate c = null;
    Direction dir;
    good_robot currRobot;
    int num;

    public Step(good_robot robot) {
        currRobot=robot;
    }
    
//    public Step(Coordinate C, Direction DIR,int n)
//    {
//        c=C;
//        dir=DIR;
//        num=n;
//    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public String Run() {
        return Step();
    }

    public String Step() {
        int X = currRobot.getCoord().x + dir.deltaX();
        int Y = currRobot.getCoord().y + dir.deltaY();
        return "stepTo " + X + " " + Y + " " + dir.name();
    }

}
