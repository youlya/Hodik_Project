/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.editorwindow;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.Serializable;
import java.util.logging.Level;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JToolBar;
import org.intsys16.GameObjectUtilities.AbstractProgram;
import org.intsys16.GameObjectUtilities.ProgramSaveCapability;
import org.netbeans.api.settings.ConvertAsProperties;
import org.netbeans.core.spi.multiview.CloseOperationState;
import org.netbeans.core.spi.multiview.MultiViewDescription;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.MultiViewElementCallback;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.UndoRedo;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;

/**
 * Top component which displays something.
 */
//@ConvertAsProperties(
//        dtd = "-//org.intsys16.editorwindow//Editor//EN",
//        autostore = false
//)
//@TopComponent.Description(
//        preferredID = "TextEditorTopComponent",
//        //iconBase = "org/intsys16/editorwindow/editor24.png",
//        persistenceType = TopComponent.PERSISTENCE_ALWAYS
//)
//@TopComponent.Registration(mode = "editor", openAtStartup = false)
//@ActionID(category = "Window", id = "org.intsys16.editorwindow.TextEditorTopComponent")
//@ActionReference(path = "Menu/Window" /*, position = 333 */)
//@TopComponent.OpenActionRegistration(
//        displayName = "#CTL_TextEditorAction",
//        preferredID = "TextEditorTopComponent"
//)
@MultiViewElement.Registration(
        displayName = "#LBL_TextDisplayName",
        //iconBase = "nl/cloudfarming/client/farm/model/house.png",
        mimeType = "application/multieditor",
        persistenceType = TopComponent.PERSISTENCE_NEVER,
        preferredID = "TextView",
        position = 100)
@Messages({
    "CTL_TextEditorAction=Text Editor",
    "CTL_TextEditorTopComponent=Text Editor Window",
    "HINT_TextEditorTopComponent=This is a Text Editor window",
    "LBL_TextDisplayName=Text view"
})
public final class TextEditorTopComponent extends TopComponent implements MultiViewElement {
    
    private MultiViewElementCallback callback = null;
    private JToolBar toolbar = new JToolBar();
    private TopComponent multiPanel;
    private static JFXPanel fxPanel;
    private TextArea programText;
    private BorderLayout borderLayout = new BorderLayout();
    //private final InstanceContent content = new InstanceContent();
    //private Lookup proxyLookup;
    private Lookup lookup;
    //private ProgramNode progNode;
    
    public TextEditorTopComponent() {
        java.util.logging.Logger.getLogger(getClass().getName()).log(Level.WARNING, 
                "Empty constructor for {0} was called", getClass().getName());
    }
    public TextEditorTopComponent(Lookup lookup) {
        this.lookup = lookup;
        initComponents();
        setName(Bundle.CTL_TextEditorTopComponent());        
        setToolTipText(Bundle.HINT_TextEditorTopComponent());       
        //associateLookup(progNode.getLookup());       
        //proxyLookup =  new ProxyLookup(new Lookup[]{new AbstractLookup(content)});
             
        //associateLookup(new AbstractLookup(content));
        /* example of usage 
        this.getLookup().lookup(ProgramNode.class).getProgramName();
        */      
        setLayout(borderLayout);
        init();
        //content.add(new ProgramSaveCapabilityImpl());
    }
    
    public void init() {
        fxPanel = new JFXPanel();
        add(fxPanel, BorderLayout.NORTH);  
        //fxPanel.setSize(fxPanel.getWidth(), borderLayout.);
        Platform.setImplicitExit(false);
        Platform.runLater(() -> createScene());      
    }
    
    private void createScene() {
        StackPane pane = new StackPane();
        programText = new TextArea(getLookup().lookup(AbstractProgram.class).getProgramText());      
        pane.getChildren().add(programText);
        fxPanel.setScene(new Scene(pane));
        programText.setMinSize(fxPanel.getWidth(), fxPanel.getHeight());
        //binding
        programText.textProperty().bindBidirectional(
                getLookup().lookup(AbstractProgram.class).programTextProperty());
    }
    
    public void setMultiPanel(TopComponent multiPanel) {
        this.multiPanel = multiPanel;  
    }
    
    /*
    public TextArea getProgramTextControl() {
        return programText;
    }
    */
    
//    private class ProgramSaveCapabilityImpl implements ProgramSaveCapability {
//
//        @Override
//        public String getProgramName() {
//            return getLookup().lookup(ProgramNode.class).getProgramName();
//        }
//
//        @Override
//        public String getProgram() {
//            return getLookup().lookup(ProgramNode.class).getProgramText();
//        }
//    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        //callback.updateTitle(Bundle.CTL_TextEditorTopComponent());

    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }
    
    @Override
    public JComponent getVisualRepresentation() {
        return this;
    }

    @Override
    public JComponent getToolbarRepresentation() {
        return toolbar;
    }

    @Override
    public Action[] getActions() {
        if(callback != null) {
            return callback.createDefaultActions();
        } else {
            return new Action[]{};
        }
    }

    @Override
    public Lookup getLookup() {
        return lookup;
    }

    @Override
    public void componentShowing() {
        
    }

    @Override
    public void componentHidden() {
        //
    }

    @Override
    public void componentActivated() {
        //callback.updateTitle(Bundle.CTL_TextEditorTopComponent());
    }

    @Override
    public void componentDeactivated() {
        //
    }

    @Override
    public UndoRedo getUndoRedo() {
        return UndoRedo.NONE;
    }

    @Override
    public void setMultiViewCallback(MultiViewElementCallback callback) {
        this.callback = callback;
    }

    @Override
    public CloseOperationState canCloseElement() {
        return CloseOperationState.STATE_OK;
    }
    
}
