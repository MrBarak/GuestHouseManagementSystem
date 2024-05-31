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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import verifications.Checks;

/**
 * FXML Controller class
 *
 * @author Hamza Barak
 */

public class RegisterCustomer3Controller implements Initializable 
{
    @FXML private ImageView logo;
    @FXML private ImageView logo2;
    
    @FXML private TextField username;
    @FXML private PasswordField password1;
    @FXML private PasswordField password2;
    @FXML private Label label;
    
    String reg1[] = new String[7];
    String reg2[] = new String[6];
    
    public void setParameter(String[] reg1, String[] reg2)
    {
        this.reg1 = reg1;
        this.reg2 = reg2;
        
        
    }
     
    //This Method is for moving back to previous page/Screen (RegisterCustomer2 Screen) from which this scene/screen is called
    public void backButtonPushed(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("RegisterCustomer2.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
//        
        RegisterCustomer2Controller controller = loader.getController();
        controller.setParameter(reg1, reg2);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }  
    
    public void getRegisterButtonPressed(ActionEvent event) throws IOException
    {
        if(!(username.getText().equals("") || password1.getText().equals("") || password2.getText().equals("")) && (password1.getText().equals(password2.getText())))
        {
            try
            {
                if(Checks.emailCheck(username.getText()))
                {
                    if(Checks.emailCheckData(username.getText()))
                    {
                        Connection conn;
                        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
                        Statement stmt = conn.createStatement();
//
//                        int up = stmt.executeUpdate();
//                        
                        String sql = "INSERT INTO `mydb`.`customer` (`first_name`, `last_name`, `gender`, `DOB`, `CNIC`, `email`, `password`, `joining_date`) VALUES ('"+reg1[0]+"', '"+reg1[1]+"', '"+reg1[4]+"', '"+reg1[3]+"', '"+reg1[2]+"', '"+username.getText()+"', '"+password1.getText()+"', '"+Checks.getCurrentdate()+"');";
                        
                        PreparedStatement preparedStatement = conn.prepareStatement(sql); //This Will Prevent From SQL Injection

                        int rowsAffected = preparedStatement.executeUpdate();
                        
                        ResultSet rs = stmt.executeQuery("select customerID from customer where email = '" + username.getText() + "';");
                        rs.next();
                        String customerID = rs.getString(1);
                        
                        sql = "INSERT INTO `mydb`.`customer_phone` (`customerID`, `phone_number`) VALUES ('"+customerID+"', '"+reg1[5]+"');";
                        preparedStatement = conn.prepareStatement(sql);
                        rowsAffected = preparedStatement.executeUpdate();
                        
                        if(!reg1[6].equals(""))
                        {
                            sql = "INSERT INTO `mydb`.`customer_phone` (`customerID`, `phone_number`) VALUES ('"+customerID+"', '"+reg1[6]+"');";
                            preparedStatement = conn.prepareStatement(sql);
                            rowsAffected = preparedStatement.executeUpdate();
                        }
                       
                        String countryID = "";
                        
                        rs = stmt.executeQuery("select countryID from country where country_name = '" + reg2[0] + "';");
                        if(rs.next())
                            countryID = rs.getString(1);
                        else
                        {
                            sql = "INSERT INTO `mydb`.`country` (`country_name`) VALUES ('"+reg2[0]+"');";
                            preparedStatement = conn.prepareStatement(sql);
                            rowsAffected = preparedStatement.executeUpdate();
                            
                            rs = stmt.executeQuery("select countryID from country where country_name = '" + reg2[0] + "';");
                            rs.next();
                            countryID = rs.getString(1);
                        }
                           
                        String cityID = "";
                        rs = stmt.executeQuery("select cityID from city where city_name = '" + reg2[1] + "' and countryID = '"+countryID+"';");
                        if(rs.next())
                            cityID = rs.getString(1);
                        else
                        {
                            sql = "INSERT INTO `mydb`.`city` (`city_name`, `countryID`) VALUES ('"+reg2[1]+"', '"+countryID+"');";
                            preparedStatement = conn.prepareStatement(sql);
                            rowsAffected = preparedStatement.executeUpdate();
                            
                            rs = stmt.executeQuery("select cityID from city where city_name = '" + reg2[1] + "' and countryID = '"+countryID+"';");
                            rs.next();
                            cityID = rs.getString(1);
                        }
                        
                        
                        String addressID = "";
                        rs = stmt.executeQuery("select addressID from address where City_cityID = '" + cityID + "' and address = '"+reg2[2]+"';");
                        
                        if(rs.next())
                            addressID = rs.getString(1);
                        else
                        {
                            sql = "INSERT INTO `mydb`.`address` (`address`, `City_cityID`) VALUES ('"+reg2[2]+"', '"+cityID+"');";
                            preparedStatement = conn.prepareStatement(sql);
                            rowsAffected = preparedStatement.executeUpdate();
                            
                            rs = stmt.executeQuery("select addressID from address where address = '" + reg2[2] + "' and City_cityID = '"+cityID+"';");
                            rs.next();
                            addressID = rs.getString(1);
                        }
                        
                        sql = "INSERT INTO `mydb`.`customer_address` (`addressID`, `customerID`) VALUES ('"+addressID+"', '"+ customerID+"');";
                        preparedStatement = conn.prepareStatement(sql);
                        rowsAffected = preparedStatement.executeUpdate();
                        
                        if(!reg2[3].equals("") && !reg2[4].equals("") && !reg2[5].equals(""))
                        {
                            countryID = "";
                        
                            rs = stmt.executeQuery("select countryID from country where country_name = '" + reg2[3] + "';");
                            if(rs.next())
                                countryID = rs.getString(1);
                            else
                            {
                                sql = "INSERT INTO `mydb`.`country` (`country_name`) VALUES ('"+reg2[3]+"');";
                                preparedStatement = conn.prepareStatement(sql);
                                rowsAffected = preparedStatement.executeUpdate();

                                rs = stmt.executeQuery("select countryID from country where country_name = '" + reg2[3] + "';");
                                rs.next();
                                countryID = rs.getString(1);
                            }

                            cityID = "";
                            rs = stmt.executeQuery("select cityID from city where city_name = '" + reg2[4] + "' and countryID = '"+countryID+"';");
                            if(rs.next())
                                cityID = rs.getString(1);
                            else
                            {
                                sql = "INSERT INTO `mydb`.`city` (`city_name`, `countryID`) VALUES ('"+reg2[4]+"', '"+countryID+"');";
                                preparedStatement = conn.prepareStatement(sql);
                                rowsAffected = preparedStatement.executeUpdate();

                                rs = stmt.executeQuery("select cityID from city where city_name = '" + reg2[4] + "' and countryID = '"+countryID+"';");
                                rs.next();
                                cityID = rs.getString(1);
                            }


                            addressID = "";
                            rs = stmt.executeQuery("select addressID from address where City_cityID = '" + cityID + "' and address = '"+reg2[5]+"';");

                            if(rs.next())
                                addressID = rs.getString(1);
                            else
                            {
                                sql = "INSERT INTO `mydb`.`address` (`address`, `City_cityID`) VALUES ('"+reg2[5]+"', '"+cityID+"');";
                                preparedStatement = conn.prepareStatement(sql);
                                rowsAffected = preparedStatement.executeUpdate();

                                rs = stmt.executeQuery("select addressID from address where address = '" + reg2[5] + "' and City_cityID = '"+cityID+"';");
                                rs.next();
                                addressID = rs.getString(1);
                            }
                            
                            sql = "INSERT INTO `mydb`.`customer_address` (`addressID`, `customerID`) VALUES ('"+addressID+"', '"+ customerID+"');";
                            preparedStatement = conn.prepareStatement(sql);
                            rowsAffected = preparedStatement.executeUpdate();
                        }
                        
                        Alert a1 = new Alert(Alert.AlertType.INFORMATION);

                        a1.setTitle("Successfully Register ");
                        a1.setContentText("Your Data has been registered successfully, Now you can login through your account !");
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
                    else
                        label.setText("This Username Already have registered, Please Entered Another one !");
                }
                else
                    label.setText("Please Enter Correct Email ! (like abcxyz@gmail.com)");
            }
            catch(SQLException e)
            {
                System.out.println(e.toString());
            }
        }
        else
        {
            if(username.getText().equals("") || password1.getText().equals("") || password2.getText().equals(""))
                label.setText("You Cannot Leave Any of the Field Empty!");
            else if(!(password1.getText().equals(password2.getText())))
                label.setText("Re-Entered Password Did'nt Match!");
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // TODO
        logo = new ImageView("logo1.png");
        logo2 = new ImageView("customerLogo.png");
    }    
    
}
