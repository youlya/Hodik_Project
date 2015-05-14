/*
 * Choosing the robot [loading saved programs] or creating a new one
 */
package org.intsys16.startwindow;

import java.awt.AWTEvent;
import java.awt.EventQueue;
import static java.lang.System.currentTimeMillis;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.intsys16.integrator.api.Integrator;
import org.openide.LifecycleManager;
import org.openide.util.Lookup;
import org.openide.windows.WindowManager;

/**
 * FXML Controller class
 *
 * @author Julia
 */
public class StartWindowController extends AnchorPane implements Initializable {

    private Integrator integrator = Integrator.getIntegrator();
    private static final Logger logger = Logger.getLogger(StartTopComponent.class.getName());
    
    @FXML
    private AnchorPane root;
    @FXML
    private AnchorPane anchor;
    @FXML
    private ListView robotsList;
    @FXML
    private ListView planetsList;
    @FXML
    private Label label_programs;
    @FXML
    private VBox vbox;
    @FXML
    private Label label_hintAboutMap;
    @FXML
    private Button createButton;
    @FXML
    private Button loadButton;
    @FXML
    private TextField newrobotName;
    @FXML
    private ImageView planetView;
    @FXML
    private Button newProgrButton;
    
    private final int PLANETS_NUMBER = integrator.getPlanetsNumber();
    //add elements 
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        drawBackground();
    
        // only controls for choosing a robot are visible
        planetsList.setVisible(false);
        loadButton.setVisible(false);
        newProgrButton.setVisible(false);
        createButton.setDisable(true);
       
        // initializing a list with robots
        robotsList.setItems(integrator.getRobotsNames());
        robotsList.setPlaceholder(new Label("No robots yet"));
        robotsList.getSelectionModel().clearSelection(); /** @why works after cleaning and rebuilding the project */
              
        
        /**
         *  @notice
         *  lambda expressions work when sources level is not less then 1.8
         *  ( 'moduleName'/Properties/Sources/)
         */
        // choosing a robot
        robotsList.setOnMouseClicked((event) -> { /** @todo find right event listener (selected item changed) */
            // initial state
            planetView.setVisible(false);
            newProgrButton.setVisible(false);
            vbox.getChildren().clear();
            
            Object selectedRobot = robotsList.getSelectionModel().getSelectedItem();
                                 
            // loading can be done soon after choosing a program
            loadButton.setVisible(true);
            loadButton.setDisable(true);           
          
            // loading programs
            final ObservableList<String> programs = 
                    integrator.getRobotProgramsTitles(selectedRobot.toString());
            
            if (!programs.isEmpty()) {  
                // set the planets list to the initial state and hide
                planetsList.setVisible(false);
                planetsList.getSelectionModel().selectFirst();
                
                // showing programs for the selected robot as checkboxes
                label_programs.setText("Programs available for " + selectedRobot + ":");                                            
                for (int i = 0; i < programs.size(); i++)
                {
                    CheckBox chb = new CheckBox(programs.get(i));
                    chb.setTextFill(Color.WHITE);                    
                    vbox.getChildren().add(chb);
                    chb.setOnMouseClicked((e) -> programsCheckedEvent());
                }  
                // enabling to start a new program for this robot
                newProgrButton.setVisible(true);
            }
            else {
                label_programs.setText("No programs available for " + selectedRobot + ".\n"
                        + "Choose a planet below for " + selectedRobot 
                        + " to start:");
                loadPlanetsList();
            }          
        });
       
        // new robot name input
        newrobotName.setOnKeyTyped((event) -> {
            createButton.setDisable(false);
            label_hintAboutMap.setText("After the creation you should choose a planet where to start.");
            label_hintAboutMap.setTextFill(Color.WHITE);
            if (newrobotName.getText().isEmpty())
            {
                createButton.setDisable(true);
                label_hintAboutMap.setText("");
            }
        });
        
        // creating a new robot       
        createButton.setOnMouseClicked((event) -> {
            integrator.createNewRobot(newrobotName.getText());
            
            // setting controls for the creation to the initial state
            createButton.setDisable(true);
            newrobotName.setText("");
            label_hintAboutMap.setText("");
            
            // showing this new robot in the list
            robotsList.getSelectionModel().selectLast(); 
            robotsList.scrollTo(robotsList.getItems().size() - 1);
                       
            // clear everything around  /** @temporary delete when appropriate event listener for robotsList is found */
            label_programs.setText("");
            vbox.getChildren().clear();
            planetsList.setVisible(false);
            planetsList.getSelectionModel().selectFirst();
            planetView.setVisible(false);
            newProgrButton.setVisible(false);
        });
        
        // starting a new program for the robot
        newProgrButton.setOnMouseClicked((event) -> { 
            // set invisible the checkbox list with prev programs for that robot
            newProgrButton.setVisible(false);
            vbox.getChildren().clear();
            
            label_programs.setText("Choose a planet:");
            loadPlanetsList();
        });
        
        // choosing a planet
        planetsList.setOnMouseClicked((event) -> {
            planetView.setVisible(true);
                                 
            planetView.setImage(new Image(getClass().getResourceAsStream(
                    "pl" + 
                    (planetsList.getSelectionModel().getSelectedIndex() + 1)
                     + ".png")));
            planetView.setEffect(new Reflection());
            
            loadButton.setDisable(false);
        });
        
        // load the program(s) for the selected robot
        loadButton.setOnMouseClicked((event) -> {
            Object selectedRobot = robotsList.getSelectionModel().getSelectedItem();
            
            // loading checked programs
            if (!vbox.getChildren().isEmpty()) {
                ObservableList<String> selectedPrograms = 
                        FXCollections.observableArrayList();
                for (int i = 0; i < vbox.getChildren().size(); i++) 
                {
                    CheckBox chb = (CheckBox) vbox.getChildren().get(i);
                    if (chb.isSelected()) 
                        selectedPrograms.add(chb.getText());
                }
                integrator.loadProgramms(selectedRobot.toString(), selectedPrograms);
            }               
            // loading a new program on the selected planet
            else {
                
                integrator.loadNewProgram(selectedRobot.toString(), 
                        planetsList.getSelectionModel().getSelectedIndex());
            }
            toInitState();
            changeScenery();
        });
      
    }
    private void changeScenery() {
        //StartTopComponent.getRegistry().getOpened()
        
        /* Window system can be used from the AWT thread only */
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                WindowManager.getDefault().findTopComponent("StartTopComponent").close();
                WindowManager.getDefault().findTopComponent("StMsgTopComponent").close();

                WindowManager.getDefault().findTopComponent("MapTopComponent").open();
                WindowManager.getDefault().findTopComponent("OutputTopComponent").open();
                WindowManager.getDefault().findTopComponent("EditorTopComponent").open();
            }
        });     
    }
    
    private void toInitState() {
        robotsList.getSelectionModel().selectFirst();
        
        createButton.setDisable(true);
        newrobotName.setText("");
        label_hintAboutMap.setText("");
        
        vbox.getChildren().clear();
        label_programs.setText("");
        
        planetsList.setVisible(false);
        planetView.setVisible(false);
        
        loadButton.setVisible(false);
        newProgrButton.setVisible(false);       
    }
    
    // enabling the load button if any program is checked and
    // otherwise disabling
    private void programsCheckedEvent() { 
        boolean smthChecked = false;
        for (int i = 0; i < vbox.getChildren().size(); i++) 
        {
            CheckBox chb = (CheckBox) vbox.getChildren().get(i);
            if (chb.isSelected()) {
                loadButton.setDisable(false);
                smthChecked = true;
                break;
            }
        }
        if (!smthChecked)
            loadButton.setDisable(true);
    }
    private void loadPlanetsList() {
        planetsList.setVisible(true);
        planetsList.getSelectionModel().selectFirst();
        planetsList.setItems(integrator.getPlanetsNames());
       
    }
    private void drawBackground() {
        root.setBackground(new Background(new BackgroundImage(
                new Image(getClass().getResourceAsStream("bg.jpg")), 
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, 
                new BackgroundSize(root.getWidth(), root.getHeight(), true, true, true, true))));
        
        drawWelcome();
        //root.setBackground(new Background(new BackgroundFill(Color.AZURE, null, null)));
    }
    
    private void drawWelcome() {       
        DropShadow dropshadow = new DropShadow();
        dropshadow.setOffsetY(3.0f);
        dropshadow.setColor(Color.color(0.4f, 0.4f, 0.4f));
        
        Rectangle rect = new Rectangle(450, 60, Color.LIGHTSKYBLUE);

        rect.setArcHeight(20);
        rect.setArcWidth(20);
        rect.setEffect(dropshadow);
        root.getChildren().add(rect);
        
        Text text = new Text();
        text.setLayoutX(10);
        text.setLayoutY(40);
        text.setEffect(dropshadow);
        text.setCache(true);
        text.setFill(Color.DARKSLATEGRAY);
        text.setText("Welcome to the Hodik IDE !");
        text.setFont(Font.font(null, FontWeight.BOLD, 32));
        root.getChildren().add(text);
    }
            
   
    
  
}
