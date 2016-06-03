/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.RobotActions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import org.intsys16.GameObjectUtilities.AbstractProgram;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.intsys16.GraphicMapAPI.GraphicMapAPI;
import org.intsys16.integrator.api.Integrator;
import org.openide.loaders.DataObject;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Edit",
        id = "org.intsys16.gamelogic.RobotActions.RotateToRight"
)
@ActionRegistration(
        iconBase = "org/intsys16/gamelogic/RobotActions/rotate.png",
        displayName = "#CTL_RotateToRight"
)
@ActionReference(path = "Menu/Robot/Rotate", position = 3333)
@Messages("CTL_RotateToRight=Направо")
public final class RotateToRight implements ActionListener {

    private final AbstractProgram context;
    private GraphicMapAPI GraphicMap = GraphicMapAPI.getGraphicMap();
    // private static final Logger logger = Logger.getLogger(ProgramSaveAction.class.getName());
    public RotateToRight(AbstractProgram context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        GraphicMap.move("TURN_RIGHT");
    }
}
