/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.RobotActions;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.*;
import org.intsys16.GameObjectUtilities.AbstractProgram;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import javax.swing.*;

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
              String[] choices = { "A", "B", "C", "D", "E", "F" };
 
    String input = (String) JOptionPane.showInputDialog(null, "Choose",
        "Program choosing", JOptionPane.QUESTION_MESSAGE, icon,  choices, choices[0]); 
 
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
    


    



