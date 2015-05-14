/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.Interpretator;


import java.util.ArrayList;

/**
 *
 * @author micen
 */
public class Program {

    String name;
    ArrayList<CMD> cmdList;

    public Program(String s, ArrayList<CMD> list) {
        name = s;
        cmdList = list;
    }

    public String name() {
        return name;
    }

    public ArrayList<CMD> List() {
        return cmdList;
    }
}

