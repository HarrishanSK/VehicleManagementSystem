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

/**
 * FXML Controller class
 *
 * @author Pahel
 */
public class AddMechanicController implements Initializable {
    
    SpecialistController S; 
    @FXML private Label HeadingLabel;
    @FXML private Label CentreLabel;
    @FXML private Label MechanicNameLabel;
    @FXML private Button cancelButton;
    @FXML private Button addButton;
    @FXML private TextField MechanicNameTextField;
    @FXML private ComboBox CentresComboBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
