/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.Interpretator;

import org.intsys16.gamelogic.FieldControl.Coordinate;
import org.intsys16.gamelogic.RobotsControl.good_robot;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.intsys16.GraphicMapAPI.GraphicMapAPI;

/**
 *
 * @author micen
 */
public class Interpretator {

    Parser parser;
    Boolean debugMode = false;
    good_robot currRobot;
    ArrayList<CMD> cmdList = new ArrayList<>();
    Iterator<CMD> iterator = cmdList.iterator();
    private static final Logger log = Logger.getLogger(Interpretator.class.getName());
    private GraphicMapAPI GraphicMap = GraphicMapAPI.getGraphicMap();

    String runNextCMD() {
        return iterator.next().Run();
    }

    public boolean debugMode() {
        return debugMode;
    }

    public String translate(String url, good_robot robot) {
        currRobot = robot;
        parser = new Parser(url, currRobot);
        if(parser.getStatus().equals("success")){
           cmdList = parser.getList();
            iterator = cmdList.iterator(); 
            return parser.getStatus();
        }
        else
        {
            return parser.getStatus();
        }
    }
    
    public String translate(String[] cmd, good_robot robot) {
        currRobot = robot;
        parser = new Parser(cmd, currRobot);
        if (parser.getStatus().equals("success")) {
            cmdList = parser.getList();
            iterator = cmdList.iterator();
            return parser.getStatus();
        } else {
            return parser.getStatus();
        }
    }
     
    String checkResult(String result) {
        String[] parts = result.split(" ");
        if (parts[0].equals("stepTo")) {
            Coordinate newC = new Coordinate(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
//            if (currRobot.getField().isFilled(newC) != true) {
//                if(currRobot.getField().endofField(newC) != true){
                       currRobot.setCoords(newC);
//                }
//                else{
//                    log.log(Level.SEVERE, "this coordinates are out of FIELD", result);
//                    return "this coordinates are out of FIELD "+result;
//                }
//            } else {
 //               log.log(Level.SEVERE, "this coordinates are filled with FieldObject ", result);
 //               return "this coordinates are filled with FieldObject "+result;
  //          }   
        }
        System.out.println(result);
        log.log(Level.FINE, result);
        return result;
    }

    public void Run() {
        if (debugMode) {
            //NTD
        } else {
            while (iterator.hasNext()) {
                String result = runNextCMD();
                String check=checkResult(result);
                if(result.startsWith("TURN")){
                    GraphicMap.move(result);
                    //move(result);
                }
                else{  
                    GraphicMap.move("MOVE_"+currRobot.dir.name());
                    //move(MOVE_+currRobot.dir.name());
                }
                System.out.println(check);
                
               // if(check.startsWith("this coordinates are filled with FieldObject")){
                    //return check;
                //}
                    
                //else
                  // if(check.startsWith("this coordinates are out of FIELD"))
                       //return check;
            }
        }
        //return "success";
    }

    public Interpretator() {
        
    }

}
