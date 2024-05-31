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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import verifications.Checks;

/**
 * FXML Controller class
 *
 * @author Hamza Barak
 */
public class ServiceController implements Initializable {

    @FXML private ImageView logo;
    @FXML private ImageView logo2;

    @FXML private ImageView housekeeping;
    @FXML private ImageView cleaning;
    @FXML private ImageView food;
    @FXML private ImageView laundry;
    @FXML private ImageView repair;
    
    @FXML private Label label;
    @FXML private ComboBox combo;
    
    String usernameOfLoggedInPerson;
    String customerID = "";
    String houseID = "";
    String roomID = "";
    String branch = "";
    
    public void setParameter(String s)
    {
        usernameOfLoggedInPerson = s;
        System.out.println(s + "\n" + usernameOfLoggedInPerson);
        
        try
        {
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
            Statement stmt = conn.createStatement();
            
            System.out.println("Customer ID  : " + customerID);
            
            ResultSet rs = stmt.executeQuery("select customerID from customer where email = '" + usernameOfLoggedInPerson + "';");
            
            while(rs.next())
                customerID = rs.getString(1);
            
            String house = "";
            String room = "";
            String b = "";
            
            rs = stmt.executeQuery("select house.houseID, branch_name from house_booking, house, branch where house_booking.houseID = house.houseID and branch.branchID = house.branchID and house_booking.customerID = '"+ customerID +"';");
            
            while(rs.next())
            {
                house = rs.getString(1);
                b = rs.getString(2);
                String temp = "house No." + house + " | " + b;
                combo.getItems().add(temp);
            }
            
            rs = stmt.executeQuery("select room.room_number, house.houseID, branch_name, room.roomID from room_booking, room, house, branch where room_booking.roomID = room.roomID and room.houseID = house.houseID and branch.branchID = house.branchID and room_booking.customerID = '"+ customerID +"';");
            
            while(rs.next())
            {
                room = rs.getString(4);
                b = rs.getString(3);
                String temp = "Room No." + rs.getString(1) + " (Room ID : " + room + "), House No." + rs.getString(2) + " | " + b;
                combo.getItems().add(temp);
            }
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
    
    
    public void houseKeeping()
    {
        if(!combo.getValue().toString().equals("Select Your Booking First"))
        {
            try
            {
                Connection conn;
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
                Statement stmt = conn.createStatement();

                String billsID = "";
                
                if(combo.getValue().toString().substring(0, 4).equals("Room"))
                {
                    int counted = 0;

                    String s = combo.getValue().toString();
                    roomID = s.substring(s.indexOf("(")+11, s.indexOf(")"));
                    branch = s.substring(s.indexOf("|")+2, s.length());
                    
                    ResultSet rs = stmt.executeQuery("select counts from bills, services where bills.serviceID = services.serviceID and service_name = 'housekeeping' and customerID = '"+customerID+"' and bills.booking = 'r"+roomID+"';");

                    if(rs.next())
                    {
                        String counts = rs.getString(1);
                        counted = Integer.parseInt(counts) + 1;
                        int uq = stmt.executeUpdate("UPDATE `mydb`.`bills` SET `counts` = '"+counted+"' WHERE (`customerID` = '"+customerID+"') and (`serviceID` = '1') and (`booking` = 'r"+roomID+"');");
                    }
                    else
                    {
                        int uq = stmt.executeUpdate("INSERT INTO `mydb`.`bills` (`customerID`, `serviceID`, `counts`, `booking`) VALUES ('"+customerID+"', '1', '1', 'r"+roomID+"');");
                    }

                    rs = stmt.executeQuery("Select counts, billsID from bills where customerID = '"+customerID+"' and serviceID = '1' and booking = 'r"+roomID+"';");
                    rs.next();
                    int countTemp = Integer.parseInt(rs.getString(1)) * counted;
                    billsID = rs.getString(2);

                    int bills = 200 * counted; //Bills of service multiplied by counts for that bills;

                    int up = stmt.executeUpdate("UPDATE `mydb`.`customer_record` SET `bills` = '"+bills+"' WHERE (`customerID` = '"+customerID+"') and (`booking` = 'r"+roomID+"');");
                
                    System.out.println("BRANCH  : "+branch);
                    
                    String empID = Checks.getEmpID("housekeeping", branch);
                    
                    if(!empID.equals(""))
                    {
                        System.out.println("EMP ID : " + empID);
                        int uq = stmt.executeUpdate("INSERT INTO `mydb`.`service_record` (`billsID`, `empID`, `date`, `time`) VALUES ('"+billsID+"', '"+empID+"', '"+Checks.getCurrentdate()+"', '"+Checks.getCurrentTime()+"');");
                    }
                    else
                        System.out.println("No Employee is Available");
                }
                else
                {
                    int counted = 0;

                    String s1 = combo.getValue().toString();
                    houseID = s1.substring(s1.indexOf(".")+1, s1.indexOf("|") - 1);
                    branch = s1.substring(s1.indexOf("|")+2, s1.length());
                    
                    ResultSet rs = stmt.executeQuery("select counts, billsID from bills, services where bills.serviceID = services.serviceID and service_name = 'housekeeping' and customerID = '"+customerID+"'and bills.booking = 'h"+houseID+"';");

                    if(rs.next())
                    {
                        String counts = rs.getString(1);
                        counted = Integer.parseInt(counts) + 1;
                        billsID = rs.getString(2);
                        int uq = stmt.executeUpdate("UPDATE `mydb`.`bills` SET `counts` = '"+counted+"' WHERE (`customerID` = '"+customerID+"') and (`serviceID` = '1') and (`booking` = 'h"+houseID+"');");
                    }
                    else
                    {
                        int uq = stmt.executeUpdate("INSERT INTO `mydb`.`bills` (`customerID`, `serviceID`, `counts`, `booking`) VALUES ('"+customerID+"', '1', '"+1+"', 'h"+houseID+"');");
                    }

                    rs = stmt.executeQuery("Select counts from bills where customerID = '"+customerID+"' and serviceID = '1' and booking = 'h"+houseID+"';");
                    rs.next();
                    int countTemp = Integer.parseInt(rs.getString(1)) * counted;


                    int bills = 200 * counted; //Bills of service multiplied by counts for that bills;

                    int up = stmt.executeUpdate("UPDATE `mydb`.`customer_record` SET `bills` = '"+bills+"' WHERE (`customerID` = '"+customerID+"') and (`booking` = 'h"+houseID+"');");
               
                    String empID = Checks.getEmpID("housekeeping", branch);
                    
                    if(!empID.equals(""))
                    {
                        int uq = stmt.executeUpdate("INSERT INTO `mydb`.`service_record` (`billsID`, `empID`, `date`, `time`) VALUES ('"+billsID+"', '"+empID+"', '"+Checks.getCurrentdate()+"', '"+Checks.getCurrentTime()+"');");
               
                    }
                    else
                        System.out.println("No Employee is Available");
                }
                label.setText("");
                Alert a1 = new Alert(Alert.AlertType.INFORMATION);  

                a1.setTitle("HOUSE KEEPING Request");
                a1.setContentText("Request Accepted you will get the service after 30 mints");
                a1.setHeaderText("For Details, Select Show Details!"); //you can also set header

                String temp = "You have Requested For HOUSEKEEPING SERVICE !\nThe Service Charges will be 200 Rupees\nThe Billing Amount has been added to your Bills\nThe Service will be provide to you After Half an Hour\n";

                TextArea ta = new TextArea(temp);
                ta.setEditable(false);
                a1.getDialogPane().setExpandableContent(ta);
                a1.showAndWait();
            }
            catch(Exception e)
            {
                System.out.println(e.toString());
            }
        }
        else
            label.setText("Please First Select Your Booking");
    }
    
    public void food()
    {
        if(!combo.getValue().toString().equals("Select Your Booking First"))
        {
            try
            {
                Connection conn;
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
                Statement stmt = conn.createStatement();

                String billsID = "";
                
                if(combo.getValue().toString().substring(0, 4).equals("Room"))
                {
                    int counted = 0;

                    String s = combo.getValue().toString();
                    roomID = s.substring(s.indexOf("(")+11, s.indexOf(")"));
                    branch = s.substring(s.indexOf("|")+2, s.length());
                    
                    ResultSet rs = stmt.executeQuery("select counts from bills, services where bills.serviceID = services.serviceID and service_name = 'food' and customerID = '"+customerID+"' and bills.booking = 'r"+roomID+"';");

                    if(rs.next())
                    {
                        String counts = rs.getString(1);
                        counted = Integer.parseInt(counts) + 1;
                        int uq = stmt.executeUpdate("UPDATE `mydb`.`bills` SET `counts` = '"+counted+"' WHERE (`customerID` = '"+customerID+"') and (`serviceID` = '4') and (`booking` = 'r"+roomID+"');");
                    }
                    else
                    {
                        int uq = stmt.executeUpdate("INSERT INTO `mydb`.`bills` (`customerID`, `serviceID`, `counts`, `booking`) VALUES ('"+customerID+"', '4', '1', 'r"+roomID+"');");
                    }

                    rs = stmt.executeQuery("Select counts, billsID from bills where customerID = '"+customerID+"' and serviceID = '4' and booking = 'r"+roomID+"';");
                    rs.next();
                    int countTemp = Integer.parseInt(rs.getString(1)) * counted;
                    billsID = rs.getString(2);

                    int bills = 250 * counted; //Bills of service multiplied by counts for that bills;

                    int up = stmt.executeUpdate("UPDATE `mydb`.`customer_record` SET `bills` = '"+bills+"' WHERE (`customerID` = '"+customerID+"') and (`booking` = 'r"+roomID+"');");
                
                    System.out.println("BRANCH  : "+branch);
                    
                    String empID = Checks.getEmpID("housekeeping", branch);
                    
                    if(!empID.equals(""))
                    {
                        System.out.println("EMP ID : " + empID);
                        int uq = stmt.executeUpdate("INSERT INTO `mydb`.`service_record` (`billsID`, `empID`, `date`, `time`) VALUES ('"+billsID+"', '"+empID+"', '"+Checks.getCurrentdate()+"', '"+Checks.getCurrentTime()+"');");
                    }
                    else
                        System.out.println("No Employee is Available");
                }
                else
                {
                    int counted = 0;

                    String s1 = combo.getValue().toString();
                    houseID = s1.substring(s1.indexOf(".")+1, s1.indexOf("|") - 1);
                    branch = s1.substring(s1.indexOf("|")+2, s1.length());
                    
                    ResultSet rs = stmt.executeQuery("select counts, billsID from bills, services where bills.serviceID = services.serviceID and service_name = 'food' and customerID = '"+customerID+"'and bills.booking = 'h"+houseID+"';");

                    if(rs.next())
                    {
                        String counts = rs.getString(1);
                        counted = Integer.parseInt(counts) + 1;
                        billsID = rs.getString(2);
                        int uq = stmt.executeUpdate("UPDATE `mydb`.`bills` SET `counts` = '"+counted+"' WHERE (`customerID` = '"+customerID+"') and (`serviceID` = '4') and (`booking` = 'h"+houseID+"');");
                    }
                    else
                    {
                        int uq = stmt.executeUpdate("INSERT INTO `mydb`.`bills` (`customerID`, `serviceID`, `counts`, `booking`) VALUES ('"+customerID+"', '4', '"+1+"', 'h"+houseID+"');");
                    }

                    rs = stmt.executeQuery("Select counts from bills where customerID = '"+customerID+"' and serviceID = '4' and booking = 'h"+houseID+"';");
                    rs.next();
                    int countTemp = Integer.parseInt(rs.getString(1)) * counted;


                    int bills = 250 * counted; //Bills of service multiplied by counts for that bills;

                    int up = stmt.executeUpdate("UPDATE `mydb`.`customer_record` SET `bills` = '"+bills+"' WHERE (`customerID` = '"+customerID+"') and (`booking` = 'h"+houseID+"');");
               
                    String empID = Checks.getEmpID("cook", branch);
                    
                    if(!empID.equals(""))
                    {
                        int uq = stmt.executeUpdate("INSERT INTO `mydb`.`service_record` (`billsID`, `empID`, `date`, `time`) VALUES ('"+billsID+"', '"+empID+"', '"+Checks.getCurrentdate()+"', '"+Checks.getCurrentTime()+"');");
               
                    }
                    else
                        System.out.println("No Employee is Available");
                }
                
                label.setText("");
                Alert a1 = new Alert(Alert.AlertType.INFORMATION);  

                a1.setTitle("FOOD Request");
                a1.setContentText("Request Accepted you will get the service after 30 mints");
                a1.setHeaderText("For Details, Select Show Details!"); //you can also set header

                String temp = "You have Requested For FOOD SERVICE !\nThe Service Charges will be 250 Rupees\nThe Billing Amount has been added to your Bills\nThe Service will be provide to you After Half an Hour\n";

                TextArea ta = new TextArea(temp);
                ta.setEditable(false);
                a1.getDialogPane().setExpandableContent(ta);
                a1.showAndWait();
            }
            catch(Exception e)
            {
                System.out.println(e.toString());
            }
        }
        else
            label.setText("Please First Select Your Booking");
    }
    
    public void cleaning() //for cleaner must use term sweeper
    {
        if(!combo.getValue().toString().equals("Select Your Booking First"))
        {
            try
            {
                Connection conn;
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
                Statement stmt = conn.createStatement();

                String billsID = "";
                
                if(combo.getValue().toString().substring(0, 4).equals("Room"))
                {
                    int counted = 0;

                    String s = combo.getValue().toString();
                    roomID = s.substring(s.indexOf("(")+11, s.indexOf(")"));
                    branch = s.substring(s.indexOf("|")+2, s.length());
                    
                    ResultSet rs = stmt.executeQuery("select counts from bills, services where bills.serviceID = services.serviceID and service_name = 'cleaning' and customerID = '"+customerID+"' and bills.booking = 'r"+roomID+"';");

                    if(rs.next())
                    {
                        String counts = rs.getString(1);
                        counted = Integer.parseInt(counts) + 1;
                        int uq = stmt.executeUpdate("UPDATE `mydb`.`bills` SET `counts` = '"+counted+"' WHERE (`customerID` = '"+customerID+"') and (`serviceID` = '3') and (`booking` = 'r"+roomID+"');");
                    }
                    else
                    {
                        int uq = stmt.executeUpdate("INSERT INTO `mydb`.`bills` (`customerID`, `serviceID`, `counts`, `booking`) VALUES ('"+customerID+"', '3', '1', 'r"+roomID+"');");
                    }

                    rs = stmt.executeQuery("Select counts, billsID from bills where customerID = '"+customerID+"' and serviceID = '3' and booking = 'r"+roomID+"';");
                    rs.next();
                    int countTemp = Integer.parseInt(rs.getString(1)) * counted;
                    billsID = rs.getString(2);

                    int bills = 100 * counted; //Bills of service multiplied by counts for that bills;

                    int up = stmt.executeUpdate("UPDATE `mydb`.`customer_record` SET `bills` = '"+bills+"' WHERE (`customerID` = '"+customerID+"') and (`booking` = 'r"+roomID+"');");
                
                    System.out.println("BRANCH  : "+branch);
                    
                    String empID = Checks.getEmpID("sweeper", branch);
                    
                    if(!empID.equals(""))
                    {
                        System.out.println("EMP ID : " + empID);
                        int uq = stmt.executeUpdate("INSERT INTO `mydb`.`service_record` (`billsID`, `empID`, `date`, `time`) VALUES ('"+billsID+"', '"+empID+"', '"+Checks.getCurrentdate()+"', '"+Checks.getCurrentTime()+"');");
                    }
                    else
                        System.out.println("No Employee is Available");
                }
                else
                {
                    int counted = 0;

                    String s1 = combo.getValue().toString();
                    houseID = s1.substring(s1.indexOf(".")+1, s1.indexOf("|") - 1);
                    branch = s1.substring(s1.indexOf("|")+2, s1.length());
                    
                    ResultSet rs = stmt.executeQuery("select counts, billsID from bills, services where bills.serviceID = services.serviceID and service_name = 'cleaning' and customerID = '"+customerID+"'and bills.booking = 'h"+houseID+"';");

                    if(rs.next())
                    {
                        String counts = rs.getString(1);
                        counted = Integer.parseInt(counts) + 1;
                        billsID = rs.getString(2);
                        int uq = stmt.executeUpdate("UPDATE `mydb`.`bills` SET `counts` = '"+counted+"' WHERE (`customerID` = '"+customerID+"') and (`serviceID` = '3') and (`booking` = 'h"+houseID+"');");
                    }
                    else
                    {
                        int uq = stmt.executeUpdate("INSERT INTO `mydb`.`bills` (`customerID`, `serviceID`, `counts`, `booking`) VALUES ('"+customerID+"', '3', '"+1+"', 'h"+houseID+"');");
                    }

                    rs = stmt.executeQuery("Select counts from bills where customerID = '"+customerID+"' and serviceID = '3' and booking = 'h"+houseID+"';");
                    rs.next();
                    int countTemp = Integer.parseInt(rs.getString(1)) * counted;


                    int bills = 100 * counted; //Bills of service multiplied by counts for that bills;

                    int up = stmt.executeUpdate("UPDATE `mydb`.`customer_record` SET `bills` = '"+bills+"' WHERE (`customerID` = '"+customerID+"') and (`booking` = 'h"+houseID+"');");
               
                    String empID = Checks.getEmpID("sweeper", branch);
                    
                    if(!empID.equals(""))
                    {
                        int uq = stmt.executeUpdate("INSERT INTO `mydb`.`service_record` (`billsID`, `empID`, `date`, `time`) VALUES ('"+billsID+"', '"+empID+"', '"+Checks.getCurrentdate()+"', '"+Checks.getCurrentTime()+"');");
               
                    }
                    else
                        System.out.println("No Employee is Available");
                }
                
                label.setText("");
                Alert a1 = new Alert(Alert.AlertType.INFORMATION);  

                a1.setTitle("CLEANING Request");
                a1.setContentText("Request Accepted you will get the service after 30 mints");
                a1.setHeaderText("For Details, Select Show Details!"); //you can also set header

                String temp = "You have Requested For CLEANING SERVICE !\nThe Service Charges will be 100 Rupees\nThe Billing Amount has been added to your Bills\nThe Service will be provide to you After Half an Hour\n";

                TextArea ta = new TextArea(temp);
                ta.setEditable(false);
                a1.getDialogPane().setExpandableContent(ta);
                a1.showAndWait();
            }
            catch(Exception e)
            {
                System.out.println(e.toString());
            }
        }
        else
            label.setText("Please First Select Your Booking");
    }
    
    public void repairing()
    {
        if(!combo.getValue().toString().equals("Select Your Booking First"))
        {
            try
            {
                Connection conn;
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
                Statement stmt = conn.createStatement();

                String billsID = "";
                
                if(combo.getValue().toString().substring(0, 4).equals("Room"))
                {
                    int counted = 0;

                    String s = combo.getValue().toString();
                    roomID = s.substring(s.indexOf("(")+11, s.indexOf(")"));
                    branch = s.substring(s.indexOf("|")+2, s.length());
                    
                    ResultSet rs = stmt.executeQuery("select counts from bills, services where bills.serviceID = services.serviceID and service_name = 'repair' and customerID = '"+customerID+"' and bills.booking = 'r"+roomID+"';");

                    if(rs.next())
                    {
                        String counts = rs.getString(1);
                        counted = Integer.parseInt(counts) + 1;
                        int uq = stmt.executeUpdate("UPDATE `mydb`.`bills` SET `counts` = '"+counted+"' WHERE (`customerID` = '"+customerID+"') and (`serviceID` = '2') and (`booking` = 'r"+roomID+"');");
                    }
                    else
                    {
                        int uq = stmt.executeUpdate("INSERT INTO `mydb`.`bills` (`customerID`, `serviceID`, `counts`, `booking`) VALUES ('"+customerID+"', '2', '1', 'r"+roomID+"');");
                    }

                    rs = stmt.executeQuery("Select counts, billsID from bills where customerID = '"+customerID+"' and serviceID = '2' and booking = 'r"+roomID+"';");
                    rs.next();
                    int countTemp = Integer.parseInt(rs.getString(1)) * counted;
                    billsID = rs.getString(2);

                    int bills = 150 * counted; //Bills of service multiplied by counts for that bills;

                    int up = stmt.executeUpdate("UPDATE `mydb`.`customer_record` SET `bills` = '"+bills+"' WHERE (`customerID` = '"+customerID+"') and (`booking` = 'r"+roomID+"');");
                
                    System.out.println("BRANCH  : "+branch);
                    
                    String empID = Checks.getEmpID("repairer", branch);
                    
                    if(!empID.equals(""))
                    {
                        System.out.println("EMP ID : " + empID);
                        int uq = stmt.executeUpdate("INSERT INTO `mydb`.`service_record` (`billsID`, `empID`, `date`, `time`) VALUES ('"+billsID+"', '"+empID+"', '"+Checks.getCurrentdate()+"', '"+Checks.getCurrentTime()+"');");
                    }
                    else
                        System.out.println("No Employee is Available");
                }
                else
                {
                    int counted = 0;

                    String s1 = combo.getValue().toString();
                    houseID = s1.substring(s1.indexOf(".")+1, s1.indexOf("|") - 1);
                    branch = s1.substring(s1.indexOf("|")+2, s1.length());
                    
                    ResultSet rs = stmt.executeQuery("select counts, billsID from bills, services where bills.serviceID = services.serviceID and service_name = 'repair' and customerID = '"+customerID+"'and bills.booking = 'h"+houseID+"';");

                    if(rs.next())
                    {
                        String counts = rs.getString(1);
                        counted = Integer.parseInt(counts) + 1;
                        billsID = rs.getString(2);
                        int uq = stmt.executeUpdate("UPDATE `mydb`.`bills` SET `counts` = '"+counted+"' WHERE (`customerID` = '"+customerID+"') and (`serviceID` = '2') and (`booking` = 'h"+houseID+"');");
                    }
                    else
                    {
                        int uq = stmt.executeUpdate("INSERT INTO `mydb`.`bills` (`customerID`, `serviceID`, `counts`, `booking`) VALUES ('"+customerID+"', '2', '"+1+"', 'h"+houseID+"');");
                    }

                    rs = stmt.executeQuery("Select counts from bills where customerID = '"+customerID+"' and serviceID = '2' and booking = 'h"+houseID+"';");
                    rs.next();
                    int countTemp = Integer.parseInt(rs.getString(1)) * counted;


                    int bills = 150 * counted; //Bills of service multiplied by counts for that bills;

                    int up = stmt.executeUpdate("UPDATE `mydb`.`customer_record` SET `bills` = '"+bills+"' WHERE (`customerID` = '"+customerID+"') and (`booking` = 'h"+houseID+"');");
               
                    String empID = Checks.getEmpID("repairer", branch);
                    
                    if(!empID.equals(""))
                    {
                        int uq = stmt.executeUpdate("INSERT INTO `mydb`.`service_record` (`billsID`, `empID`, `date`, `time`) VALUES ('"+billsID+"', '"+empID+"', '"+Checks.getCurrentdate()+"', '"+Checks.getCurrentTime()+"');");
               
                    }
                    else
                        System.out.println("No Employee is Available");
                }
                
                label.setText("");
                Alert a1 = new Alert(Alert.AlertType.INFORMATION);  

                a1.setTitle("REPAIR Request");
                a1.setContentText("Request Accepted you will get the service after 30 mints");
                a1.setHeaderText("For Details, Select Show Details!"); //you can also set header

                String temp = "You have Requested For REPAIR SERVICE !\nThe Service Charges will be 150 Rupees\nThe Billing Amount has been added to your Bills\nThe Service will be provide to you After Half an Hour\n";

                TextArea ta = new TextArea(temp);
                ta.setEditable(false);
                a1.getDialogPane().setExpandableContent(ta);
                a1.showAndWait();
            }
            catch(Exception e)
            {
                System.out.println(e.toString());
            }
        }
        else
            label.setText("Please First Select Your Booking");
    }
    
    public void laundry()
    {
        if(!combo.getValue().toString().equals("Select Your Booking First"))
        {
            try
            {
                Connection conn;
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
                Statement stmt = conn.createStatement();

                String billsID = "";
                
                if(combo.getValue().toString().substring(0, 4).equals("Room"))
                {
                    int counted = 0;

                    String s = combo.getValue().toString();
                    roomID = s.substring(s.indexOf("(")+11, s.indexOf(")"));
                    branch = s.substring(s.indexOf("|")+2, s.length());
                    
                    ResultSet rs = stmt.executeQuery("select counts from bills, services where bills.serviceID = services.serviceID and service_name = 'laundry' and customerID = '"+customerID+"' and bills.booking = 'r"+roomID+"';");

                    if(rs.next())
                    {
                        String counts = rs.getString(1);
                        counted = Integer.parseInt(counts) + 1;
                        int uq = stmt.executeUpdate("UPDATE `mydb`.`bills` SET `counts` = '"+counted+"' WHERE (`customerID` = '"+customerID+"') and (`serviceID` = '5') and (`booking` = 'r"+roomID+"');");
                    }
                    else
                    {
                        int uq = stmt.executeUpdate("INSERT INTO `mydb`.`bills` (`customerID`, `serviceID`, `counts`, `booking`) VALUES ('"+customerID+"', '5', '1', 'r"+roomID+"');");
                    }

                    rs = stmt.executeQuery("Select counts, billsID from bills where customerID = '"+customerID+"' and serviceID = '5' and booking = 'r"+roomID+"';");
                    rs.next();
                    int countTemp = Integer.parseInt(rs.getString(1)) * counted;
                    billsID = rs.getString(2);

                    int bills = 100 * counted; //Bills of service multiplied by counts for that bills;

                    int up = stmt.executeUpdate("UPDATE `mydb`.`customer_record` SET `bills` = '"+bills+"' WHERE (`customerID` = '"+customerID+"') and (`booking` = 'r"+roomID+"');");
                
                    System.out.println("BRANCH  : "+branch);
                    
                    String empID = Checks.getEmpID("laundryman", branch);
                    
                    if(!empID.equals(""))
                    {
                        System.out.println("EMP ID : " + empID);
                        int uq = stmt.executeUpdate("INSERT INTO `mydb`.`service_record` (`billsID`, `empID`, `date`, `time`) VALUES ('"+billsID+"', '"+empID+"', '"+Checks.getCurrentdate()+"', '"+Checks.getCurrentTime()+"');");
                    }
                    else
                        System.out.println("No Employee is Available");
                }
                else
                {
                    int counted = 0;

                    String s1 = combo.getValue().toString();
                    houseID = s1.substring(s1.indexOf(".")+1, s1.indexOf("|") - 1);
                    branch = s1.substring(s1.indexOf("|")+2, s1.length());
                    
                    ResultSet rs = stmt.executeQuery("select counts, billsID from bills, services where bills.serviceID = services.serviceID and service_name = 'laundry' and customerID = '"+customerID+"'and bills.booking = 'h"+houseID+"';");

                    if(rs.next())
                    {
                        String counts = rs.getString(1);
                        counted = Integer.parseInt(counts) + 1;
                        billsID = rs.getString(2);
                        int uq = stmt.executeUpdate("UPDATE `mydb`.`bills` SET `counts` = '"+counted+"' WHERE (`customerID` = '"+customerID+"') and (`serviceID` = '5') and (`booking` = 'h"+houseID+"');");
                    }
                    else
                    {
                        int uq = stmt.executeUpdate("INSERT INTO `mydb`.`bills` (`customerID`, `serviceID`, `counts`, `booking`) VALUES ('"+customerID+"', '5', '"+1+"', 'h"+houseID+"');");
                    }

                    rs = stmt.executeQuery("Select counts from bills where customerID = '"+customerID+"' and serviceID = '5' and booking = 'h"+houseID+"';");
                    rs.next();
                    int countTemp = Integer.parseInt(rs.getString(1)) * counted;


                    int bills = 100 * counted; //Bills of service multiplied by counts for that bills;

                    int up = stmt.executeUpdate("UPDATE `mydb`.`customer_record` SET `bills` = '"+bills+"' WHERE (`customerID` = '"+customerID+"') and (`booking` = 'h"+houseID+"');");
               
                    String empID = Checks.getEmpID("laundryman", branch);
                    
                    if(!empID.equals(""))
                    {
                        int uq = stmt.executeUpdate("INSERT INTO `mydb`.`service_record` (`billsID`, `empID`, `date`, `time`) VALUES ('"+billsID+"', '"+empID+"', '"+Checks.getCurrentdate()+"', '"+Checks.getCurrentTime()+"');");
               
                    }
                    else
                        System.out.println("No Employee is Available");
                }
                
                label.setText("");
                Alert a1 = new Alert(Alert.AlertType.INFORMATION);  

                a1.setTitle("LAUNDRY Request");
                a1.setContentText("Request Accepted you will get the service after 30 mints");
                a1.setHeaderText("For Details, Select Show Details!"); //you can also set header

                String temp = "You have Requested For LAUNDRY SERVICE !\nThe Service Charges will be 100 Rupees\nThe Billing Amount has been added to your Bills\nThe Service will be provide to you After Half an Hour\n";

                TextArea ta = new TextArea(temp);
                ta.setEditable(false);
                a1.getDialogPane().setExpandableContent(ta);
                a1.showAndWait();
            }
            catch(Exception e)
            {
                System.out.println(e.toString());
            }
        }
        else
            label.setText("Please First Select Your Booking");
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
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        logo = new ImageView("logo1.png");
        logo2 = new ImageView("room.png");
        
        housekeeping = new ImageView("housekeeping.png");
        cleaning = new ImageView("cleaning.png");
        food = new ImageView("food.png");
        laundry = new ImageView("laundry.png");
        repair = new ImageView("repair.png");
        
        combo.setValue("Select Your Booking First");
        
//        try
//        {
//            
//        }
//        catch(Exception e)
//        {
//            
//        }
    }    
    
}
