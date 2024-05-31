package guesthousemanagement;

import java.io.IOException;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import verifications.Checks;

/**
 * FXML Controller class
 *
 * @author Hamza Barak
 */
public class ForgetPassword2Controller implements Initializable
{
    @FXML private ImageView logo;
    @FXML private ImageView logo2;
    
    @FXML private PasswordField pass1;
    @FXML private PasswordField pass2;
    
    @FXML private Label label;
    
    public String backPageParameter; //this attribute will store store the parameteric value for moving back to previous page and also to verify from which (customer/employee/admin) the forget password request is made
    
    String cnic = "";
    String mob = "";
    String username = "";
    String backPageParameterFromAccounts = "";
    String thingToBeReturned = ""; 
   
    
    public void setParameter(String s, String backPageParameterFromAccounts, String cnic, String mob)
    {
        this.backPageParameter = s;
        this.backPageParameterFromAccounts = backPageParameterFromAccounts;
        this.cnic = cnic;
        this.mob = mob;
    }
    
    public void setParameter(String s, String cnic, String mob)
    {
        this.backPageParameter = s;
        this.cnic = cnic;
        this.mob = mob;
    }
    
    
    public void changePasswordButtonPushed(ActionEvent event) throws IOException, SQLException
    {
        if(!(pass1.getText().equals("") || pass2.getText().equals("")) && pass1.getText().equals(pass2.getText()))
        {
            
            
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
            Statement stmt = conn.createStatement();
            
            if(backPageParameter.equals("Account") || backPageParameterFromAccounts.equals("CustomerLogin.fxml"))
            {
                String sql = "UPDATE `mydb`.`customer` SET `password` = '"+pass1.getText()+"' WHERE (`CNIC` = '"+cnic+"');";

                PreparedStatement preparedStatement = conn.prepareStatement(sql); 
                int rowsAffected = preparedStatement.executeUpdate();

                Alert a1 = new Alert(Alert.AlertType.INFORMATION);

                a1.setTitle("Password Changed ");
                a1.setContentText("Your Password has been changed successfully, Now you can login through your new Password !");
                a1.setHeaderText(null); //you can also set header
                a1.showAndWait();

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("CustomerLogin.fxml"));
                Parent tableViewParent = loader.load();

                Scene tableViewScene = new Scene(tableViewParent);

                String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
                tableViewScene.getStylesheets().add(css);

                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

                window.setScene(tableViewScene);
                window.show();
            }
            else if(backPageParameter.equals("ReceptionistAccount") || backPageParameterFromAccounts.equals("ReceptionistLogin.fxml"))
            {
                ResultSet rs = stmt.executeQuery("select empID from employee where CNIC = '"+ cnic +"';");
                rs.next();
                String empID = rs.getString(1);
                
                String sql = "UPDATE `mydb`.`receptionist` SET `password` = '"+pass1.getText()+"' WHERE (`empID` = '"+empID+"');";

                PreparedStatement preparedStatement = conn.prepareStatement(sql); 
                int rowsAffected = preparedStatement.executeUpdate();

                Alert a1 = new Alert(Alert.AlertType.INFORMATION);

                a1.setTitle("Password Changed ");
                a1.setContentText("Your Password has been changed successfully, Now you can login through your new Password !");
                a1.setHeaderText(null); //you can also set header
                a1.showAndWait();

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("ReceptionistLogin.fxml"));
                Parent tableViewParent = loader.load();

                Scene tableViewScene = new Scene(tableViewParent);

                String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
                tableViewScene.getStylesheets().add(css);

                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

                window.setScene(tableViewScene);
                window.show();
            }
            else if(backPageParameter.equals("AdminAccount") || backPageParameterFromAccounts.equals("AdminLogin.fxml"))
            {
                ResultSet rs = stmt.executeQuery("select empID from employee where CNIC = '"+ cnic +"';");
                rs.next();
                String empID = rs.getString(1);
                
                String sql = "UPDATE `mydb`.`admin` SET `password` = '"+pass1.getText()+"' WHERE (`empID` = '"+empID+"');";

                PreparedStatement preparedStatement = conn.prepareStatement(sql); 
                int rowsAffected = preparedStatement.executeUpdate();

                Alert a1 = new Alert(Alert.AlertType.INFORMATION);

                a1.setTitle("Password Changed ");
                a1.setContentText("Your Password has been changed successfully, Now you can login through your new Password !");
                a1.setHeaderText(null); //you can also set header
                a1.showAndWait();

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("AdminLogin.fxml"));
                Parent tableViewParent = loader.load();

                Scene tableViewScene = new Scene(tableViewParent);

                String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
                tableViewScene.getStylesheets().add(css);

                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

                window.setScene(tableViewScene);
                window.show();
            }
        }
        else
        {
            if(!pass1.getText().equals(pass2.getText()))
            {
                label.setText("Password Did'nt Match Re Enter!");
                pass1.setText("");
                pass2.setText("");
            }
            else
                label.setText("You Cannot Leave Any of the Field Empty!");
        }
    }
    
    //This Method is for moving back to previous page/Screen from which this scene/screen is called
    public void backButtonPushed(ActionEvent event) throws IOException, SQLException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(backPageParameter+".fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        if(backPageParameter.equals("ForgetPassword"))
        {
            ForgetPasswordController controller = loader.getController();
            controller.setParameter(backPageParameterFromAccounts);
        }
        
        if(backPageParameter.equals("Account"))
        {
            AccountController controller2 = loader.getController();
            controller2.setParameter(backPageParameterFromAccounts);
        }
        
        if(backPageParameter.equals("ReceptionistAccount"))
        {
            ReceptionistAccountController controller2 = loader.getController();
            controller2.setParameter(backPageParameterFromAccounts);
        }
        
        if(backPageParameter.equals("AdminAccount"))
        {
            AdminAccountController controller2 = loader.getController();
            controller2.setParameter(backPageParameterFromAccounts);
        }
        
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
        logo2 = new ImageView("customerLogo.png");
    }    
    
}
