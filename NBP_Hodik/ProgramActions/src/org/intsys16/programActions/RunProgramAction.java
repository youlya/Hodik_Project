/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.programActions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.intsys16.GameObjectUtilities.AbstractProgram;
import org.intsys16.integrator.api.Integrator;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.loaders.DataObject;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.Exceptions;
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
@Messages({
    "CTL_RunProgramAction=Run Program",
    "MSG_RunningFailed=Can not run the program: field was not loaded"
})
public final class RunProgramAction implements ActionListener {

    private final AbstractProgram context;
    private static final Logger logger = Logger.getLogger(ProgramSaveAction.class.getName());

    public RunProgramAction(AbstractProgram context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        //check if this program was added to the robot
        try {
        BufferedWriter out = new BufferedWriter(new FileWriter(context.getProgramPath()));
        out.write(context.getProgramText());
        out.close();
        Logger.getLogger(RunProgramAction.class.getName())
                .log(Level.INFO, "Saving {0}", context.getProgramName());
        } catch (IOException ioe) {
            Exceptions.printStackTrace(ioe);
        }
        if (Integrator.getIntegrator().sessionIsLoaded())
            Integrator.getIntegrator().launchProgram(context.getProgramPath());
        else
            DialogDisplayer.getDefault().notify(
                    new NotifyDescriptor.Message(
                            Bundle.MSG_RunningFailed()));
    }
}
