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
import org.intsys16.gamelogic.RobotsControl.good_robot;
import org.intsys16.gamelogic.XMLParser.Info;
import org.intsys16.gamelogic.XMLParser.loadLevel;
import org.intsys16.gamelogic.XMLParser.mobInfo;
import org.intsys16.gamelogic.XMLParser.XMLobject;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;
import org.w3c.dom.*;
import java.io.File;
import java.io.FileFilter;
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
    good_robot currRobot;
    Interpretator interp = new Interpretator(); //???????
    Parser pars = new Parser("", currRobot);
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
    class MyFileFilter implements FileFilter {
    public boolean accept(File pathname) 
    {
        // проверям, что это файл и что он заканчивается на .txt 
       return pathname.isFile() && pathname.getName().endsWith(".txt");
    }
}
    File f = new File("_resources\\robots\\programs");
    ObservableList<String> programs = FXCollections.observableArrayList();
        String program = Bundle.CTL_Program();
     MyFileFilter filter = new MyFileFilter();
      File[] list = f.listFiles(filter);
      for(int i = 0; i<list.length; i++) {
          programs.add(program + " " + list[i]);
      }
      return programs;
            }
        @Override
    
    public ObservableList<String> getSessionTitles() { 
        Random rand = new Random(currentTimeMillis());
        ObservableList<String> sessions = FXCollections.observableArrayList(); 
        String session = Bundle.CTL_Session();
            for (int i = 0; i < 3; i++)
                sessions.add(session + currentTimeMillis()/ (rand.nextInt(20) + 10));
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
    public void loadSavedSession(String xmlMapName) {
        try {
            logger.log(Level.INFO, "Loading saved session [{0}]...", 
                    xmlMapName);
            load.getDocument(xmlMapName);
            Info i= load.getInfo();
            level = i.levelNumber;
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
    public void saveCurrentSession(String xmlPathNameToSave)
    {
        XMLobject obj = new XMLobject();
        obj.setcurrLevel(level);
        obj = this.getCurrentRobot().toXML(obj);        //good_robot
        obj = this.getCurrentField().toXML(obj);        //собрать мобов и препятствия
        obj = this.getCurrentRobot().score.toXML(obj);  //забрать счет
        obj.toXMLfile(xmlPathNameToSave);
        //сохранение программ для робота?
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
    public good_robot getCurrentRobot() {
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
        
        return pars.alphabet.get(i);
    
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
