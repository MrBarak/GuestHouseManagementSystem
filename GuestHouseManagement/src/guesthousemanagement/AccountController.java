package guesthousemanagement;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import verifications.Checks;

public class AccountController implements Initializable 
{
    @FXML private ImageView logo;
    @FXML private ImageView logo2;
    
    @FXML private Label name;
    @FXML private Label gender;
    @FXML private Label dob;
    @FXML private Label cnic;
    @FXML private Label age;
    @FXML private Label username;
    @FXML private Label joiningDate;
    @FXML private Label mob1;
    @FXML private Label mob2;
    @FXML private Label mob3;
    @FXML private Label address1_1;
    @FXML private Label address1_2;
    @FXML private Label address2_1;
    @FXML private Label address2_2;
    @FXML private Label address3_1;
    @FXML private Label address3_2;
    
    String usernameOfLoggedInPerson;
    String customerID = "";
    
    public void setParameter(String s)
    {
        try
        {
            System.out.println("\nUser Name from Account : "+s);
            username.setText(s);
            
            usernameOfLoggedInPerson = s;
            
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
            Statement stmt = conn.createStatement();
            
            //ResultSet rs = stmt.executeQuery("select first_name, last_name, gender, DOB, CNIC, joining_date from customer where email = '" + s + "';");
            ResultSet rs = stmt.executeQuery("select first_name, last_name, gender, DOB, CNIC, joining_date, address, city_name, country_name, phone_number, customer.customerID from customer, address, customer_phone, customer_address, country, city where Customer.customerID = customer_phone.customerID and customer_address.customerID = customer.customerID and customer_address.addressID = address.addressID and address.City_cityID = city.cityID and city.countryID = country.countryID and email = '" + s + "';");
            boolean temp = rs.next();
            
            name.setText(rs.getString(1) + " " + rs.getString(2));
            gender.setText(rs.getString(3));
            dob.setText(rs.getString(4));
            System.out.println("\nDate Of Birth : "+dob.getText());
            cnic.setText(rs.getString(5));
            joiningDate.setText(rs.getString(6));
            
            customerID = rs.getString(11);
            
            //for Age we Do,
            String dataForAge[] = dob.getText().split("-");
            int ageInInt = Checks.ageReturner(Integer.parseInt(dataForAge[0]), Integer.parseInt(dataForAge[1]), Integer.parseInt(dataForAge[2]));
            age.setText(Integer.toString(ageInInt));
            
            rs = stmt.executeQuery("select distinct address, city_name, country_name from customer, address, customer_address, country, city where customer_address.customerID = customer.customerID and customer_address.addressID = address.addressID and address.City_cityID = city.cityID and city.countryID = country.countryID and customer.email = '"+ s +"';");
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
            
            rs = stmt.executeQuery("select distinct  phone_number from customer, customer_phone where Customer.customerID = customer_phone.customerID and customer.email = '"+s+"';");
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
        catch(Exception e)
        { 
            System.out.println(e.toString());
        }
    }
    
    public void changeInfoButtonPushed(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ChangingInfo.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        ChangingInfoController controller = loader.getController();
        controller.setParameter(usernameOfLoggedInPerson);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
        
        System.out.println("Username in back button of Account : " + username.getText());
    }
    
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
        
        System.out.println("Username in back button of Account : " + username.getText());
    } 
    
    public void resetPasswordButtonPushed(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ForgetPassword2.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        ForgetPassword2Controller controller = loader.getController();
        controller.setParameter("Account", usernameOfLoggedInPerson, cnic.getText(), mob1.getText());

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    
    public void deleteAccountButtonPushed(ActionEvent event) throws IOException, SQLException
    {
        Alert a1 = new Alert(Alert.AlertType.CONFIRMATION);  
        
        a1.setTitle("Deletion Confirmation");
        a1.setContentText("Are you sure you want to DELETE Your Account? ");
        a1.setHeaderText(null); //you can also set header
        
        ButtonType yes = new ButtonType("YES", ButtonBar.ButtonData.YES);
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        
        a1.getButtonTypes().setAll(yes, cancel);
        
        Optional<ButtonType> result = a1.showAndWait();
        
        if(result.get() == yes) //type can also be "OK" as well
        {
            System.out.println("OKAY");
            
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("select * from customer where customerID = '" + customerID + "';");
            rs.next();
            
            String sql = "INSERT INTO `mydb`.`customer_deleted` (`customerID`, `first_name`, `last_name`, `gender`, `DOB`, `CNIC`, `email`, `joining_date`, `leaving_date`) VALUES ('"+rs.getString(1)+"', '"+rs.getString(2)+"', '"+rs.getString(3)+"', '"+rs.getString(4)+"', '"+rs.getString(5)+"', '"+rs.getString(6)+"', '"+rs.getString(7)+"', '"+rs.getString(9)+"', '"+Checks.getCurrentdate()+"');";
            PreparedStatement preparedStatement = conn.prepareStatement(sql); //This Will Prevent From SQL InjectionS
            int rowsAffected = preparedStatement.executeUpdate();
            
            rs = stmt.executeQuery("select complaintID from complaint where customerID = '" + customerID + "';");
            while(rs.next())
            {
                 sql = "DELETE FROM `mydb`.`response` WHERE (`comlpaintID` = '"+rs.getString(1)+"');";
                 preparedStatement = conn.prepareStatement(sql); //This Will Prevent From SQL InjectionS
                 rowsAffected = preparedStatement.executeUpdate();
            }
            
            sql = "DELETE FROM `mydb`.`complaint` WHERE (`customerID` = '"+customerID+"');";
            preparedStatement = conn.prepareStatement(sql); //This Will Prevent From SQL InjectionS
            rowsAffected = preparedStatement.executeUpdate();
            
            rs = stmt.executeQuery("select billsID from bills where customerID = '" + customerID + "';");
            while(rs.next())
            {
                 sql = "DELETE FROM `mydb`.`service_record` WHERE (`billsID` = '"+rs.getString(1)+"');";
                 preparedStatement = conn.prepareStatement(sql); //This Will Prevent From SQL InjectionS
                 rowsAffected = preparedStatement.executeUpdate();
            }
            
            sql = "DELETE FROM `mydb`.`bills` WHERE (`customerID` = '"+customerID+"');";
            preparedStatement = conn.prepareStatement(sql); //This Will Prevent From SQL InjectionS
            rowsAffected = preparedStatement.executeUpdate();
            
            sql = "DELETE FROM `mydb`.`customer` WHERE (`customerID` = '"+customerID+"');";
            preparedStatement = conn.prepareStatement(sql); //This Will Prevent From SQL InjectionS
            rowsAffected = preparedStatement.executeUpdate();
            
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("CustomerLogin.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);

            String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
            tableViewScene.getStylesheets().add(css);
            
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(tableViewScene);
            window.show();
            
            Alert a2 = new Alert(Alert.AlertType.INFORMATION);

            a2.setTitle("Successfully Deleted ");
            a2.setContentText("Your Account has been Deleted successfully, We Will Miss You !");
            a2.setHeaderText(null); //you can also set header
            a2.showAndWait();
        }
        else
        {
            System.out.println("Cancel");
        }
    }
    
    public void logoutButtonPushed(ActionEvent event) throws IOException //this is INFORMATION
    {
        Alert a1 = new Alert(Alert.AlertType.CONFIRMATION);  /*Alert types *
                                                             1) INFORMATION
                                                             2) WARNING
                                                             3) ERROR
                                                             4) CONFIRMATION*/
        a1.setTitle("Logout Confirmation");
        a1.setContentText("Are you sure you want to LOGOUT? ");
        a1.setHeaderText(null); //you can also set header
        
        ButtonType yes = new ButtonType("YES", ButtonBar.ButtonData.YES);
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        
        a1.getButtonTypes().setAll(yes, cancel);
        
        Optional<ButtonType> result = a1.showAndWait();
        
        if(result.get() == yes) //type can also be "OK" as well
        {
            System.out.println("OKAY");
            
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("CustomerLogin.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);

            String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
            tableViewScene.getStylesheets().add(css);
            
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(tableViewScene);
            window.show();
        }
        else
        {
            System.out.println("Cancel");
        }
    }
    

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        logo = new ImageView("logo1.png");
        logo2 = new ImageView("account.png");
        // TODO
    }    
}
