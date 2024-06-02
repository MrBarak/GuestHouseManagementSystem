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
public class AvailableHousesController implements Initializable {

    @FXML private TableView<HouseModalTable> tableView;
    @FXML private TableColumn<HouseModalTable, String> srNoC;
    @FXML private TableColumn<HouseModalTable, String> branchNameC; 
    
    @FXML private Button detailedView;
    @FXML private ComboBox city;
    @FXML private Label label;
    
    String usernameOfLoggedInPerson = "";
    String branchNameForNextScreen = "";
    
    public void setParameter(String s)
    {
        usernameOfLoggedInPerson = s;
    }
      
    public void uponComboSelection()
    {
        ObservableList<HouseModalTable> list = FXCollections.observableArrayList();
        try
        {
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
            Statement stmt = conn.createStatement();
            
            if(city.getValue().equals("Islamabad"))
            {
                ResultSet rs = stmt.executeQuery("select branch_name from branch, location, city where branch.locationID = location.locationID and location.cityID = city.cityID and city_name = 'Islamabad';");
            
            
                int counter = 1;

                while(rs.next())
                {
                    branchNameForNextScreen = rs.getString(1);
                    System.out.println(branchNameForNextScreen);
                    list.add(new HouseModalTable(Integer.toString(counter), branchNameForNextScreen));
                    counter++;
                }
            }
            else if(city.getValue().equals("Karachi"))
            {
                ResultSet rs = stmt.executeQuery("select branch_name from branch, location, city where branch.locationID = location.locationID and location.cityID = city.cityID and city_name = 'Karachi';");
            
            
                int counter = 1;

                while(rs.next())
                {
                    branchNameForNextScreen = rs.getString(1);
                    System.out.println(branchNameForNextScreen);
                    list.add(new HouseModalTable(Integer.toString(counter), branchNameForNextScreen));
                    counter++;
                }
            }
            else if(city.getValue().equals("Lahore"))
            {
                ResultSet rs = stmt.executeQuery("select branch_name from branch, location, city where branch.locationID = location.locationID and location.cityID = city.cityID and city_name = 'Lahore';");
            
            
                int counter = 1;

                while(rs.next())
                {
                    branchNameForNextScreen = rs.getString(1);
                    System.out.println(branchNameForNextScreen);
                    list.add(new HouseModalTable(Integer.toString(counter), branchNameForNextScreen));
                    counter++;
                }
            }
            else if(city.getValue().equals("Peshawar"))
            {
                ResultSet rs = stmt.executeQuery("select branch_name from branch, location, city where branch.locationID = location.locationID and location.cityID = city.cityID and city_name = 'Peshawar';");
            
            
                int counter = 1;

                while(rs.next())
                {
                    branchNameForNextScreen = rs.getString(1);
                    System.out.println(branchNameForNextScreen);
                    list.add(new HouseModalTable(Integer.toString(counter), branchNameForNextScreen));
                    counter++;
                }
            }
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
        tableView.setItems(list);
    }
    //this method will move the make the detailed view button available
    public void userClickOnTable()
    {
        this.detailedView.setDisable(false);
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
    
    public void seeDetailedButtonPushed(ActionEvent event) throws IOException
    {
        if(!branchNameForNextScreen.equals(""))
        {
            System.out.println(branchNameForNextScreen);
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("AvailableHouses2.fxml"));
            Parent tableViewParent = loader.load();

            Scene tableViewScene = new Scene(tableViewParent);

            String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
            tableViewScene.getStylesheets().add(css);

            AvailableHouses2Controller controller = loader.getController();
            controller.setParameter(usernameOfLoggedInPerson, tableView.getSelectionModel().getSelectedItem());

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(tableViewScene);
            window.show();
        }
        else
            label.setText("Please Select A Row First");
    } 
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       detailedView.setDisable(true);
       
       city.getItems().addAll("Islamabad", "Lahore", "Karachi", "Peshawar");
       
       city.setValue("Islamabad");
       
       srNoC.setCellValueFactory(new PropertyValueFactory<HouseModalTable, String>("srNo"));
       branchNameC.setCellValueFactory(new PropertyValueFactory<HouseModalTable, String>("branchName")); 
       
       uponComboSelection();
    }    
}
