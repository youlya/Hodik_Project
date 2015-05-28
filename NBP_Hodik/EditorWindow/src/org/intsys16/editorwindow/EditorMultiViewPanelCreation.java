/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.editorwindow;

import java.awt.Image;
import java.io.Serializable;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import org.netbeans.core.spi.multiview.CloseOperationState;
import org.netbeans.core.spi.multiview.MultiViewDescription;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.MultiViewElementCallback;
import org.netbeans.core.spi.multiview.MultiViewFactory;
import org.openide.awt.UndoRedo;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.openide.windows.TopComponent;

/**
 *
 * @author Julia
 */
public class EditorMultiViewPanelCreation {
   
    TopComponent tc;
    
    public EditorMultiViewPanelCreation() {
        MultiViewTextEditorDescription text_editor = new MultiViewTextEditorDescription();
        MultiViewGraphicEditorDescription graph_editor = new MultiViewGraphicEditorDescription();
        
        MultiViewDescription dsc[] = {text_editor, graph_editor};
        tc = MultiViewFactory.createMultiView(dsc, dsc[0]);
        
        text_editor.setMultiViewPanel(tc);
        graph_editor.setMultiViewPanel(tc);          
    }
    
    public TopComponent getEditor() {
        return tc;
    }

    private class MultiViewTextEditorDescription implements MultiViewDescription, Serializable {
        private TextEditorTopComponent tc = new TextEditorTopComponent();
        
        public void setMultiViewPanel(TopComponent multiPanel) {
            tc.setMultiPanel(multiPanel);
        }
        
        @Override
        public MultiViewElement createElement() {
            return tc;
        }
        @Override
        public String preferredID() {
            return "TextView";
        }
        @Override
        public int getPersistenceType() {
            return TopComponent.PERSISTENCE_NEVER;
        }
        @Override
        public String getDisplayName() {
            return Bundle.CTL_TextDisplayName();
        }
        @Override
        public Image getIcon() {
            return null;
        }
        public HelpCtx getHelpCtx() {
            return HelpCtx.DEFAULT_HELP;
        }
    }
    private class MultiViewGraphicEditorDescription implements MultiViewDescription, Serializable {
        private GraphicEditorTopComponent tc = new GraphicEditorTopComponent();
        
        public void setMultiViewPanel(TopComponent multiPanel) {
            tc.setMultiPanel(multiPanel);
        }
        
        @Override
        public MultiViewElement createElement() {
            return tc;
        }
        @Override
        public String preferredID() {
            return "GraphicView";
        }
        @Override
        public int getPersistenceType() {
            return TopComponent.PERSISTENCE_NEVER;
        }
        @Override
        public String getDisplayName() {
            return Bundle.CTL_GraphicDisplayName();
        }
        @Override
        public Image getIcon() {
            return null;
        }
        public HelpCtx getHelpCtx() {
            return HelpCtx.DEFAULT_HELP;
        }
    }
}
