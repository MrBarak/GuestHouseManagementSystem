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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import verifications.Checks;

/**
 * FXML Controller class
 *
 * @author Hamza Barak
 */
public class LeaveDetailedCustomerInfoController implements Initializable {
@FXML private ImageView logo;
    
    @FXML private Label name;
    @FXML private Label gender;
    @FXML private Label dob;
    @FXML private Label cnic;
    @FXML private Label username;
    @FXML private Label joiningDate;
    @FXML private Label leavingDate;
    @FXML private Label mob1;
    @FXML private Label mob2;
    @FXML private Label mob3;
    @FXML private Label address1_1;
    @FXML private Label address1_2;
    @FXML private Label address2_1;
    @FXML private Label address2_2;
    @FXML private Label address3_1;
    @FXML private Label address3_2;
    
    String empID = "";
    String customerCNIC = "";
    String customerID = "";
    String prevPageID = "";
    
    public void setParameter(String empID, String c)throws SQLException
    {
        this.empID = empID;
        this.customerCNIC = c;
        
        setDetailDataOfUser();
    }
    
    public void setParameter(String empID, Customer c)    throws SQLException
    {
        this.empID = empID;
        this.customerCNIC = c.getCnic();
        
        setDetailDataOfUser();
    }
    
    
    public void setParameter(String empID, Customer c, String s)    throws SQLException
    {
        this.empID = empID;
        this.customerCNIC = c.getCnic();
        this.prevPageID = s;
        
        setDetailDataOfUser();
    }
    
    public void setParameter(String empID, String c, String s)    throws SQLException
    {
        this.empID = empID;
        this.customerCNIC = c;
        this.prevPageID = s;
        
        setDetailDataOfUser();
    }
    
    public void setDetailDataOfUser() throws SQLException
    {
        
            System.out.println("Customer CNIC from Account : "+customerCNIC);
            cnic.setText(customerCNIC);
            
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
            Statement stmt = conn.createStatement();
            
            //ResultSet rs = stmt.executeQuery("select first_name, last_name, gender, DOB, CNIC, joining_date from customer where email = '" + s + "';");
            ResultSet rs = stmt.executeQuery("select first_name, last_name, gender, DOB, email, joining_date, leaving_date, customer_deleted.customerID from customer_deleted where customer_deleted.CNIC = '" + customerCNIC + "';");
            boolean temp = rs.next();
            
            name.setText(rs.getString(1) + " " + rs.getString(2));
            gender.setText(rs.getString(3));
            dob.setText(rs.getString(4));
            System.out.println("\nDate Of Birth : "+dob.getText());
            username.setText(rs.getString(5));
            joiningDate.setText(rs.getString(6));
            leavingDate.setText(rs.getString(7));
            customerID = rs.getString(8);
            
            //for Age we Do,
            
            rs = stmt.executeQuery("select distinct address, city_name, country_name from customer_deleted, address, customer_address, country, city where customer_address.customerID = customer_deleted.customerID and customer_address.addressID = address.addressID and address.City_cityID = city.cityID and city.countryID = country.countryID and customer_deleted.CNIC = '"+ customerCNIC +"';");
            temp = rs.next();
            
            address1_1.setText(rs.getString(1) + ", ");
            address1_2.setText(rs.getString(2) + ", " + rs.getString(3));

            int counter = 1;
            
            while(rs.next())
            {
                if(counter == 1)
                {
                    address2_1.setText(rs.getString(1) + ", ");
                    address2_2.setText(rs.getString(2) + ", " + rs.getString(3));
                }
                else
                {
                    address3_1.setText(rs.getString(1) + ", ");
                    address3_2.setText(rs.getString(2) + ", " + rs.getString(3));
                }
                counter++;
            }
            
            rs = stmt.executeQuery("select distinct phone_number from customer_deleted, customer_phone where customer_deleted.customerID = customer_phone.customerID and customer_deleted.CNIC = '"+customerCNIC+"';");
            temp = rs.next();
            
            mob1.setText(rs.getString(1));
            
            counter = 1;
            
            while(rs.next())
            {
                if(counter == 1)
                {
                    mob2.setText(rs.getString(1));
                }
                else
                {
                    mob3.setText(rs.getString(1));
                }
                counter++;
            }
        
    }
    
    public void seeBookingButtonPushed(ActionEvent event) throws IOException, SQLException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("LeavedCustomerBooking.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        LeavedCustomerBookingController controller = loader.getController();
        controller.setParameter(empID, customerCNIC, prevPageID);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    
    public void backButtonPushed(ActionEvent event) throws IOException, SQLException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("LeavedCustomer.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        LeavedCustomerController controller = loader.getController();
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
    }    
}
