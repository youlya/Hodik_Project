/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.programActions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;
import org.intsys16.GameObjectUtilities.AbstractProgram;
import org.intsys16.integrator.api.Integrator;
import org.openide.loaders.DataObject;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "File",
        id = "org.intsys16.programActions.RunProgramAction"
)
@ActionRegistration(
        iconBase = "org/intsys16/programActions/runProg24.png",
        displayName = "#CTL_RunProgramAction"
)
@ActionReferences({
    @ActionReference(path = "Menu/Program", position = 1425, separatorAfter = 1437),
    @ActionReference(path = "Toolbars/File", position = 375)
})
@Messages("CTL_RunProgramAction=Run Program")
public final class RunProgramAction implements ActionListener {

    private final AbstractProgram context;
    private static final Logger logger = Logger.getLogger(ProgramSaveAction.class.getName());

    public RunProgramAction(AbstractProgram context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        //check if this program was added to the robot
        Integrator.getIntegrator().launchProgram(context.getProgramPath());
    }
}
