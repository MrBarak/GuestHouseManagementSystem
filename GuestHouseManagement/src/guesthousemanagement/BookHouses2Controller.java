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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import verifications.Checks;

/**
 * FXML Controller class
 *
 * @author Hamza Barak
 */
public class BookHouses2Controller implements Initializable {

    @FXML private TableView<HouseModalTable> tableView;
    
    @FXML private TableColumn<HouseModalTable, String> houseName;
    @FXML private TableColumn<HouseModalTable, String> branchNameC; //this will consider as the House Name
    @FXML private TableColumn<HouseModalTable, String> locationC;
    @FXML private TableColumn<HouseModalTable, String> cityC;
    @FXML private TableColumn<HouseModalTable, String> categoryC;
    @FXML private TableColumn<HouseModalTable, String> statusC;
    
    @FXML private Button bookButton;
    
    @FXML private Label label;
    @FXML private DatePicker date;
    
    String usernameOfLoggedInPerson = "";
    String branchNameFromPrevScene = "";
    String categoryName = "";
    String customerID = "";
    
    int houseNo = 0;
    int check = 0;
    
    public void setParameter(String person, String branchNameFromPrevScene, String categoryName)
    {
        this.usernameOfLoggedInPerson = person;
        this.branchNameFromPrevScene = branchNameFromPrevScene;
        this.categoryName = categoryName;
        setTableData();
    }
    
    public void userClickOnTable()
    {
        check++;
        
        if(check > 1)
            this.bookButton.setDisable(false);
    }
    
    public void userClickOnDaTe()
    {
        check++;
        if(check > 1)
            this.bookButton.setDisable(false);

    }
    
    public void setTableData()
    {
        ObservableList<HouseModalTable> list = FXCollections.observableArrayList();
        
        System.out.println("I am in the SEt Tabel DAta Method");
        
        try
        {
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
            Statement stmt = conn.createStatement();
            
            ResultSet rs = stmt.executeQuery("select distinct house.houseID, branch_name, location, city_name from house, branch, city, location, house_booking where branch.branch_name = '"+ branchNameFromPrevScene +"' and house.branchID = branch.branchID and branch.locationID = location.locationID and location.cityID = city.cityID and huose_Type = '" + categoryName + "' and house.houseID not in (select house_booking.houseID from house_booking);");
            
            while(rs.next())
            {
                String houseNameS = "House No. " + rs.getString(1);
                System.out.println("House Name from DATA BASE : " + houseNameS);
                list.add(new HouseModalTable(houseNameS, rs.getString(2), rs.getString(3), rs.getString(4), categoryName, "AVAILABLE"));
            }
            
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
        tableView.setItems(list);
    }

    public void addDataInRecord()
    {
        try
        {
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
            Statement stmt = conn.createStatement();

            int recordUpdate = stmt.executeUpdate("INSERT INTO `mydb`.`customer_record` (`customerID`, `booking`, `booking_date`, `leaving_date`, `bills`) VALUES ('" + customerID + "', 'h" + houseNo + "', '" + Checks.getCurrentdate() + "', '" + date.getValue().toString() + "', '0');");
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
    
    public void backButtonPushed(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("BookHouses.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        BookHousesController controller = loader.getController();
        controller.setParameter(usernameOfLoggedInPerson);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    
    public void bookButtonPushed(ActionEvent event) throws IOException 
    {
        if(Checks.checkLeavingDate(date.getValue().toString()))
        {
            HouseModalTable hmt = tableView.getSelectionModel().getSelectedItem();

            Alert a1 = new Alert(Alert.AlertType.CONFIRMATION);  /*Alert types *
                                                                 1) INFORMATION
                                                                 2) WARNING
                                                                 3) ERROR
                                                                 4) CONFIRMATION*/
            a1.setTitle("CONFIRMATION");
            a1.setContentText("ARE YOU SURE YOU WANT TO BOOK THIS HOUSE ? ");
            //a1.setHeaderText("Basic ALERT 2 "); //you can also set header


            Optional<ButtonType> result = a1.showAndWait();

            if(result.get() == ButtonType.OK) //type can also be "OK" as well
            {
                System.out.println("OKAY");
                try
                {
                    Connection conn;
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
                    Statement stmt = conn.createStatement();

                    ResultSet rs = stmt.executeQuery("select customerID from customer where email = '"+ usernameOfLoggedInPerson +"';");

                    while(rs.next())
                    {
                       customerID = rs.getString(1);
                    }

                    String temp = hmt.getSrNo();
                    houseNo = Integer.parseInt(temp.substring(10, temp.length()));

                    int recordUpdate = stmt.executeUpdate("INSERT INTO `mydb`.`house_booking` (`houseID`, `customerID`) VALUES ('" + houseNo + "', ' " + customerID + " ');");

                    addDataInRecord();
                    
                    Alert a2 = new Alert(Alert.AlertType.INFORMATION);  /*Alert types *
                                                                 1) INFORMATION
                                                                 2) WARNING
                                                                 3) ERROR
                                                                 4) CONFIRMATION*/
                    a2.setTitle("INFORMATION");
                    a2.setContentText("Your House has Successfully Booked ! ");
                    a2.setHeaderText(null); //you can also set header
                    a2.showAndWait();
                }
                catch(Exception e)
                {
                    System.out.println(e.toString());
                }
            }
            else
            {
                System.out.println("Cancel");
            }
        }
        else
            label.setText("Incorrect Leaving Date !!!");
            
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       this.bookButton.setDisable(true);
        
       houseName.setCellValueFactory(new PropertyValueFactory<HouseModalTable, String>("srNo"));
       branchNameC.setCellValueFactory(new PropertyValueFactory<HouseModalTable, String>("branchName"));
       locationC.setCellValueFactory(new PropertyValueFactory<HouseModalTable, String>("location"));
       cityC.setCellValueFactory(new PropertyValueFactory<HouseModalTable, String>("city"));
       categoryC.setCellValueFactory(new PropertyValueFactory<HouseModalTable, String>("category"));
       statusC.setCellValueFactory(new PropertyValueFactory<HouseModalTable, String>("status"));
       
       setTableData();
    }    
}
