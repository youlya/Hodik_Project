/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.programActions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.intsys16.GameObjectUtilities.AbstractProgram;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import org.intsys16.integrator.api.Integrator;

@ActionID(
        category = "Edit",
        id = "org.intsys16.programActions.SaveSession"
)
@ActionRegistration(
        iconBase = "org/intsys16/programActions/saveSession.png",
        displayName = "#CTL_SaveSession"
)
@ActionReference(path = "Toolbars/File", position = 475)
@Messages("CTL_SaveSession=Сохранить сессию")
public final class SaveSession implements ActionListener {

    private final AbstractProgram context;

    public SaveSession(AbstractProgram context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        Integrator.getIntegrator().saveCurrentSession();
    }
}
