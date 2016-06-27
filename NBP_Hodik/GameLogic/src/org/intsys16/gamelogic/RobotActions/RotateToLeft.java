/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.RobotActions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import org.intsys16.GameObjectUtilities.AbstractProgram;
import org.intsys16.GraphicMapAPI.GraphicMapAPI;

/**
 *
 * @author Georgiy
 */
@ActionID(
        category = "Edit",
        id = "org.intsys16.gamelogic.RobotActions.RotateToLeft"
)
@ActionRegistration(
        iconBase = "org/intsys16/gamelogic/RobotActions/left.png",
        displayName = "#CTL_RotateToLeft"
)
@ActionReference(path = "Menu/Robot/Rotate", position = 3233)
@Messages("CTL_RotateToLeft=Налево")
public final class RotateToLeft implements ActionListener {

    private final AbstractProgram context;
    private GraphicMapAPI GraphicMap = GraphicMapAPI.getGraphicMap();
    public RotateToLeft(AbstractProgram context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        try{
        GraphicMap.move("TURN_LEFT");
        //Logger.getLogger(RotateToRight.class.getName());
        //throw new ExceptionRR("Menu/Robot/RotateToRight");
        //throw new ExceptionA("I am Exception Alpha!");
        } 
         catch (Exception err) {
           //Exceptions.printStackTrace(err);
           java.util.logging.Logger.getLogger(getClass().getName()).log(Level.SEVERE, 
                err.getMessage(), getClass().getName());
        }
    }
}
