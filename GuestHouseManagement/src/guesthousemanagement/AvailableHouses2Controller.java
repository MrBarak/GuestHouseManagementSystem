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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Hamza Barak
 */
public class AvailableHouses2Controller implements Initializable {
    
    @FXML private TableView<HouseModalTable> tableView;
    @FXML private TableColumn<HouseModalTable, String> srNoC;
    @FXML private TableColumn<HouseModalTable, String> branchNameC; //this will consider as the House Name
    @FXML private TableColumn<HouseModalTable, String> locationC;
    @FXML private TableColumn<HouseModalTable, String> cityC;
    @FXML private TableColumn<HouseModalTable, String> categoryC;
    @FXML private TableColumn<HouseModalTable, String> statusC;
    
    @FXML private ComboBox status;
    @FXML private ComboBox category;
    
    String usernameOfLoggedInPerson;
    String branchNameFromPrevScene = "";
    
   
    public void setParameter(String person, HouseModalTable hmt)
    {
        usernameOfLoggedInPerson = person;
        branchNameFromPrevScene = hmt.getBranchName();
        System.out.println("Branch Name from Second : " + branchNameFromPrevScene);
        bothCombBoxUpdated();
    }
    
    public void bothCombBoxUpdated()
    {
        ObservableList<HouseModalTable> list = FXCollections.observableArrayList();
        try
        {
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
            Statement stmt = conn.createStatement();
            
            //PreparedStatement stmt = conn.prepareStatement();
            
            ResultSet rs;
            ResultSet rs2;
            
            if(status.getValue().equals("AVAILABLE"))
            {
                if(category.getValue().equals("LUXURY"))
                {
                    rs = stmt.executeQuery("select branch_name, huose_Type, location, city_name from house, branch, location, city where house.branchID = branch.branchID and  branch.locationID = location.locationID and location.cityID = city.cityID and branch_name = '"+ branchNameFromPrevScene +"' and (huose_Type = 'Luxury') and house.houseID not in (select houseID from house_booking);");
            
                    int counter = 1;

                    while(rs.next())
                    {
                        String houseName = "House No. " + counter;
                        list.add(new HouseModalTable(Integer.toString(counter), houseName, rs.getString(3), rs.getString(4), rs.getString(2), "AVAILABLE"));
                        counter++;
                    }
                }
                else if(category.getValue().equals("ECONOMY"))
                {
                    rs = stmt.executeQuery("select branch_name, huose_Type, location, city_name from house, branch, location, city where house.branchID = branch.branchID and  branch.locationID = location.locationID and location.cityID = city.cityID and branch_name = '"+ branchNameFromPrevScene +"' and (huose_Type = 'Economy') and house.houseID not in (select houseID from house_booking);");
            
                    int counter = 1;

                    while(rs.next())
                    {
                        String houseName = "House No. " + counter;
                        list.add(new HouseModalTable(Integer.toString(counter), houseName, rs.getString(3), rs.getString(4), rs.getString(2), "AVAILABLE"));
                        counter++;
                    }
                }
                else
                {
                    rs = stmt.executeQuery("select branch_name, huose_Type, location, city_name from house, branch, location, city where house.branchID = branch.branchID and  branch.locationID = location.locationID and location.cityID = city.cityID and branch_name = '"+ branchNameFromPrevScene +"' and (huose_Type = 'Luxury' or huose_Type = 'Economy') and house.houseID not in (select houseID from house_booking);");
            
                    int counter = 1;

                    while(rs.next())
                    {
                        String houseName = "House No. " + counter;
                        list.add(new HouseModalTable(Integer.toString(counter), houseName, rs.getString(3), rs.getString(4), rs.getString(2), "AVAILABLE"));
                        counter++;
                    }
                }
            }
            else if(status.getValue().equals("BOOKED"))
            {
                if(category.getValue().equals("LUXURY"))
                {
                    rs = stmt.executeQuery("select branch_name, huose_Type, location, city_name from house, branch, location, city where house.branchID = branch.branchID and  branch.locationID = location.locationID and location.cityID = city.cityID and branch_name = '"+ branchNameFromPrevScene +"' and (huose_Type = 'Luxury') and house.houseID in (select houseID from house_booking);");
            
                    int counter = 1;

                    while(rs.next())
                    {
                        String houseName = "House No. " + counter;
                        list.add(new HouseModalTable(Integer.toString(counter), houseName, rs.getString(3), rs.getString(4), rs.getString(2), "BOOKED"));
                        counter++;
                    }
                }
                else if(category.getValue().equals("ECONOMY"))
                {
                    rs = stmt.executeQuery("select branch_name, huose_Type, location, city_name from house, branch, location, city where house.branchID = branch.branchID and  branch.locationID = location.locationID and location.cityID = city.cityID and branch_name = '"+ branchNameFromPrevScene +"' and (huose_Type = 'Economy') and house.houseID in (select houseID from house_booking);");
            
                    int counter = 1;

                    while(rs.next())
                    {
                        String houseName = "House No. " + counter;
                        list.add(new HouseModalTable(Integer.toString(counter), houseName, rs.getString(3), rs.getString(4), rs.getString(2), "BOOKED"));
                        counter++;
                    }
                }
                else
                {
                    rs = stmt.executeQuery("select branch_name, huose_Type, location, city_name from house, branch, location, city where house.branchID = branch.branchID and  branch.locationID = location.locationID and location.cityID = city.cityID and branch_name = '"+ branchNameFromPrevScene +"' and (huose_Type = 'Luxury' or huose_Type = 'Economy') and house.houseID in (select houseID from house_booking);");
            
                    int counter = 1;

                    while(rs.next())
                    {
                        String houseName = "House No. " + counter;
                        list.add(new HouseModalTable(Integer.toString(counter), houseName, rs.getString(3), rs.getString(4), rs.getString(2), "BOOKED"));
                        counter++;
                    }
                }
            }
            else
            {
                if(category.getValue().equals("LUXURY"))
                {
                    rs = stmt.executeQuery("select branch_name, huose_Type, location, city_name from house, branch, location, city where house.branchID = branch.branchID and  branch.locationID = location.locationID and location.cityID = city.cityID and branch_name = '"+ branchNameFromPrevScene +"' and (huose_Type = 'Luxury') and house.houseID not in (select houseID from house_booking);");
            
                    int counter = 1;

                    while(rs.next())
                    {
                        String houseName = "House No. " + counter;
                        list.add(new HouseModalTable(Integer.toString(counter), houseName, rs.getString(3), rs.getString(4), rs.getString(2), "AVAILABLE"));
                        counter++;
                    }
                    
                    rs2 = stmt.executeQuery("select branch_name, huose_Type, location, city_name from house, branch, location, city where house.branchID = branch.branchID and  branch.locationID = location.locationID and location.cityID = city.cityID and branch_name = '"+ branchNameFromPrevScene +"' and (huose_Type = 'Luxury') and house.houseID in (select houseID from house_booking);");
                    
                    while(rs2.next())
                    {
                        String houseName = "House No. " + counter;
                        list.add(new HouseModalTable(Integer.toString(counter), houseName, rs2.getString(3), rs2.getString(4), rs2.getString(2), "BOOKED"));
                        counter++;
                    }
                }
                else if(category.getValue().equals("ECONOMY"))
                {
                    rs = stmt.executeQuery("select branch_name, huose_Type, location, city_name from house, branch, location, city where house.branchID = branch.branchID and  branch.locationID = location.locationID and location.cityID = city.cityID and branch_name = '"+ branchNameFromPrevScene +"' and (huose_Type = 'Economy') and house.houseID not in (select houseID from house_booking);");
            
                    int counter = 1;

                    while(rs.next())
                    {
                        String houseName = "House No. " + counter;
                        list.add(new HouseModalTable(Integer.toString(counter), houseName, rs.getString(3), rs.getString(4), rs.getString(2), "AVAILABLE"));
                        counter++;
                    }
                    
                    rs2 = stmt.executeQuery("select branch_name, huose_Type, location, city_name from house, branch, location, city where house.branchID = branch.branchID and  branch.locationID = location.locationID and location.cityID = city.cityID and branch_name = '"+ branchNameFromPrevScene +"' and (huose_Type = 'Economy') and house.houseID in (select houseID from house_booking);");
                    
                    while(rs2.next())
                    {
                        String houseName = "House No. " + counter;
                        list.add(new HouseModalTable(Integer.toString(counter), houseName, rs2.getString(3), rs2.getString(4), rs2.getString(2), "BOOKED"));
                        counter++;
                    }
                }
                else
                {
                    rs = stmt.executeQuery("select branch_name, huose_Type, location, city_name from house, branch, location, city where house.branchID = branch.branchID and  branch.locationID = location.locationID and location.cityID = city.cityID and branch_name = '"+ branchNameFromPrevScene +"' and (huose_Type = 'Luxury' or huose_Type = 'Economy') and house.houseID not in (select houseID from house_booking);");
            
                    int counter = 1;

                    while(rs.next())
                    {
                        String houseName = "House No. " + counter;
                        list.add(new HouseModalTable(Integer.toString(counter), houseName, rs.getString(3), rs.getString(4), rs.getString(2), "AVAILABLE"));
                        counter++;
                    }
                    
                    rs2 = stmt.executeQuery("select branch_name, huose_Type, location, city_name from house, branch, location, city where house.branchID = branch.branchID and  branch.locationID = location.locationID and location.cityID = city.cityID and branch_name = '"+ branchNameFromPrevScene +"' and (huose_Type = 'Luxury' or huose_Type = 'Economy') and house.houseID in (select houseID from house_booking);");
                    
                    while(rs2.next())
                    {
                        String houseName = "House No. " + counter;
                        list.add(new HouseModalTable(Integer.toString(counter), houseName, rs2.getString(3), rs2.getString(4), rs2.getString(2), "BOOKED"));
                        counter++;
                    }
                }
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.toString());
        }
        tableView.setItems(list);
    }
    
    public void backButtonPushed(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AvailableHouses.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        AvailableHousesController controller = loader.getController();
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
       status.getItems().addAll("ALL", "BOOKED", "AVAILABLE");
       category.getItems().addAll("ALL", "LUXURY", "ECONOMY");
       
       status.setValue("AVAILABLE");
       category.setValue("ALL");
       
       srNoC.setCellValueFactory(new PropertyValueFactory<HouseModalTable, String>("srNo"));
       branchNameC.setCellValueFactory(new PropertyValueFactory<HouseModalTable, String>("branchName"));
       locationC.setCellValueFactory(new PropertyValueFactory<HouseModalTable, String>("location"));
       cityC.setCellValueFactory(new PropertyValueFactory<HouseModalTable, String>("city"));
       categoryC.setCellValueFactory(new PropertyValueFactory<HouseModalTable, String>("category"));
       statusC.setCellValueFactory(new PropertyValueFactory<HouseModalTable, String>("status"));
    }    
    
}
