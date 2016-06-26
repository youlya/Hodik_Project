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
public class obstaclesInfo {
    private int damage;
    private Coordinate coords;
    
    obstaclesInfo (){
        
    }
    obstaclesInfo (int d, Coordinate c){
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
}
