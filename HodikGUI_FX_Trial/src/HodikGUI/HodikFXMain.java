/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tool | Templates
 * and open the template in the editor.
 */
package HodikGUI;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Julia
 */
public class HodikFXMain extends Application {
    
    private Stage stage;
    private final double MINIMUM_WINDOW_WIDTH = 600.0;
    private final double MINIMUM_WINDOW_HEIGHT = 415.0;
    
    
    @Override
    public void start(Stage primaryStage) {   
        try {
            stage = primaryStage;
            stage.setMinWidth(MINIMUM_WINDOW_WIDTH);
            stage.setMinHeight(MINIMUM_WINDOW_HEIGHT);
            stage.setTitle("Welcome to Hodik IDE!");
            loadMainWindow();
            primaryStage.show();
        } catch (Exception ex) {
            Logger.getLogger(HodikFXMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(HodikFXMain.class, (java.lang.String[])null);
    }
    
    private void loadMainWindow() {
        try {
            MainWindowController mainWindow = (MainWindowController) replaceSceneContent("MainWindow.fxml");
            mainWindow.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(HodikFXMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //add loading other windows
    
    private Initializable replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = HodikFXMain.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(HodikFXMain.class.getResource(fxml));
        AnchorPane page;
        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        } 
        Scene scene = new Scene(page, 600, 415); //should be a full screen 
        stage.setScene(scene);
        stage.sizeToScene();
        return (Initializable) loader.getController();
    }
}
