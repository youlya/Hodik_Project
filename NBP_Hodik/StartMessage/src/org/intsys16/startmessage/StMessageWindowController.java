/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.startmessage;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

/**
 * FXML Controller class
 *
 * @author Julia
 */
public class StMessageWindowController implements Initializable {
    @FXML
    private AnchorPane root;
    @FXML
    private AnchorPane anchor;
    @FXML
    private Label welcomeText;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        root.setBackground(new Background(new BackgroundImage(
                new Image(getClass().getResourceAsStream("stmsg_bg.jpg")), 
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, 
                new BackgroundSize(root.getWidth(), root.getHeight(), true, true, true, true))));
        
        welcomeText.setText(Bundle.CTL_StartText());
        welcomeText.setPrefHeight(200);
        welcomeText.setEffect(new Bloom());
        /** @todoVika bind the text width and hight to the window's width */
        
        
//        setSize();       
    }    
//    private void setSize() {
//        EventQueue.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                TopComponent tc = WindowManager.getDefault().findTopComponent("StMsgTopComponent");
//                tc.setBounds(new Rectangle(tc.getX() + 100, tc.getY(), 180, tc.getHeight()));
////                WindowManager.getDefault().findMode(tc).setBounds(
////                    new Rectangle(tc.getX() + 100, tc.getY(), 180, tc.getHeight()));
//            }
//        });     
//    }
}
