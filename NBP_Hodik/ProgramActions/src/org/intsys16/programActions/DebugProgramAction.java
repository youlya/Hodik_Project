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
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle;
/**
 *
 * @author vdoka
 */
@ActionID(
        category = "File",
        id = "org.intsys16.programActions.DebugProgramAction"
)
@ActionRegistration(
        iconBase = "org/intsys16/programActions/debug24.png",        
        displayName = "#CTL_DebugProgramAction"
)
@ActionReferences({
    @ActionReference(path = "Menu/Debug", position = 1525, separatorAfter = 1537)
})
@NbBundle.Messages({
    "CTL_DebugProgramAction=Start debug"
})
public final class DebugProgramAction implements ActionListener{
    private final AbstractProgram context;

    public DebugProgramAction(AbstractProgram context) {
        this.context = context;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(context.getDebugStat()==true)
        context.setDebugStat(true);
        else
        context.setDebugStat(false);
    }
    
}
