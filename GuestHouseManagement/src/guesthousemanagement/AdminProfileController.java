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
public class AdminProfileController implements Initializable {

    @FXML private ImageView logo;
    @FXML private ImageView logo2;
    @FXML private ImageView welcome;
    
    @FXML private ImageView customer;
    @FXML private ImageView employee;
    @FXML private ImageView earning;
    @FXML private ImageView account;
    
    @FXML private Label label;
    
    String empID = "";
    
    public void setParameter(String empID) throws SQLException
    {
        this.empID = empID;
        
        Connection conn;
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
        Statement stmt = conn.createStatement();
        
        ResultSet rs = stmt.executeQuery("Select first_name, last_name from employee where empID = '"+empID+"';");
        rs.next();
        label.setText(rs.getString(1) + " " + rs.getString(2));
    }
    
    @FXML
    public void customerImagePressed(MouseEvent event) throws IOException, SQLException // this function is call when home image is pressed
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Customer.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        CustomerController controller = loader.getController();
        controller.setParameter(empID, "AdminProfile.fxml");

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    
    public void customerButtonPressed(ActionEvent event) throws IOException, SQLException // this function is call when acount Button is pushed
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Customer.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        CustomerController controller = loader.getController();
        controller.setParameter(empID, "AdminProfile.fxml");

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    
    @FXML
    public void employeeImagePressed(MouseEvent event) throws IOException, SQLException // this function is call when home image is pressed
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AdminEmployee.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        AdminEmployeeController controller = loader.getController();
        controller.setParameter(empID);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    
    public void employeeButtonPressed(ActionEvent event) throws IOException, SQLException // this function is call when acount Button is pushed
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AdminEmployee.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        AdminEmployeeController controller = loader.getController();
        controller.setParameter(empID);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    
    @FXML
    public void earningImagePressed(MouseEvent event) throws IOException, SQLException // this function is call when home image is pressed
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Earning.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        EarningController controller = loader.getController();
        controller.setParameter(empID);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    
    public void earningButtonPressed(ActionEvent event) throws IOException, SQLException // this function is call when acount Button is pushed
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Earning.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        EarningController controller = loader.getController();
        controller.setParameter(empID);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    
    @FXML
    public void accountImagePressed(MouseEvent event) throws IOException, SQLException // this function is call when home image is pressed
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AdminAccount.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        AdminAccountController controller = loader.getController();
        controller.setParameter(empID);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    
    public void accountButtonPressed(ActionEvent event) throws IOException, SQLException // this function is call when acount Button is pushed
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AdminAccount.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        AdminAccountController controller = loader.getController();
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
        logo2 = new ImageView("admin2.png");
        welcome = new ImageView("welcome3.png");
        
        earning = new ImageView("Earning.png");
        account = new ImageView("account.png");
        customer = new ImageView("customerR.png");
        employee = new ImageView("employeeR.png");
    }    
    
}
