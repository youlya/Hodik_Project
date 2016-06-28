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
        id = "org.intsys16.programActions.RedoProgramAction"
)
@ActionRegistration(
        iconBase = "org/intsys16/programActions/redo.gif",        
        displayName = "#CTL_RedoProgramAction"
)
@ActionReferences({
    @ActionReference(path = "Menu/Debug", position = 1725, separatorAfter = 1737)
})
@NbBundle.Messages({
    "CTL_RedoProgramAction=Redo"
})
public class RedoProgramAction implements ActionListener{
      private final AbstractProgram context;

    public RedoProgramAction(AbstractProgram context) {
        this.context = context;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
