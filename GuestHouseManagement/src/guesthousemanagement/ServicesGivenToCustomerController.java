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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Hamza Barak
 */
public class ServicesGivenToCustomerController implements Initializable {

    @FXML private ImageView logo; 
    
    @FXML private TableView<Customer> tableView;
    @FXML private TableColumn<Customer, String> srNoC;
    @FXML private TableColumn<Customer, String> customerNameC; 
    @FXML private TableColumn<Customer, String> cnicC;
    
    @FXML private ComboBox combo; 
    @FXML private TextField searchBar; 
    @FXML private Button search; 
    @FXML private Button DetailView; 
    
    @FXML private Label label;
    @FXML private Label label2;
    
    int total_customer = 0;
    
    String empID = "";
    String prevPageID = "";
    
    public void setParameter(String empID) throws SQLException   
    {
        this.empID = empID;
        
        Connection conn;
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
        Statement stmt = conn.createStatement();
        
        ResultSet rs = stmt.executeQuery("select * from customer;");
        
        while(rs.next())
            total_customer++;
        
        label.setText("TOTAL CUSTOMERS : " + total_customer);
        
        setTableData();
    }
    
    public void setParameter(String empID, String s) throws SQLException   
    {
        this.empID = empID;
        this.prevPageID = s;
        
        Connection conn;
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
        Statement stmt = conn.createStatement();
        
        ResultSet rs = stmt.executeQuery("select * from customer;");
        
        while(rs.next())
            total_customer++;
        
        label.setText("TOTAL CUSTOMERS : " + total_customer);
        
        setTableData();
    }
    
    public void userClickOnTable()
    {
        this.DetailView.setDisable(false);
    }
    
    public void searchButtonPushed() throws SQLException
    {
        ObservableList<Customer> list = FXCollections.observableArrayList();
        
        Connection conn;
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
        Statement stmt = conn.createStatement();
        
        if(combo.getValue().toString().equals("NAME"))
        {
            int counter = 1;
            
            String name[] = searchBar.getText().split(" ");
            
            String firstName = "";
            String lastName = "";
            
            if(name.length > 1)
            {
                firstName = name[0];
                lastName = name[1];
            }
            else if(name.length == 1)
            {
                firstName = name[0];
            }
            
            ResultSet rs = stmt.executeQuery("select first_name, last_name, CNIC from customer where first_name = '"+ firstName +"' and last_name = '"+ lastName +"';");
            
            if(rs.next())
            {
                do{
                    list.add(new Customer(Integer.toString(counter), rs.getString(1) + " " + rs.getString(2), rs.getString(3)));
                    counter++;
                }while(rs.next());
            }
            else
                label2.setText("Your Entered Name is Not Present in our Database");
        }
        else if(combo.getValue().toString().equals("FIRST NAME"))
        {
            int counter = 1;
            
            ResultSet rs = stmt.executeQuery("select first_name, last_name, CNIC from customer where first_name = '"+ searchBar.getText()+"';");
            
            if(rs.next())
            {
                do{
                    list.add(new Customer(Integer.toString(counter), rs.getString(1) + " " + rs.getString(2), rs.getString(3)));
                    counter++;
                }while(rs.next());
            }
            else
                label2.setText("Your Entered First Name is Not Present in our Database");    
        }
        else if(combo.getValue().toString().equals("LAST NAME"))
        {
            int counter = 1;
            
            ResultSet rs = stmt.executeQuery("select first_name, last_name, CNIC from customer where last_name = '"+ searchBar.getText()+"';");
            
            if(rs.next())
            {
                do{
                    list.add(new Customer(Integer.toString(counter), rs.getString(1) + " " + rs.getString(2), rs.getString(3)));
                    counter++;
                }while(rs.next());
            }
            else
                label2.setText("Your Enterd Last Name is Not Present in our Database");
        }
        else if(combo.getValue().toString().equals("CNIC"))
        {
            int counter = 1;
            
            ResultSet rs = stmt.executeQuery("select first_name, last_name, CNIC from customer where CNIC = '"+ searchBar.getText()+"';");
            
            if(rs.next())
            {
                do{
                    list.add(new Customer(Integer.toString(counter), rs.getString(1) + " " + rs.getString(2), rs.getString(3)));
                    counter++;
                }while(rs.next());
            }
            else
                label2.setText("Your Entered CNIC is Not Present in our Database");
        }
        else if(combo.getValue().toString().equals("E-MAIL"))
        {
            int counter = 1;
            
            ResultSet rs = stmt.executeQuery("select first_name, last_name, CNIC from customer where email = '"+ searchBar.getText()+"';");
            
            if(rs.next())
            {
                do{
                    list.add(new Customer(Integer.toString(counter), rs.getString(1) + " " + rs.getString(2), rs.getString(3)));
                    counter++;
                }while(rs.next());
            }
            else
                label2.setText("Your Entered E-MAIL is Not Present in our Database");
        }
        else if(combo.getValue().toString().equals("CUSTOMER ID"))
        {
            int counter = 1;
            
            ResultSet rs = stmt.executeQuery("select first_name, last_name, CNIC from customer where customerID = '"+ searchBar.getText()+"';");
            
            if(rs.next())
            {
                do{
                    list.add(new Customer(Integer.toString(counter), rs.getString(1) + " " + rs.getString(2), rs.getString(3)));
                    counter++;
                }while(rs.next());
            }
            else
                label2.setText("Your Entered Customer ID is Not Present in our Database");
        }
        else
        {
            int counter = 1;

            ResultSet rs = stmt.executeQuery("select first_name, last_name, CNIC from customer;");

            if(rs.next())
            {
                do{
                    list.add(new Customer(Integer.toString(counter), rs.getString(1) + " " + rs.getString(2), rs.getString(3)));
                    counter++;
                }while(rs.next());
            }
        }
        
        tableView.setItems(list);
    }
    
    public void setTableData() throws SQLException
    {
        ObservableList<Customer> list = FXCollections.observableArrayList();
        
        Connection conn;
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
        Statement stmt = conn.createStatement();
        
        int counter = 1;
        
        ResultSet rs = stmt.executeQuery("select first_name, last_name, CNIC from customer;");
        
        while(rs.next())
        {
            list.add(new Customer(Integer.toString(counter), rs.getString(1) + " " + rs.getString(2), rs.getString(3)));
            counter++;
        }
        
        tableView.setItems(list);
    }
    
    public void seeGivenServicesButtonPushed(ActionEvent event) throws IOException, SQLException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ServicesGivenToCustomer2.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        ServicesGivenToCustomer2Controller controller = loader.getController();
        controller.setParameter(empID, tableView.getSelectionModel().getSelectedItem(), prevPageID);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    
    public void backButtonPushed(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Customer.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        CustomerController controller = loader.getController();
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
        
        DetailView.setDisable(true);
       
        combo.getItems().addAll("NAME", "FIRST NAME", "LAST NAME","CNIC", "E-MAIL", "CUSTOMER ID", "Show All Data");
        
        combo.setValue("Enter Your Desired Input for Search ");
        
        srNoC.setCellValueFactory(new PropertyValueFactory<Customer, String>("srNo"));
        customerNameC.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName")); 
        cnicC.setCellValueFactory(new PropertyValueFactory<Customer, String>("cnic")); 
    }    
}
