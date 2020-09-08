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
public class AddCustomerFXMLController implements Initializable {

    CustomerFXMLController c;
    @FXML private Button cancelButton;
    @FXML private Button addButton;
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
        
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                
                String fullName = fullNameTextField.getText();
                String address = addressTextField.getText();
                String postcode = postcodeTextField.getText();
                String phoneNumber = phoneNumberTextField.getText();
                String email = emailTextField.getText();
                CustomerType customerType = CustomerType.Private;
                
                String ct = (String) customerTypeChoiceBox.getValue();
                if(ct.equals("Business")) {
                    customerType = CustomerType.Business;
                }
                
                if(fullName.equals("") || address.equals("") || postcode.equals("") || phoneNumber.equals("") || email.equals("")) {
                    //
                } else {
                    Customer customer = new Customer(fullName, address, postcode, phoneNumber, email, customerType);
                    Database.getInstance().addCustomer(customer);
                    c.loadTable();
                    Stage stage = (Stage) addButton.getScene().getWindow();
                    stage.close();
                }
            }
        });
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Stage stage = (Stage) addButton.getScene().getWindow();
                stage.close();
            }
        });
    }    
    
}
