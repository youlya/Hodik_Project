/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.IntegratorImpl;

import static java.lang.System.currentTimeMillis;
import java.util.Map;
import java.util.Random;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.intsys16.gamelogic.FieldControl.Direction;
import org.openide.util.lookup.ServiceProvider;
import org.intsys16.integrator.api.Integrator;
import org.intsys16.gamelogic.RobotsControl.Unit;
import org.intsys16.gamelogic.FieldControl.Field;
import org.intsys16.gamelogic.XMLParser.loadLevel;

/**
 *
 * @author Julia
 */
@ServiceProvider(
        service = Integrator.class,
        path = "HodikIntegrator")  //for the quick access via Lookups.forPath()
public class HodikIntegratorImpl extends Integrator {
    
    private Map<String, Unit> rMap; //was Map<String, Robot> rMap
    //java.util.Timer timer;
    private ObservableList<Field> fields; //коллекция полей
    private ObservableList<Unit> units; //коллекция роботов
    private loadLevel load;
    private String levelname;
    
    /** @debug integrator works correctly when is called from the field_object */
    private ObservableList<String> robotsNames = FXCollections.observableArrayList(
          "Hodik", "Yunna", "Jbenya", "Rina", "Kolya", "Lesha", "Lena", "Nastya");
    
    
    //Заглушки
    @Override
    public ObservableList<String> getRobotsNames() {
        /** @debug integrator works correctly when is called from the field_object */
        return robotsNames;
    }
    @Override
    public ObservableList<String> getLastProgramsTitles() { // ранее открытые 
        Random rand = new Random(currentTimeMillis());
        ObservableList<String> programs = FXCollections.observableArrayList();
        programs.add("Program" + currentTimeMillis()/ (rand.nextInt(20) + 10));
        programs.add("Program" + currentTimeMillis()/ (rand.nextInt(20) + 10));
        programs.add("Program" + currentTimeMillis()/ (rand.nextInt(20) + 10));
        return programs;
    }
    @Override
    public ObservableList<String> getRobotProgramsTitles(String robotName) {
        Random rand = new Random(currentTimeMillis());
        ObservableList<String> programs = FXCollections.observableArrayList();
        programs.add("Program" + currentTimeMillis()/ (rand.nextInt(20) + 10));
        programs.add("Program" + currentTimeMillis()/ (rand.nextInt(20) + 10));
        programs.add("Program" + currentTimeMillis()/ (rand.nextInt(20) + 10));       
        return programs;
    }
    @Override
    public void createNewRobot(String newRobotName) {  // или возвращает робота
        /** @debug integrator works correctly when is called from the field_object */
        robotsNames.add(newRobotName);
        units.add(new Unit("NewRob"));
        units.get(0).add_robot();
    }
    
    // From hodikgit.integrator
    public HodikIntegratorImpl() throws Exception  {
       fields = FXCollections.observableArrayList(); //was new Vector<Field>();
       units = FXCollections.observableArrayList(); //was new Vector<Unit>();      
//     load = new loadLevel();
       
       
//       if (true) //выбрать имеющегося робота
//       {
//                load.getDocument(levelname);
//           //загрузить поле, поместить в вектор fields  //fields.add(new Field)
//           //загрузить робота, поместить в вектор units //units.add(new Unit)
//       } else 
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
