package guesthousemanagement;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class BillsController implements Initializable 
{

    @FXML private ImageView logo;
    @FXML private ImageView logo2;

    @FXML private ImageView currentBill;
    @FXML private ImageView billRecord;
    
    String usernameOfLoggedInPerson;
    
    String counts = "";
    String rate = "";
    String serviceName = "";
    
    int total = 0;
    
    public void setParameter(String s)
    {
        usernameOfLoggedInPerson = s;
        
        try
        {
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
            Statement stmt = conn.createStatement();
            
            ResultSet rs = stmt.executeQuery("select service_name, rate, counts from bills, services, (select customerID from customer where email = '" + usernameOfLoggedInPerson + "')temps where temps.customerID = bills.customerID and services.serviceID = bills.serviceID;");
            
            while(rs.next())
            {
                serviceName = serviceName + rs.getString(1) + "#";
                
                int temp1 = Integer.parseInt(rs.getString(2));
                int temp2 = Integer.parseInt(rs.getString(3));
                
                rate = rate + temp1 + "#";
                counts = counts + temp2 + "#";
                
                total = total + (temp1 * temp2);
            }
            
            System.out.println(serviceName + "\n" + rate + "\n" + counts);
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
    
    
    public void currentBillButtonPushed()
    {
        Alert a1 = new Alert(Alert.AlertType.INFORMATION);  
        
        a1.setTitle("CURRENT BILLS");
        a1.setContentText("Your Current Bills : " + total);
        a1.setHeaderText(null); //you can also set header
        a1.showAndWait();
    }
    
    
    public void billRecordButtonPushed()
    {
        Alert a1 = new Alert(Alert.AlertType.INFORMATION);  
        
        a1.setTitle("BILLING RECORD");
        a1.setContentText("Total Bill : " + total);
        a1.setHeaderText("For Complete Record, Select Show Details!"); //you can also set header
                         
        String temp = "\t__________________________________________________________________________\n\tSERVICE NAME \t\t SERVICE RATE \t\t NUMBERS OF REQ.\n\t-----------------------------------------------------------------------------\n";
        
        String serviceNameA[] = serviceName.split("#");    
        String rateA[] = rate.split("#");
        String countsA[] = counts.split("#");
        
        for(int i = 0; i < serviceNameA.length; i++)
        {
            if(serviceNameA[i].length() >= 10)
                temp = temp + "\t" + serviceNameA[i] + "\t\t\t\t" + rateA[i] + "\t\t\t\t\t" + countsA[i] + "\n";
            else if(serviceNameA[i].length() < 10 && serviceNameA[i].length() > 6)
                temp = temp + "\t" + serviceNameA[i] + "\t\t\t\t\t" + rateA[i] + "\t\t\t\t\t" + countsA[i] + "\n";
            else
                temp = temp + "\t" + serviceNameA[i] + "\t\t\t\t\t\t" + rateA[i] + "\t\t\t\t\t" + countsA[i] + "\n";
        }
        temp = temp + "\t__________________________________________________________________________\n";
//        laundryman
//        cook
//        repairer
//        sweeper
//        housekeeping
                
        TextArea ta = new TextArea(temp);
        ta.setEditable(false);
        a1.getDialogPane().setExpandableContent(ta);
        a1.showAndWait();
    }
    
    //This Method is for moving back to previous page/Screen from which this scene/screen is called
    public void backButtonPushed(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("CustomerProfile.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        CustomerProfileController controller = loader.getController();
        controller.setParameter(usernameOfLoggedInPerson);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    } 
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        logo = new ImageView("logo1.png");
        logo2 = new ImageView("bill.png");
        
        currentBill = new ImageView("currentBill.png");
        billRecord = new ImageView("billRecord.png");
        // TODO
    }    
    
}
