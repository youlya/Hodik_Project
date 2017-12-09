/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.Interpretator;

import org.intsys16.gamelogic.FieldControl.Coordinate;
import org.intsys16.gamelogic.FieldControl.Direction;
import org.intsys16.gamelogic.RobotsControl.robot;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.openide.util.NbBundle;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;


/**
 *
 * @author micen
 */
//@NbBundle.Messages("ERR_NoCommand=Error: no such command:")
public class Parser {

    Coordinate c=new Coordinate(2,3);
    Direction d;
    private static final Logger log = Logger.getLogger(Parser.class.getName());
    robot currRobot;

    public static ArrayList<String> alphabet = new ArrayList();
    ArrayList<String> readedText = new ArrayList();
    ArrayList<CMD> cmdList = new ArrayList(); //команды на выполнение
    ArrayList<CMD> commands = new ArrayList<CMD>(); //все возможные команды
    String status="";
    BufferedReader br = null;
    File file = null;

    
    public Parser(String Url, robot robot) {
        currRobot=robot;
        this.d = Direction.UP;
        initAlphabet();
        openFile(Url);
        read();
        Parse();
    }
    
       public Parser(String[]cmd, robot robot) {
        currRobot=robot;
        this.d = Direction.UP;
        initAlphabet();
        for (int i = 0; i < cmd.length; i++) {
            readedText.add(cmd[i]);
        }
        Parse();
        }
       public static void initAlphabet() {
            alphabet.add("Step");
            alphabet.add("Rotate");
            alphabet.add("Left");
            alphabet.add("Right");
            alphabet.add("Forward"); 
            alphabet.add("Turn");
            alphabet.add("Help");
       }
       
       
       public static void initRobotAlphabet(robot robot){
           //в теории должно работать
           //на будущее для разных наборов команд для разных роботов
           //пропишите, кто там роботами занимается, им toString()
           //или еще что-нибудь, что можно будет достать и точно определить тип робота
           switch(robot.toString()) {
               case "Hodik":
                   initAlphabet();
                   break;
               case "Something else":
                   initAlphabet();
                   alphabet.add("1");
                   alphabet.add("2");
                   break;
               case "One more type":
                   initAlphabet();
                   alphabet.add("3");
                   alphabet.add("4");
                   break;
               default:
                   initAlphabet();             
           }
       }
 
    public static ArrayList<String> getAlphabet()
    {
        initAlphabet();
        return alphabet;
    }
    
    public ArrayList<CMD> getList(){
        return cmdList;
    }
    
    public ArrayList<CMD> getCommands()
    {//отсутствие изначально задуманных единых конструкторов для команд 
     //не позволяет нормально реализовать через приведение типов
     //так что как есть
        commands.add(new Step(currRobot));
        commands.add(new Rotate("left", currRobot)); //???????????????? 
        commands.add(new Help());
        commands.add(new UnknownCommand());    
        return commands;
    }
    
    final void openFile(String filePath) {
        try {
            file = new File(filePath);
            try {
                br = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(file), "UTF-8"
                        )
                );
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            log.log(Level.SEVERE, "{0}" + " " + "File Not Found", filePath);
        }
    }

    void read() {
        while (true) {
            String line = null;
            try {
                if ((line = br.readLine()) != null) {
                    log.log(Level.FINE, "readed:", line);
                    readedText.add(line);
                } else {
                    break;
                }
            } catch (IOException ex) {
                Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
    
   ArrayList<String> prepare() {
        ArrayList<String> buffer = new ArrayList();
        for (String readedText1 : readedText) {
            String[] split = readedText1.split(" ");
            for (String split1 : split) {
                if (!split1.equals("")) {
                    buffer.add(split1);
                }
            }
        }
        return buffer;
    }
  private String getAngle(String angle){
       int angleint = Integer.parseInt(angle) % 360;
       if(angleint >=0 && angleint <=45)
           return "0";
       else if(angleint >45 && angleint <=90)
           return "90";
       else if(angleint >90 && angleint <=135)
           return "90";
       else if(angleint >135 && angleint <=180)
           return "180";
       else if(angleint >180 && angleint <=225)
           return "180";
       else if(angleint >225 && angleint <=270)
           return "270";
       else if(angleint >270 && angleint <=315)
           return "270";
       else if (angleint >315 && angleint <360)
           return "0";
       else return null;
        }
    void Parse() {
        ArrayList<String> buffer=prepare(); 
  
        for (int i = 0; i < buffer.size(); i++){
            CMD result = null;
            if (alphabet.contains(buffer.get(i))) {
                if (buffer.get(i).equals("Step") || (buffer.get(i).equals("Forward"))) {
                result = new Step(currRobot);
                    cmdList.add(result);
                    continue;
                }
                if (buffer.get(i).equals("Help")){
                    result = new Help();
                    cmdList.add(result);
                } 
                if (buffer.get(i).equals("Rotate") || (buffer.get(i).equals("Turn"))) {
                    String tag = buffer.get(i + 1);
                     
                    if (tag.toLowerCase().equals("left")) {
                        /*result = new Rotate("left",currRobot);
                        cmdList.add(result);
                        i++;*/
                        String angle = getAngle(buffer.get(i + 2));
                        if((angle.toLowerCase().equals("90"))){
                            result = new Rotate("left",currRobot);
                            cmdList.add(result);
                            i = i+2;
                            continue; 
                        }
                        else if((angle.toLowerCase().equals("180"))){
                            result = new Rotate("left",currRobot);
                            cmdList.add(result);
                            result = new Rotate("left",currRobot);
                            cmdList.add(result);
                            i = i+2;
                            continue; 
                        }
                        else if((angle.toLowerCase().equals("270"))){
                            result = new Rotate("right", currRobot);
                            cmdList.add(result);
                            i=i+2;
                            continue;  
                        }
                        else{
                            result = new UnknownCommand(currRobot, buffer.get(i), "angle");
                            cmdList.add(result);
                            i++;
                            continue; 
                        }                     
                    }
                    if (tag.toLowerCase().equals("right")) {
                        /*result = new Rotate("left",currRobot);
                        cmdList.add(result);
                        i++;*/
                        String angle = getAngle(buffer.get(i + 2));
                        if((angle.toLowerCase().equals("90"))){
                            result = new Rotate("right",currRobot);
                            cmdList.add(result);
                            i = i+2;
                            continue; 
                        }
                        else if((angle.toLowerCase().equals("180"))){
                            result = new Rotate("right",currRobot);
                            cmdList.add(result);
                            result = new Rotate("right",currRobot);
                            cmdList.add(result);
                            i = i+2;
                            continue; 
                        }
                        else if((angle.toLowerCase().equals("270"))){
                            result = new Rotate("left", currRobot);
                            cmdList.add(result);
                            i=i+2;
                            continue; 
                        }
                        else{
                            result = new UnknownCommand(currRobot, buffer.get(i), "angle");
                            cmdList.add(result);
                            i++;
                            continue; 
                        }                     
                    }
                    else{
                        result = new UnknownCommand(currRobot, buffer.get(i), "direction");
                        cmdList.add(result);
                    }
            } 
            }
            else {
                result = new UnknownCommand(currRobot, buffer.get(i));
                cmdList.add(result);
                //i++;
               /* log.log(Level.SEVERE, i+"no such command:", buffer.get(i));
                InputOutput io =  IOProvider.getDefault().getIO(Bundle.LBL_Running(), false);
                io.getErr().println(Bundle.ERR_NoCommand() + " "+buffer.get(i));
                io.getOut().close(); */
                //JOptionPane.showMessageDialog(null, "no such command: "+buffer.get(i)); 
                //status="Syntax error "+i+buffer.get(i);
                //continue;
        }
       
    }
         status="success";
    }
    public String getStatus() {
        return status;
    }
}


