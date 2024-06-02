/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guesthousemanagement;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author Hamza Barak
 */
public class RoomMainController implements Initializable {

    @FXML private ImageView logo; 
    
    String empID = "";
    
    public void setParameter(String empID)
    {
        this.empID = empID;
    }
    
    public void allRoomButtonPressed(ActionEvent event) throws IOException, SQLException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AllRoom.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        AllRoomController controller = loader.getController();
        controller.setParameter(empID);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    
    public void bookedRoomButtonPressed(ActionEvent event) throws IOException, SQLException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("BookedRooms.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        BookedRoomsController controller = loader.getController();
        controller.setParameter(empID);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    
    public void availableRoomButtonPressed(ActionEvent event) throws IOException, SQLException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AvailableHousesFromMainHouses.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        AvailableHousesFromMainHousesController controller = loader.getController();
        controller.setParameter(empID);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }        
      
    public void allRoomBookesButtonPressed(ActionEvent event) throws IOException, SQLException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("RoomBookees.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        RoomBookeesController controller = loader.getController();
        controller.setParameter(empID);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    } 
    
    public void backButtonPushed(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ReceptionistProfile.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        ReceptionistProfileController controller = loader.getController();
        controller.setParameter(empID);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        logo = new ImageView("logo1.png");
    }  
    
}
