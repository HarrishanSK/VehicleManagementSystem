/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package authentication;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.scene.control.*;
import Database.*;
import javafx.collections.FXCollections;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author matteo
 */
public class AddEmployeeFXMLController implements Initializable {

    @FXML private TextField firstNameTextField;
    @FXML private TextField lastNameTextField;
    @FXML private TextField wageTextField;
    @FXML private PasswordField passwordTextField;
    @FXML private PasswordField passwordConfirmationTextField;
    @FXML private ChoiceBox typeChoiceBox;
    
    EmployeeFXMLController ec = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        typeChoiceBox.getItems().addAll(FXCollections.observableArrayList("User", "Admin"));
        typeChoiceBox.getSelectionModel().selectFirst();
    }    
    
    public void save() {
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String p1 = passwordTextField.getText();
        String p2 = passwordConfirmationTextField.getText();
        double wage = Double.parseDouble(wageTextField.getText());
        EmployeeType et = EmployeeType.User;
        if(((String) typeChoiceBox.getValue()).equals("Admin")) {
            et = EmployeeType.Admin;
        }
        
        if(firstName.equals("") || lastName.equals("") || p1.equals("") || !p1.equals(p2)) {
            //
        } else {
            Employee e = new Employee(firstName,lastName,p1,wage,et);
            Database.getInstance().addEmployee(e);
            ec.loadTable();
            closeStage();
        }
        
    }
    
    public void setEmployeeController(EmployeeFXMLController ec) {
        this.ec = ec;
    }
    
    public void closeStage() {
        ((Stage) firstNameTextField.getScene().getWindow()).close();
    }
    
}
