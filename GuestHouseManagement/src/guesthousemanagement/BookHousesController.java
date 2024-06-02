/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guesthousemanagement;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Hamza Barak
 */
public class BookHousesController implements Initializable {

    
    @FXML private ComboBox branchCombo;
    @FXML private ComboBox categoryCombo;
    
    
    @FXML private Label label;
    
    @FXML private Button nextButton;
    
    String usernameOfLoggedInPerson;
    String branchName = "";
    String cityName = "";
    String categoryName = "";
    
    //String cityAS[];
    String categoryAS[];
    String branchAS[];
    
    public void setParameter(String s)
    {
        usernameOfLoggedInPerson = s;
    }
    
    public void userClickOnTable()
    {
        this.nextButton.setDisable(false);
    }
    
   
    public void undisableButton()
    {
        if(!branchCombo.getValue().toString().equals("Select Branch") && !categoryCombo.getValue().toString().equals("Select Category"))
            nextButton.setDisable(false);
    }
    
    public void nextButtonPushed(ActionEvent event) throws IOException
    {
        if(!branchCombo.getValue().toString().equals("Select Branch") && !categoryCombo.getValue().toString().equals("Select Category"))
        {
            branchName = branchCombo.getValue().toString();
            categoryName = categoryCombo.getValue().toString();
            
            System.out.println(branchName);
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("BookHouses2.fxml"));
            Parent tableViewParent = loader.load();

            Scene tableViewScene = new Scene(tableViewParent);

            String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
            tableViewScene.getStylesheets().add(css);

            BookHouses2Controller controller = loader.getController();
            controller.setParameter(usernameOfLoggedInPerson, branchName, categoryName);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(tableViewScene);
            window.show();
        }
        else
            label.setText("Please Select A Row First");
    } 
    
    public void backButtonPushed(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("House.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        HouseController controller = loader.getController();
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
        
        categoryCombo.getItems().addAll("LUXURY", "ECONOMY");
        categoryCombo.setValue("Select Category");
        try
        {
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("select branch_name from branch;");
            
            while(rs.next())
                branchCombo.getItems().addAll(rs.getString(1));
            
            branchCombo.setValue("Select Branch");    
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
        
        this.nextButton.setDisable(true);
    }     
}
