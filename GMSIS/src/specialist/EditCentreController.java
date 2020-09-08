/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package specialist;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.control.*;

import Database.*;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Pahel
 */
public class EditCentreController implements Initializable {

    SpecialistController S;
    SpecialistRepairCentre centre;
    
    @FXML private Button editButton;
    @FXML private Button cancelButton;
    @FXML private Label headingLabel;
    @FXML private TextField newNameTextField;
    @FXML private TextField newAddressTextField;
    @FXML private TextField newPhoneTextField;
    @FXML private TextField newEmailTextField;
    @FXML private Label nameLabel;
    @FXML private Label addressLabel;
    @FXML private Label phoneLabel;
    @FXML private Label emailLabel;
    @FXML private Label originalLabel;
    @FXML private Label newLabel;
    @FXML private TextField oldNameTextField;
    @FXML private TextField oldAddressTextField;
    @FXML private TextField oldPhoneTextField;
    @FXML private TextField oldEmailTextField;

        
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
               
        editButton.setOnAction(new EventHandler<ActionEvent>() {
            
            
            @Override public void handle(ActionEvent e) {         
                
                String name = newNameTextField.getText();
                String address = newAddressTextField.getText();
                String phone = newPhoneTextField.getText();
                String email = newEmailTextField.getText();
                
                if(name.equals("")) 
                {
                    name = oldNameTextField.getText();
                } 
                if(address.equals(""))
                {
                    address = oldAddressTextField.getText();
                }
                if(phone.equals(""))
                {
                    phone = oldPhoneTextField.getText();
                }
                if(email.equals(""))
                {
                    email = oldEmailTextField.getText();
                }
                
                centre.setName(name);
                centre.setAddress(address);
                centre.setPhone(phone);
                centre.setEmail(email);
                
                
                    Database.getInstance().editSPC(centre);
                    S.loadTable();
                    Stage stage = (Stage) editButton.getScene().getWindow();
                    stage.close();
                
            }
        });
        
        cancelButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override public void handle(ActionEvent e)
            {
                Stage stage = (Stage) editButton.getScene().getWindow();
                stage.close();
            }
        });
        
    }    
    
    public void displayCentre(SpecialistRepairCentre centre) {
        
        this.centre = centre;
        oldNameTextField.setText(centre.getName());
        oldAddressTextField.setText(centre.getAddress());
        oldEmailTextField.setText(centre.getEmail());
        oldPhoneTextField.setText(centre.getPhone());
    }

    
}
