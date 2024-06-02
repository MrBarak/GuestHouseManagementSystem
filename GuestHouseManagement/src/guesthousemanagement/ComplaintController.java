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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Hamza Barak
 */



public class ComplaintController implements Initializable {

 
    @FXML private ImageView logo;
    @FXML private ImageView logo2;

    @FXML private ImageView create;
    @FXML private ImageView record;
    @FXML private ImageView response;
    
    String usernameOfLoggedInPerson;
    
    public void setParameter(String s)
    {
        usernameOfLoggedInPerson = s;
    }
    
    public void createComplaintButtonPushed(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("createComplaint1.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        CreateComplaint1Controller controller = loader.getController();
        controller.setParameter(usernameOfLoggedInPerson);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    
    public void createComplaintImagePushed(MouseEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("createComplaint1.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        CreateComplaint1Controller controller = loader.getController();
        controller.setParameter(usernameOfLoggedInPerson);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    
    public void complaintRecordButtonPushed(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ComplaintRecord.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        ComplaintRecordController controller = loader.getController();
        controller.setParameter(usernameOfLoggedInPerson);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    
    public void complaintRecordImagePushed(MouseEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ComplaintRecord.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        ComplaintRecordController controller = loader.getController();
        controller.setParameter(usernameOfLoggedInPerson);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    
    public void complaintResponseButtonPushed(ActionEvent event) throws IOException
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
    
    public void complaintResponseImagePushed(MouseEvent event) throws IOException
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
    
     //This Method is for moving back to previous page/Screen from which this scene/screen is called
    public void backButtonPushed(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("CustomerProfile.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        CustomerProfileController controller = loader.getController();
        controller.setParameter(usernameOfLoggedInPerson);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    } 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        logo = new ImageView("logo1.png");
        logo2 = new ImageView("complaintLogo.png");
        
        create = new ImageView("createComplaint.png");
        record = new ImageView("complaintRecord.png");
        response = new ImageView("complaintResponse.png");
        // TODO
    }    
    
}
