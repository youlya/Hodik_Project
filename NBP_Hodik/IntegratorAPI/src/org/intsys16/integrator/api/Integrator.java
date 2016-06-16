/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.integrator.api;

import static java.lang.System.currentTimeMillis;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author Julia
 */
@Messages({  /* for localization */
    "CTL_Session=Session",
    "CTL_Program=Program",
    "CTL_RobotsNames=Hodik,Sue,Matthew,Hannah,Stephan,Denise,Mike,Tatyana",
    "CTL_PlanetsNames=Planet 1,Planet 2,Planet 3,Planet 4,Planet 5"
})
public abstract class Integrator {
    public abstract ObservableList<String> getRobotsNames(); // a list of all available robots
    public abstract ObservableList<String> getSessionTitles(); // names of xml files (the changed maps from Rina)
    public abstract ObservableList<String> getRobotProgramsTitles(String robotName); // a list of programs available for the selected robot
    public abstract void createNewRobot(String newRobotName);  // или возвращает Object
    public abstract int getPlanetsNumber(); // number of levels from Yunna
    public abstract ObservableList<String> getPlanetsNames();
    // loading the selected robot on the selected map 
    // with or without opening selected programs (available for that robot) 
    public abstract void loadNewSession(String robotName, /*ObservableList<String> selectedPrograms,*/ int planetId);
    public abstract ObservableList<String> getSelectedPrograms(); // programs selected when loading new session (for the Editor Window to open)
    public abstract void loadSavedSession(String xmlMapName); // xml file from Rina
    public abstract void saveCurrentSession(String xmlPathNameToSave); //save session to XML file
    public abstract int getLevel();
    public abstract void launchProgram(String programName);
    public abstract Object getCurrentField();
    public abstract Object getCurrentRobot();
    public abstract boolean sessionIsLoaded();
    public abstract String getCommandAt(int i);
    
    // etc
    
    public static Integrator getIntegrator() {
        /**
         * The Lookup is a Map, with Class objects as keys and
         * instances of those Class objects as values. In the line
         * below the Lookup searches for the implementation (registered
         * service provider) for this abstract class Integrator.
         */
        Integrator i = Lookups.forPath("HodikIntegrator").lookup(Integrator.class);
        if (i == null ) {
            Logger logger = Logger.getLogger(Integrator.class.getName());
            logger.log(Level.WARNING, "Cannot get an Integrator object. " 
                    + "The Default Integrator was built.");
            i = new DefaultIntegrator();
        }
        return i;
    }
    
    /** 
     * If HodikIntegratorImpl (main implementation) is not available    
     * or you need to turn it off (comment line with service provider annotation)
     * for debug purposes or whatever, this default integrator will be built.
     */
    private static class DefaultIntegrator extends Integrator {
        private ObservableList<String> robotsNames = FXCollections.observableArrayList(
          Bundle.CTL_RobotsNames().split(","));
        private ObservableList<String> selectedPrograms = null;
        
        @Override
        public ObservableList<String> getRobotsNames() {
            return robotsNames;
        }
        @Override
        public ObservableList<String> getSessionTitles() {
            Random rand = new Random(currentTimeMillis());
            ObservableList<String> sessions = FXCollections.observableArrayList();
            String session = Bundle.CTL_Session();
            /* default names */
            for (int i = 0; i < 3; i++)
                sessions.add(session + currentTimeMillis()/ (rand.nextInt(20) + 10));
           
            return sessions;
        }
        @Override
        public ObservableList<String> getRobotProgramsTitles(String robotName) {
            Random rand = new Random(currentTimeMillis());
            ObservableList<String> programs = FXCollections.observableArrayList();
            String program = Bundle.CTL_Program();
            /* default names */
            for (int i = 0; i < 6; i++)
                programs.add(program + currentTimeMillis()/ (rand.nextInt(20) + 10)); 
            /* alternation of programs lists: random names - empty list */
            if (robotsNames.indexOf(robotName) % 2 == 0) {
                ObservableList<String> emptyProgramsList =
                        FXCollections.observableArrayList();
                return emptyProgramsList;
            }
            else
                return programs;
        }
        @Override
        public void createNewRobot(String newRobotName) {
            robotsNames.add(newRobotName);
        }
        @Override
        public int getPlanetsNumber() {
            return 5;
        }
        @Override
        public ObservableList<String> getPlanetsNames() {
            ObservableList<String> planets = FXCollections.observableArrayList(
                Bundle.CTL_PlanetsNames().split(","));
            return planets; 
        }
        @Override
        public void loadNewSession(String robotName, /*ObservableList<String> selectedPrograms,*/ int planetId) {
            if (!selectedPrograms.isEmpty()) 
                Logger.getLogger(Integrator.class.getName()).
                    log(Level.INFO, "Loading programs {0} for {1} on the planet {2}...",
                            new Object[]{selectedPrograms.toString(), robotName, planetId + 1});
            else 
                Logger.getLogger(Integrator.class.getName()).
                    log(Level.INFO, "Loading new program for {0} on the planet {1}...",
                            new Object[]{robotName, planetId + 1});      
            //this.selectedPrograms = selectedPrograms;
        }
        @Override
        public void loadSavedSession(String xmlMapName) {
            Logger.getLogger(Integrator.class.getName()).
                log(Level.INFO, "Loading saved session [{0}]...",
                        xmlMapName); 
        }
        @Override
        public void saveCurrentSession(String xmlPathNameToSave)
        {
            //заглушка от Рины. доделать
        }
        @Override
        public ObservableList<String> getSelectedPrograms() {
            if (selectedPrograms == null)
                Logger.getLogger(Integrator.class.getName()).
                    log(Level.WARNING, "The method [getSelectedPrograms()] "
                            + "is not available in this context.");
            
            return selectedPrograms;                
        }
        
        @Override
        public int getLevel()
        {
            return 1;
        }
        
        @Override
        public void launchProgram(String programName)
        {
            Logger.getLogger(Integrator.class.getName()).
                    log(Level.INFO, "Loading program {0}", programName);
        }
        @Override
        public Object getCurrentField() {
            return null;
        }
        @Override
        public Object getCurrentRobot() {
            return null;
        }
        @Override
        public boolean sessionIsLoaded() {
            return false;
        }
        @Override
        public String getCommandAt(int i){
            return "";
        
        }
    }
}
