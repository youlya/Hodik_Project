/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.JSONparser;
import org.intsys16.gamelogic.FieldControl.Coordinate;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Daria Artemova
 */
public class obstaclesInfo {
    private int damage;
    private Coordinate coords;
    
    public obstaclesInfo (){
        
    }
    public obstaclesInfo (int d, Coordinate c){
        damage = d;
        coords = c;
    }
    public int getObsDamage(){
        return damage;
    }
    public Coordinate getObsCoords(){
        return coords;
    }
    public void setObsDamage(int d){
        damage = d;
    }    
    public void setObsCoords (Coordinate c){
        coords = c;
    }
    public JSONObject toJSON(){
        JSONObject obj = new JSONObject();
        obj.put("health", this.getObsDamage());
        JSONArray coordinstes = new JSONArray();
        coordinstes.add(this.getObsCoords().getX());
        coordinstes.add(this.getObsCoords().getY());
        obj.put("coordinates", coordinstes);
        return obj;
    }
}
