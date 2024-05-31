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
import java.sql.PreparedStatement;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import verifications.Checks;

/**
 * FXML Controller class
 *
 * @author Hamza Barak
 */
public class ChangeSalaryController implements Initializable {

     @FXML private ImageView logo;
    
    @FXML private TextField cnic;
    @FXML private Label label;
    
    @FXML private TextField salary;
    @FXML private Label label2;
    @FXML private Label label3;
    
    String empID = "";
    
    public void setParameter(String empID)
    {
        this.empID = empID;
    }
    
    public void seePreviousSalaryButtonPushed() throws SQLException
    {
        if(!cnic.getText().equals(""))
        {
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
            Statement stmt = conn.createStatement();

            String employeeID = "";
            
            ResultSet rs = stmt.executeQuery("select salary from employee where CNIC = '"+cnic.getText()+"';");
            if(rs.next())
            {
                label2.setText(rs.getString(1) + " Rupees ");
            }
            else
            {
                label.setText("Please Enter Valid CNIC !");
            }
        }
        else
            label.setText("Please Enter Eny CNIC First !");
    }
    public void changeSalary(ActionEvent event) throws IOException, SQLException
    {
        if(!cnic.getText().equals(""))
        {
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
            Statement stmt = conn.createStatement();

            String employeeID = "";
            
            ResultSet rs = stmt.executeQuery("select empID from employee where CNIC = '"+cnic.getText()+"';");
            if(rs.next())
            {
                employeeID = rs.getString(1);
                
                String sql = "UPDATE `mydb`.`employee` SET `salary` = '"+salary.getText()+"' WHERE (`empID` = '"+employeeID+"');";
                
                PreparedStatement preparedStatement = conn.prepareStatement(sql); //This Will Prevent From SQL Injection

                int rowsAffected = preparedStatement.executeUpdate();
            }
            else
                label.setText("No Such Person Exits in Employe Database or Invalid CNIC !");
            
                
            Alert a1 = new Alert(Alert.AlertType.INFORMATION);

            a1.setTitle("Successfully Changed ");
            a1.setContentText("Employee Salary has been Changed successfully!");
            a1.setHeaderText(null); //you can also set header
            a1.showAndWait();
            
            backButtonPushed(event);

        }
        else
            label.setText("Please Enter Eny CNIC First !");
    }
    
    public void backButtonPushed(ActionEvent event) throws IOException, SQLException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AdminEmployee.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
//        
        AdminEmployeeController controller = loader.getController();
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
