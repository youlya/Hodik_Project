/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.programActions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.intsys16.GameObjectUtilities.AbstractProgram;
import org.intsys16.GameObjectUtilities.ProgramSaveCapability;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.loaders.DataObject;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileChooserBuilder;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "File",
        id = "org.intsys16.programActions.ProgramSaveAction"
)
@ActionRegistration(
        iconBase = "org/intsys16/programActions/save24.png",
        displayName = "#CTL_ProgramSaveAction"
)
@ActionReferences({
    @ActionReference(path = "Menu/Program", position = 1450),
    @ActionReference(path = "Toolbars/File", position = 350),
    @ActionReference(path = "Loaders/content/unknown/Actions", position = 0) //?
})
@Messages({
    "CTL_ProgramSaveAction=Save program",
    "# {0} - windowname",
    "MSG_SAVE_DIALOG=Save {0}",
    "# {0} - Filename",
    "MSG_SaveFailed=Could not write to file {0}",
    "# {0} - Filename",
    "MSG_Overwrite=File {0} exists. Overwrite?",
    "# {0} - Filename",
    "LBL_SaveProgram=Save {0} as:"
})
public final class ProgramSaveAction implements ActionListener {

    private final AbstractProgram context;
    private static final Logger logger = Logger.getLogger(ProgramSaveAction.class.getName());

    public ProgramSaveAction(AbstractProgram context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        File f = new FileChooserBuilder(
                ProgramSaveAction.class).setTitle(Bundle.LBL_SaveProgram(context.getProgramName()))
                    .setFileFilter(new FileNameExtensionFilter("txt file", "txt"))
                            .setDefaultWorkingDirectory(new File(context.getProgramPath()))
                                .showSaveDialog();
        if (f != null) {
            if (!f.getAbsolutePath().endsWith(".txt")) {
                f = new File(f.getAbsolutePath() + ".txt");
            }
            try {
                if (!f.exists()) {
                    // the file doesn't exist; create it
                    if (!f.createNewFile()) {
                        DialogDisplayer.getDefault().notify(
                                new NotifyDescriptor.Message(
                                        Bundle.MSG_SaveFailed(f.getName())));
                        return;
                    }
                } else {
                    // the file exists; asks if it's okay to overwrite
                    Object userChose = DialogDisplayer.getDefault().notify(
                            new NotifyDescriptor.Confirmation(
                                    Bundle.MSG_Overwrite(f.getName())));
                    if (NotifyDescriptor.CANCEL_OPTION.equals(userChose)) {
                        return;
                    }
                }
                // Need getAbsoluteFile(), 
                // or X.png and x.png are different on windows
                String program = context.getProgramText();
                if (!program.isEmpty()) {
                    logger.log(Level.INFO, "Program saved to file {0}", f.getName());
                } else {
                    logger.log(Level.WARNING, "Saving empty file {0}", f.getName());
                }
                BufferedWriter out = new BufferedWriter(new FileWriter(f.getAbsoluteFile()));
                out.write(program);
                out.close();
                
            } catch (IOException ioe) {
                Exceptions.printStackTrace(ioe);
            }
        }
    }
}
