/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author elsahu
 */
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class TestController {

    @FXML
    private Button Test;
    @FXML
    private void handleEditButtonAction(ActionEvent event) {
     // Button was clicked, do something...
         System.out.print("dsddd");
    }
    
    public void initialize(URL url, ResourceBundle rb) {
        
    }

}
