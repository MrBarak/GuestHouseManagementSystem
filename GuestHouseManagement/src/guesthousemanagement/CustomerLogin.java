/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guesthousemanagement;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


import java.sql.*;
import java.sql.DriverManager;
import java.util.Scanner;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class CustomerLogin implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML private ImageView msgIcon;
    @FXML private ImageView lockIcon;
    @FXML private ImageView close;
    @FXML private ImageView logo; // Main logo
    @FXML private ImageView logo2; // for customer logo
    
    
    @FXML private Label label;
    @FXML private TextField username;
    @FXML private PasswordField password;
    
    
    
    @FXML
    public void close_app(MouseEvent event)
    {
        System.exit(0);
    }
    
    @FXML private CheckBox rememberMe;
    
    //function for CHECK BOX
    public void checkBox(String uName, String pass) throws FileNotFoundException
    {
        PrintWriter pw = new PrintWriter(new FileOutputStream("customer.txt"));
        if(rememberMe.isSelected())
        {
            pw.println(uName + " " + pass);
            System.out.println("WRITTEN \n");
        }
        else
        {
            pw.println();
            System.out.println("\nNOT Written\n");
        }
        pw.close();
    }
    
    public void getCreditials() throws FileNotFoundException
    {
        Scanner reader = new Scanner(new FileInputStream("customer.txt"));
        
        if(reader.hasNextLine())
        {
            String credentials[] = reader.nextLine().split(" ");
            
            username.setText(credentials[0]);
            password.setText(credentials[1]);
            
            rememberMe.setSelected(true);
        }

        reader.close();
    }
    
    //This Method is for moving back to previous page/Screen from which this scene/screen is called
    public void backButtonPushed(ActionEvent event) throws IOException
    {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        
        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableViewScene);
        window.show();
    } 
    
    //This Method is for moving to page/Screen for reseting password
    public void forgetPasswordButtonPushed(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ForgetPassword.fxml"));
        Parent tableViewParent = loader.load();
        
        Scene tableViewScene = new Scene(tableViewParent);
        
        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        ForgetPasswordController controller = loader.getController();
        controller.setParameter("CustomerLogin.fxml");
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableViewScene);
        window.show();
    }
    
    //This Method is for moving to registration page/Screen 
    public void registerButtonPushed(ActionEvent event) throws IOException
    {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("RegisterCustomer.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        
        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableViewScene);
        window.show();
    }  
    
    //This Method is for moving to Customer Profile
    public void loginButtonPushed(ActionEvent event) throws IOException
    {
        if(!(username.getText().equals("") || password.getText().equals("")) && loginAuthentication())
        {
            String tempUsername = username.getText();
            String tempPassword = password.getText();
            
            checkBox(tempUsername, tempPassword);
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("CustomerProfile.fxml"));
            Parent tableViewParent = loader.load();

            Scene tableViewScene = new Scene(tableViewParent);
            
            String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
            tableViewScene.getStylesheets().add(css);

            CustomerProfileController controller = loader.getController();
            controller.setParameter(username.getText());

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(tableViewScene);
            window.show();
        }
        else
        {
            if(username.getText().equals("") || password.getText().equals(""))
                label.setText("You Cannot Leave Any of the Field Empty!");
            if(!loginAuthentication())
                label.setText("You Are NOT Registered in Our DATABASE !");
        }
    }
    
    
    public boolean loginAuthentication() //this method return true when login successfull else return false
    {
        boolean check = false;
        
        try
        {
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
            Statement stmt = conn.createStatement();
            
            ResultSet rs = stmt.executeQuery("SELECT Customer.email, Customer.password from Customer where Customer.email = '" + username.getText() + "' and " + " Customer.password = '" + password.getText() + "';");
            boolean temp = rs.next();
            //System.out.println("EMal : " + rs.getString(1) + "\t\t" + "Password : " + rs.getString(2));
            if(temp && rs.getString(1).equals(username.getText()) && rs.getString(2).equals(password.getText()))
            {
                check = true;
                System.out.println("EMal : " + rs.getString(1) + "\t\t" + "Password : " + rs.getString(2));
            }
        }
        catch(Exception e)
        { 
            System.out.println(e.toString());
        }
        
        return check;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        msgIcon = new ImageView("id.png");
        lockIcon = new ImageView("lock.png");
        close = new ImageView("close.png");
        logo = new ImageView("logo1.png");
        logo2 = new ImageView("customerLogo.png");
        
        try
        {
            getCreditials();
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    } 
}
