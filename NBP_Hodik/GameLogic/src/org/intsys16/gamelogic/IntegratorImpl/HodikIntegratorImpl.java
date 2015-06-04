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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.intsys16.gamelogic.FieldControl.Coordinate;
import org.intsys16.gamelogic.FieldControl.Direction;
import org.openide.util.lookup.ServiceProvider;
import org.intsys16.integrator.api.Integrator;
import org.intsys16.gamelogic.RobotsControl.Unit;
import org.intsys16.gamelogic.FieldControl.Field;
import org.intsys16.gamelogic.Interpretator.Interpretator;
import org.intsys16.gamelogic.RobotsControl.Scores;
import org.intsys16.gamelogic.RobotsControl.good_robot;
import org.intsys16.gamelogic.XMLParser.Info;
import org.intsys16.gamelogic.XMLParser.loadLevel;
import org.intsys16.gamelogic.XMLParser.mobInfo;
import org.openide.util.Exceptions;

/**
 *
 * @author Julia
 */
/** To see changes after editing this file you need to clean and build the project */
@ServiceProvider(
        service = Integrator.class,
        path = "HodikIntegrator")  //for the quick access via Lookups.forPath()*/
public class HodikIntegratorImpl extends Integrator {
    
    //private Map<String, Unit> rMap; //was Map<String, Robot> rMap
    //java.util.Timer timer;
    private ObservableList<Field> fields; //коллекция полей
    private ObservableList<Unit> units; //коллекция роботов
    private final loadLevel load = new loadLevel(); 
    private int level;
    Interpretator interp;
    //String RobotName;
    // нужные
    private ObservableList<String> selectedPrograms = null;
 
//======================================================================================    
    // для заглушек
    protected ObservableList<String> robotsNames = FXCollections.observableArrayList(
          "Ходик", "Юнна", "Женя", "Рина", "Коля", "Леша", "Лена", "Настя");
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
    public ObservableList<String> getSessionTitles() { 
        Random rand = new Random(currentTimeMillis());
        ObservableList<String> sessions = FXCollections.observableArrayList();
        sessions.add("Сессия" + currentTimeMillis()/ (rand.nextInt(20) + 10));
        sessions.add("Сессия" + currentTimeMillis()/ (rand.nextInt(20) + 10));
        sessions.add("Сессия" + currentTimeMillis()/ (rand.nextInt(20) + 10));
        return sessions;
    }
    @Override
    public ObservableList<String> getRobotProgramsTitles(String robotName) {
        Random rand = new Random(currentTimeMillis());
        ObservableList<String> programs = FXCollections.observableArrayList();
        programs.add("Программа" + currentTimeMillis()/ (rand.nextInt(20) + 10));
        programs.add("Программа" + currentTimeMillis()/ (rand.nextInt(20) + 10));
        programs.add("Программа" + currentTimeMillis()/ (rand.nextInt(20) + 10));
        programs.add("Программа" + currentTimeMillis()/ (rand.nextInt(20) + 10));
        programs.add("Программа" + currentTimeMillis()/ (rand.nextInt(20) + 10));
        programs.add("Программа" + currentTimeMillis()/ (rand.nextInt(20) + 10)); 
        
        if (robotsNames.indexOf(robotName) % 2 == 0) {  // для разнообразия)
            ObservableList<String> emptyProgramsList =
                    FXCollections.observableArrayList();
            return emptyProgramsList;
        }
        else
            return programs;
    }
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
            planets.add("Планета "+ (i + 1));
            
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
            String pathTo = "level" + level + ".xml";
            load.getDocument(pathTo);
            Info i= load.getInfo();
            List<mobInfo> mob = new ArrayList();
            mob=i.getMobs();
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
    public void loadSavedSession(String xmlMapName) {
        try {
            logger.log(Level.INFO, "Loading saved session [{0}]...", 
                    xmlMapName);
            load.getDocument(xmlMapName);
            Info i= load.getInfo();
            List<mobInfo> mob = new ArrayList();
            mob=i.getMobs();
            Unit u = new Unit(i.robotName);
            units.add(u);
            Coordinate c = new Coordinate(i.getX(), i.getY());
            Direction d=Direction.UP;
            Field F = new Field();
            fields.add(F);
            Scores s = i.score;
            units.get(0).add_robot(F, interp, c, d, i.HP, s);
        } catch (Exception ex) {
            //Exceptions.printStackTrace(ex);
            logger.log(Level.SEVERE, "ERROR: failed to load document", ex);
        }
    }
    @Override
    public Field getCurrentField() {
        return fields.get(0);
    }
    @Override
    public good_robot getCurrentRobot() {
        return units.get(0).getAvatar(fields.get(0));
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
    
    public void launchProgram(String programName)
    {
        units.get(0).launch_prog(programName, 0);
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
