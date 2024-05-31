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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import verifications.Checks;

/**
 * FXML Controller class
 *
 * @author Hamza Barak
 */
public class EarningController implements Initializable {

    String empID = "";
    
    public void setParameter(String empID)
    {
        this.empID = empID;
    }
    
    public void wholeEarningButtonPushed() throws SQLException
    {
        Connection conn;
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
        Statement stmt = conn.createStatement();
        
        String bills = "0";
        
        System.out.println("BILLS : " +bills);
        
        ResultSet rs = stmt.executeQuery("select sum(bills) from customer_record;");
        rs.next();
        bills = rs.getString(1);
        
        int earning = 0;
        
        String roomData[];
        String houseData[];
        
        String houseTemp = "";
        String roomTemp = "";
        
        rs = stmt.executeQuery("select booking from customer_record;");
        while(rs.next())
        {
            String selection = rs.getString(1);
            System.out.println(selection);
            if(selection.startsWith("r"))
                roomTemp = roomTemp + selection + "#";
            else
                houseTemp = houseTemp + selection + "#";
        }
        roomData = roomTemp.split("#");
        houseData = houseTemp.split("#");
        
        for(int i = 0; i < roomData.length; i++)
        {
            rs = stmt.executeQuery("select house.huose_type from house, room where room.houseID = house.houseID and room.roomID = '"+roomData[i].substring(1, roomData[i].length())+"';");
            while(rs.next())
            {
                if(rs.getString(1).equals("roomsOnlyEconomy"))
                    earning = earning + 10000;
                else
                    earning = earning + 15000;
            }
        }
        
        for(int i = 0; i < houseData.length; i++)
        {
            rs = stmt.executeQuery("select house.huose_type from house where houseID = '"+houseData[i].substring(1, houseData[i].length())+"';");
            while(rs.next())
            {
                if(rs.getString(1).equals("Economy"))
                    earning = earning + 20000;
                else
                    earning = earning + 25000;
            }
        }
        
        earning = earning + Integer.parseInt(bills);
        
        Alert a1 = new Alert(Alert.AlertType.INFORMATION); 
        a1.setTitle("TOTAL EARNING");
        a1.setContentText("Total Earnings : " + earning + " Rupees");
        a1.setHeaderText(null); //you can also set header
        a1.showAndWait();
    }
    
    public void earningRecordButtonPushed() throws SQLException
    {
        Alert a1 = new Alert(Alert.AlertType.INFORMATION); 
                                                             
        a1.setTitle("EARNING RECORD");
        a1.setContentText("See the whole Record of Earning!");
        a1.setHeaderText("See the whole Record of Earning!");
        
        String temp = "\t-----------------------------------------------------------------------------------------------------------------\n\t CUSTOMER ID   \t\t BOOKING \t\t BOOKING DATE \t\t LEAVING DATE \t\t BILLS\n\t------------------------------------------------------------------------------------------------------------------\n";
        
        Connection conn;
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
        Statement stmt = conn.createStatement();
        
        ResultSet rs = stmt.executeQuery("SELECT * FROM mydb.customer_record;");
        
        while(rs.next())
        {
            temp = temp + "\t\t\t " + rs.getString(1) + "\t\t\t\t"  + rs.getString(2) + "\t\t\t   " + rs.getString(3) + "\t\t\t " + rs.getString(4) + "\t\t\t  " + rs.getString(5) + "\n";
        }
        
        temp = temp + "\t-----------------------------------------------------------------------------------------------------------------";
        //TextArea ta = new TextArea(s.toString());
        TextArea ta = new TextArea(temp);
        a1.getDialogPane().setExpandableContent(ta);
        a1.showAndWait();
    }
    
    public void earningOfThisMonthButtonPushed() throws SQLException
    {
        String date = Checks.getCurrentdate();
        
        String dateData[] = date.split("-");
        
        String currentDate = "";
        
        if(dateData[1].length() == 1)
            currentDate = dateData[0] + "-" + "0" + dateData[1];
        else
            currentDate = dateData[0] + "-" + dateData[1];
        
        System.out.println("Current Date : " + currentDate);
        
        Connection conn;
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
        Statement stmt = conn.createStatement();
        
        int bills = 0;
        
        System.out.println("Bills : " + bills);
        
        ResultSet rs = stmt.executeQuery("select sum(bills) from customer_record where booking_date like '"+currentDate+"%';");
        if(rs.next())
        {
            String temp = rs.getString(1);
            System.out.println("TEMP : " + temp);
            
            if(temp != null)
                bills = Integer.parseInt(temp);
        }
        
        int earning = 0;
        
        String roomData[];
        String houseData[];
        
        String houseTemp = "";
        String roomTemp = "";
        
        rs = stmt.executeQuery("select booking, booking_date from customer_record where booking_date like '"+currentDate+"%';");
        while(rs.next())
        {
            String bookingDate = rs.getString(2); 
            String selection = rs.getString(1);
            
//            if(bookingDate.substring(0, 7).equals(Checks.getCurrentdate().substring(0, 7)))
//            {
                if(selection.startsWith("r"))
                    roomTemp = roomTemp + selection + "#";
                else
                    houseTemp = houseTemp + selection + "#";
//            }
        }
        roomData = roomTemp.split("#");
        houseData = houseTemp.split("#");
        
        for(int i = 0; i < roomData.length; i++)
        {
            if(!roomData[i].equals(""))
            {
                System.out.println("ROOM DATA FROM ARRAY INDEX " + i + " : " + roomData[i]);
                rs = stmt.executeQuery("select house.huose_type from house, room where room.houseID = house.houseID and room.roomID = '"+roomData[i].substring(1, roomData[i].length())+"';");
                while(rs.next())
                {
                    if(rs.getString(1).equals("roomsOnlyEconomy"))
                        earning = earning + 10000;
                    else
                        earning = earning + 15000;
                }
            }
        }
        
        for(int i = 0; i < houseData.length; i++)
        {
            if(!houseData[i].equals(""))
            {
                rs = stmt.executeQuery("select house.huose_type from house where houseID = '"+houseData[i].substring(1, houseData[i].length())+"';");
                while(rs.next())
                {
                    if(rs.getString(1).equals("Economy"))
                        earning = earning + 20000;
                    else
                        earning = earning + 25000;
                }
            }
        }
        
        earning = earning + bills;
        
        Alert a1 = new Alert(Alert.AlertType.INFORMATION); 
        a1.setTitle("TOTAL EARNING");
        a1.setContentText("Total Earnings of this Month : " + earning + " Rupees");
        a1.setHeaderText(null); //you can also set header
        a1.showAndWait();
    }
    
    public void backButtonPushed(ActionEvent event) throws IOException, SQLException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AdminProfile.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        AdminProfileController controller = loader.getController();
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
    }    
    
}
