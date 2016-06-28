/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.JSONparser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.intsys16.gamelogic.FieldControl.Coordinate;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Daria Artemova
 */
public class saveSessionJSON {
    private String sessionName;
    private int mapNumber; //номер планеты
    List<mobsInfo> mobList = new ArrayList();
    List<obstaclesInfo> obstacleList = new ArrayList();
    List<robotsInfo> robotList = new ArrayList();
    saveSessionJSON(){
        
    }
    saveSessionJSON (String sN, int m, List mbs, List obstcls, List rbts){
        sessionName = sN;
        mapNumber = m;
        mobList = mbs;
        obstacleList = obstcls;
        robotList = rbts;
    }
    public void saveSession (){
        Date date = new Date() ;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        sessionName = dateFormat.format(date);
        File file = new File(sessionName + ".json");
        JSONObject session = new JSONObject();
        session.put("sessionName", sessionName);
        session.put("mapName", mapNumber);
        JSONArray mobs = new JSONArray();
        JSONArray obstacles = new JSONArray();
        JSONArray robots = new JSONArray();
        for (int i = 0; i < robotList.size(); i++) {
            JSONObject robot = new JSONObject();
            robot = robotList.get(i).toJSON(robotList.get(i));
            robots.add(robot);
        }
//        for () {
//            JSONObject mob = new JSONObject();
//            mob.put("mobActType", act_type);
//            mob.put("hp", damage);
//            JSONArray coords = new JSONArray();
//            coords.add(x);
//            coords.add(y);
//            mob.put("coordinates", coords);
//            mobs.add(mob);
//        }
//        for (){
//            JSONObject obstacle = new JSONObject();
//            obstacle.put("health", damage);
//            JSONArray coords = new JSONArray();
//            coords.add(x);
//            coords.add(y);
//            obstacle.put("coordinates", coords);
//            obstacles.add(obstacle);
//        }
        try (FileWriter writer = new FileWriter(file)){
            writer.write(session.toJSONString());
            writer.flush();
            writer.close();
            System.out.println("Файл сохранен!");
        } catch (IOException ex) {
            Logger.getLogger(org.intsys16.gamelogic.JSONparser.saveSessionJSON.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }    
}