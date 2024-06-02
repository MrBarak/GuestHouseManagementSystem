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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import verifications.Checks;

public class RegisterCustomerController implements Initializable {

    String radioButtonValue = "";
    
    @FXML private ImageView logo;
    @FXML private ImageView logo2;
    
    
    //For Radio Button
    @FXML private RadioButton male;
    @FXML private RadioButton female;
    @FXML private ToggleGroup favLang; //this is for grouping the upper 2 radio button, so that only one button can be selected at a time
    
    //for getting Date
    @FXML private DatePicker dateOfBirth;
    
    //TextFields
    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField cnic;
    @FXML private TextField mob1; //mobile number 1
    @FXML private TextField mob2;
    @FXML private Label label; //label for wrong input
    
    String reg1[] = new String[7];
    
    public void setParameter(String[] reg1)
    {
        this.reg1 = reg1;
        
        firstName.setText(reg1[0]);
        lastName.setText(reg1[1]);
        cnic.setText(reg1[2]);
        dateOfBirth.setValue(Checks.stringToLocalDateConverter(reg1[3]));
        
        if(reg1[4].equalsIgnoreCase("Male"))
            favLang.selectToggle(male);
        else if(reg1[4].equalsIgnoreCase("Female"))
            favLang.selectToggle(female);
        
        mob1.setText(reg1[5]);
        mob2.setText(reg1[6]);
    }
    
    //For getting RadioButton's Value
    public void radioButtonView()
    {
        if(favLang.getSelectedToggle().equals(male))
            this.radioButtonValue = male.getText();
        else if(favLang.getSelectedToggle().equals(female))
            this.radioButtonValue = female.getText();
    }
    
    //This Method is for moving back to previous page/Screen (Customer Screen) from which this scene/screen is called
    public void backButtonPushed(ActionEvent event) throws IOException
    {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("CustomerLogin.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        
        String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
        tableViewScene.getStylesheets().add(css);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableViewScene);
        window.show();
    }  
    
    //This Method is for moving to next page/Screen which is RegisterCustomer2
    public void nextButtonPushedRegister(ActionEvent event) throws IOException
    {
        radioButtonView(); //call just to initalize the value of RadioButtons
        
        if(Checks.alphaCheckIgnoreSpace(firstName.getText()) && !firstName.getText().equals("") && Checks.alphaCheckIgnoreSpace(lastName.getText()) && !lastName.getText().equals("")&& Checks.cnicCheck(cnic.getText()) && !cnic.getText().equals("")&& Checks.mobCheck(mob1.getText()) && !(dateOfBirth.getValue().equals("")) && !radioButtonValue.equals("") && !mob1.getText().equals("") && (Checks.mobCheck(mob2.getText()) || mob2.getText().equals("")))
        {
            if(Checks.cnicCheckData(cnic.getText()))
            {
                if(Checks.mobCheckData(mob1.getText()))
                {
                    if(Checks.mobCheckData(mob2.getText()) || mob2.getText().equals(""))
                    {
                        String DOB[] = dateOfBirth.getValue().toString().split("-");
                        
                        if(Checks.ageCheck(Integer.parseInt(DOB[0]), Integer.parseInt(DOB[1]), Integer.parseInt(DOB[2])))
                        {
                            
                            reg1[0] = firstName.getText();
                            reg1[1] = lastName.getText();
                            reg1[2] = cnic.getText();
                            reg1[3] = dateOfBirth.getValue().toString();
                            reg1[4] = radioButtonValue;
                            reg1[5] = mob1.getText();
                            reg1[6] = mob2.getText();

                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("RegisterCustomer2.fxml"));
                            Parent tableViewParent = loader.load();

                            Scene tableViewScene = new Scene(tableViewParent);

                            String css = GuestHouseManagement.class.getResource("buttonStyles.css").toExternalForm();
                            tableViewScene.getStylesheets().add(css);
                    //        
                            RegisterCustomer2Controller controller = loader.getController();
                            controller.setParameter(reg1);

                            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

                            window.setScene(tableViewScene);
                            window.show();
                        }
                        else
                            label.setText("You Cannot Register ! Your Age is Under 18!!!");
                    }
                    else
                        label.setText("This Mobile 2 is already registered");
                }
                else
                    label.setText("This Mobile 1 is already registered");
            }
            else
                label.setText("This CNIC Already Registered !!!");
        }
        else
        {
            if(firstName.getText().equals("") || lastName.getText().equals("") || cnic.getText().equals("") || mob1.getText().equals("") || (dateOfBirth.getValue().equals("")) || radioButtonValue == null)
            {
                label.setText("You Cannot Leave Any of the Field Empty! (Except Other mobile number)");
//                System.out.println(firstName.getText());
//                System.out.println(lastName.getText());
//                System.out.println(cnic.getText());
//                System.out.println(mob1.getText());
//                System.out.println(dateOfBirth.getValue());
//                System.out.println(radioButtonValue);
            }
            
            if(!Checks.mobCheck(mob2.getText()))
                label.setText("Please! Enter Mobile Number like 03035405615 (Only 11 digits)");
            if(!Checks.alphaCheckIgnoreSpace(firstName.getText()))
                label.setText("Please! Enter Only Alpha Characters in First Name Field");
            if(!Checks.alphaCheckIgnoreSpace(lastName.getText()))
                label.setText("Please! Enter Only Alpha Characters in Last Name Field");
            if(!Checks.cnicCheck(cnic.getText()))
                label.setText("Please! Enter CNIC in Format 12345-1234567-1 (Use only digits and '-')");
            if(!Checks.mobCheck(mob1.getText()))
                label.setText("Please! Enter Mobile Number like 03035405615 (Only 11 digits)");
        }
    } 
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        logo = new ImageView("logo1.png");
        logo2 = new ImageView("customerLogo.png");
        
        //for RADIO Button and there toggle group
        favLang = new ToggleGroup();
        male.setToggleGroup(favLang);
        female.setToggleGroup(favLang);
        
        favLang.selectToggle(male);
    }    
    
}
