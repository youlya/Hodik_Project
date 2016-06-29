/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.JSONparser;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.intsys16.gamelogic.FieldControl.Coordinate;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openide.util.Exceptions;

/**
 *
 * @author Daria Artemova
 */
public class loadSessionJSON {
    private String sessionName;
    private int mapNumber; //номер планеты
    List<mobsInfo> mobList = new ArrayList();
    List<obstaclesInfo> obstacleList = new ArrayList();
    List<robotsInfo> robotList = new ArrayList();
    public loadSessionJSON(){
        
    }
    public loadSessionJSON (int m, List mbs, List obstcls, List rbts){
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
    public void loadSession(){
        JSONParser parser = new JSONParser();
        Object obj;
        try {
            obj = parser.parse(new FileReader("c:\\test.json"));
            JSONObject session = (JSONObject) obj;
            sessionName = (String) session.get("sessionName");
            mapNumber = (int) session.get("mapName");
            JSONArray mobs = new JSONArray();
            mobs = (JSONArray) session.get("mobs");
            Iterator<String> mobIter = mobs.iterator();
            while (mobIter.hasNext()) {
                mobsInfo mob = new mobsInfo();
                //дособирать инфу
            }
            JSONArray obstacles = new JSONArray();            
            obstacles = (JSONArray) session.get("obstacles");
            Iterator<String> obsIter = mobs.iterator();
            while (obsIter.hasNext()) {
                obstaclesInfo obstacle = new obstaclesInfo();
                //дособирать инфу
            }
            JSONArray robots = new JSONArray();
            robots = (JSONArray) session.get("robots");
            Iterator<String> robotIter = mobs.iterator();
            while (robotIter.hasNext()) {
                robotsInfo robot = new robotsInfo();
                //дособирать инфу
            }
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        } catch (ParseException ex) {
            Exceptions.printStackTrace(ex);
        }
        
        
    }
}
