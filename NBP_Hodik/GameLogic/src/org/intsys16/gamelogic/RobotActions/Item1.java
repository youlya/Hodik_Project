/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.RobotActions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Edit",
        id = "org.intsys16.gamelogic.RobotActions.RobotMenu"
)
@ActionRegistration(
        iconBase = "org/intsys16/gamelogic/RobotActions/Item.png",
        displayName = "#CTL_Item1"
)
@ActionReference(path = "Menu/Robot", position = 3333, separatorBefore = 3283, separatorAfter = 3383)
@Messages("CTL_Item1=Программа 1")
public final class Item1 implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO implement action body
       
    }


    
}
