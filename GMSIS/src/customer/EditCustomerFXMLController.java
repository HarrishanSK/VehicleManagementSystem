/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customer;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.collections.*;
import javafx.scene.control.*;

import Database.*;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author matteo
 */
public class EditCustomerFXMLController implements Initializable {

    CustomerFXMLController c;
    Customer customer;
    
    @FXML private Button cancelButton;
    @FXML private Button editButton;
    @FXML private TextField fullNameTextField;
    @FXML private TextField addressTextField;
    @FXML private TextField postcodeTextField;
    @FXML private TextField phoneNumberTextField;
    @FXML private TextField emailTextField;
    @FXML private ChoiceBox customerTypeChoiceBox;
    
    //Customer customer = new Customer("Matteo Cassia", "Scape East, 450 Mile End Road", "E14GG", "+44 (0) 7843847126", "matteo.cassia@me.com", CustomerType.Private);
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        customerTypeChoiceBox.getItems().addAll(FXCollections.observableArrayList("Private", "Business"));
        customerTypeChoiceBox.getSelectionModel().selectFirst();
        
        //
        
        //
        
        
        
        
        editButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                
                String fullName = fullNameTextField.getText();
                String address = addressTextField.getText();
                String postcode = postcodeTextField.getText();
                String phoneNumber = phoneNumberTextField.getText();
                String email = emailTextField.getText();
                String customerType = (String) customerTypeChoiceBox.getValue();
                
                customer.setFullName(fullName);
                customer.setAddress(address);
                customer.setPostcode(postcode);
                customer.setPhoneNumber(phoneNumber);
                customer.setEmail(email);
                customer.setCustomerType(customerType);
                
                if(fullName.equals("") || address.equals("") || postcode.equals("") || phoneNumber.equals("") || email.equals("")) {
                    //
                } else {
                    Database.getInstance().editCustomer(customer);
                    c.loadTable();
                    Stage stage = (Stage) editButton.getScene().getWindow();
                    stage.close();
                }
            }
        });
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Stage stage = (Stage) editButton.getScene().getWindow();
                stage.close();
            }
        });
    }    
    
    public void loadCustomer(Customer customer) {
        this.customer = customer;
        fullNameTextField.setText(customer.getFullName());
        addressTextField.setText(customer.getAddress());
        postcodeTextField.setText(customer.getPostcode());
        emailTextField.setText(customer.getEmail());
        phoneNumberTextField.setText(customer.getPhoneNumber());
        if(customer.getCustomerType() == CustomerType.Business) {
            customerTypeChoiceBox.getSelectionModel().select(1);
        }
    }
    
}
