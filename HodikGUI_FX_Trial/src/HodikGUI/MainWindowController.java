/*
 * Main IDE Window
 */
package HodikGUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Julia
 */
public class MainWindowController extends AnchorPane implements Initializable {

    @FXML
    TextArea editor;
    //add other elements 
    
    private HodikFXMain application;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       //initialize elements here 
       // (move from the fxml and set proper values:
       // panels, menubar and etc should not have fixed sizes, but should 
       // occupy some parts regarding their parent elements)
    }  
    
    public void setApp(HodikFXMain application){
        this.application = application;
    }
    
    //add events' handlers
}
