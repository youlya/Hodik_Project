/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.RobotActions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.lang.annotation.Annotation;
import org.intsys16.GameObjectUtilities.AbstractProgram;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import org.intsys16.GraphicMapAPI.GraphicMapAPI;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;
import javax.swing.ActionMap;

@ActionID(
        category = "Edit",
        id = "org.intsys16.gamelogic.RobotActions.RotateToRight"
)
@ActionRegistration(
        iconBase = "org/intsys16/gamelogic/RobotActions/right.png",
        displayName = "#CTL_RotateToRight"
)
@ActionReference(path = "Menu/Robot/Rotate", position = 3333)
@Messages("CTL_RotateToRight=Направо")
public final class RotateToRight implements ActionListener {

    private final AbstractProgram context;
    private GraphicMapAPI GraphicMap = GraphicMapAPI.getGraphicMap();
    private ActionMap actionMap;
    // private static final Logger logger = Logger.getLogger(ProgramSaveAction.class.getName());
   // private static final Logger logger = Logger.getLogger(RotateToRight.class.getName());
    public RotateToRight(AbstractProgram context) {
       
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        try{
        GraphicMap.move("TURN_RIGHT");
    
   // actionMap.put("the-key", 
       // actionMap.put("the-key", new MyAction());
        //Logger.getLogger(RotateToRight.class.getName());
        //throw new ExceptionRR("Menu/Robot/RotateToRight");
        //throw new ExceptionA("I am Exception Alpha!");
    
        } 
        catch (Exception err) {
           //Exceptions.printStackTrace(err);
           java.util.logging.Logger.getLogger(getClass().getName()).log(Level.SEVERE, 
                err.getMessage(), getClass().getName());
        }
   
       
    }}

   