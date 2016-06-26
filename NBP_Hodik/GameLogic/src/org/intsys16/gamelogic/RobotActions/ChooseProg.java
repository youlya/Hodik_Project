/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.RobotActions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.JDialog;
import org.intsys16.GameObjectUtilities.AbstractProgram;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;
import org.openide.filesystems.annotations.*;
import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;
import javax.annotation.processing.RoundEnvironment;
import java.util.Set;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.*;

/**
 *
 * @author Howard M.
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
    public ChooseProg(AbstractProgram context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
       // ImageIcon icon = new ImageIcon("/robot.png");
       
        String[] choices = { "A", "B", "C", "D", "E", "F" };
    String input = (String) JOptionPane.showInputDialog(null, "Choose",
        "Program choosing", JOptionPane.QUESTION_MESSAGE, icon, // Use
                                                                        // default
                                                                        // icon
        choices, // Array of choices
        choices[0]); // Initial choice
      //  NotifyDescriptor d = new NotifyDescriptor.
       // GeneratingItems generate;
      // JOptionPane.
        
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
    
//private class GeneratingItems extends LayerGeneratingProcessor {
//   // @Override
//    //protected boolean handleProcess(Set set, RoundEnvironment env) 
//        //    throws LayerGenerationException {
//        //Elements elements = processingEnv.getElementUtils();
//        //File f = layer(env).file();   
//        
//        return true;
//    }

    



