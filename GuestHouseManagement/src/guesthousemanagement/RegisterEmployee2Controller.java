/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guesthousemanagement;

import java.io.IOException;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import verifications.Checks;

/**
 * FXML Controller class
 *
 * @author Hamza Barak
 */
public class RegisterEmployee2Controller implements Initializable {

    @FXML private ImageView logo;
    @FXML private ImageView logo2;

   
    @FXML private TextField country1;
    @FXML private TextField country2;
    @FXML private TextField city1;
    @FXML private TextField city2;
    @FXML private TextArea address1;
    @FXML private TextArea address2;
    @FXML private Label label;
    
    String reg1[] = new String[8];
    String reg2[] = new String[6];
    
    String empID = "";
    
    public void setParameter(String[] reg1)
    {
        this.reg1 = reg1;
    }
    
    public void setParameter(String[] reg1, String[] reg2, String empID)
    {
        this.empID = empID;
        this.reg1 = reg1;
        
        this.reg2 = reg2;
        
        country1.setText(reg2[0]);
        country2.setText(reg2[3]);
        city1.setText(reg2[1]);
        city2.setText(reg2[4]);
        address1.setText(reg2[2]);
        address2.setText(reg2[5]);
    }
    
    //This Method is for moving back to previous page/Screen (RegisterCustomer Screen) from which this scene/screen is called
    public void backButtonPushed(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("RegisterEmployee.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
//        
        RegisterEmployeeController controller = loader.getController();
        controller.setParameter(reg1, empID);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }  
    
    //This Method is for moving to next page/Screen which is RegisterCustomer3
    public void nextButtonPushedRegister2(ActionEvent event) throws IOException
    {
        if(!(country1.getText().equals("") || city1.getText().equals("")  || address1.getText().equals("")))
        {
            if(Checks.alphaCheckIgnoreSpace(country1.getText()))
            {
                if(Checks.alphaCheckIgnoreSpace(country2.getText()) || country2.getText().equals(""))
                {
                    if(Checks.alphaCheckIgnoreSpace(city1.getText()))
                    {
                        if(Checks.alphaCheckIgnoreSpace(city2.getText()) || city2.getText().equals(""))
                        {
                            reg2[0] = country1.getText();
                            reg2[1] = city1.getText();
                            reg2[2] = address1.getText();
                            reg2[3] = country2.getText();
                            reg2[4] = city2.getText();
                            reg2[5] = address2.getText();
                            
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("RegisterEmployee3.fxml"));
                            Parent tableViewParent = loader.load();

                            Scene tableViewScene = new Scene(tableViewParent);

                            String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
                            tableViewScene.getStylesheets().add(css);
                            
                            RegisterEmployee3Controller controller = loader.getController();
                            controller.setParameter(reg1, reg2, empID);

                            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

                            window.setScene(tableViewScene);
                            window.show();
                        }
                        else
                            label.setText("City 2 Name only contains alphabets");
                    }
                    else
                        label.setText("City 1 Name only contains alphabets");
                }
                else
                    label.setText("Country 2 Name only contains alphabets");
            }
            else
                label.setText("Country 1 Name only contains alphabets");
        }
        else
        {
            label.setText("You Cannot Leave Any of the Field Empty! (Except Other mobile number)");
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
        logo = new ImageView("logo1.png");
        logo2 = new ImageView("customerLogo.png");
    } 
}
