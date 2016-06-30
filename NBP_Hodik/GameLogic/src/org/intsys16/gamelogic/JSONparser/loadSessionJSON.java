/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.JSONparser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.intsys16.gamelogic.FieldControl.Coordinate;
import org.intsys16.gamelogic.FieldControl.Direction;
import static org.intsys16.gamelogic.FieldControl.Direction.UP;
import org.intsys16.gamelogic.RobotsControl.Scores;
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
    public List<mobsInfo> getMobs() {
        return mobList;
    }
    public List<obstaclesInfo> getObstacles() {
        return obstacleList;
    }
    public List<robotsInfo> getRobots() {
        return robotList;
    }
    public void loadSession(String JSONfileName){
        JSONParser parser = new JSONParser();
        Object obj;
        try {
            FileReader f = new FileReader("_resources\\sessions\\" + JSONfileName.substring(1, JSONfileName.length()));
            obj = parser.parse(f);
            JSONObject session = (JSONObject) obj;
            sessionName = (String) session.get("sessionName");
            mapNumber = (int) session.get("mapName");
            JSONArray mobs = new JSONArray();
            mobs = (JSONArray) session.get("mobs");
            for (int i = 0; i < mobs.size(); i++) {
                mobsInfo mob = new mobsInfo();
                JSONObject mobJSON = (JSONObject) mobs.get(i);
                mob.setMobActType((String) mobJSON.get("mobActType"));
                mob.setMobDamage((int) mobJSON.get("hp"));
                JSONArray coordinates = (JSONArray) mobJSON.get("coordinates");
                Coordinate c = new Coordinate ((int) coordinates.get(0), (int) coordinates.get(1));
                mob.setMobCoords(c);
                mobList.add(mob);
            }
            JSONArray obstacles = new JSONArray();            
            obstacles = (JSONArray) session.get("obstacles");
            for (int i = 0; i < obstacles.size(); i++) {
                obstaclesInfo obstacle = new obstaclesInfo();
                JSONObject obstacleJSON = (JSONObject) obstacles.get(i);
                obstacle.setObsDamage((int) obstacleJSON.get("hp"));
                JSONArray coordinates = (JSONArray) obstacleJSON.get("coordinates");
                Coordinate c = new Coordinate ((int) coordinates.get(0), (int) coordinates.get(1));
                obstacle.setObsCoords(c);
                obstacleList.add(obstacle);
            }
            JSONArray robots = new JSONArray();
            robots = (JSONArray) session.get("robots");
            for (int i = 0; i < robots.size(); i++) {
                robotsInfo robot = new robotsInfo();
                JSONObject robotJSON = (JSONObject) robots.get(i);
                robot.setRobotName((String) robotJSON.get("robotName"));
                robot.setRobotDirection((Direction) robotJSON.get("direction"));
                robot.setRobotHealth((int) robotJSON.get("health"));
                Scores score = new Scores();
                score.setEat_sc((int) robotJSON.get("nyam"));
                score.setObs_sc((int) robotJSON.get("bum"));
                score.set_Stepsc((int) robotJSON.get("steps"));
                robot.setRobotScore(score);
                JSONArray coordinates = (JSONArray) robotJSON.get("coordinates");
                Coordinate c = new Coordinate ((int) coordinates.get(0), (int) coordinates.get(1));
                robot.setRobotCoords(c);
                robotList.add(robot);
            }
        } catch (IOException | ParseException ex) {
            Exceptions.printStackTrace(ex);
        }      
    }
}
