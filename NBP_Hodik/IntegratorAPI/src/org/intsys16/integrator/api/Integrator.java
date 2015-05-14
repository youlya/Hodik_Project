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
import org.openide.util.lookup.Lookups;

/**
 *
 * @author Julia
 */
public abstract class Integrator {
    public abstract ObservableList<String> getRobotsNames();
    public abstract ObservableList<String> getLastProgramsTitles(); // ранее открытые 
    public abstract ObservableList<String> getRobotProgramsTitles(String robotName);
    public abstract void createNewRobot(String newRobotName);  // или возвращает Object
    public abstract int getPlanetsNumber();
    public abstract ObservableList<String> getPlanetsNames();
    public abstract void loadProgramms(String robotName, ObservableList<String> selectedPrograms);
    public abstract void loadNewProgram(String robotName, int planetId);
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
            logger.log(Level.WARNING, "Cannot get Integrator object. " 
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
          "Hodik", "Sue", "Matthew", "Hannah", "Stephan", "Denise", "Mike", "Tatyana");
        
        @Override
        public ObservableList<String> getRobotsNames() {
            return robotsNames;
        }
        @Override
        public ObservableList<String> getLastProgramsTitles() {
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
            programs.add("Program" + currentTimeMillis()/ (rand.nextInt(20) + 10));
            programs.add("Program" + currentTimeMillis()/ (rand.nextInt(20) + 10));
            programs.add("Program" + currentTimeMillis()/ (rand.nextInt(20) + 10));  
            
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
                "Planet1", "Planet2", "Planet3", "Planet4", "Planet5");
            return planets; 
        }
        @Override
        public void loadProgramms(String robotName, ObservableList<String> selectedPrograms) {
            Logger.getLogger(getClass().getName()).
                    log(Level.INFO, "Loading programs {0} for {1}...",
                    new Object[]{selectedPrograms.toString(), robotName});
        }
        @Override
        public void loadNewProgram(String robotName, int planetId) {
            Logger.getLogger(getClass().getName()).
                    log(Level.INFO, "Loading new program for {0} on the planet {1}...",
                    new Object[]{robotName, planetId + 1}); 
        }
    }
}
