/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package booking;

import Database.Database;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
//import parts.Parts.*;
import authentication.Employee;
import customer.Customer;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import parts.Part;

/**
 * FXML Controller class
 *
 * @author Yousf
 */
public class RepairSectionController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML private ChoiceBox<Employee> mechanic_id;
   
    @FXML private TextField hoursWorked;
    
    @FXML private Button submit;    
    @FXML private Button close;    
    Booking bookings;
    BookingController books;
    
    private ObservableList<Employee> employees = FXCollections.observableArrayList((Database.getInstance()).getEmployees());
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        mechanic_id.setItems(employees);
        mechanic_id.setConverter(new StringConverter<Employee>() {
            @Override
            public String toString(Employee object) {
                return object.getId() + ": "+object.getFirstName();
            }

            @Override
            public Employee fromString(String string) {
                int userId = Integer.parseInt(string.split(": ")[0]);
                return Database.getInstance().getEmployee(userId);
            }
            
        });
        mechanic_id.getSelectionModel().selectFirst();
        
        
        
        
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                  if(hoursWorked.getText().isEmpty()){
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Error");
                    alert1.setContentText("Please enter the hours worked by the mechanic");
                    alert1.showAndWait();
             
             }   
                else{
                int mechanicID = mechanic_id.getValue().getId();
                
                int hoursSpent=Integer.parseInt(hoursWorked.getText());
                
                
                
                    bookings.setHoursWorked(hoursSpent);
                bookings.setEmployeeID(mechanicID);
                
                
                    Database.getInstance().editBookingHours(bookings);
                
                
                
                
                    
                    Stage stage = (Stage) submit.getScene().getWindow();
                    stage.close();
            }
            }
    });    
      close.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Stage stage = (Stage) close.getScene().getWindow();
                stage.close();
            }
        });
    
    
}
}