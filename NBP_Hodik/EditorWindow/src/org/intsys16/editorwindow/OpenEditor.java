/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.editorwindow;

import java.io.File;
import javafx.collections.ObservableList;
import org.openide.windows.TopComponent;

/**
 *
 * @author Julia
 */
public class OpenEditor {
    public OpenEditor(ObservableList<String> selectedPrograms) {
        //todo
        if (selectedPrograms.isEmpty()) {
            File f = new File("_resources\\robots\\programs\\NewProgram.txt");
            TopComponent multiEditor_tc = new EditorMultiViewPanelCreation(
                    f.getName(), "", f.getAbsolutePath()).getEditor();
            multiEditor_tc.setName(f.getName());
            multiEditor_tc.open();
            multiEditor_tc.requestActive();
        }
    }
}
