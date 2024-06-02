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
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import verifications.Checks;

/**
 * FXML Controller class
 *
 * @author Hamza Barak
 */
public class ResponseComplaint2Controller implements Initializable {

    @FXML private ImageView logo;
    @FXML private TextArea complaintText;
    @FXML private TextArea responseText;
    @FXML private Label label;
    
    String empID = "";
    String complaintID = "";
    String complaint = "";
    String complaintDate = "";
    
    public void setParameter(String empID, Customer c)
    {
        this.empID = empID;
        this.complaint = c.getCustomerName();
        this.complaintID = c.getSrNo();
        this.complaintDate = c.getCnic();
        
        for(int i = 0; i < complaint.length(); i++)
        {
            if(i % 30 == 0)
            {
                complaint = complaint.substring(0, i) + "\n" + complaint.substring(i, complaint.length());
            }
        }
        
        complaintText.setText(complaint);
    }
    
    public void submitResponse(ActionEvent event) throws IOException, SQLException
    {
        if(!responseText.getText().equals(""))
        {
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
            Statement stmt = conn.createStatement();
            
            String sql = "INSERT INTO `mydb`.`response` (`response`, `complaintID`, `responseDate`) VALUES ('"+responseText.getText()+"', '"+complaintID+"', '"+Checks.getCurrentdate()+"');";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            int rowsAffected = preparedStatement.executeUpdate();
            
            Alert a1 = new Alert(Alert.AlertType.INFORMATION);  
            
            a1.setTitle("Response Submission !");
            a1.setContentText("Your Response Has Been Submitted !");
            a1.setHeaderText(null); //you can also set header
            a1.showAndWait();
            
            backButtonPushed(event);
        }
        else
            label.setText("You Cannot Submit Empty Response !");
    }
    
    public void backButtonPushed(ActionEvent event) throws IOException, SQLException
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
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        logo = new ImageView("logo1.png");
        
        complaintText.setEditable(false);
    }    
    
}
