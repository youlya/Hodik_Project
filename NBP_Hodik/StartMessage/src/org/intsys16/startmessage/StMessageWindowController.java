/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.startmessage;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

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
        
        welcomeText.setText("The Earth is in danger, and Robots "
                + "will be sent to the newly found planets in search of a life. "
                + "Are you able to write appropriate programs for them so "
                + "they could succeed and save the inhabitans of the Earth?");
       
       
    }    
    
}
