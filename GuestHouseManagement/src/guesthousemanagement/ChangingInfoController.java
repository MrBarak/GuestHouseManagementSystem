package guesthousemanagement;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import verifications.Checks;

public class ChangingInfoController implements Initializable 
{

    @FXML private ImageView logo;
    @FXML private ImageView logo2;
    
    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private DatePicker dob;
    @FXML private TextField cnic;
    //For Radio Button
    @FXML private RadioButton male;
    @FXML private RadioButton female;
    @FXML private ToggleGroup favLang; //this is for grouping the upper 2 radio button, so that only one button can be selected at a time
    
    @FXML private TextField mob1;
    @FXML private TextField mob2;
    @FXML private TextField mob3;
    
    @FXML private TextField address1_city;
    @FXML private TextField address1_country;
    @FXML private TextField address1_address;
    @FXML private TextField address2_city;
    @FXML private TextField address2_country;
    @FXML private TextField address2_address;
    @FXML private TextField address3_city;
    @FXML private TextField address3_country;
    @FXML private TextField address3_address;
    
    
    String prevAddress1ID = "";
    String prevAddress2ID = "";
    String prevAddress3ID = "";
    
    String prevMob1 = "";
    String prevMob2 = "";
    String prevMob3 = "";
    
    @FXML private Label label;
    
    String usernameOfLoggedInPerson;
    String gender = "";
    
    public void setParameter(String s)
    {
        try
        {
            usernameOfLoggedInPerson = s;
            
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
            Statement stmt = conn.createStatement();

            String employeeID = "";
            
            ResultSet rs = stmt.executeQuery("select * from customer where email = '"+s+"';");
            
            if(rs.next())
            {
                firstName.setText(rs.getString(2));
                lastName.setText(rs.getString(3));
                cnic.setText(rs.getString(6));
                dob.setValue(Checks.stringToLocalDateConverter(rs.getString(5)));
                
                gender = rs.getString(4);
                
                if(gender.equals("Male"))
                    favLang.selectToggle(male);
                else
                    favLang.selectToggle(female);
            }
            
            int counter = 1;
            
            rs = stmt.executeQuery("select distinct address, city.city_name, country.country_name, address.addressID from address, city, country, customer, customer_address where customer.customerID = customer_address.customerID and address.addressID = customer_address.addressID  and address.City_cityID = city.cityID and city.countryID = country.countryID and customer.email = '"+s+"';;");
            
            while(rs.next())
            {
                if(counter == 1)
                {
                    address1_address.setText(rs.getString(1));
                    address1_city.setText(rs.getString(2));
                    address1_country.setText(rs.getString(3));
                    
                    prevAddress1ID = rs.getString(4);
                }
                else if(counter == 2)
                {
                    address2_address.setVisible(true);
                    address2_city.setVisible(true);
                    address2_country.setVisible(true);
                    
                    address2_address.setText(rs.getString(1));
                    address2_city.setText(rs.getString(2));
                    address2_country.setText(rs.getString(3));
                    
                    prevAddress2ID = rs.getString(4);
                }
                else
                {
                    address3_address.setVisible(true);
                    address3_city.setVisible(true);
                    address3_country.setVisible(true);
                    
                    address3_address.setText(rs.getString(1));
                    address3_city.setText(rs.getString(2));
                    address3_country.setText(rs.getString(3));
                    
                    prevAddress3ID = rs.getString(4);
                }
                
                counter++;
            }
            
            counter = 1;
            
            rs = stmt.executeQuery("select phone_number from customer natural join customer_phone where email = '"+s+"';");
            
            while(rs.next())
            {
                if(counter == 1)
                {
                    mob1.setText(rs.getString(1));
                    
                    prevMob1 = mob1.getText();
                }
                else if(counter == 2)
                {
                    mob2.setVisible(true);
                    mob2.setText(rs.getString(1));
                    
                    prevMob2 = mob2.getText();
                }
                else
                {
                    mob3.setVisible(true);
                    mob3.setText(rs.getString(1));
                    
                    prevMob3 = mob3.getText();
                }
                
                counter++;
            }
        }
        catch(Exception e)
        { 
            System.out.println(e.toString());
        }
    }
    
    public boolean cnicCheck(String s) throws SQLException
    {
        boolean check = false;
        
        Connection conn;
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
        Statement stmt = conn.createStatement();

        String cnic = "";

        ResultSet rs = stmt.executeQuery("select CNIC from customer where email = '"+usernameOfLoggedInPerson+"';");
        if(rs.next())
            cnic = rs.getString(1);
        
        if(cnic.equals(s))
            check = true;
        return check;
    }
    
    public void updateData(ActionEvent event) throws IOException, SQLException
    {
        Connection conn;
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
        Statement stmt = conn.createStatement();
        
        if(Checks.alphaCheckIgnoreSpace(firstName.getText()) && Checks.alphaCheckIgnoreSpace(lastName.getText()))
        {
            if(Checks.cnicCheck(cnic.getText()))
            {
                if(Checks.cnicCheckData(cnic.getText()) || cnicCheck(cnic.getText()))
                {
                    String date = dob.getValue().toString();
                    String dateData[] = date.split("-");
                    
                    System.out.println("Year : "+ dateData[0]);
                    System.out.println("MOnth : "+ dateData[1]);
                    System.out.println("Day : "+ dateData[2]);
                    
                    if(Checks.ageCheck(Integer.parseInt(dateData[0]), Integer.parseInt(dateData[1]), Integer.parseInt(dateData[2])))
                    {
                        //System.out.println(favLang.getSelectedToggle().toString());
                        if(favLang.getSelectedToggle().equals(male))
                            gender = "Male";
                        else
                            gender = "Female";
                        
                        System.out.println(gender);
                        
                        String sql = "UPDATE `mydb`.`customer` SET `first_name` = '"+firstName.getText()+"', `last_name` = '"+lastName.getText()+"', `gender` = '"+gender+"', `DOB` = '"+date+"', `CNIC` = '"+cnic.getText()+"' WHERE (`email` = '"+usernameOfLoggedInPerson+"');";
                        
                        PreparedStatement preparedStatement = conn.prepareStatement(sql); //This Will Prevent From SQL Injection

                        int rowsAffected = preparedStatement.executeUpdate();
                        
                        if(address1_address.getText().equals("") || address1_city.getText().equals("") || address1_country.getText().equals(""))
                            label.setText("You Cannot Leave any Address Field Empty!");
                        else
                            addressUpdate();
                        
                        if(mob1.getText().equals(""))
                            label.setText("You Cannot Leave Mobile Field Empty!");
                        else
                            mobileUpdate();
                        
                        saveAlert(event);
                        //MObile update cpmes here
                    }
                    else
                        label.setText("Your Age shoudn't be Under 18 !");
                }
                else
                    label.setText("Your Entered CNIC is already registered!");
            }
            else
                label.setText("Please Enter CNIC in Format like '12345-1234567-1' !");
        }
        else
            label.setText("First Name should only consist of Alphabets !");
    }
    
    public void addressUpdate() throws SQLException 
    {
        System.out.println("=====================================================================================");
        System.out.println("===========================  ADDRESSS 1 =============================================");
        System.out.println("=====================================================================================");
        
        Connection conn;
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
        Statement stmt = conn.createStatement();
        
        String customerID = "";
        
        ResultSet rs = stmt.executeQuery("Select customerID from customer where email = '"+usernameOfLoggedInPerson+"';");
        if(rs.next())
            customerID = rs.getString(1);
        
        
        String countryID = "";
        
        rs = stmt.executeQuery("Select countryID from country where country_name = '"+address1_country.getText()+"';");
        if(rs.next())
            countryID = rs.getString(1);
        else
        {
            String sql = "INSERT INTO `mydb`.`country` (`country_name`) VALUES ('"+address1_country.getText()+"');";
            
            PreparedStatement preparedStatement = conn.prepareStatement(sql); //This Will Prevent From SQL Injection

            int rowsAffected = preparedStatement.executeUpdate();
            
            rs = stmt.executeQuery("Select countryID from country where country_name = '"+address1_country.getText()+"';");
            
            if(rs.next())
                countryID = rs.getString(1);
        }
        
        System.out.println("CountryID : " + countryID);
        
        String cityID = "";
        
        rs = stmt.executeQuery("Select cityID from city where city_name = '"+address1_city.getText()+"' and countryID = '"+countryID+"';");
        if(rs.next())
            cityID = rs.getString(1);
        else
        {
            String sql = "INSERT INTO `mydb`.`city` (`city_name`, `countryID`) VALUES ('"+address1_city.getText()+"', '"+countryID+"');";
            
            PreparedStatement preparedStatement = conn.prepareStatement(sql); //This Will Prevent From SQL Injection

            int rowsAffected = preparedStatement.executeUpdate();
            
            rs = stmt.executeQuery("Select cityID from city where city_name = '"+address1_city.getText()+"' and countryID = '"+countryID+"';");
            
            if(rs.next())
                cityID = rs.getString(1);
        }
        
        System.out.println("City ID : " + cityID);
        
        String addressID = "";
        
        rs = stmt.executeQuery("Select addressID from address where address = '"+address1_address.getText()+"' and City_cityID = '"+cityID+"';");
        if(rs.next())
            addressID = rs.getString(1);
        else
        {
            String sql = "INSERT INTO `mydb`.`address` (`address`, `City_cityID`) VALUES ('"+address1_address.getText()+"', '"+cityID+"');";
            
            PreparedStatement preparedStatement = conn.prepareStatement(sql); //This Will Prevent From SQL Injection

            int rowsAffected = preparedStatement.executeUpdate();
            
            rs = stmt.executeQuery("Select addressID from address where address = '"+address1_address.getText()+"' and City_cityID = '"+cityID+"';");
            
            if(rs.next())
                addressID = rs.getString(1);
        }
        
        System.out.println("CustomerID : " +customerID);
        System.out.println("AddressID : " +addressID);
        System.out.println("Previous Address ID : " + prevAddress1ID);
        String sql = "UPDATE `mydb`.`customer_address` SET `addressID` = '"+addressID+"' WHERE (`addressID` = '"+prevAddress1ID+"') and (`customerID` = '"+customerID+"');";
        
        PreparedStatement preparedStatement = conn.prepareStatement(sql); //This Will Prevent From SQL Injection

        int rowsAffected = preparedStatement.executeUpdate();
        
        System.out.println("Affected Rows : " + rowsAffected);
        
        System.out.println("=====================================================================================");
        System.out.println("===========================  ADDRESSS 2 =============================================");
        System.out.println("=====================================================================================");
        
        if(address2_address.isVisible())
        {
            if(address1_address.getText().equals("") || address1_city.getText().equals("") || address1_country.getText().equals(""))
                label.setText("You Cannot Leave Previous Address Field Empty !");
            else
            {
                countryID = "";

                rs = stmt.executeQuery("Select countryID from country where country_name = '"+address2_country.getText()+"';");
                if(rs.next())
                    countryID = rs.getString(1);
                else
                {
                    sql = "INSERT INTO `mydb`.`country` (`country_name`) VALUES ('"+address2_country.getText()+"');";

                    preparedStatement = conn.prepareStatement(sql); //This Will Prevent From SQL Injection

                    rowsAffected = preparedStatement.executeUpdate();

                    rs = stmt.executeQuery("Select countryID from country where country_name = '"+address2_country.getText()+"';");

                    if(rs.next())
                        countryID = rs.getString(1);
                }

                System.out.println("Country ID : " + countryID);
                
                cityID = "";

                rs = stmt.executeQuery("Select cityID from city where city_name = '"+address2_city.getText()+"' and countryID = '"+countryID+"';");
                if(rs.next())
                    cityID = rs.getString(1);
                else
                {
                    sql = "INSERT INTO `mydb`.`city` (`city_name`, `countryID`) VALUES ('"+address2_city.getText()+"', '"+countryID+"');";

                    preparedStatement = conn.prepareStatement(sql); //This Will Prevent From SQL Injection

                    rowsAffected = preparedStatement.executeUpdate();

                    rs = stmt.executeQuery("Select cityID from city where city_name = '"+address2_city.getText()+"' and countryID = '"+countryID+"';");

                    if(rs.next())
                        cityID = rs.getString(1);
                }
                
                System.out.println("City ID : " + cityID);

                addressID = "";

                rs = stmt.executeQuery("Select addressID from address where address = '"+address2_address.getText()+"' and City_cityID = '"+cityID+"';");
                if(rs.next())
                    addressID = rs.getString(1);
                else
                {
                    sql = "INSERT INTO `mydb`.`address` (`address`, `City_cityID`) VALUES ('"+address2_address.getText()+"', '"+cityID+"');";

                    preparedStatement = conn.prepareStatement(sql); //This Will Prevent From SQL Injection

                    rowsAffected = preparedStatement.executeUpdate();

                    rs = stmt.executeQuery("Select addressID from address where address = '"+address2_address.getText()+"' and City_cityID = '"+cityID+"';");

                    if(rs.next())
                        addressID = rs.getString(1);
                }

                System.out.println("CustomerID : " +customerID);
                System.out.println("Address ID : " + addressID);
                System.out.println("Previous AddressID : " + prevAddress2ID);
                
                if(prevAddress2ID.equals(""))
                {
                    sql = "INSERT INTO `mydb`.`customer_address` (`addressID`, `customerID`) VALUES ('"+addressID+"', '"+customerID+"');";
                    
                    preparedStatement = conn.prepareStatement(sql); //This Will Prevent From SQL Injection

                    rowsAffected = preparedStatement.executeUpdate();
                    System.out.println("Affected Rows : " + rowsAffected);
                }
                else
                {
                    sql = "UPDATE `mydb`.`customer_address` SET `addressID` = '"+addressID+"' WHERE (`addressID` = '"+prevAddress2ID+"') and (`customerID` = '"+customerID+"');";

                    preparedStatement = conn.prepareStatement(sql); //This Will Prevent From SQL Injection

                    rowsAffected = preparedStatement.executeUpdate();
                    System.out.println("Affected Rows : " + rowsAffected);
                }
            }
        }
        
        System.out.println("=====================================================================================");
        System.out.println("==============================  ADDRESSS 3 ==========================================");
        System.out.println("=====================================================================================");
        
        if(address3_address.isVisible() || !address3_address.getText().equals(""))
        {
            System.out.println("Inside of address 3");
            
            if(address2_address.getText().equals("") || address2_city.getText().equals("") || address2_country.getText().equals(""))
                label.setText("You Cannot Leave Previous Address Field Empty !");
            else
            {
                System.out.println("Purely Inside of Address 3");
                
                countryID = "";

                rs = stmt.executeQuery("Select countryID from country where country_name = '"+address3_country.getText()+"';");
                if(rs.next())
                    countryID = rs.getString(1);
                
                else
                {
                    sql = "INSERT INTO `mydb`.`country` (`country_name`) VALUES ('"+address3_country.getText()+"');";

                    preparedStatement = conn.prepareStatement(sql); //This Will Prevent From SQL Injection

                    rowsAffected = preparedStatement.executeUpdate();

                    rs = stmt.executeQuery("Select countryID from country where country_name = '"+address3_country.getText()+"';");

                    if(rs.next())
                        countryID = rs.getString(1);
                }

                System.out.println("Country ID  : " + countryID);
                
                cityID = "";

                rs = stmt.executeQuery("Select cityID from city where city_name = '"+address3_city.getText()+"' and countryID = '"+countryID+"';");
                if(rs.next())
                    cityID = rs.getString(1);
                else
                {
                    sql = "INSERT INTO `mydb`.`city` (`city_name`, `countryID`) VALUES ('"+address3_city.getText()+"', '"+countryID+"');";

                    preparedStatement = conn.prepareStatement(sql); //This Will Prevent From SQL Injection

                    rowsAffected = preparedStatement.executeUpdate();

                    rs = stmt.executeQuery("Select cityID from city where city_name = '"+address3_city.getText()+"' and countryID = '"+countryID+"';");

                    if(rs.next())
                        cityID = rs.getString(1);
                }

                System.out.println("City ID : " + cityID);
                
                addressID = "";

                rs = stmt.executeQuery("Select addressID from address where address = '"+address3_address.getText()+"' and City_cityID = '"+cityID+"';");
                if(rs.next())
                    addressID = rs.getString(1);
                else
                {
                    sql = "INSERT INTO `mydb`.`address` (`address`, `City_cityID`) VALUES ('"+address3_address.getText()+"', '"+cityID+"');";

                    preparedStatement = conn.prepareStatement(sql); //This Will Prevent From SQL Injection

                    rowsAffected = preparedStatement.executeUpdate();

                    System.out.println("Affected Rows : " + rowsAffected);
                    rs = stmt.executeQuery("Select addressID from address where address = '"+address3_address.getText()+"' and City_cityID = '"+cityID+"';");

                    if(rs.next())
                        addressID = rs.getString(1);
                }

                System.out.println("Address ID : " + addressID);
                System.out.println("CustomerID : " +customerID);
                System.out.println("Previous Address ID : " + prevAddress3ID);
                
                if(prevAddress3ID.equals(""))
                {
                    sql = "INSERT INTO `mydb`.`customer_address` (`addressID`, `customerID`) VALUES ('"+addressID+"', '"+customerID+"');";
                    
                    preparedStatement = conn.prepareStatement(sql); //This Will Prevent From SQL Injection

                    rowsAffected = preparedStatement.executeUpdate();
                    System.out.println("Affected Rows : " + rowsAffected);
                }
                else
                {
                    sql = "UPDATE `mydb`.`customer_address` SET `addressID` = '"+addressID+"' WHERE (`addressID` = '"+prevAddress3ID+"') and (`customerID` = '"+customerID+"');";

                    preparedStatement = conn.prepareStatement(sql); //This Will Prevent From SQL Injection

                    rowsAffected = preparedStatement.executeUpdate();
                    System.out.println("Affected Rows : " + rowsAffected);
                }
            }
        }
    }
    
    public void mobileUpdate() throws SQLException
    {
        Connection conn;
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
        Statement stmt = conn.createStatement();
        
        String customerID = "";
        
        ResultSet rs = stmt.executeQuery("Select customerID from customer where email = '"+usernameOfLoggedInPerson+"';");
        if(rs.next())
            customerID = rs.getString(1);
        
        String sql = "UPDATE `mydb`.`customer_phone` SET `phone_number` = '"+mob1.getText()+"' WHERE (`phone_number` = '"+prevMob1+"') and (`customerID` = '"+customerID+"');";

        PreparedStatement preparedStatement = conn.prepareStatement(sql); //This Will Prevent From SQL Injection

        int rowsAffected = preparedStatement.executeUpdate();

        if(mob2.isVisible())
        {
            if(prevMob2.equals(""))
            {
                sql = "INSERT INTO `mydb`.`customer_phone` (`customerID`, `phone_number`) VALUES ('"+customerID+"', '"+mob2.getText()+"');";
                
                preparedStatement = conn.prepareStatement(sql); //This Will Prevent From SQL Injection

                rowsAffected = preparedStatement.executeUpdate();
            }
            else
            {
                sql = "UPDATE `mydb`.`customer_phone` SET `phone_number` = '"+mob2.getText()+"' WHERE (`phone_number` = '"+prevMob2+"') and (`customerID` = '"+customerID+"');";

                preparedStatement = conn.prepareStatement(sql); //This Will Prevent From SQL Injection

                rowsAffected = preparedStatement.executeUpdate();
            }
        }
        
        if(mob3.isVisible())
        {
            if(prevMob3.equals(""))
            {
                sql = "INSERT INTO `mydb`.`customer_phone` (`customerID`, `phone_number`) VALUES ('"+customerID+"', '"+mob3.getText()+"');";
                
                preparedStatement = conn.prepareStatement(sql); //This Will Prevent From SQL Injection

                rowsAffected = preparedStatement.executeUpdate();
            }
            else
            {
                sql = "UPDATE `mydb`.`customer_phone` SET `phone_number` = '"+mob3.getText()+"' WHERE (`phone_number` = '"+prevMob3+"') and (`customerID` = '"+customerID+"');";

                preparedStatement = conn.prepareStatement(sql); //This Will Prevent From SQL Injection

                rowsAffected = preparedStatement.executeUpdate();
            }
        }
    }
    
    public void addMobileNumber()
    {
        if(!mob2.isVisible() && !mob1.getText().equals(""))
            mob2.setVisible(true);
        else if(!mob3.isVisible() && !mob2.getText().equals(""))
            mob3.setVisible(true);
        else
        {
            if(mob2.isVisible())
                label.setText("Already Visible");
            else if(mob1.getText().equals(""))
                label.setText("You Cannot Leave Mobile Number Field Empty");

            if(mob3.isVisible())
                label.setText("Already Visible");
            else if(mob2.getText().equals(""))
                label.setText("You Cannot Left Mobile Number Field Empty");
            
            if(mob2.isVisible() && mob3.isVisible())
                label.setText("You Can Only Add Three numbers");
        }
    }
    
    public void addAddress()
    {
        if(address3_address.isVisible())
            label.setText("You Cannot Add more than 3 Addresses!");
        
        if(address2_address.isVisible())
        {
            if(address2_address.getText().equals("") || address2_city.getText().equals("") || address2_country.getText().equals(""))
            {
                label.setText("You Cannot Leave Any previous Address Filed Empty ! ");
            }
            else
            {
                address3_address.setVisible(true);
                address3_city.setVisible(true);
                address3_country.setVisible(true);
            }
        }
        else
        {
            if(address1_address.getText().equals("") || address1_city.getText().equals("") || address1_country.getText().equals(""))
            {
                label.setText("You Cannot Leave Any previous Address Filed Empty ! ");
            }
            else
            {
                address2_address.setVisible(true);
                address2_city.setVisible(true);
                address2_country.setVisible(true);
            }
        }
    }
    
    public void saveAlert(ActionEvent event) throws IOException //this is INFORMATION
    {
        Alert a1 = new Alert(Alert.AlertType.INFORMATION);  /*Alert types *
                                                             1) INFORMATION
                                                             2) WARNING
                                                             3) ERROR
                                                             4) CONFIRMATION*/
        a1.setTitle("Save Changes");
        a1.setContentText("Your Changes have been Saved ! ");
        //a1.setHeaderText("Basic ALERT 2 "); //you can also set header
        
        a1.showAndWait();
        
        backButtonPushed(event);
    }
    
    
    public void backButtonPushed(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Account.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
//        
        AccountController controller = loader.getController();
        controller.setParameter(usernameOfLoggedInPerson);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
        
        //System.out.println("Username in back button of Account : " + username.getText());
        
    } 
    

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        logo = new ImageView("logo1.png");
        logo2 = new ImageView("account.png");
        
        favLang = new ToggleGroup();
        male.setToggleGroup(favLang);
        female.setToggleGroup(favLang);
        
        address2_address.setVisible(false);
        address2_city.setVisible(false);
        address2_country.setVisible(false);
        
        address3_address.setVisible(false);
        address3_city.setVisible(false);
        address3_country.setVisible(false);
        
        mob2.setVisible(false);
        mob3.setVisible(false);
        // TODO
    }    
}
