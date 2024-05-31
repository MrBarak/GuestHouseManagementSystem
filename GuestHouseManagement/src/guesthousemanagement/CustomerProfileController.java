/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guesthousemanagement;

import java.io.IOException;
import java.net.URL;
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
import java.sql.*;
import java.sql.DriverManager;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;


/**
 * FXML Controller class
 *
 * @author Hamza Barak
 */
public class CustomerProfileController implements Initializable {

    @FXML private ImageView logo;
    @FXML private ImageView logo2;
    @FXML private ImageView welcome;
    
    @FXML private ImageView house;
    @FXML private ImageView room;
    @FXML private ImageView service;
    @FXML private ImageView bill;
    @FXML private ImageView complaint;
    @FXML private ImageView account;
    
    @FXML private Label nameLabel;
    
    String username = "";
    String firstName = "";
    String lastName = "";
    
    public void setParameter(String s)
    {
        username = s;
        
        try
        {
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
            Statement stmt = conn.createStatement();
            
            ResultSet rs = stmt.executeQuery("select first_name, last_name from customer where email = '" + username + "';");
            rs.next();
            
            firstName = rs.getString(1);
            lastName = rs.getString(2);
            
            System.out.println(firstName + " " + lastName);
            nameLabel.setText(firstName + " " + lastName);
        }
        catch(Exception e)
        { 
            System.out.println(e.toString());
        }
    }
    
    
    //============================================== IMAGE Methods =====================================================
    @FXML
    public void homeImagePressed(MouseEvent event) throws IOException // this function is call when home image is pressed
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("House.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        HouseController controller = loader.getController();
        controller.setParameter(username);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    @FXML
    public void roomImagePressed(MouseEvent event) throws IOException // this function is call when room image is pressed
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Room.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        RoomController controller = loader.getController();
        controller.setParameter(username);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    @FXML
    public void serviceImagePressed(MouseEvent event) throws IOException // this function is call when service image is pressed
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Service.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        ServiceController controller = loader.getController();
        controller.setParameter(username);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    @FXML
    public void billImagePressed(MouseEvent event) throws IOException // this function is call when bill image is pressed
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Bills.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        BillsController controller = loader.getController();
        controller.setParameter(username);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    @FXML
    public void complaintImagePressed(MouseEvent event) throws IOException // this function is call when complaint image is pressed
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Complaint.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        System.out.println("Username in Profile : " + username);
        ComplaintController controller = loader.getController();
        controller.setParameter(username);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    @FXML
    public void accountImagePressed(MouseEvent event) throws IOException // this function is call when account image is pressed
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Account.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        System.out.println("Username in Profile : " + username);
        AccountController controller = loader.getController();
        controller.setParameter(username);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    //------------------------------------------ END Image Methods -----------------------------------------------------------
    
    
    
    //============================================ BUTTON METHODS ============================================================
    public void homeButtonPressed(ActionEvent event) throws IOException // this function is call when home Button is pushed
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("House.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        HouseController controller = loader.getController();
        controller.setParameter(username);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();

    }
    public void roomButtonPressed(ActionEvent event) throws IOException // this function is call when room Button is pushed
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Room.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        RoomController controller = loader.getController();
        controller.setParameter(username);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    public void serviceButtonPressed(ActionEvent event) throws IOException // this function is call when service Button is pushed
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Service.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        ServiceController controller = loader.getController();
        controller.setParameter(username);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    public void billButtonPressed(ActionEvent event) throws IOException // this function is call when bill Button is pushed
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Bills.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        BillsController controller = loader.getController();
        controller.setParameter(username);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    public void complaintButtonPressed(ActionEvent event) throws IOException // this function is call when complaint Button is pushed
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Complaint.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        ComplaintController controller = loader.getController();
        controller.setParameter(username);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    public void accountButtonPressed(ActionEvent event) throws IOException // this function is call when acount Button is pushed
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Account.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        AccountController controller = loader.getController();
        controller.setParameter(username);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    //--------------------------------------- END BUTTON METHODS ----------------------------------------------------------
    
   
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        logo = new ImageView("logo1.png");
        logo2 = new ImageView("customerNew.png");
        welcome = new ImageView("welcome3.png");
        
        house = new ImageView("house.png");
        room = new ImageView("room.png");
        service = new ImageView("service.png");
        bill = new ImageView("bill.png");
        complaint = new ImageView("complaint.png");
        account = new ImageView("account.png");
        
        //setFirstName();
        
       
    }    
    
}
