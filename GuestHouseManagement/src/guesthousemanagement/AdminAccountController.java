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
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Hamza Barak
 */
public class AdminAccountController implements Initializable {

    @FXML private ImageView logo; 
    
    String empID = "";
    String cnic = "";
    String mob = "";
    
    public void setParameter(String empID) throws SQLException
    {
        this.empID = empID;
        
        Connection conn;
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
        Statement stmt = conn.createStatement();
        
        ResultSet rs = stmt.executeQuery("select CNIC, phone_number from employee, employee_phone where employee.empID = '"+empID+"';");
        rs.next();
        cnic = rs.getString(1);
        mob = rs.getString(2);
    }
    
    public void logOutButtonPushed(ActionEvent event) throws IOException, SQLException
    {
        Alert a1 = new Alert(Alert.AlertType.CONFIRMATION);  /*Alert types *
                                                             1) INFORMATION
                                                             2) WARNING
                                                             3) ERROR
                                                             4) CONFIRMATION*/
        a1.setTitle("LOGOUT");
        a1.setContentText("ARE YOU SURE YOU WANT TO LOGOUT? ");
        a1.setHeaderText(null); //you can also set header
        
        
        Optional<ButtonType> result = a1.showAndWait();
        
        if(result.get() == ButtonType.OK) //type can also be "OK" as well
        {
            System.out.println("OKAY");
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("AdminLogin.fxml"));
            Parent tableViewParent = loader.load();

            Scene tableViewScene = new Scene(tableViewParent);

            String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
            tableViewScene.getStylesheets().add(css);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(tableViewScene);
            window.show();
        }
        else
        {
            System.out.println("Cancel");
        }
    }
    
    public void changePasswordButtonPushed(ActionEvent event) throws IOException, SQLException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ForgetPassword2.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        ForgetPassword2Controller controller = loader.getController();
        controller.setParameter("AdminAccount", empID, cnic, mob);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    
    public void backButtonPushed(ActionEvent event) throws IOException, SQLException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AdminProfile.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        AdminProfileController controller = loader.getController();
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
