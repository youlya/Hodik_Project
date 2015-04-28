/*
 * Choosing the robot [loading saved programs] or creating a new one
 */
package HodikGUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Julia
 */
public class StartWindowController extends AnchorPane implements Initializable {

    @FXML
    //add elements 
    
    private HodikFXMain application;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    public void setApp(HodikFXMain application){
        this.application = application;
    }
    
    // If one of the previous programs was chosen, then the MainWindow is called here
    
    //add events' handlers
}
