/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.sessionJSON;
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
import static org.intsys16.gamelogic.FieldControl.Direction.DOWN;
import static org.intsys16.gamelogic.FieldControl.Direction.LEFT;
import static org.intsys16.gamelogic.FieldControl.Direction.RIGHT;
import org.intsys16.gamelogic.RobotsControl.Scores;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openide.util.Exceptions;
//28.04.2017, добавлен swing для showMessageDialog
import javax.swing.*;

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
    //28.04.2017, функция преобразования строки в Direction (стоит перенести в методы Direction?)
//    private Direction chooseDirection(String temp)
//    {
//        switch(temp)
//        {
//            case "DOWN": return DOWN;
//            case "UP": return UP;
//            case "RIGHT": return RIGHT;
//            case "LEFT": return LEFT;   
//        }
//        return null;
//    }
    //
    public void loadSession(String JSONfileName){
        try {
            JSONParser parser = new JSONParser();
            Object obj;
            FileReader f = new FileReader("_resources\\sessions\\" + JSONfileName.substring(1, JSONfileName.length()));
            obj = parser.parse(f);
            JSONObject session = (JSONObject) obj;
            //28.04.2017, добавлено привидение к long объектов от JSON
            sessionName = (String) session.get("sessionName");
            //long temp = (long)session.get("mapName");
            mapNumber = (int)(long)session.get("mapName");
            //JOptionPane.showMessageDialog(null, mapNumber);
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
                obstacle.setObsDamage((int)(long)obstacleJSON.get("health"));
                JSONArray coordinates = (JSONArray) obstacleJSON.get("coordinates");
                Coordinate c = new Coordinate ((int) (long) coordinates.get(0), (int)(long)coordinates.get(1));
                obstacle.setObsCoords(c);
                obstacleList.add(obstacle);
            }
            JSONArray robots = new JSONArray();
            robots = (JSONArray) session.get("robots");
            for (int i = 0; i < robots.size(); i++) {
                robotsInfo robot = new robotsInfo();
                JSONObject robotJSON = (JSONObject) robots.get(i);
                robot.setRobotName((String) robotJSON.get("robotName"));   
                robot.setRobotDirection(Direction.chooseDirection((String)robotJSON.get("direction")));
               // JOptionPane.showMessageDialog(null, robot.getRobotDirection());
                robot.setRobotHealth((int)(long)robotJSON.get("health"));
                Scores score = new Scores();
                score.setEat_sc((int) (long)robotJSON.get("nyam"));
                score.setObs_sc((int) (long)robotJSON.get("bum"));
                score.set_Stepsc((int) (long)robotJSON.get("steps"));
                robot.setRobotScore(score);
                JSONArray coordinates = (JSONArray) robotJSON.get("coordinates");
                Coordinate c = new Coordinate ((int) (long)coordinates.get(0), (int) (long)coordinates.get(1));
                robot.setRobotCoords(c);
                robotList.add(robot);
            }
        } catch (IOException | ParseException  ex) {
            Exceptions.printStackTrace(ex);
        }      
    }
}
