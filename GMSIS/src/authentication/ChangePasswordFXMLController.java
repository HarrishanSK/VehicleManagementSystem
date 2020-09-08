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
import javafx.stage.*;
import Database.*;

/**
 * FXML Controller class
 *
 * @author matteo
 */
public class ChangePasswordFXMLController implements Initializable {
    
    
    @FXML private TextField firstPasswordTextField;
    @FXML private TextField secondPasswordTextField;
    
    private Employee employee;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void save() {
        String p1 = firstPasswordTextField.getText();
        String p2 = secondPasswordTextField.getText();
        if(p1.equals(p2) && !p1.equals("")) {
            employee.setPassword(p1);
            (Database.getInstance()).editEmployee(employee);
            closeStage();
        } else {
            //
        }
    }
    
    public void setEmployee(Employee e) {
        this.employee = e;
    }
    
    public void closeStage() {
        ((Stage) firstPasswordTextField.getScene().getWindow()).close();
    }
    
}
