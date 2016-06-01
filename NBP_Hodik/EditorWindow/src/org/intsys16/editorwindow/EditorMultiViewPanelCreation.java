/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.editorwindow;

import java.awt.Image;
import java.awt.event.FocusListener;
import java.io.Serializable;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import org.netbeans.api.settings.ConvertAsProperties;
import org.netbeans.core.api.multiview.MultiViews;
import org.netbeans.core.spi.multiview.CloseOperationState;
import org.netbeans.core.spi.multiview.MultiViewDescription;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.MultiViewElementCallback;
import org.netbeans.core.spi.multiview.MultiViewFactory;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.UndoRedo;
//import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;

/**
 *
 * @author Julia
 */
public class EditorMultiViewPanelCreation {
   
    private static TopComponent tc;
    ProgramNode progNode;
    
    public EditorMultiViewPanelCreation(String programName, String programText, String path) {
        progNode = new ProgramNode(programName, programText, path);
        tc = MultiViews.createMultiView("application/multieditor", progNode);
        progNode.setEditorTC(tc);
        
        
//        MultiViewTextEditorDescription text_editor = new MultiViewTextEditorDescription();
//        MultiViewGraphicEditorDescription graph_editor = new MultiViewGraphicEditorDescription();
//        
//        MultiViewDescription dsc[] = {text_editor, graph_editor};
//        tc =  MultiViewFactory.createMultiView(dsc, dsc[0]);
//        //tc.setActionMap(text_editor.getActionMap()); causes stack overflow
////        FocusListener[] fl = tc.getFocusListeners();
////        for (int i = 0; i < fl.length; i++)
////            tc.removeFocusListener(fl[i]);       
////        fl = text_editor.getFocusListeners();
////        for (int i = 0; i < fl.length; i++)
////            tc.addFocusListener(fl[i]);
//        
//        text_editor.setMultiViewPanel(tc);
//        graph_editor.setMultiViewPanel(tc);          
    }
    
    public TopComponent getEditor() {
        return tc;
    }
}
//    private class MultiViewTextEditorDescription implements MultiViewDescription, Serializable {
//        private TextEditorTopComponent tc = new TextEditorTopComponent(progNode);
//        
//        public void setMultiViewPanel(TopComponent multiPanel) {
//            tc.setMultiPanel(multiPanel);
//        }
////        public FocusListener[] getFocusListeners() {
////            return tc.getFocusListeners();
////        }
////        public Lookup getLookup() {
////            return tc.getLookup();
////        }
////        public ActionMap getActionMap() {
////            return tc.getActionMap();
////        }
//        
//        @Override
//        public MultiViewElement createElement() {
//            return tc;
//        }
//        @Override
//        public String preferredID() {
//            return "TextView";
//        }
//        @Override
//        public int getPersistenceType() {
//            return TopComponent.PERSISTENCE_NEVER;
//        }
//        @Override
//        public String getDisplayName() {
//            return Bundle.CTL_TextDisplayName();
//        }
//        @Override
//        public Image getIcon() {
//            return null;
//        }
//        public HelpCtx getHelpCtx() {
//            return HelpCtx.DEFAULT_HELP;
//        }
//    }
//    private class MultiViewGraphicEditorDescription implements MultiViewDescription, Serializable {
//        private GraphicEditorTopComponent tc = new GraphicEditorTopComponent(progNode);
//        
//        public void setMultiViewPanel(TopComponent multiPanel) {
//            tc.setMultiPanel(multiPanel);
//        }
//        
//        @Override
//        public MultiViewElement createElement() {
//            return tc;
//        }
//        @Override
//        public String preferredID() {
//            return "GraphicView";
//        }
//        @Override
//        public int getPersistenceType() {
//            return TopComponent.PERSISTENCE_NEVER;
//        }
//        @Override
//        public String getDisplayName() {
//            return Bundle.CTL_GraphicDisplayName();
//        }
//        @Override
//        public Image getIcon() {
//            return null;
//        }
//        public HelpCtx getHelpCtx() {
//            return HelpCtx.DEFAULT_HELP;
//        }
//    }
//    
//    void writeProperties(java.util.Properties p) {
//        // better to version settings since initial version as advocated at
//        // http://wiki.apidesign.org/wiki/PropertyFiles
//        p.setProperty("version", "1.0");
//        // TODO store your settings
//    }
//
//    void readProperties(java.util.Properties p) {
//        String version = p.getProperty("version");
//        // TODO read your settings according to their version
//    }
//}
