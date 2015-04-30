/*
 * Choosing the robot [loading saved programs] or creating a new one
 */
package org.intsys16.startwindow;

import static java.lang.System.currentTimeMillis;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Vector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
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
import javafx.scene.layout.BackgroundFill;
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

/**
 * FXML Controller class
 *
 * @author Julia
 */
public class StartWindowController extends AnchorPane implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private AnchorPane anchor;
    @FXML
    private ListView robotsList;
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
    private final int PLANETS_NUMBER = 5;
    //add elements 
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //does not load an image
//        root.setBackground(new Background(new BackgroundImage(
//                new Image(getClass().getResourceAsStream("stw_bg.png")), 
//                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, null)));
        
        //root.setBackground(new Background(new BackgroundFill(Color.AZURE, null, null)));
        loadButton.setVisible(false);
        createButton.setDisable(true);
        
        //to load from integrator
        ObservableList<String> robot_names = FXCollections.observableArrayList(
          "Hodik", "Sue", "Matthew", "Hannah", "Stephan", "Denise", "Mike", "Tatyana");
        robotsList.setItems(robot_names);
        robotsList.setPlaceholder(new Label("No robots yet"));
        robotsList.getSelectionModel().clearSelection();
         
        Random rand = new Random(currentTimeMillis());
        
        robotsList.setOnMouseClicked((event) -> {
            Object selectedRobot = robotsList.getSelectionModel().getSelectedItem();
            label_programs.setText("Programs available for " + selectedRobot + ":");
            label_programs.autosize();
            //to load from integrator for the selected robot
            final ObservableList<String> programs = FXCollections.observableArrayList();
            programs.add("Program" + currentTimeMillis()/ (rand.nextInt(20) + 10));
            programs.add("Program" + currentTimeMillis()/ (rand.nextInt(20) + 10));
            programs.add("Program" + currentTimeMillis()/ (rand.nextInt(20) + 10));
            
            vbox.getChildren().clear();
            for (int i = 0; i < programs.size(); i++)
                vbox.getChildren().add(new CheckBox(programs.get(i)));
            
            loadButton.setVisible(true);
            loadButton.setDisable(true);
        });
               
        
        vbox.setOnMouseClicked((event) -> {         
            ObservableList<CheckBox> programs = FXCollections.observableArrayList();
            for (int i = 0; i < vbox.getChildren().size(); i++)
                programs.add((CheckBox) vbox.getChildren().get(i));
            
            // if anything is selected then enable the load button
        });
        
        newrobotName.setOnKeyTyped((event) -> {
            createButton.setDisable(false);
            label_hintAboutMap.setText("After the creation you should choose a planet where to start.");
            if (newrobotName.getText().isEmpty())
            {
                createButton.setDisable(true);
                label_hintAboutMap.setText("");
            }
        });
        
        
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
               
//        Vector<Image> planets = new Vector();
//        Vector<ImageView> pl_view = new Vector();
//        for (int i = 1; i <= PLANETS_NUMBER; i++)
//        {
//            planets.add(new Image("@pl" + i + ".png")) ;  //throws exception
//            pl_view.add(new ImageView(planets.get(i-1)));
//        }
//        
//        for (int i = 0; i < pl_view.size(); i++)
//        {
//            root.getChildren().add(pl_view.get(i));
//            pl_view.get(i).setEffect(new Reflection());
//        }
    }   
    
    
   
    
  
}
