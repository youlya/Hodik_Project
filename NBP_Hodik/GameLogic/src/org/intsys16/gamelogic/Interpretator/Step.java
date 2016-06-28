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
 * @author micen
 */
public class Step implements CMD {


    //    Coordinate c = null;
    Direction dir;
    robot currRobot;
    int num;

    public Step(robot robot) {
        currRobot=robot;
        dir=currRobot.dir;
    }
    
    @Override
    public String toString() {
        return "step";
    }
    @Override
    public String Run() {
        return Step();
    }
    public String Step() {
        int X = currRobot.getCoord().getX() + currRobot.dir.deltaX();
        int Y = currRobot.getCoord().getY() + currRobot.dir.deltaY();
        currRobot.score.set_Stepsc(currRobot.score.get_Stepsc() + 1);
        GraphicMapAPI.getGraphicMap().setStepScore(currRobot.score.get_Stepsc());
        return "stepTo " + X + " " + Y + " " + dir.name();
    }
}
