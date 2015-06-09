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
import org.intsys16.gamelogic.XMLParser.Info;

/**
 *
 * @author jbenua
 */
public class Unit {
    String name;
    Map<String, Algorithm> progs;
    ArrayList <good_robot> robots;
    
    public Unit(String n)
    {
        name=n;
        robots=new ArrayList();
        progs=new HashMap<>();
    }
    public ArrayList<String> getProgs()
    {
        return new ArrayList(progs.keySet());
    }
    public String getName()
    {
        return name;
    }
    
    public good_robot getAvatar(Field a)
    {
        for (good_robot i : robots)
        {
            if (i.getField()==a)
                return i;
        }
        return null;
    }
    
    public void add_robot(Field a, Interpretator in, Coordinate coord, Direction d,  int hp, Scores s){
        //Scores s = new Scores();
        good_robot r=new good_robot(a, in, coord, hp, d, this, s);
        robots.add(r);
    }
    boolean check_if_prog_exists(String path)
    {
        File f = new File(path);
        return f.exists() && !f.isDirectory();
    }
    public boolean add_prog(String p)
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
    public boolean add_prog(String n, String p)
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
    public void launch_prog(String n, int rob)
    {
        System.out.println("Launching program '"+ n+"'...");
        Algorithm alg=progs.get(n);
        good_robot cur=robots.get(rob);
        // Interpretator in=cur.getInterpr();
        alg.translate(alg.getname(), alg.getPath(), cur); //java.lang.NullPointerException
    }  
    public void del_prog(String n)
    {
        progs.remove(n);
        System.out.println("Program '"+n+"' deleted");
    }
    /*public ArrayList<Info> save()
    {
        ArrayList<Info> info=new ArrayList();
        for (good_robot i : robots)
        {
            Info temp=new Info(name, i.getLevel(), i.getCoord(), i.HP, i.score);
            temp.loadMobs(i.getField());
            info.add(temp);
        }
        return info;
    }*/
}
