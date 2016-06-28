/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.Interpretator;

import org.intsys16.gamelogic.FieldControl.Direction;
import org.intsys16.gamelogic.RobotsControl.robot;

/**
 *
 * @author micen
 */
public class Rotate implements CMD 
{
    String direction;
    robot currRobot;
    
    public Rotate(String d, robot robot)
    {
        currRobot=robot;
        direction=d;
    }
    @Override
    public String toString()
    {
        return this.direction;
    }
    @Override
    public String Run() {
        return Rotate();
    }
    
    public String Rotate()
    {
        switch(direction){
//            case "up":
//                dir=Direction.UP;
//                break;
            case "Left":
                if(currRobot.dir==Direction.DOWN){
                    currRobot.dir=Direction.RIGHT;
                }
                else if(currRobot.dir==Direction.LEFT){
                    currRobot.dir=Direction.DOWN;
                }
                else if(currRobot.dir==Direction.UP){
                    currRobot.dir=Direction.LEFT;
                }
                else if(currRobot.dir==Direction.RIGHT){
                    currRobot.dir=Direction.UP;
                }
                break;
            case "Right":
                if(currRobot.dir==Direction.DOWN){
                    currRobot.dir=Direction.LEFT;
                }
                else if(currRobot.dir==Direction.LEFT){
                    currRobot.dir=Direction.UP;
                }
                else if(currRobot.dir==Direction.UP){
                    currRobot.dir=Direction.RIGHT;
                }
                else if(currRobot.dir==Direction.RIGHT){
                    currRobot.dir=Direction.DOWN;
                }
                break;
//            case "down":
//                dir=Direction.DOWN; 
//                break;
        }
        return "TURN_"+direction.toUpperCase();
    }
}
   