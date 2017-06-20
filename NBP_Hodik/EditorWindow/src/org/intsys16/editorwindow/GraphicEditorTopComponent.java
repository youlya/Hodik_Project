/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.editorwindow;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.logging.Level;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JToolBar;
import org.intsys16.GameObjectUtilities.AbstractProgram;
import org.netbeans.core.spi.multiview.CloseOperationState;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.MultiViewElementCallback;
import org.openide.awt.UndoRedo;
import org.openide.util.Lookup;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;

/**
 * Top component which displays something.
 */
//@ConvertAsProperties(
//        dtd = "-//org.intsys16.editorwindow//GraphicEditor//EN",
//        autostore = false
//)
//@TopComponent.Description(
//        preferredID = "GraphicEditorTopComponent",
//        //iconBase="SET/PATH/TO/ICON/HERE", 
//        persistenceType = TopComponent.PERSISTENCE_ALWAYS
//)
//@TopComponent.Registration(mode = "editor", openAtStartup = false)
//@ActionID(category = "Window", id = "org.intsys16.editorwindow.GraphicEditorTopComponent")
//@ActionReference(path = "Menu/Window" /*, position = 333 */)
//@TopComponent.OpenActionRegistration(
//        displayName = "#CTL_GraphicEditorAction",
//        preferredID = "GraphicEditorTopComponent"
//) 
@MultiViewElement.Registration(
        displayName = "#LBL_GraphicDisplayName",
        //iconBase = "nl/cloudfarming/client/farm/model/house.png",
        mimeType = "application/multieditor",
        persistenceType = TopComponent.PERSISTENCE_NEVER,
        preferredID = "GraphicView",
        position = 200)
@Messages({
    "CTL_GraphicEditorAction=Graphic Editor",
    "CTL_GraphicEditorTopComponent=Graphic Editor Window",
    "HINT_GraphicEditorTopComponent=This is a GraphicEditor window",
    "LBL_GraphicDisplayName=Graphic view"
})
public final class GraphicEditorTopComponent extends TopComponent implements MultiViewElement {

    private MultiViewElementCallback callback = null;
    private JToolBar toolbar = new JToolBar();
    private TopComponent multiPanel;
    private static JFXPanel fxPanel;
    //private ProgramNode progNode;
    public Lookup lookup;
    private DnD dragNDrop = new DnD(500, this);
    
    public GraphicEditorTopComponent() {
        java.util.logging.Logger.getLogger(getClass().getName()).log(Level.WARNING, 
                "Empty constructor for {0} was called", getClass().getName());
    }
    public GraphicEditorTopComponent(Lookup lookup) {
        this.lookup = lookup;
        initComponents();
        setName(Bundle.CTL_GraphicEditorTopComponent());
        setToolTipText(Bundle.HINT_GraphicEditorTopComponent());
        //associateLookup(progNode.getLookup());
        setLayout(new BorderLayout());
        init();
    }
    public void init() {
        fxPanel = new JFXPanel();
        add(fxPanel, BorderLayout.NORTH);   
        Platform.setImplicitExit(false);
        fxPanel.updateUI();
        Platform.runLater(() -> createScene());
    }
    
  
    
    private void createScene() {      ///создаем поле для стрелок
        Scene scene = new Scene(dragNDrop);
        fxPanel.setScene(scene);
        scene.widthProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) -> {
                dragNDrop.setPrefWidth((double)newSceneWidth);
            });
        scene.heightProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) -> {
                dragNDrop.setPrefHeight((double)newSceneHeight);
            });
        setCommandSequence(); // уже что-то есть в text area
        
        
    }
    public ArrayList<String> getCommandSequence() {
        return dragNDrop.getSequence();
    }
    public void setCommandSequence() {
        ArrayList<String> commands = new ArrayList<>();
        commands = getLookup().lookup(ProgramNode.class).getSequence(); //в commands запихиваем последовательность команд
        //if(dragNDrop.isCommand(programLine))
        dragNDrop.setSequence(commands);
       
        /* if (dragNDrop.sequence.isEmpty()==true)
             fxPanel.updateUI(); //очистить панель?
        
        else {
             
         int i = dragNDrop.commands.size();
          while(dragNDrop.sequence.isEmpty()==false)
          {
              dragNDrop.deleteItem(dragNDrop.getPicture(dragNDrop.commands.get(i), true, i));
              i--;
          }}*/ }
        
    public void setMultiPanel(TopComponent multiPanel) {
      this.multiPanel = multiPanel;
    }

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
        //callback.updateTitle(Bundle.CTL_GraphicEditorTopComponent());
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
     dragNDrop.repaint();
    }

    @Override
    public void componentHidden() {
        //
    }

    @Override
    public void componentActivated() {
        //setCommandSequence(); 
    }

    @Override
    public void componentDeactivated() {
        
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
