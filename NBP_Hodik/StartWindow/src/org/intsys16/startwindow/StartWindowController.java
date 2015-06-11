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
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.effect.Shadow;
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
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.intsys16.editorwindow.EditorMultiViewPanelCreation;
import org.intsys16.editorwindow.OpenEditor;
import org.intsys16.integrator.api.Integrator;
import org.openide.LifecycleManager;
import org.openide.util.Lookup;
import org.openide.windows.IOContainer;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import org.openide.windows.TopComponent;
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
    private Label label_planets;
    @FXML
    private Label label_sessions;
    @FXML
    private Label label_chooseRobot;
    @FXML
    private Label label_createRobot;
    @FXML
    private VBox vbox_programs;
    @FXML
    private VBox vbox_sessions;
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

    
    private final int PLANETS_NUMBER = integrator.getPlanetsNumber();
    //add elements 
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        drawBackground();
    
        // initializing text
        label_planets.setText(Bundle.CTL_ChoosePlanetLabel());
        label_chooseRobot.setText(Bundle.CTL_ChooseRobotLabel());
        label_createRobot.setText(Bundle.CTL_CreateRobotLabel());
        newrobotName.setPromptText(Bundle.CTL_CreationInputPrompt());
        loadButton.setText(Bundle.CTL_LoadButton());
        createButton.setText(Bundle.CTL_CreateButton());
        
                     
        // only controls for choosing a robot are visible
        label_planets.setVisible(false);
        planetsList.setVisible(false);
        planetView.setVisible(false);
        loadButton.setVisible(false);
        createButton.setDisable(true);        
        // and sessions if any
        loadSessions();
      
        // initializing a list with robots
        robotsList.setItems(integrator.getRobotsNames());
        robotsList.setPlaceholder(new Label(Bundle.CTL_NoRobotsMsg()));
        robotsList.getSelectionModel().clearSelection(); /** @why works after cleaning and rebuilding the project */
              
        
        /**
         *  @notice
         *  lambda expressions work when sources level is not less then 1.8
         *  ( 'moduleName'/Properties/Sources/)
         */
        // choosing a robot
        robotsList.setOnMouseClicked((event) -> { /** @todo find right event listener (selected item changed) */
            // initial state
            vbox_programs.getChildren().clear();
            
            label_planets.setVisible(true);
            loadPlanetsList();
            planetView.setVisible(true);
            planetView.setImage(new Image(getClass().getResourceAsStream(
                    "pl1.png")));
            planetView.setEffect(new Reflection());
            loadButton.setVisible(true);
            loadButton.setDisable(false);
            
            Object selectedRobot = robotsList.getSelectionModel().getSelectedItem();
           // loading programs
            final ObservableList<String> programs = 
                    integrator.getRobotProgramsTitles(selectedRobot.toString());
            
            if (!programs.isEmpty()) {  
               // showing programs for the selected robot as checkboxes
                label_programs.setText(Bundle.CTL_OpenProgramsForMsg() + " "
                                         + selectedRobot + ":");                                            
                for (int i = 0; i < programs.size(); i++)
                {
                    CheckBox chb = new CheckBox(programs.get(i));
                    chb.setTextFill(Color.WHITE);                    
                    vbox_programs.getChildren().add(chb);
//                    chb.setOnMouseClicked((e) -> programsCheckedEvent());
                }  
            }
            else {
                label_programs.setText("(" + Bundle.CTL_NoProgramsForMsg() + " "
                                        + selectedRobot + ")");
            }          
        });
       
        // new robot name input
        newrobotName.setOnKeyTyped((event) -> {
            createButton.setDisable(false);
            label_hintAboutMap.setText(Bundle.CTL_CreationHint());
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
            vbox_programs.getChildren().clear();
            label_planets.setVisible(false);
            planetsList.setVisible(false);
            planetsList.getSelectionModel().selectFirst();
            planetView.setVisible(false);
            loadButton.setDisable(true);
        });
        
//        // starting a new program for the robot
//        newProgrButton.setOnMouseClicked((event) -> { 
//            // set invisible the checkbox list with prev programs for that robot
//            newProgrButton.setVisible(false);
//            vbox.getChildren().clear();
//            
//            label_programs.setText("Choose a planet:");
//            loadPlanetsList();
//        });
        
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
        
        // loading a new session for the selected robot
        loadButton.setOnMouseClicked((event) -> {
            Object selectedRobot = robotsList.getSelectionModel().getSelectedItem();
            ObservableList<String> selectedPrograms = FXCollections.observableArrayList();
            
            // loading checked programs if any
            if (!vbox_programs.getChildren().isEmpty()) {               
                for (int i = 0; i < vbox_programs.getChildren().size(); i++) 
                {
                    CheckBox chb = (CheckBox) vbox_programs.getChildren().get(i);
                    if (chb.isSelected()) 
                        selectedPrograms.add(chb.getText());
                }               
            }
            integrator.loadNewSession(selectedRobot.toString(), /*selectedPrograms,*/ 
                        planetsList.getSelectionModel().getSelectedIndex());
            toInitState();
            changeScenery(selectedPrograms);
        });
      
    }
    private void changeScenery(ObservableList<String> selectedPrograms) {
        //StartTopComponent.getRegistry().getOpened()
        
        /* Window system can be used from the AWT thread only */
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                WindowManager.getDefault().findTopComponent("StartTopComponent").close();
                WindowManager.getDefault().findTopComponent("StMsgTopComponent").close();

                WindowManager.getDefault().findTopComponent("MapTopComponent").open();
                //WindowManager.getDefault().findTopComponent("OutputTopComponent").open();
 
                OpenEditor emptyEditor = new OpenEditor(selectedPrograms);
                
                IOContainer.getDefault().open();
//                InputOutput io =  IOProvider.getDefault().getIO("Hello", false);
//                io.getOut().println("Hi there");
//                io.getOut().close();
            }
        });     
    }
    
    private void toInitState() {
        robotsList.getSelectionModel().selectFirst();
        
        createButton.setDisable(true);
        newrobotName.setText("");
        label_hintAboutMap.setText("");
        
        vbox_programs.getChildren().clear();
        label_programs.setText("");
        
        label_planets.setVisible(false);
        planetsList.setVisible(false);
        planetView.setVisible(false);
        
        loadButton.setVisible(false);      
    }
    
//    // enabling the load button if any program is checked and
//    // otherwise disabling
//    private void programsCheckedEvent() { 
//        boolean smthChecked = false;
//        for (int i = 0; i < vbox_robots.getChildren().size(); i++) 
//        {
//            CheckBox chb = (CheckBox) vbox_robots.getChildren().get(i);
//            if (chb.isSelected()) {
//                loadButton.setDisable(false);
//                smthChecked = true;
//                break;
//            }
//        }
//        if (!smthChecked)
//            loadButton.setDisable(true);
//    }
    private void loadSessions() { /** @todo associate lookup (updating the list of saved sessions) */
        vbox_sessions.getChildren().clear();
        
        ObservableList<String> sessions = integrator.getSessionTitles();
        if (!sessions.isEmpty())
        {           
            Rectangle rect = new Rectangle(656, 339, 171, 27);
            rect.setFill(null);
            rect.setStroke(Color.GREY);
            rect.setStrokeWidth(2.1);
            rect.setStrokeType(StrokeType.OUTSIDE);
            rect.setArcHeight(20);
            rect.setArcWidth(20);
            root.getChildren().add(rect);
            label_sessions.setText(Bundle.CTL_OpenSessionMsg());
            label_sessions.setAlignment(Pos.CENTER);
            label_sessions.setEffect(new Blend(BlendMode.ADD));
            for (int i = 0; i < sessions.size(); i++)
            {
                Hyperlink link = new Hyperlink(sessions.get(i));       
                link.setTextFill(Color.ANTIQUEWHITE);
                link.setFont(new Font(14));
                link.setEffect(new Blend(BlendMode.ADD));
                link.setEffect(new Shadow(USE_PREF_SIZE, Color.AQUA));
                vbox_sessions.getChildren().add(link);
                link.setOnMouseClicked((e) -> {
                    // load this session
                    integrator.loadSavedSession(link.getText());
                    toInitState();
                    changeScenery(FXCollections.observableArrayList());
                });
            } 
        }
        else
            label_sessions.setText("(" + Bundle.CTL_NoSessionsMsg() + ")");
    }
    private void loadPlanetsList() {
        planetsList.setVisible(true);      
        planetsList.setItems(integrator.getPlanetsNames());
        planetsList.getSelectionModel().selectFirst();
       
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
        
        Rectangle rect = new Rectangle(530, 60, Color.LIGHTSKYBLUE); //en occupies 450

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
        text.setText(Bundle.CTL_WelcomeText());
        /** @todoVika bind the width of the rectangle with the text width */
        //rect.setWidth(text.//);
        text.setFont(Font.font(null, FontWeight.BOLD, 32));
        root.getChildren().add(text);
    }
            
   
    
  
}
