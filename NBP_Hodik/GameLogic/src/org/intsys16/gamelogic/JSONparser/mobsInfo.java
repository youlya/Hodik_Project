/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.JSONparser;
import org.intsys16.gamelogic.FieldControl.Coordinate;

/**
 *
 * @author Daria Artemova
 */
public class mobsInfo {
    private String actType;
    private int damage;
    private Coordinate coords;
    mobsInfo(){
        
    }
    mobsInfo(String aT, int d, Coordinate c){
        actType = aT;
        damage = d;
        coords = c;
    }
    public String getMobActType(){
        return actType;
    }
    public int getMobDamage(){
        return damage;
    }
    public Coordinate getMobCoords(){
        return coords;
    }
    public void setMobActType(String aT){
        actType = aT;
    }
    public void setMobDamage(int d){
        damage = d;
    }
    public void setMobCoords(Coordinate c){
        coords = c;
    }
}
