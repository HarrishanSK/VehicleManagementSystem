/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package booking;

import java.net.URL;
import java.util.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.event.*;
import Database.*;

import authentication.AuthenticationFXMLController;
import authentication.Employee;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.*;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import vehicle.*;
import customer.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author Yousf
 */
public class VehicleDetailsController implements Initializable {

    
      Booking booking;
    BookingController books;
    
     @FXML private TextField vehicle_id;
    
    @FXML private TextField mileage;
     @FXML private TextField new_mileage;
      @FXML private TextField vehicle_reg;
    
    @FXML private Button updateMileage;
    @FXML private Button close;
    
    Vehicle vehicle;
    Vehicle v1;
     
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
     
        
        
          close.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Stage stage = (Stage) close.getScene().getWindow();
                stage.close();
            }
        });
        
        updateMileage.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
             if(new_mileage.getText().isEmpty()){
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Error");
                    alert1.setContentText("Please enter the mileage of the vehicle");
                    alert1.showAndWait();
             
             }   
             else{
               
                
               int vehicleID = booking.getVehicleID();
               vehicle=Database.getInstance().getVehicle(vehicleID);
               
               
                
               
             
                     int vehicleMileage=Integer.parseInt(new_mileage.getText());
                    vehicle.setCurrentMileage(vehicleMileage);
                    
                
                
                
                    Database.getInstance().editVehicleMileage(vehicle);
            
                
                
                    
                    Stage stage = (Stage) updateMileage.getScene().getWindow();
                    stage.close();
             }
            }
        
    });    
        
    }
 public void loadBooking(Booking booking) {
        this.booking = booking;
       
        vehicle_id.setText(String.valueOf(booking.getVehicleID()));
        vehicle_reg.setText(String.valueOf(booking.getRegistrationNumber()));
        int vehicleID=booking.getVehicleID();
        int mileages=Database.getInstance().getVehicleMileage(vehicleID);
      
        mileage.setText(String.valueOf(mileages));
        
    }    
    
    
    
}
