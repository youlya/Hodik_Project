/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.RobotActions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
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

/**
 *
 * @author Howard M.
 */

@ActionID(
        category = "Edit",
        id = "org.intsys16.gamelogic.RobotActions.ChooseProg"
)
@ActionRegistration(
         key = "ChooseProg",
        iconBase = "org/intsys16/gamelogic/RobotActions/1466107626_other.png",
        displayName = "#CTL_ChooseProg"
       
)
@ActionReference(path = "Menu/Robot/Choose Program", position = 3333)
@Messages("CTL_ChooseProg=ChooseProgram")
public final class ChooseProg implements ActionListener  {

    private final AbstractProgram context;

    public ChooseProg(AbstractProgram context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        GeneratingItems generate;
        
    }
    
private class GeneratingItems extends LayerGeneratingProcessor {
    @Override
    protected boolean handleProcess(Set set, RoundEnvironment env) 
            throws LayerGenerationException {
        Elements elements = processingEnv.getElementUtils();
        //File f = layer(env).file();   
        
        return true;
    }

    }
}


