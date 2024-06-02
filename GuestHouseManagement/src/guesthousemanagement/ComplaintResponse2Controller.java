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
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Hamza Barak
 */
public class ComplaintResponse2Controller implements Initializable {

    @FXML private TextArea textArea;
    
    String usernameOfLoggedInPerson = "";
    String complaintID = "";
    String response = "";
    
    public void setParameter(String person, ComplaintModalTable cmt)
    {
        usernameOfLoggedInPerson = person;
        complaintID = cmt.getStatus();
        System.out.println("Branch Name from Second : " + complaintID);
        setData();
    }
    
    public void setData()
    {
        try
        {
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
            Statement stmt = conn.createStatement();
            
            ResultSet rs = stmt.executeQuery("select response.response from response where complaintID = '" + complaintID + "'");
            
            while(rs.next())
                response = rs.getString(1);
            
            for(int i = 0; i < response.length(); i++)
            {
                if(i%30 == 0)
                    response = response.substring(0, i) + "-\n" + response.substring(i, response.length());
            }
            textArea.setText(response);
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
    
    public void backButtonPushed(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ComplaintResponse.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        ComplaintResponseController controller = loader.getController();
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
        setData();
        textArea.setEditable(false);
    }    
    
}
