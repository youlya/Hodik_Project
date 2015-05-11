/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.RobotsControl;

/**
 *
 * @author jbenua
 */
public class Algorithm {
    String name;
    String path;
    int state=0; // 0 - empty, 1 - file path
    public Algorithm(String p)
    {
        path=p;
        String[] a = path.split("/");
        String[] temp=(a[a.length-1]).split("\\.");
        name=temp[0];
        state=1;
    }
    public Algorithm(String n, String p)
    {
        name=n;
        path=p;
        state=1;
    }
    public int getState()
    {
        return state;
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
}
