/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.sessionJSON;
import org.intsys16.gamelogic.FieldControl.Coordinate;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Daria Artemova
 */
public class mobsInfo {
    private String actType;
    private int damage;
    private Coordinate coords;
    public mobsInfo(){
        
    }
    public mobsInfo(String aT, int d, Coordinate c){
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
    public JSONObject toJSON(){
        JSONObject obj = new JSONObject();
        obj.put("mobActType", this.getMobActType());
        obj.put("hp", this.getMobDamage());
        JSONArray coordinates = new JSONArray();
        coordinates.add(this.getMobCoords().getX());
        coordinates.add(this.getMobCoords().getY());
        obj.put("coordinates", coordinates);
        return obj;
    }
}
