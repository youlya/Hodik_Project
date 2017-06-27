/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.RobotActions;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.intsys16.GameObjectUtilities.AbstractProgram;
import org.intsys16.integrator.api.Integrator;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import javax.swing.*;

import org.intsys16.gamelogic.RobotsControl.robot;

/**
 *
 * @author Georgiy
 */

@ActionID(
        category = "Edit",
        id = "org.intsys16.gamelogic.RobotActions.ChooseProg"
)
@ActionRegistration(
        
        iconBase = "org/intsys16/gamelogic/RobotActions/list.png",
        displayName = "#CTL_ChooseProg"
       
)
@ActionReference(path = "Menu/Robot/Choose Program", position = 3333)
@Messages("CTL_ChooseProg=Выбрать программу")
public final class ChooseProg implements ActionListener  {

    private final AbstractProgram context;
    private final ImageIcon icon = createImageIcon("robot.png","ROBOT");
 // private final ImageIcon item = createImageIcon("Item.png","Item");
    public ChooseProg(AbstractProgram context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
          try{
              robot currRobot = (robot) Integrator.getIntegrator().getCurrentRobot();
              ObservableList<String> programs = FXCollections.observableArrayList( 
              Integrator.getIntegrator().getRobotProgramsTitles(currRobot.getName()));//robot name is used now 
    String input = (String) JOptionPane.showInputDialog(null, "Choose",
        "Run program", JOptionPane.QUESTION_MESSAGE, icon,  programs.toArray(), programs.toArray()[0]); 
    Integrator.getIntegrator().launchProgram("_resources\\robots\\programs\\"+ 
            input.substring(0, input.length()));
 
        } 
         catch (Exception err) {
      
           java.util.logging.Logger.getLogger(getClass().getName()).log(Level.SEVERE, 
                err.getMessage(), getClass().getName());
        }
      
        
    }
    protected ImageIcon createImageIcon(String path,
                                           String description) {
    java.net.URL imgURL = getClass().getResource(path);
    if (imgURL != null) {
        return new ImageIcon(imgURL, description);
    } else {
        System.err.println("Couldn't find file: " + path);
        return null;
    }
}
}