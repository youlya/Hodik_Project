/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.RobotsControl;

import java.util.ArrayList;
import java.util.HashMap;
import org.intsys16.gamelogic.Interpretator.CMD;
import org.intsys16.gamelogic.Interpretator.Parser;
import org.intsys16.gamelogic.Interpretator.Program;

/**
 *
 * @author Anastasiya Mostovaya
 */
public class Algorithm {
    String name;
    String path;
    HashMap<robot, ArrayList<Program>> test;
    Parser parser;
    public Algorithm(String p)
    {
        path=p;
        String[] a = path.split("/");
        String[] temp=(a[a.length-1]).split("\\.");
        name=temp[0];
    }
    public Algorithm(String n, String p)
    {
        name=n;
        path=p;
    }
    public String getPath()
    {
        return path;
    }
    
    public void changeName(String newname)
    {
        System.out.print("Algorithm name changed: " + name);
        name=newname;
        System.out.print(" -> " + name);
    }
    public String getname()
    {
        return name;
    }
    public String out()
    {
        return name + "(" + path + ")";
    }
    
    public ArrayList<CMD> find(robot robot, String programName) {
        ArrayList<Program> get = test.get(robot);
        for (Program get1 : get) {
            if (get1.name.equals(programName)) {
                return get1.List();
            }
        }
        return null;
    }
    /**
     *
     * @param name
     * @param url
     * @param robot
     */
    public void translate(String name, String url, robot robot) {
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
}
