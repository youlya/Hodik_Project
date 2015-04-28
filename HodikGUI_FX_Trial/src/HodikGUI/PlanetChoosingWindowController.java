/*
 * If no previous program has been chosen, then this window is called 
 * and offers a list of maps [planets] for a user to choose
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
public class PlanetChoosingWindowController extends AnchorPane implements Initializable {

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
    
    // from here after submitting a choice the MainWindow is loaded
    
    //add events' handlers
    
}
