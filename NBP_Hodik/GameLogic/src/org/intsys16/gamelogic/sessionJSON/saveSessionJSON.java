/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.sessionJSON;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    List<FieldObjectsInfoExceptRobot> obstacleList = new ArrayList();
    List<robotsInfo> robotList = new ArrayList();
    public saveSessionJSON(){
        
    }
    public saveSessionJSON (int m, List mbs, List obstcls, List rbts){
        Date date = new Date() ;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        sessionName = dateFormat.format(date);
        mapNumber = m;
        mobList = mbs;
        obstacleList = obstcls;
        robotList = rbts;
    }
    public String getSessionName(){
       return sessionName; 
    }        
    public void setSessionName(String sN){
        sessionName = sN;
    }
    public int getMapNumber(){
       return mapNumber; 
    }        
    public void setMapNumber(int m){
        mapNumber = m;
    }
    public void saveSession (){
        File file = new File("_resources\\sessions\\" + sessionName + ".json");
        JSONObject session = new JSONObject();
        session.put("sessionName", sessionName);
        session.put("mapName", mapNumber);
        JSONArray mobs = new JSONArray();
        JSONArray obstacles = new JSONArray();
        JSONArray robots = new JSONArray();
        for (int i = 0; i < robotList.size(); i++) {
            JSONObject robot = robotList.get(i).toJSON();
            robots.add(robot);
        }
        for (int i = 0; i < mobList.size(); i++) {
            JSONObject mob = mobList.get(i).toJSON();
            mobs.add(mob);
        }
        for (int i = 0; i < obstacleList.size(); i++){
            JSONObject obstacle = obstacleList.get(i).toJSON();
            obstacles.add(obstacle);
        }
        session.put("robots", robots);
        session.put("mobs", mobs);
        session.put("obstacles", obstacles);
        
        try (FileWriter writer = new FileWriter(file)){
            writer.write(session.toJSONString());
            writer.flush();
            writer.close();
            System.out.println("Файл сохранен!");
        } catch (IOException ex) {
            Logger.getLogger(org.intsys16.gamelogic.sessionJSON.saveSessionJSON.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }    
}