/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.Interpretator;

import org.intsys16.gamelogic.FieldControl.Coordinate;
import org.intsys16.gamelogic.RobotsControl.good_robot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author micen
 */
public class Interpretator {

    Parser parser;
    Boolean debugMode = false;
    good_robot currRobot;
    ArrayList<CMD> cmdList;
    Iterator<CMD> iterator = cmdList.iterator();
    private static final Logger log = Logger.getLogger(Interpretator.class.getName());

    HashMap<good_robot, ArrayList<Program>> test;

    String runNextCMD() {
        return iterator.next().Run();
    }

    public boolean debugMode() {
        return debugMode;
    }

    public void translate(String name, String url, good_robot robot) {
        if (test.get(robot) != null) {
            parser = new Parser(url, robot);
            ArrayList<CMD> list = parser.getList();
            test.get(robot).add(new Program(name, list));
        } else {
            parser = new Parser(url, robot);
            ArrayList<CMD> list = parser.getList();
            ArrayList<Program> buf = new ArrayList<>();
            buf.add(new Program(name, list));
            test.put(robot, buf);
        }
    }

    public void translate(String url, good_robot robot) {
        currRobot = robot;
        parser = new Parser(url, currRobot);
        cmdList = parser.getList();
        iterator = cmdList.iterator();
    }

    void checkResult(String result) {
        String[] parts = result.split(" ");
        if (parts[0].equals("stepTo")) {
            Coordinate newC = new Coordinate(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
            if (currRobot.getField().isFilled(newC) != true) {
                currRobot.setCoords(newC);
            } else {
                log.log(Level.SEVERE, "this coordinates are filled with FieldObject", result);
            }

        }
        System.out.println(result);
        log.log(Level.FINE, result);
    }

    void Run() {
        if (debugMode) {
            //NTD
        } else {
            while (iterator.hasNext()) {
                String result = runNextCMD();
                checkResult(result);
                System.out.println(result);
            }
        }
    }

    public Interpretator() {
//        parser=new Parser("cmd.txt");
//        cmdList=parser.getList();
//        iterator= cmdList.iterator();
//        run();
    }

}
