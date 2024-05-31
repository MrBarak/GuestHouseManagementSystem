/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guesthousemanagement;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Hamza Barak
 */
public class ReceptionistProfileController implements Initializable {

    @FXML private ImageView logo;
    @FXML private ImageView logo2;
    @FXML private ImageView welcome;
    
    @FXML private ImageView house;
    @FXML private ImageView room;
    @FXML private ImageView customer;
    @FXML private ImageView employee;
    @FXML private ImageView complaint;
    @FXML private ImageView account;
    
    @FXML private Label nameLabel;
    
    String empID = "";
    String firstName = "";
    String lastName = "";
    
    public void setParameter(String s)
    {
        empID = s;
        
        try
        {
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
            Statement stmt = conn.createStatement();
            
            ResultSet rs = stmt.executeQuery("select first_name, last_name from employee where empID = '" + empID + "';");
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
        loader.setLocation(getClass().getResource("HouseMain.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        HouseMainController controller = loader.getController();
        controller.setParameter(empID);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    @FXML
    public void roomImagePressed(MouseEvent event) throws IOException // this function is call when room image is pressed
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("RoomMain.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        RoomMainController controller = loader.getController();
        controller.setParameter(empID);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    @FXML
    public void customerImagePressed(MouseEvent event) throws IOException // this function is call when service image is pressed
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Customer.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        CustomerController controller = loader.getController();
        controller.setParameter(empID);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    @FXML
    public void employeeImagePressed(MouseEvent event) throws IOException // this function is call when bill image is pressed
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("EmpployeeMain.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        EmpployeeMainController controller = loader.getController();
        controller.setParameter(empID);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    @FXML
    public void complaintImagePressed(MouseEvent event) throws IOException, SQLException // this function is call when complaint image is pressed
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ResponseComplaint1.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        System.out.println("EMP ID in Profile : " + empID);
        ResponseComplaint1Controller controller = loader.getController();
        controller.setParameter(empID);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    @FXML
    public void accountImagePressed(MouseEvent event) throws IOException, SQLException // this function is call when account image is pressed
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ReceptionistAccount.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        System.out.println("Username in Profile : " + empID);
        ReceptionistAccountController controller = loader.getController();
        controller.setParameter(empID);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    //------------------------------------------ END Image Methods -----------------------------------------------------------
    
    
    
    //============================================ BUTTON METHODS ============================================================
    public void homeButtonPressed(ActionEvent event) throws IOException // this function is call when home Button is pushed
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("HouseMain.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        HouseMainController controller = loader.getController();
        controller.setParameter(empID);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();

    }
    public void roomButtonPressed(ActionEvent event) throws IOException // this function is call when room Button is pushed
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("RoomMain.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        RoomMainController controller = loader.getController();
        controller.setParameter(empID);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    public void customerButtonPressed(ActionEvent event) throws IOException // this function is call when service Button is pushed
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Customer.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        CustomerController controller = loader.getController();
        controller.setParameter(empID);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    public void employeeButtonPressed(ActionEvent event) throws IOException // this function is call when bill Button is pushed
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("EmpployeeMain.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        EmpployeeMainController controller = loader.getController();
        controller.setParameter(empID);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    public void complaintButtonPressed(ActionEvent event) throws IOException, SQLException // this function is call when complaint Button is pushed
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ResponseComplaint1.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        ResponseComplaint1Controller controller = loader.getController();
        controller.setParameter(empID);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    public void accountButtonPressed(ActionEvent event) throws IOException, SQLException // this function is call when acount Button is pushed
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ReceptionistAccount.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        ReceptionistAccountController controller = loader.getController();
        controller.setParameter(empID);

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
        logo2 = new ImageView("receptionistLogo.png");
        welcome = new ImageView("welcome3.png");
        
        house = new ImageView("houseR.png");
        room = new ImageView("roomR.png");
        customer = new ImageView("customerR.png");
        employee = new ImageView("employeeR.png");
        complaint = new ImageView("complaint.png");
        account = new ImageView("account.png");
        
    }    
    
}
