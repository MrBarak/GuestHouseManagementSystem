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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Hamza Barak
 */
public class CustomerBookingController implements Initializable {

    @FXML private TableView<HouseModalTable> tableView;
    @FXML private TableColumn<HouseModalTable, String> srNoC;
    @FXML private TableColumn<HouseModalTable, String> booking; //this will consider as the House Name
    @FXML private TableColumn<HouseModalTable, String> address;
    @FXML private TableColumn<HouseModalTable, String> bookingDate;
    @FXML private TableColumn<HouseModalTable, String> leavingDate;
    
    @FXML private ComboBox combo;
    
    String empID = "";
    String customerCNIC = "";
    String prevPageID = "";
    
    public void setParameter(String empID, String customerCNIC, String s) throws SQLException
    {
        this.empID = empID;
        this.customerCNIC = customerCNIC;
        this.prevPageID = s;
        
        setTableDataUponCombo();
    }
    
    public void setTableDataUponCombo() throws SQLException
    {
        ObservableList<HouseModalTable> list = FXCollections.observableArrayList();
        
        Connection conn;
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
        Statement stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery("Select customerID from customer where CNIC = '" + customerCNIC + "';");
        boolean temp = rs.next();
        String customerID = rs.getString(1);
        
        if(combo.getValue().toString().equals("LUXURY"))
        {
            boolean check = false;
            
            String houseID[];
            String tempHouse = "";
            
            String address[];
            String tempAddress = "";
            
            rs = stmt.executeQuery("select house.houseID, branch.branch_name, location.location from house, house_booking, branch, location where house.houseID = house_booking.houseID and house.branchID = branch.branchID and branch.locationID = location.locationID  and house_booking.customerID = '"+customerID+"' and house.huose_Type = 'Luxury';");
            
            while(rs.next())
            {
                check = true;
                tempHouse = tempHouse + rs.getString(1) + "#";
                tempAddress = tempAddress + rs.getString(2) + ", " + rs.getString(3) + "#";
            }
            
            houseID = tempHouse.split("#");
            address = tempAddress.split("#");
            
            String booking[];
            String leaving[];
            
            String tempBooking = "";
            String tempLeaving = "";
            
            for(int i = 0; i < houseID.length; i++)
            {
                rs = stmt.executeQuery("select booking_date, leaving_date from customer_record where customer_record.customerID = '"+customerID+"' and booking = 'h" + houseID[i] + "';");
                
                while(rs.next())
                {
                    tempBooking = tempBooking + rs.getString(1) + "#";
                    tempLeaving = tempLeaving + rs.getString(2) + "#";
                }
            }
            
            booking = tempBooking.split("#");
            leaving = tempLeaving.split("#");
            
            int counter = 1;
            
            if(check)
            {
                for(int i = 0; i < houseID.length; i++)
                {
                    list.add(new HouseModalTable(Integer.toString(counter), "House." + houseID[i], address[i], booking[i], leaving[i]));
                    counter++;
                }
            }
            //=============================================================================================================================================================
            
            check = false;
            
            String tempForEmpty[] = new String[0];
            houseID = tempForEmpty;
            address = tempForEmpty;
            booking = tempForEmpty;
            leaving = tempForEmpty;
            
            String room[]; //Room Number Not the Room ID
            String roomID[];
            
            tempHouse = "";
            tempAddress = "";
            tempBooking = "";
            tempLeaving = "";
            String tempRoomNo = "";
            String tempRoomID = "";
            
            rs = stmt.executeQuery("select room.room_number, room.roomID, house.houseID, branch.branch_name, location.location from room, house, room_booking, branch, location where room.houseID = house.houseID and room.roomID = room_booking.roomID and house.branchID = branch.branchID and branch.locationID = location.locationID  and room_booking.customerID = '"+customerID+"' and house.huose_Type = 'roomsOnlyLuxury';");
            
            while(rs.next())
            {
                check = true;
                tempRoomNo = tempRoomNo + rs.getString(1) + "#";
                tempRoomID = tempRoomID + rs.getString(2) + "#";
                tempHouse = tempHouse + rs.getString(3) + "#";
                tempAddress = tempAddress + rs.getString(4) + ", " + rs.getString(5) + "#";
            }
            
            room = tempRoomNo.split("#");
            roomID = tempRoomID.split("#");
            houseID = tempHouse.split("#");
            address = tempAddress.split("#");
            
            
            for(int i = 0; i < roomID.length; i++)
            {
                rs = stmt.executeQuery("select booking_date, leaving_date from customer_record where customer_record.customerID = '"+customerID+"' and booking = 'r" + roomID[i] + "';");
                
                while(rs.next())
                {
                    tempBooking = tempBooking + rs.getString(1) + "#";
                    tempLeaving = tempLeaving + rs.getString(2) + "#";
                }
            }
            
            booking = tempBooking.split("#");
            leaving = tempLeaving.split("#");
            
            if(check)
            {
                for(int i = 0; i < roomID.length; i++)
                {
                    list.add(new HouseModalTable(Integer.toString(counter), "Room No.(" + roomID[i] + ") HouseID (" + houseID[i] + ")", address[i], booking[i], leaving[i]));
                    counter++;
                }
            }
        }
        else if(combo.getValue().toString().equals("ECONOMY"))
        {
            boolean check = false;
            
            String houseID[];
            String tempHouse = "";
            
            String address[];
            String tempAddress = "";
            
            rs = stmt.executeQuery("select house.houseID, branch.branch_name, location.location from house, house_booking, branch, location where house.houseID = house_booking.houseID and house.branchID = branch.branchID and branch.locationID = location.locationID  and house_booking.customerID = '"+customerID+"' and house.huose_Type = 'Economy';");
        
            while(rs.next())
            {
                check = true;
                tempHouse = tempHouse + rs.getString(1) + "#";
                tempAddress = tempAddress + rs.getString(2) + ", " + rs.getString(3) + "#";
            }
            
            houseID = tempHouse.split("#");
            address = tempAddress.split("#");
            
            String booking[];
            String leaving[];
            
            String tempBooking = "";
            String tempLeaving = "";
            
            for(int i = 0; i < houseID.length; i++)
            {
                rs = stmt.executeQuery("select booking_date, leaving_date from customer_record where customer_record.customerID = '"+customerID+"' and booking = 'h" + houseID[i] + "';");
                
                while(rs.next())
                {
                    tempBooking = tempBooking + rs.getString(1) + "#";
                    tempLeaving = tempLeaving + rs.getString(2) + "#";
                }
            }
            
            booking = tempBooking.split("#");
            leaving = tempLeaving.split("#");
            
            int counter = 1;
            
            if(check)
            {
                for(int i = 0; i < houseID.length; i++)
                {
                    list.add(new HouseModalTable(Integer.toString(counter), "House." + houseID[i], address[i], booking[i], leaving[i]));
                    counter++;
                }
            }
            //=============================================================================================================================================================
            
            check = false;
            
            String tempForEmpty[] = new String[0];
            houseID = tempForEmpty;
            address = tempForEmpty;
            booking = tempForEmpty;
            leaving = tempForEmpty;
            
            String room[]; //Room Number Not the Room ID
            String roomID[];
            
            tempHouse = "";
            tempAddress = "";
            tempBooking = "";
            tempLeaving = "";
            String tempRoomNo = "";
            String tempRoomID = "";
            
            rs = stmt.executeQuery("select room.room_number, room.roomID, house.houseID, branch.branch_name, location.location from room, house, room_booking, branch, location where room.houseID = house.houseID and room.roomID = room_booking.roomID and house.branchID = branch.branchID and branch.locationID = location.locationID  and room_booking.customerID = '"+customerID+"' and house.huose_Type = 'roomsOnlyEconomy';");
            
            while(rs.next())
            {
                check = true;
                tempRoomNo = tempRoomNo + rs.getString(1) + "#";
                tempRoomID = tempRoomID + rs.getString(2) + "#";
                tempHouse = tempHouse + rs.getString(3) + "#";
                tempAddress = tempAddress + rs.getString(4) + ", " + rs.getString(5) + "#";
            }
            
            room = tempRoomNo.split("#");
            roomID = tempRoomID.split("#");
            houseID = tempHouse.split("#");
            address = tempAddress.split("#");
            
            
            for(int i = 0; i < roomID.length; i++)
            {
                rs = stmt.executeQuery("select booking_date, leaving_date from customer_record where customer_record.customerID = '"+customerID+"' and booking = 'r" + roomID[i] + "';");
                
                while(rs.next())
                {
                    tempBooking = tempBooking + rs.getString(1) + "#";
                    tempLeaving = tempLeaving + rs.getString(2) + "#";
                }
            }
            
            booking = tempBooking.split("#");
            leaving = tempLeaving.split("#");
            
            if(check)
            {
                for(int i = 0; i < roomID.length; i++)
                {
                    list.add(new HouseModalTable(Integer.toString(counter), "Room No.(" + roomID[i] + ") HouseID (" + houseID[i] + ")", address[i], booking[i], leaving[i]));
                    counter++;
                }
            }
        }
        else
        {
            boolean check = false;
            
            String houseID[];
            String tempHouse = "";
            
            String address[];
            String tempAddress = "";
            
            rs = stmt.executeQuery("select house.houseID, branch.branch_name, location.location from house, house_booking, branch, location where house.houseID = house_booking.houseID and house.branchID = branch.branchID and branch.locationID = location.locationID  and house_booking.customerID = '"+customerID+"';");
        
            while(rs.next())
            {
                check = true;
                tempHouse = tempHouse + rs.getString(1) + "#";
                tempAddress = tempAddress + rs.getString(2) + ", " + rs.getString(3) + "#";
            }
            
            houseID = tempHouse.split("#");
            address = tempAddress.split("#");
            
            String booking[];
            String leaving[];
            
            String tempBooking = "";
            String tempLeaving = "";
            
            for(int i = 0; i < houseID.length; i++)
            {
                rs = stmt.executeQuery("select booking_date, leaving_date from customer_record where customer_record.customerID = '"+customerID+"' and booking = 'h" + houseID[i] + "';");
                
                while(rs.next())
                {
                    tempBooking = tempBooking + rs.getString(1) + "#";
                    tempLeaving = tempLeaving + rs.getString(2) + "#";
                }
            }
            
            booking = tempBooking.split("#");
            leaving = tempLeaving.split("#");
            
            int counter = 1;
            
            if(check)
            {
                for(int i = 0; i < houseID.length; i++)
                {
                    list.add(new HouseModalTable(Integer.toString(counter), "House." + houseID[i], address[i], booking[i], leaving[i]));
                    counter++;
                }
            }
            //=============================================================================================================================================================
            
            check = false;
            
            String tempForEmpty[] = new String[0];
            houseID = tempForEmpty;
            address = tempForEmpty;
            booking = tempForEmpty;
            leaving = tempForEmpty;
            
            String room[]; //Room Number Not the Room ID
            String roomID[];
            
            tempHouse = "";
            tempAddress = "";
            tempBooking = "";
            tempLeaving = "";
            String tempRoomNo = "";
            String tempRoomID = "";
            
            rs = stmt.executeQuery("select room.room_number, room.roomID, house.houseID, branch.branch_name, location.location from room, house, room_booking, branch, location where room.houseID = house.houseID and room.roomID = room_booking.roomID and house.branchID = branch.branchID and branch.locationID = location.locationID  and room_booking.customerID = '"+customerID+"';");
            
            while(rs.next())
            {
                check = true;
                tempRoomNo = tempRoomNo + rs.getString(1) + "#";
                tempRoomID = tempRoomID + rs.getString(2) + "#";
                tempHouse = tempHouse + rs.getString(3) + "#";
                tempAddress = tempAddress + rs.getString(4) + ", " + rs.getString(5) + "#";
            }
            
            room = tempRoomNo.split("#");
            roomID = tempRoomID.split("#");
            houseID = tempHouse.split("#");
            address = tempAddress.split("#");
            
            
            for(int i = 0; i < roomID.length; i++)
            {
                rs = stmt.executeQuery("select booking_date, leaving_date from customer_record where customer_record.customerID = '"+customerID+"' and booking = 'r" + roomID[i] + "';");
                System.out.println("room ID from All (else) : " + roomID[i]);
                while(rs.next())
                {
                    tempBooking = tempBooking + rs.getString(1) + "#";
                    tempLeaving = tempLeaving + rs.getString(2) + "#";
                }
            }
            
            booking = tempBooking.split("#");
            leaving = tempLeaving.split("#");
            
            if(check)
            {
                for(int i = 0; i < roomID.length; i++)
                {
                    list.add(new HouseModalTable(Integer.toString(counter), "Room No.(" + roomID[i] + ") HouseID (" + houseID[i] + ")", address[i], booking[i], leaving[i]));
                    counter++;
                }
            }
        }
        
        tableView.setItems(list);
    }
    
    public void backButtonPushed(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("DetailedCustomerInfo.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        DetailedCustomerInfoController controller = loader.getController();
        controller.setParameter(empID, customerCNIC, prevPageID);

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
        
        combo.getItems().addAll("ALL", "LUXURY", "ECONOMY");
        
        combo.setValue("ALL");
        
        srNoC.setCellValueFactory(new PropertyValueFactory<HouseModalTable, String>("srNo"));
        booking.setCellValueFactory(new PropertyValueFactory<HouseModalTable, String>("booking"));
        address.setCellValueFactory(new PropertyValueFactory<HouseModalTable, String>("address"));
        bookingDate.setCellValueFactory(new PropertyValueFactory<HouseModalTable, String>("bookingDate"));
        leavingDate.setCellValueFactory(new PropertyValueFactory<HouseModalTable, String>("leavingDate"));
    }    
    
}
