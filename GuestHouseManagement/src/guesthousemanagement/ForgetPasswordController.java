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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import verifications.Checks;

public class ForgetPasswordController implements Initializable 
{
    @FXML private ImageView logo;
    @FXML private ImageView logo2;
    
    public String backPageParameter; //this attribute will store store the parameteric value for moving back to previous page and also to verify from which (customer/employee/admin) the forget password request is made
    
    @FXML private TextField cnic;
    @FXML private TextField mobNo;
    @FXML private Label label;
    
    public void setParameter(String s)
    {
        backPageParameter = s;
    }
    
    //this function is for next button in forget password controll
    public void nextButtonPushed(ActionEvent event) throws IOException, SQLException
    {
        if(!(cnic.getText().equals("") || mobNo.getText().equals("")) && Checks.cnicCheck(cnic.getText()) && Checks.mobCheck(mobNo.getText()))
        {
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
            Statement stmt = conn.createStatement();
            
            if(backPageParameter.equals("CustomerLogin.fxml"))
            {
                if(!Checks.cnicCheckData(cnic.getText()))
                {
                    if(!Checks.mobCheckData(mobNo.getText()))
                    {
                        ResultSet rs =  stmt.executeQuery("Select customer.customerID from customer, customer_phone where customer.customerID = customer_phone.customerID and customer.CNIC = '"+ cnic.getText() +"' and customer_phone.phone_number = '" + mobNo.getText() +"';");

                        if(rs.next())
                        {
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("ForgetPassword2.fxml"));
                            Parent tableViewParent = loader.load();

                            Scene tableViewScene = new Scene(tableViewParent);

                            String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
                            tableViewScene.getStylesheets().add(css);

                            ForgetPassword2Controller controller = loader.getController();
                            controller.setParameter("ForgetPassword", backPageParameter, cnic.getText(), mobNo.getText());

                            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

                            window.setScene(tableViewScene);
                            window.show();
                        }
                        else
                            label.setText("Your Entere CNIC and Mobile Number Donot Belong to Same Person");
                    }
                    else
                        label.setText("Your Entered Mobile Number is NOT Registered !");
                }
                else
                    label.setText("Your Entered CNIC is NOT Registered !");
            }
            else if(backPageParameter.equals("ReceptionistLogin.fxml"))
            {
                ResultSet rs =  stmt.executeQuery("Select receptionist.empID from receptionist, employee_phone, employee where employee.empID = receptionist.empID and receptionist.empID = employee_phone.empID and employee.CNIC = '"+ cnic.getText() +"' and employee_phone.phone_number = '" + mobNo.getText() +"';");

                if(rs.next())
                {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("ForgetPassword2.fxml"));
                    Parent tableViewParent = loader.load();

                    Scene tableViewScene = new Scene(tableViewParent);

                    String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
                    tableViewScene.getStylesheets().add(css);

                    ForgetPassword2Controller controller = loader.getController();
                    controller.setParameter("ForgetPassword", backPageParameter, cnic.getText(), mobNo.getText());

                    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

                    window.setScene(tableViewScene);
                    window.show();
                }
                else
                    label.setText("Your Enter CNIC and Mobile Number Donot Belong to Receptionist");
            }
            else if(backPageParameter.equals("AdminLogin.fxml"))
            {
                ResultSet rs =  stmt.executeQuery("Select admin.empID from admin, employee_phone, employee where employee.empID = admin.empID and admin.empID = employee_phone.empID and employee.CNIC = '"+ cnic.getText() +"' and employee_phone.phone_number = '" + mobNo.getText() +"';");

                if(rs.next())
                {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("ForgetPassword2.fxml"));
                    Parent tableViewParent = loader.load();

                    Scene tableViewScene = new Scene(tableViewParent);

                    String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
                    tableViewScene.getStylesheets().add(css);

                    ForgetPassword2Controller controller = loader.getController();
                    controller.setParameter("ForgetPassword", backPageParameter, cnic.getText(), mobNo.getText());

                    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

                    window.setScene(tableViewScene);
                    window.show();
                }
                else
                    label.setText("Your Enter CNIC and Mobile Number Donot Belong to Receptionist");
            }
            
            
        }
        else
        {
            if(!Checks.mobCheck(mobNo.getText()))
            {
                label.setText("Please! Enter Mob Number in Format Like '03035405615' !"); //there is possiility that either the customer from any of the country so there number pattern could be different
                mobNo.setText("");
            }
            if(!Checks.cnicCheck(cnic.getText()))
            {
                label.setText("Please! Enter CNIC in Format Like '12345-1234567-1' !");
                cnic.setText("");
            } 
            if(cnic.getText().equals("") || mobNo.getText().equals(""))
                label.setText("You Cannot Leave Any of the Field Empty!");
        }
    }
    
    //This Method is for moving back to previous page/Screen from which this scene/screen is called
    public void backButtonPushed(ActionEvent event) throws IOException
    {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource(backPageParameter));
        Scene tableViewScene = new Scene(tableViewParent);
        
        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
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
        backPageParameter = "";
        
        logo = new ImageView("logo1.png");
        logo2 = new ImageView("customerLogo.png");
    }    
    
}
