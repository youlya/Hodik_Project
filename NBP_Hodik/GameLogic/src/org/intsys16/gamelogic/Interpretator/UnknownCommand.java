package org.intsys16.gamelogic.Interpretator;

import org.intsys16.gamelogic.FieldControl.Coordinate;
import org.intsys16.gamelogic.FieldControl.Direction;
import org.intsys16.gamelogic.RobotsControl.good_robot;
import org.intsys16.gamelogic.RobotsControl.Scores;
import org.intsys16.GraphicMapAPI.GraphicMapAPI;
/**
 *
 * @author StBiuRay
 */
public class UnknownCommand implements CMD {
    String ErrorLog;
    String sep = System.getProperty("line.separator");

    public UnknownCommand(good_robot robot, String txtOfCommand) {
        ErrorLog = txtOfCommand;
    }
    
    @Override
    public String toString() {
        return "Unknown Command Error";
    }
    @Override
    public String Run() {
        return Error();
    }
    public String Error() {
        return "UnknownCommand: " + ErrorLog + sep;
    }
}