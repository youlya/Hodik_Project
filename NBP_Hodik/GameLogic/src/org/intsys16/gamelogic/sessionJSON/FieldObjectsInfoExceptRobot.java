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
 * editors: DimaIra
 */
//20170602 - убрано все, что касается изменения hp у робота при взаимодействии с объектом - информация уже задана в типе объекта
public class FieldObjectsInfoExceptRobot {
    //private int changeHPSize;//hp или damage
    private Coordinate coords;
    private String className;//чтобы различить препятствия и бонусы по имени класса
    
    
    public FieldObjectsInfoExceptRobot (){
        
    }
    public FieldObjectsInfoExceptRobot (/*int changeHPSize, */Coordinate coords, String className){
        //this.changeHPSize = changeHPSize;
        this.coords = coords;
        this.className = className;
    }
    public String getClassname()
    {
        return this.className;
    }
    public void setClassname(String classname)
    {
        this.className = classname;
    }
//    public int getObsDamage(){
//        return changeHPSize;
//    }
    public Coordinate getObsCoords(){
        return coords;
    }
//    public void setObsDamage(int d){
//        changeHPSize = d;
//    }    
    public void setObsCoords (Coordinate c){
        coords = c;
    }
    public JSONObject toJSON(){
        JSONObject obj = new JSONObject();
       // obj.put("changeHPSize", this.changeHPSize);
        obj.put("className", this.className);
        JSONArray coordinates = new JSONArray();
        coordinates.add(this.coords.getX());
        coordinates.add(this.coords.getY());
        obj.put("coordinates", coordinates);
        return obj;
    }
}
