package org.intsys16.gamelogic.Interpretator;

import org.intsys16.gamelogic.RobotsControl.robot;
/**
 *
 * @author StBiuRay
 */
public class UnknownCommand implements CMD {
    String errorLog;
    String sep = System.getProperty("line.separator");
    String missedArgument = "";
    public UnknownCommand()
    {
        errorLog = "UnknownCommand";
    }
    public UnknownCommand(robot robot, String txtOfCommand) {
        errorLog = txtOfCommand;
    }
    public UnknownCommand(robot robot, String txtOfCommand, String MissedArgument)
    {
        errorLog = txtOfCommand;
        missedArgument = ("(possibly missed: " + MissedArgument + ")");
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
        String Error = ("Unknown" + errorLog + missedArgument + sep);
        return Error;
    }
    public String Help() {
        return "UnknownCommand return " + errorLog + " %text of command% if can't interpret";
        
    }
}