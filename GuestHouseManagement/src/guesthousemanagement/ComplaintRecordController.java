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
public class ComplaintRecordController implements Initializable {

    @FXML private TableView<ComplaintModalTable> tableView;
    
    @FXML private TableColumn<ComplaintModalTable, String> srNo;
    @FXML private TableColumn<ComplaintModalTable, String> complaintCategory; //this will consider as the House Name
    @FXML private TableColumn<ComplaintModalTable, String> complaint;
    @FXML private TableColumn<ComplaintModalTable, String> submissionDate;
    @FXML private TableColumn<ComplaintModalTable, String> status;
    
    
    String usernameOfLoggedInPerson = "";
    
    String categoryName = "";
    String customerID = "";
    String complaintID = "";
    
    public void setParameter(String person)
    {
        this.usernameOfLoggedInPerson = person;
        setTableData();
    }
    
    public void setTableData()
    {
        ObservableList<ComplaintModalTable> list = FXCollections.observableArrayList();
        
        try
        {
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
            Statement stmt = conn.createStatement();
            
            System.out.println("I am Before query 1");
            ResultSet rs = stmt.executeQuery("select customerID from customer where email = '"+ usernameOfLoggedInPerson +"';");
            
            while(rs.next())
                customerID = rs.getString(1);
            
            System.out.println("custoerID : " + customerID);
            
            String categoryS = "";
            String complaintS = "";
            String dateS = "";
        
            int counter = 1;
            
            System.out.println("I am Before query 2");
            rs = stmt.executeQuery("select complaint.complaintID, complaint_category.category, complaint.complaint, complaint.complaint_date from customer, complaint, complaint_category where customer.customerID = complaint.customerID and complaint_category.complaintCategoryID = complaint.complaintCategoryID and customer.customerID = '"+customerID+"';");
            
            while(rs.next())
            {
                complaintID = complaintID + rs.getString(1) + "#";
                categoryS = categoryS + rs.getString(2) + "#";
                complaintS = complaintS + rs.getString(3) + "#";
                dateS = dateS + rs.getString(4) + "#";
            }
            
            String complaintIDA[] = complaintID.split("#");
            String categoryA[] = categoryS.split("#");
            String complaintA[] = complaintS.split("#");
            String dateA[] = dateS.split("#");
            
            for(int i = 0; i < complaintIDA.length; i++)
            {
            
                System.out.println("I am Before query 3");
                rs = stmt.executeQuery("select complaint.complaintID from complaint where complaintID = '" + complaintIDA[i] + "' and complaintID in (select complaintID from response);");
                
                if(rs.next())
                    list.add(new ComplaintModalTable(Integer.toString(counter), categoryA[i], complaintA[i], dateA[i], "Resolved"));
                else
                    list.add(new ComplaintModalTable(Integer.toString(counter), categoryA[i], complaintA[i], dateA[i], "Pending"));
                
                counter++;
            }
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
        tableView.setItems(list);
    }
    
    public void backButtonPushed(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Complaint.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        ComplaintController controller = loader.getController();
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
        
       srNo.setCellValueFactory(new PropertyValueFactory<ComplaintModalTable, String>("srNo"));
       complaintCategory.setCellValueFactory(new PropertyValueFactory<ComplaintModalTable, String>("complaintCategory"));
       complaint.setCellValueFactory(new PropertyValueFactory<ComplaintModalTable, String>("complaint"));
       submissionDate.setCellValueFactory(new PropertyValueFactory<ComplaintModalTable, String>("submissionDate"));
       status.setCellValueFactory(new PropertyValueFactory<ComplaintModalTable, String>("status"));
       
       setTableData();
    }    
}
