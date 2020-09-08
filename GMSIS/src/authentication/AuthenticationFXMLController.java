package authentication;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.stage.*;

import authentication.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Scene;
import javafx.scene.layout.*;

public class AuthenticationFXMLController implements Initializable {

    @FXML private TextField employeeTextField;
    @FXML private PasswordField passwordTextField;
    @FXML private Button loginButton;
    @FXML private Label label;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String employeeId = employeeTextField.getText();
                
                if(employeeId.length() != 5) {
                    label.setText("It should be a 5-digit number!");
                    return;
                }
                
                
                String password = passwordTextField.getText();
                try {
                    int id = Integer.parseInt(employeeId);
                    boolean login = Authentication.login(id,password);
                    if(login) {
                        Stage stage = (Stage) loginButton.getScene().getWindow();
                        stage.close();
                        showNextStage();
                    } else {
                        //System.out.println("nope");
                        label.setText("Your password is incorrect!");
                    }
                } catch(NumberFormatException e) {
                    
                }
            }
        });
    }
    
    public void showNextStage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MenuFXML.fxml"));
            AnchorPane page = (AnchorPane) loader.load();            
            Stage stage = new Stage();
            Scene scene = new Scene(page);
            stage.setScene(scene);
            stage.setTitle("Menu");
            stage.show();
        } catch (Exception ex) {
            Logger.getLogger(AuthenticationFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
