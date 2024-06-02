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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Hamza Barak
 */
public class AdminLoginController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML private Label label;
    
    @FXML private ImageView msgIcon;
    @FXML private ImageView lockIcon;
    @FXML private ImageView close;
    @FXML private ImageView logo;
    @FXML private ImageView logo2;
    
    @FXML private TextField username;
    @FXML private PasswordField password;
    
    String empID = "";
    
    @FXML
    public void close_app(MouseEvent event)
    {
        System.exit(0);
    }
    
    
    @FXML private CheckBox rememberMe;
    
    //function for CHECK BOX
    public void checkBox(String uName, String pass) throws FileNotFoundException
    {
        PrintWriter pw = new PrintWriter(new FileOutputStream("admin.txt"));
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
        Scanner reader = new Scanner(new FileInputStream("admin.txt"));
        
        if(reader.hasNextLine())
        {
            String credentials[] = reader.nextLine().split(" ");
            
            username.setText(credentials[0]);
            password.setText(credentials[1]);
            
            rememberMe.setSelected(true);
        }

        reader.close();
    }
    
    public void loginButtonPushed(ActionEvent event) throws IOException, SQLException
    {
        String tempU = username.getText();
        String tempP = password.getText();
        if(!tempU.equals("") && !tempP.equals(""))
        {
            if(tempU.subSequence(0, 5).equals("empID") || tempU.subSequence(0, 5).equals("empid"))
            {
                Connection conn;
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
                Statement stmt = conn.createStatement();

                ResultSet rs = stmt.executeQuery("select admin.empID from employee, admin where admin.empID = employee.empID and admin.empID = '" + tempU.substring(5, tempU.length()) + "' and admin.password = '"+tempP+"';");
                if(rs.next())
                {
                    checkBox(tempU, tempP);
                    empID = rs.getString(1);
                    
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
                else
                    label.setText("Please Enter Correct Credentials!");
            }
            else
                label.setText("Please Enter Correct Credentials!");
        }
        else
            label.setText("Please Enter Username and Password First !");
        
        //REST OF THE WORk 
    }
    
    public void forgetPasswordButtonPushed(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ForgetPassword.fxml"));
        Parent tableViewParent = loader.load();
        
        Scene tableViewScene = new Scene(tableViewParent);
        
        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        ForgetPasswordController controller = loader.getController();
        controller.setParameter("AdminLogin.fxml");
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableViewScene);
        window.show();
        
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            
        try
        {
           getCreditials();
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }

        // TODO
        msgIcon = new ImageView("id.png");
        lockIcon = new ImageView("lock.png");
        close = new ImageView("close.png");
        logo = new ImageView("logo1.png");
        logo2 = new ImageView("adminLogo.png");
    }    
    
    
    
}
