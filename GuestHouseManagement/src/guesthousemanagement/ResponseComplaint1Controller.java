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
public class ResponseComplaint1Controller implements Initializable {

    @FXML private ImageView logo; 
    
    @FXML private TableView<Customer> tableView;
    @FXML private TableColumn<Customer, String> srNoC;
    @FXML private TableColumn<Customer, String> complaintC; 
    @FXML private TableColumn<Customer, String> dateC; 
    
    @FXML private ComboBox combo; 
    @FXML private Button DetailView; 
    
    @FXML private Label label;
    @FXML private Label label2;
    
    int total_customer = 0;
    
    String empID = "";
    
    public void setParameter(String empID) throws SQLException   
    {
        this.empID = empID;
        
        Connection conn;
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
        Statement stmt = conn.createStatement();
        
        ResultSet rs = stmt.executeQuery("select * from complaint;");
        
        while(rs.next())
            total_customer++;
        
        label.setText("TOTAL COMPLAINTS : " + total_customer);
        
        setTableData();
    }
    
    public void userClickOnTable()
    {
        this.DetailView.setDisable(false);
    }
    
    public void updatedComboBox() throws SQLException
    {
        ObservableList<Customer> list = FXCollections.observableArrayList();
        
        Connection conn;
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
        Statement stmt = conn.createStatement();
        
        if(combo.getValue().toString().equals("FOOD"))
        {
            ResultSet rs = stmt.executeQuery("select complaint.complaintID, complaint.complaint, complaint.complaint_date from complaint, complaint_category where complaint_category.complaintCategoryID = complaint.complaintCategoryID and complaint_category.category = 'food' and complaint.complaintID not in (select complaintID from response);");
            while(rs.next())
            {
                list.add(new Customer(rs.getString(1), rs.getString(2), rs.getString(3)));
            }
        }
        else if(combo.getValue().toString().equals("LAUNDRY"))
        {
            ResultSet rs = stmt.executeQuery("select complaint.complaintID, complaint.complaint, complaint.complaint_date from complaint, complaint_category where complaint_category.complaintCategoryID = complaint.complaintCategoryID and complaint_category.category = 'laundry' and complaint.complaintID not in (select complaintID from response);");
            while(rs.next())
            {
                list.add(new Customer(rs.getString(1), rs.getString(2), rs.getString(3)));
            }
        }
        else if(combo.getValue().toString().equals("CLEANING"))
        {
            ResultSet rs = stmt.executeQuery("select complaint.complaintID, complaint.complaint, complaint.complaint_date from complaint, complaint_category where complaint_category.complaintCategoryID = complaint.complaintCategoryID and complaint_category.category = 'cleaning' and complaint.complaintID not in (select complaintID from response);");
            while(rs.next())
            {
                list.add(new Customer(rs.getString(1), rs.getString(2), rs.getString(3)));
            }
        }
        else if(combo.getValue().toString().equals("MISMANAGEMENT"))
        {
            ResultSet rs = stmt.executeQuery("select complaint.complaintID, complaint.complaint, complaint.complaint_date from complaint, complaint_category where complaint_category.complaintCategoryID = complaint.complaintCategoryID and complaint_category.category = 'mismanagement' and complaint.complaintID not in (select complaintID from response);");
            while(rs.next())
            {
                list.add(new Customer(rs.getString(1), rs.getString(2), rs.getString(3)));
            }
        }
        else if(combo.getValue().toString().equals("HOUSE CONDITION"))
        {
            ResultSet rs = stmt.executeQuery("select complaint.complaintID, complaint.complaint, complaint.complaint_date from complaint, complaint_category where complaint_category.complaintCategoryID = complaint.complaintCategoryID and complaint_category.category = 'house condition' and complaint.complaintID not in (select complaintID from response);");
            while(rs.next())
            {
                list.add(new Customer(rs.getString(1), rs.getString(2), rs.getString(3)));
            }
        }
        else if(combo.getValue().toString().equals("OTHERS"))
        {
            ResultSet rs = stmt.executeQuery("select complaint.complaintID, complaint.complaint, complaint.complaint_date from complaint, complaint_category where complaint_category.complaintCategoryID = complaint.complaintCategoryID and complaint_category.category = 'other' and complaint.complaintID not in (select complaintID from response);");
            while(rs.next())
            {
                list.add(new Customer(rs.getString(1), rs.getString(2), rs.getString(3)));
            }
        }
        else
        {
            ResultSet rs = stmt.executeQuery("select complaintID, complaint, complaint_date from complaint where complaint.complaintID not in (select complaintID from response);");
        
            while(rs.next())
            {
                list.add(new Customer(rs.getString(1), rs.getString(2), rs.getString(3)));
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
        
        ResultSet rs = stmt.executeQuery("select complaintID, complaint, complaint_date from complaint where complaint.complaintID not in (select complaintID from response);");
        
        while(rs.next())
        {
            list.add(new Customer(rs.getString(1), rs.getString(2), rs.getString(3)));
        }
        
        tableView.setItems(list);
    }
    
    public void giveResponseButtonPushed(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ResponseComplaint2.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        ResponseComplaint2Controller controller = loader.getController();
        controller.setParameter(empID, tableView.getSelectionModel().getSelectedItem());

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    
    public void backButtonPushed(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ReceptionistProfile.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        ReceptionistProfileController controller = loader.getController();
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
        
        DetailView.setDisable(true);
       
        combo.getItems().addAll("FOOD", "LAUNDRY", "CLEANING","MISMANAGEMENT", "HOUSE CONDITION", "OTHERS", "ALL");
        
        combo.setValue("ALL");
        
        srNoC.setCellValueFactory(new PropertyValueFactory<Customer, String>("srNo"));
        complaintC.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName")); 
        dateC.setCellValueFactory(new PropertyValueFactory<Customer, String>("cnic")); 
    }    
    
}
