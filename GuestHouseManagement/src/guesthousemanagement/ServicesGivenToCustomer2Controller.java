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
import javafx.scene.control.Label;
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
public class ServicesGivenToCustomer2Controller implements Initializable {

    @FXML private ImageView logo; 
    
    @FXML private TableView<Customer> tableView;
    @FXML private TableColumn<Customer, String> srNoC;
    @FXML private TableColumn<Customer, String> serviceC; 
    @FXML private TableColumn<Customer, String> dateC;
    @FXML private TableColumn<Customer, String> timeC;
    
    @FXML private Label label;
    @FXML private Label label2;
    
    int total_customer = 0;
    
    String empID = "";
    String customerCNIC = "";
    String prevPageID = "";
    
    public void setParameter(String empID, Customer customerCNIC) throws SQLException   
    {
        this.empID = empID;
        this.customerCNIC = customerCNIC.getCnic();
        
        setTableData();
    }
    
    public void setParameter(String empID, Customer customerCNIC, String s) throws SQLException   
    {
        this.empID = empID;
        this.customerCNIC = customerCNIC.getCnic();
        this.prevPageID = s;
        
        setTableData();
    }
    
    public void setParameter(String empID, String customerCNIC, String s) throws SQLException   
    {
        this.empID = empID;
        this.customerCNIC = customerCNIC;
        this.prevPageID = s;
        
        setTableData();
    }
    public void setTableData() throws SQLException
    {
        ObservableList<Customer> list = FXCollections.observableArrayList();
        
        Connection conn;
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
        Statement stmt = conn.createStatement();
        
        int counter = 1;
        
        ResultSet rs = stmt.executeQuery("select customerID from customer where CNIC = '"+customerCNIC+"';");
        rs.next();
        String customerID = rs.getString(1);
        
        rs = stmt.executeQuery("select services.service_name, service_record.date,  service_record.time from bills, service_record, services where services.serviceID = bills.serviceID and bills.billsID = service_record.billsID and bills.customerID = '"+customerID+"';");
        
        while(rs.next())
        {
            list.add(new Customer(Integer.toString(counter), rs.getString(1), rs.getString(2), rs.getString(3)));
            counter++;
        }
        
        tableView.setItems(list);
    }
    
    public void backButtonPushed(ActionEvent event) throws IOException, SQLException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ServicesGivenToCustomer.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        ServicesGivenToCustomerController controller = loader.getController();
        controller.setParameter(empID, prevPageID);

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
        
        srNoC.setCellValueFactory(new PropertyValueFactory<Customer, String>("srNo"));
        serviceC.setCellValueFactory(new PropertyValueFactory<Customer, String>("service")); 
        dateC.setCellValueFactory(new PropertyValueFactory<Customer, String>("date")); 
        timeC.setCellValueFactory(new PropertyValueFactory<Customer, String>("time")); 
    }    
    
}
