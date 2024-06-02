/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guesthousemanagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Hamza Barak
 */
public class GuestHouseManagement extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        scene.getStylesheets().add(css);
        
        Image image = new Image("logo1.png");
        
        stage.getIcons().add(image);
        
        stage.setTitle("HB Guest House Management System ");
        
        stage.setResizable(false);
        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
