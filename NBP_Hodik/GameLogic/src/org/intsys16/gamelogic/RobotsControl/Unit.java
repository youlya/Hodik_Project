/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.RobotsControl;

import org.intsys16.gamelogic.FieldControl.Coordinate;
import org.intsys16.gamelogic.FieldControl.Direction;
import org.intsys16.gamelogic.FieldControl.Field;
import org.intsys16.gamelogic.Interpretator.Interpretator;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jbenua
 */
public class Unit {
    String name;
    Map<String, Algorithm> progs;
    ArrayList <good_robot> robots;
    Unit(String n)
    {
        name=n;
        robots=new ArrayList();
        progs=new HashMap<>();
    }
    
    void add_robot(Field a, Integrator i, Interpretator in, Coordinate coord, Direction d,  int xp){
        good_robot r=new good_robot(a, i, in, coord, xp, d, this);
        robots.add(r);
    }
    
    boolean check_if_prog_exists(String path)
    {
        File f = new File(path);
        return f.exists() && !f.isDirectory();
    }
    
    boolean add_prog(String p)
    {
        if (check_if_prog_exists(p))
        {
            Algorithm a = new Algorithm(p);
            progs.put(a.getname(), a);
            return true;
        }
        else 
            return false;
    }
    boolean add_prog(String n, String p)
    {
        if (check_if_prog_exists(p))
        {
            Algorithm a = new Algorithm(n, p);
            progs.put(n, a);
            System.out.println("Algorithm added: "+ a.out());
            return true;
        }
        else 
            return false;
    }    
    void launch_prog(String n, int rob)
    {
        System.out.println("Launching program '"+ n+"'...");
        Algorithm alg=progs.get(n);
        good_robot cur=robots.get(rob);
        Interpretator in=cur.getInterpr();
        if (alg.getState()==0)
        {
            System.out.println("NO FILE");
            //raise error;
        }
        else
            in.translate(n, cur);
    }
    
    void del_prog(String n)
    {
        progs.remove(n);
        System.out.println("Program '"+n+"' deleted");
    }
    
}
