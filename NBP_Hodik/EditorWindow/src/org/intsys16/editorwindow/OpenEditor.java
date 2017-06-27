/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.editorwindow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javafx.collections.ObservableList;
import org.openide.util.Exceptions;
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
        else {

            for (String current_selected_program : selectedPrograms) {
                File f = new File("_resources\\robots\\programs\\" + current_selected_program);
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
                    
                }
                catch (FileNotFoundException ex) {
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

