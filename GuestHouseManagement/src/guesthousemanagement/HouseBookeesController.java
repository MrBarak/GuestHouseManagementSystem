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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Hamza Barak
 */
public class HouseBookeesController implements Initializable {

    @FXML private ImageView logo;
    
    @FXML private TableView<HouseModalTable> tableView;
    
    @FXML private TableColumn<HouseModalTable, String> houseName;
    @FXML private TableColumn<HouseModalTable, String> branchNameC; //this will consider as the House Name
    @FXML private TableColumn<HouseModalTable, String> locationC;
    @FXML private TableColumn<HouseModalTable, String> cityC;
    @FXML private TableColumn<HouseModalTable, String> categoryC;
    @FXML private TableColumn<HouseModalTable, String> statusC;
    
    @FXML private ComboBox branch;
    @FXML private ComboBox category;
    
    String empID = "";
    
    public void setParameter(String empID)
    {
        this.empID = empID;
    }
    
    public void showResultButtonPushed(ActionEvent event) throws IOException, SQLException
    {
        ObservableList<HouseModalTable> list = FXCollections.observableArrayList();
        
        System.out.println("I am in the SEt Tabel DAta Method");
        
        Connection conn;
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
        Statement stmt = conn.createStatement();
        
        if(category.getValue().toString().equals("ALL"))
        {
            String tempHouse = "";
            String tempBranch = "";
            String tempLocation = "";
            String tempCity = "";
            String tempType = "";
            
            String house[];
            String branch_name[];
            String location[];
            String city[];
            String houseType[];
            
            ResultSet rs = stmt.executeQuery("select distinct house.houseID, branch_name, location, city_name, house.huose_Type from house, branch, city, location where branch.branch_name = '"+branch.getValue().toString()+"' and house.branchID = branch.branchID and branch.locationID = location.locationID and location.cityID = city.cityID and house.huose_Type not like 'roomsOnly%';");

            while(rs.next())
            {
                tempHouse = tempHouse + rs.getString(1) + "#";
                tempBranch = tempBranch + rs.getString(2) + "#";
                tempLocation = tempLocation + rs.getString(3) + "#";
                tempCity = tempCity + rs.getString(4) + "#";
                tempType = tempType + rs.getString(5) + "#";
            }
            
            house = tempHouse.split("#");
            branch_name = tempBranch.split("#");
            location = tempLocation.split("#");
            city = tempCity.split("#");
            houseType = tempType.split("#");
            
            for(int i = 0; i < house.length; i++)
            {
                rs = stmt.executeQuery("SELECT booking, count(*) as Total_Bookees from customer_record where booking = 'h"+house[i]+"' group by booking;");
                if(rs.next())
                {
                    list.add(new HouseModalTable("House No." + house[i], branch_name[i], location[i], city[i], houseType[i], rs.getString(2)));
                }
                else
                {
                    list.add(new HouseModalTable("House No." + house[i], branch_name[i], location[i], city[i], houseType[i], "0"));
                }
            }
        }
        else
        {
            String tempHouse = "";
            String tempBranch = "";
            String tempLocation = "";
            String tempCity = "";
            String tempType = "";
            
            String house[];
            String branch_name[];
            String location[];
            String city[];
            String houseType[];
            
            ResultSet rs = stmt.executeQuery("select distinct house.houseID, branch_name, location, city_name, house.huose_Type from house, branch, city, location where branch.branch_name = '"+branch.getValue().toString()+"' and house.branchID = branch.branchID and branch.locationID = location.locationID and location.cityID = city.cityID and house.huose_Type = '"+category.getValue().toString()+"';");

            while(rs.next())
            {
                tempHouse = tempHouse + rs.getString(1) + "#";
                tempBranch = tempBranch + rs.getString(2) + "#";
                tempLocation = tempLocation + rs.getString(3) + "#";
                tempCity = tempCity + rs.getString(4) + "#";
                tempType = tempType + rs.getString(5) + "#";
            }
            
            house = tempHouse.split("#");
            branch_name = tempBranch.split("#");
            location = tempLocation.split("#");
            city = tempCity.split("#");
            houseType = tempType.split("#");
            
            for(int i = 0; i < house.length; i++)
            {
                rs = stmt.executeQuery("SELECT booking, count(*) as Total_Bookees from customer_record where booking = 'h"+house[i]+"' group by booking;");
                if(rs.next())
                {
                    list.add(new HouseModalTable("House No." + house[i], branch_name[i], location[i], city[i], houseType[i], rs.getString(2)));
                }
                else
                {
                    list.add(new HouseModalTable("House No." + house[i], branch_name[i], location[i], city[i], houseType[i], "0"));
                }
            }
        }
        
        tableView.setItems(list);
    }
    
    public void backButtonPushed(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("HouseMain.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        HouseMainController controller = loader.getController();
        controller.setParameter(empID);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        // TODO
        
        branch.getItems().addAll("ISB Branch 1", "ISB Branch 2", "PEW Branch 1", "PEW Branch 2", "LHR Branch", "KHI Branch");
        category.getItems().addAll("ALL", "LUXURY", "ECONOMY");
        
        String branchID = "";
        String branchName = "";
        
        try
        {
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("select branchID from employee where empID = '"+ empID +"';");

            while(rs.next())
            {
               branchID = rs.getString(1);
            }
            
            rs = stmt.executeQuery("select branch_name from branch where branchID = '" + branchID + "';");

            while(rs.next())
            {
               branchName = rs.getString(1);
            }
            
            
        }
        catch(SQLException e)
        {
            System.out.println(e.toString());
        }
        
        branch.setValue(branchName);
        category.setValue("ALL");
        
        houseName.setCellValueFactory(new PropertyValueFactory<HouseModalTable, String>("srNo"));
        branchNameC.setCellValueFactory(new PropertyValueFactory<HouseModalTable, String>("branchName"));
        locationC.setCellValueFactory(new PropertyValueFactory<HouseModalTable, String>("location"));
        cityC.setCellValueFactory(new PropertyValueFactory<HouseModalTable, String>("city"));
        categoryC.setCellValueFactory(new PropertyValueFactory<HouseModalTable, String>("category"));
        statusC.setCellValueFactory(new PropertyValueFactory<HouseModalTable, String>("status"));
        
        logo = new ImageView("logo1.png");
    }    
    
}
