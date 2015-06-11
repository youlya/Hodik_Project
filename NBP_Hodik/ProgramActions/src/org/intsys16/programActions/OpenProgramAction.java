/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.programActions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javafx.scene.control.TextArea;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.intsys16.editorwindow.EditorMultiViewPanelCreation;
import org.intsys16.editorwindow.TextEditorTopComponent;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileChooserBuilder;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import org.openide.util.Lookup;
import org.netbeans.core.spi.multiview.MultiViewElement;

@ActionID(
        category = "File",
        id = "org.intsys16.programActions.OpenProgramAction"
)
@ActionRegistration(
        iconBase = "org/intsys16/programActions/openProg24.png",        
        displayName = "#CTL_OpenProgramAction"
)
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 1300),
    @ActionReference(path = "Toolbars/File", position = 300)
})
@Messages({
    "CTL_OpenProgramAction=Open a Program",
    "# {0} - Filename",
    "MSG_OpenFailed=Could not open program {0}: need txt file",
    "MSG_OpenProgram=Open a program (txt file)"
})
public final class OpenProgramAction implements ActionListener {  
    @Override
    public void actionPerformed(ActionEvent e) {    
        File f = new FileChooserBuilder(
                OpenProgramAction.class).setTitle(Bundle.MSG_OpenProgram())
                    .setDefaultWorkingDirectory(new File("_resources\\robots\\programs\\NewProgram.txt"))
                    .setFileFilter(new FileNameExtensionFilter("txt file", "txt"))
                        .showOpenDialog();
        if (f != null ) {
            if (!f.getAbsolutePath().endsWith(".txt")) {
                DialogDisplayer.getDefault().notify(
                        new NotifyDescriptor.Message(
                                Bundle.MSG_OpenFailed(f.getName())));
            }
            else {
                BufferedReader in = null;
                try {                   
//                    TopComponent tc = WindowManager.getDefault().findTopComponent("TextEditorTopComponent");
//                    TextEditorTopComponent tc_editor = (TextEditorTopComponent)tc;

                    in = new BufferedReader(new FileReader(f.getAbsoluteFile()));
                    String t = "";
//                    String ls = System.getProperty("line.separator");
                    String fullProgText = "";

                    while ((t = in.readLine()) != null) {
//                        tc_editor.getProgramTextControl().appendText(t);
//                        tc_editor.getProgramTextControl().appendText(ls);
                        fullProgText += (t + "\n");
                        //fullProgText += ls;
                    }
                    in.close();
                    
                    TopComponent multiEditor_tc = new EditorMultiViewPanelCreation(
                            f.getName(), fullProgText, f.getAbsolutePath()).getEditor();
                    multiEditor_tc.setName(f.getName());
                    multiEditor_tc.open();
                    multiEditor_tc.requestActive();
                    
                } catch (FileNotFoundException ex) {
                    Exceptions.printStackTrace(ex);
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                } finally {
                    try {
                        in.close();
                    } catch (IOException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
            }           
        }
    }
}
