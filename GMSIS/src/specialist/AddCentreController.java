/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package specialist;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.collections.*;
import javafx.scene.control.*;

import Database.*;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * FXML Controller class
 *
 * @author Pahel
 */
public class AddCentreController implements Initializable {
    
    SpecialistController S; 
    @FXML private Button cancelButton;
    @FXML private Button addButton;
    @FXML private Label HeadingLabel;
    @FXML private Label CentreNameLabel;
    @FXML private Label CentreAddressLabel;
    @FXML private Label CentrePhoneLabel;
    @FXML private Label CentreEmailLabel;
    @FXML private TextField CentreNameTextField;
    @FXML private TextField CentreAddressTextField;
    @FXML private TextField CentrePhoneTextField;
    @FXML private TextField CentreEmailTextField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                
                String name = CentreNameTextField.getText();
                String address = CentreAddressTextField.getText();
                String phone = CentrePhoneTextField.getText();
                String email = CentreEmailTextField.getText();                
                
                if(name.equals("") || address.equals("") || phone.equals("") || email.equals("")) 
                {
                    //Error message 
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setContentText("Please fill in all the fields!");
                    alert.showAndWait();
                } 
                else 
                {
                    SpecialistRepairCentre SPC = new SpecialistRepairCentre(name, address, phone, email);
                    Database.getInstance().addSPC(SPC);
                    S.loadTable();
                    Stage stage = (Stage) addButton.getScene().getWindow();
                    stage.close();
                }
            }
        });
        
        cancelButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override public void handle(ActionEvent e)
            {
                Stage stage = (Stage) addButton.getScene().getWindow();
                stage.close();
            }
        });
    }    
    
} 
 