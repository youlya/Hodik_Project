/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.IntegratorImpl;

import static java.lang.System.currentTimeMillis;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.intsys16.gamelogic.FieldControl.Coordinate;
import org.intsys16.gamelogic.FieldControl.Direction;
import org.openide.util.lookup.ServiceProvider;
import org.intsys16.integrator.api.Integrator;
import org.intsys16.gamelogic.RobotsControl.Unit;
import org.intsys16.gamelogic.FieldControl.Field;
import org.intsys16.gamelogic.Interpretator.Interpretator;
import org.intsys16.gamelogic.Interpretator.Parser;
import org.intsys16.gamelogic.RobotsControl.Scores;
import org.intsys16.gamelogic.RobotsControl.robot;
import org.intsys16.gamelogic.XMLParser.Info;
import org.intsys16.gamelogic.XMLParser.loadLevel;
import org.intsys16.gamelogic.XMLParser.mobInfo;
import org.intsys16.gamelogic.XMLParser.XMLobject;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;
import org.w3c.dom.*;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JOptionPane;
import org.intsys16.gamelogic.FieldControl.Field_object;
import org.intsys16.gamelogic.JSONparser.mobsInfo;
import org.intsys16.gamelogic.JSONparser.obstaclesInfo;
import org.intsys16.gamelogic.JSONparser.robotsInfo;
import org.intsys16.gamelogic.JSONparser.saveSessionJSON;
import org.intsys16.gamelogic.JSONparser.loadSessionJSON;
import org.intsys16.gamelogic.RobotsControl.Obstacles;
/**
 *
 * @author Julia
 */
/** To see changes after editing this file you need to clean and build the project */
@NbBundle.Messages({
    "LBL_Planet=Planet",
    "CTL_Session=Session",
    "CTL_Program=Program",
    "CTL_RobotsNames=Hodik,Sue,Matthew,Hannah,Stephan,Denise,Mike,Tatyana",
    "CTL_PlanetsNames=Planet 1,Planet 2,Planet 3,Planet 4,Planet 5"
})
@ServiceProvider(
        service = Integrator.class,
        path = "HodikIntegrator")  /* for the quick access via Lookups.forPath()*/
public class HodikIntegratorImpl extends Integrator {
    
    //private Map<String, Unit> rMap; //was Map<String, Robot> rMap
    //java.util.Timer timer;
    private ObservableList<Field> fields; //коллекция полей
    private ObservableList<Unit> units; //коллекция роботов
    private final loadLevel load = new loadLevel(); 
    private int level;

    private robot currRobot;
    private Interpretator interp = new Interpretator(); //???????
   // private Parser pars = new Parser("", currRobot);

    //Interpretator interp = new Interpretator();

    //String RobotName;
    // нужные
    private ObservableList<String> selectedPrograms = null;
 
//======================================================================================    
    // для заглушек
    protected ObservableList<String> robotsNames = FXCollections.observableArrayList(
            Bundle.CTL_RobotsNames().split(","));
    private static final Logger logger = Logger.getLogger(HodikIntegratorImpl.class.getName());
    
    //Заглушки
    @Override
    public ObservableList<String> getRobotsNames() {
        for (Unit i : units)
        {
            robotsNames.add(i.getName());
        }
        return robotsNames;
    }
     @Override
       public ObservableList<String> getRobotProgramsTitles(String robotName) { 
            /*class MyFileFilter implements FileFilter {
            public boolean accept(File pathname) 
            {
                // проверям, что это файл и что он заканчивается на .txt 
               return pathname.isFile() && pathname.getName().endsWith(".txt");
            }
        } */
        File f = new File("_resources\\robots\\programs");
        ObservableList<String> programs = FXCollections.observableArrayList();
        MyFileFilter filter = new MyFileFilter();
        try {
            String program = Bundle.CTL_Program();
            File[] list = f.listFiles(filter);
            for(int i = 0; i<list.length; i++) {
                programs.add(program + " " + list[i]);
            }            
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
        return programs;
    }
        @Override
    
    public ObservableList<String> getSessionTitles() {
        File f = new File("_resources\\sessions");
        ObservableList<String> sessions = FXCollections.observableArrayList();
        MyFileFilter filter = new MyFileFilter();
        try {
            String program = Bundle.CTL_Program();
            File[] list = f.listFiles();
            for(int i = 0; i<list.length; i++) {
                sessions.add(" " + list[i].toString().substring(list[i].toString().lastIndexOf("\\")+1, list[i].toString().length()));
            }            
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
        return sessions;
    }  
     /* public ObservableList<String> getRobotProgramsTitles(String robotName) { // составление рандомных названий для программ, оставлю на всякий случай
      Random rand = new Random(currentTimeMillis());
        ObservableList<String> programs = FXCollections.observableArrayList();
        String program = Bundle.CTL_Program();
            for (int i = 0; i < 6; i++)
                programs.add(program + currentTimeMillis()/ (rand.nextInt(20) + 10)); 
        
        if (robotsNames.indexOf(robotName) % 2 == 0) {  // для разнообразия)
            ObservableList<String> emptyProgramsList =
                    FXCollections.observableArrayList();
            return emptyProgramsList;
        }
        else
            return programs;
    } */
    @Override
    public void createNewRobot(String newRobotName) {  // или возвращает робота       
        robotsNames.add(newRobotName);
    }
    @Override
    public int getPlanetsNumber() {
        return 5;//ДОПИСАТЬ
    }
    @Override
    public ObservableList<String> getPlanetsNames() {
        ObservableList<String> planets = FXCollections.observableArrayList();
        for (int i = 0; i<getPlanetsNumber(); i++)
        {
            planets.add(Bundle.LBL_Planet() + " "+ (i + 1));
            
        }
        return planets;
    }
    @Override
    public void loadNewSession(String robotName, /*ObservableList<String> selectedPrograms,*/ int planetId) {
//        level = planetId + 1;
//        String pathTo = "level" + level + ".xml";
//        try {
//            load.getDocument(pathTo);
//        } catch (Exception ex) {
//            Exceptions.printStackTrace(ex);
//        }
        try {
//            if (!selectedPrograms.isEmpty())
//                logger.log(Level.INFO, "Loading programs {0} for {1} on the planet {2}...",
//                        new Object[]{selectedPrograms.toString(), robotName, planetId + 1});
//            else
//                logger.log(Level.INFO, "Loading new program for {0} on the planet {1}...",
//                        new Object[]{robotName, planetId + 1});
//            this.selectedPrograms = selectedPrograms;
            level = planetId + 1;
            //does not connect with the field /* !! Somewhere here was an error with loading XML */
//            String pathTo = "_resources\\maps\\initial\\level" + level + ".xml";
//            load.getDocument(pathTo);
//            Info i= load.getInfo();
//            List<mobInfo> mob = new ArrayList();
//            mob=i.getMobs();
            Unit u = new Unit(robotName);
            units.add(u);
            Coordinate c = new Coordinate();
            Direction d=Direction.UP;
            Field F = new Field();
            fields.add(F);
            Scores s = new Scores();
            units.get(0).add_robot(F, interp, c, d, 100, s);
            //RobotName = robotName;        
        } catch (Exception ex) {
            //Exceptions.printStackTrace(ex);
            logger.log(Level.SEVERE, "ERROR: failed to load document", ex);
        }
        
        
    }
    @Override
    public void loadSavedSession(String JSONfileName) {
        loadSessionJSON obj = new loadSessionJSON();
        obj.loadSession(JSONfileName);
        level = obj.getMapNumber();
        List<mobsInfo> mobList = new ArrayList();
        List<obstaclesInfo> obstacleList = new ArrayList();
        List<robotsInfo> robotList = new ArrayList();
        mobList = obj.getMobs();
        obstacleList = obj.getObstacles();
        robotList = obj.getRobots();
        Field F = new Field();
        fields.add(F);
        for (int i = 0; i < robotList.size(); i++) {
            Unit u = new Unit(robotList.get(i).getRobotName());
            units.add(u);
            Coordinate coords = new Coordinate();
            coords = robotList.get(i).getRobotCoords();
            Direction dir = robotList.get(i).getRobotDirection();
            Scores score = robotList.get(i).getRobotScore();
            int health = robotList.get(i).getRobotHealth();
            units.get(i).add_robot(F, interp, coords, dir, health, score);
        }
        for (int i = 0; i < obstacleList.size(); i++) {
            Coordinate coords = new Coordinate();
            coords = obstacleList.get(i).getObsCoords();
            int damage = obstacleList.get(i).getObsDamage();
            Obstacles obs = new Obstacles (F, coords, damage);
        }
        for (int i = 0; i < mobList.size(); i++) {
            //заглушка для добавления плохих роботов
        }
    }
    @Override
    public void saveCurrentSession()
    {
        int map = level;
        
        Coordinate coordinates = new Coordinate();
        coordinates.setX(0);
        coordinates.setY(0);
        List<mobsInfo> mobList = new ArrayList();
        List<obstaclesInfo> obstacleList = new ArrayList();
        List<robotsInfo> robotList = new ArrayList();
        Field fieldObj = getCurrentField();
        List <Field_object> objects = fieldObj.getObjects();
        for (int i = 0; i < objects.size(); i++){
            /*
            эта хрень с if-ами ужасна. 
            она такая для того, чтобы отделить мобов от препятствий. 
            в будущем сделайте ровно, плизки 
            */
            if (!objects.get(i).getClass().getSimpleName().equalsIgnoreCase("robot")){
                if ((!objects.get(i).getClass().getSimpleName().equalsIgnoreCase("obstacles")) &&
                    (!objects.get(i).getClass().getSimpleName().equalsIgnoreCase("Stone")) &&
                    (!objects.get(i).getClass().getSimpleName().equalsIgnoreCase("Liquid")) &&
                    (!objects.get(i).getClass().getSimpleName().equalsIgnoreCase("Pit"))){
                        mobsInfo mob = new mobsInfo(objects.get(i).getActtype(), objects.get(i).getDamage(), objects.get(i).getCoord());
                        mobList.add(mob);
                }
                else {
                    obstaclesInfo obstacle = new obstaclesInfo(objects.get(i).getDamage(), objects.get(i).getCoord());
                    obstacleList.add(obstacle);
                }                
            }
            else {
//                robotsInfo robot = new robotsInfo(getCurrentRobot().getName(), getCurrentRobot().getCoord(), getCurrentRobot().getDir(), getCurrentRobot().getHP(), getCurrentRobot().getScore());
//                robotList.add(robot);
            }
        }              
        robotsInfo robot = new robotsInfo(getCurrentRobot().getName(), getCurrentRobot().getCoord(), getCurrentRobot().getDir(), getCurrentRobot().getHP(), getCurrentRobot().getScore());
        robotList.add(robot);
        
        saveSessionJSON obj = new saveSessionJSON (map, mobList, obstacleList, robotList);
        obj.saveSession();
        
        JOptionPane.showMessageDialog(null, "Session saved");
    }
    @Override
    public Field getCurrentField() {
        if (sessionIsLoaded())
            return fields.get(0);
        else {
            logger.log(Level.SEVERE, "No field");
            return new Field();
        }
    }
    @Override
    public robot getCurrentRobot() {
        if (!units.isEmpty())
            return units.get(0).getAvatar(fields.get(0));
        else {
            logger.log(Level.SEVERE, "No robot yet");
            return null;
        }
    }
    @Override
    public boolean sessionIsLoaded() {
        return !fields.isEmpty(); //robot?
    }
    @Override
    public ObservableList<String> getSelectedPrograms() {
        if (selectedPrograms == null)
            logger.log(Level.WARNING, "The method [getSelectedPrograms()] "
                            + "is not available in this context.");

        return selectedPrograms;                
    }
    
    @Override
    public int getLevel() {
        return level;
    }
    
    @Override
    public void launchProgram(String programPath)
    {
//        units.get(0).add_prog(programName);
//        units.get(0).launch_prog(programName, 0);
        interp.translate(programPath, getCurrentRobot());
        interp.Run();       
    }

    
    @Override
    public String getCommandAt(int i)
    {
        return Parser.getAlphabet().get(i);
    
    }

//=======================================================================================    
    // From hodikgit.integrator
    public HodikIntegratorImpl() throws Exception  {
       fields = FXCollections.observableArrayList(); //was new Vector<Field>();
       units = FXCollections.observableArrayList(); //was new Vector<Unit>();      
//     load = new loadLevel();
       
       
//       if (true) //выбрать имеющегося робота
//       {
          //Scanner sc = new Scanner(System.in);
          //levelname = 1;
//          int height=10;
//          int width=10;
//          Info i = new  Info();
//          i=load.getInfo();
//          List<mobInfo> mob = new ArrayList();
//          mob=i.getMobs();
//          int level=i.levelNumber;
//          int x=i.getX();
//          int y=i.getY();
//          int hp=i.getHP();
//          Coordinate c=new Coordinate(x, y);
//          Direction d=Direction.UP;
          
          //load.getDocument(levelname);
          
          //Field F=new Field(level, width, height);
          //units.get(0).add_robot(F, interp, c, d, hp);
          
//       } else 
//       {
           //createNewRobot
//       }
//       { 
//           //создать робота, поместить в вектор units
//           //выбрать планету и подгрузить поле, поместить в вектор fields
//       }
       
       
         
         /*Field field = new Field(10,10);
         rMap = new HashMap<String, Robot>();
         rMap.put("nasa",new Robot("nasa", 33));
         rMap.put("saturn",new Robot("saturn", 66));
         // for(int i=0;i<this.RobCollection.size();i++)
         //     System.out.print(this.RobCollection.get(i).RName);
         //myfr = new MainFr();
         LoginFrm logfr = new LoginFrm(this);
         logfr.setVisible(true);
         logfr.setLocationRelativeTo(null);*/
         
        
       /*Field field = new Field(10,10);
       rMap = new HashMap<String, Robot>();
       rMap.put("nasa",new Robot("nasa", 33));
       rMap.put("saturn",new Robot("saturn", 66));
              
       // for(int i=0;i<this.RobCollection.size();i++)
       //     System.out.print(this.RobCollection.get(i).RName);
       //myfr = new MainFr(); 
       LoginFrm logfr = new LoginFrm(this);
       logfr.setVisible(true); 
       logfr.setLocationRelativeTo(null);*/
    }
            
}
class MyFileFilter implements FileFilter {
    public boolean accept(File pathname) 
    {
        // проверям, что это файл и что он заканчивается на .txt 
       return pathname.isFile() && pathname.getName().endsWith(".txt");
    }
}