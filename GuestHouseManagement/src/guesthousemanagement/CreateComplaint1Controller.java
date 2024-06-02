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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import verifications.Checks;

/**
 * FXML Controller class
 *
 * @author Hamza Barak
 */
public class CreateComplaint1Controller implements Initializable {

    @FXML private ComboBox categoryCombo;
    @FXML private TextArea textArea;
    
    @FXML private Label label;
    
    @FXML private Button submitButton;
    
    String usernameOfLoggedInPerson;
    String categoryName = "";
    
    int check = 0;
    
    public void setParameter(String s)
    {
        usernameOfLoggedInPerson = s;
    }
    
    public void userClickOnCombo()
    {
        check++;
        if(check == 2)
            this.submitButton.setDisable(false);
    }
    
    public void userClickOnTextArea()
    {
        check++;
        if(check == 2)
            this.submitButton.setDisable(false);
    }
    
    public void submitComplaintButtonPushed(ActionEvent event) throws IOException
    {
        if(!categoryCombo.getValue().toString().equals("Select Category"))
        {
            if(!textArea.getText().equals(""))
            {
                categoryName = categoryCombo.getValue().toString();

                try
                {
                    Connection conn;
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("select customerID from customer where email = '" + usernameOfLoggedInPerson + "';");
                    
                    String customerID = "";
                    
                    while(rs.next())
                    {
                        customerID = rs.getString(1);
                    }
                    
                    rs = stmt.executeQuery("select complaintCategoryID from complaint_category where category = '" + categoryCombo.getValue().toString() + "';");
                    
                    String categoryID = "";
                    
                    while(rs.next())
                    {
                        categoryID = rs.getString(1);
                    }
                    
                    String sql = "INSERT INTO `mydb`.`complaint` (`complaintCategoryID`, `complaint`, `customerID`, `complaint_date`) VALUES ('"+categoryID+"', '"+textArea.getText()+"', '"+customerID+"', '"+Checks.getCurrentdate()+"');";

                    PreparedStatement preparedStatement = conn.prepareStatement(sql);

                    int rowsAffected = preparedStatement.executeUpdate();
                    
                    Alert a1 = new Alert(Alert.AlertType.INFORMATION);  
                    
                    a1.setTitle("Complaint");
                    a1.setContentText("Your Complaint Has Been Entered !");
                    a1.setHeaderText(null); //you can also set header
                    a1.showAndWait();
                    
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("Complaint.fxml"));
                    Parent tableViewParent = loader.load();

                    Scene tableViewScene = new Scene(tableViewParent);

                    String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
                    tableViewScene.getStylesheets().add(css);

                    ComplaintController controller = loader.getController();
                    controller.setParameter(usernameOfLoggedInPerson);

                    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

                    window.setScene(tableViewScene);
                    window.show();
                }
                catch(Exception e)
                {
                    System.out.println(e.toString());
                }
            }
            else
                 label.setText("Fill The Text Area");
        }
        else
            label.setText("Please Select Category First");
    } 
    
    public void backButtonPushed(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Complaint.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        ComplaintController controller = loader.getController();
        controller.setParameter(usernameOfLoggedInPerson);

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
        
        categoryCombo.getItems().addAll("food", "laundry", "cleaning", "mismanagement", "house condition", "other");
        categoryCombo.setValue("Select Complaint Category");
        
        
        this.submitButton.setDisable(true);
    }     
    
}
