/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.Interpretator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.intsys16.gamelogic.FieldControl.Coordinate;
import org.intsys16.gamelogic.RobotsControl.good_robot;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import org.intsys16.GraphicMapAPI.GraphicMapAPI;
import org.intsys16.gamelogic.FieldControl.Field_object;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import org.openide.util.NbBundle;

/**
 *
 * @author micen
 */
@NbBundle.Messages({
    "LBL_Running=Running:",
    "LBL_Going=going to",
    "LBL_Rotating=rotating",
    "DIR_Left=left",
    "DIR_Right=rigth"
})
public class Interpretator implements ActionListener{

    Parser parser;
    Boolean debugMode = false;
    good_robot currRobot;
    ArrayList<CMD> cmdList = new ArrayList<>();
    Iterator<CMD> iterator = cmdList.iterator();
    private static final Logger log = Logger.getLogger(Interpretator.class.getName());
    private GraphicMapAPI GraphicMap = GraphicMapAPI.getGraphicMap();
    Timer timer;

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
            currRobot.getCoord().setX(Integer.parseInt(parts[1]));
            currRobot.getCoord().setY(Integer.parseInt(parts[2]));
            Field_object buf = currRobot.getField().isFilled(currRobot.getCoord());
            if (buf != null) {
                    buf.interact(currRobot);
                    GraphicMap.deleteFieldObject(buf.getCoord().getX(), buf.getCoord().getY());
                    currRobot.getField().deleteFieldObject(currRobot.getCoord());
                }
            else {
                log.log(Level.SEVERE, "this coordinates are filled with FieldObject ", result);
                return "this coordinates are filled with FieldObject "+result;
            }
        }
        System.out.println(result);
        log.log(Level.FINE, result);
        return result;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(iterator.hasNext()) {
                InputOutput io =  IOProvider.getDefault().getIO(Bundle.LBL_Running(), false);
                String result = runNextCMD();
                String check=checkResult(result);
                if(result.startsWith("TURN")){
                    GraphicMap.move(result);
                    io.getOut().println(Bundle.LBL_Rotating() + " " +
                            (result.substring(5).toLowerCase().equals("left") ? Bundle.DIR_Left() : Bundle.DIR_Right())
                            + "...");                    
                    //move(result);
                }
                else{  
                    GraphicMap.move("MOVE_"+currRobot.dir.name());
                    io.getOut().println(Bundle.LBL_Going() + 
                            result.substring(6, result.length()-2).toLowerCase() + "...");   
                    //move(MOVE_+currRobot.dir.name());
                }
                //System.out.println(check);            
                //io.getOut().println(check);
                io.getOut().close();
        }
        else{
            timer.stop();
        }
    }
    
    
    
    public void Run() {
        timer=new Timer(2500, this);
        timer.start();
//        if (debugMode) {
//            //NTD
//        } else {
//            while (iterator.hasNext()) {
//                String result = runNextCMD();
//                String check=checkResult(result);
//                if(result.startsWith("TURN")){
//                    GraphicMap.move(result);
//                    //move(result);
//                }
//                else{  
//                    GraphicMap.move("MOVE_"+currRobot.dir.name());
//                    //move(MOVE_+currRobot.dir.name());
//                }
//                System.out.println(check);
//                
//               // if(check.startsWith("this coordinates are filled with FieldObject")){
//                    //return check;
//                //}
//                    
//                //else
//                  // if(check.startsWith("this coordinates are out of FIELD"))
//                       //return check;
//            }
//        }
//        //return "success";
    }

    public Interpretator() {
        
    }

}
