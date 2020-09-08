

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import authentication.*;
import customer.CustomerFXMLController;
import booking.*;
import booking.BookingController;
import specialist.SpecialistController;
import vehicle.*;
import vehicle.VehicleController;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

/**
 * FXML Controller class
 *
 * @author matteo
 */
public class MenuFXMLController implements Initializable {

    @FXML private Button customersButton;
    @FXML private Button bookingButton;
    @FXML private Button specialistButton;
    @FXML private Button employeesButton;
    @FXML private Button vehicleButton;
    @FXML private Button partsButton;
    
   @Override
    public void initialize(URL url, ResourceBundle rb) {
        customersButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/customer/CustomerFXML.fxml"));
                    AnchorPane page = (AnchorPane) loader.load();
                    Stage stage = new Stage();
                    Scene scene = new Scene(page);
                    stage.setScene(scene);
                    stage.setTitle("Customers");
                    stage.show();
                    //((Stage) customersButton.getScene().getWindow()).close();
                } catch (Exception ex) {
                    Logger.getLogger(CustomerFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            
        });
        
                vehicleButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                try {
                   FXMLLoader loader = new FXMLLoader(getClass().getResource("/vehicle/Vehicle.fxml"));
                    AnchorPane page = (AnchorPane) loader.load();
                    Stage stage = new Stage();
                    Scene scene = new Scene(page);
                    stage.setScene(scene);
                    stage.setTitle("Vehicle");
                    stage.show();
                } catch (Exception ex) {
                    Logger.getLogger(VehicleController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        specialistButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/specialist/Specialist.fxml"));
                    AnchorPane page = (AnchorPane) loader.load();
                    Stage stage = new Stage();
                    Scene scene = new Scene(page);
                    stage.setScene(scene);
                    stage.setTitle("Specialist Repairs");
                    stage.show();
                } catch (Exception ex) {
                    Logger.getLogger(SpecialistController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        bookingButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/booking/booking.fxml"));
                    AnchorPane page = (AnchorPane) loader.load();
                    Stage stage = new Stage();
                    Scene scene = new Scene(page);
                    stage.setScene(scene);
                    stage.setTitle("Booking");
                    stage.show();
                    //((Stage) customersButton.getScene().getWindow()).close();
                } catch (Exception ex) {
                    Logger.getLogger(BookingController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        partsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/parts/Parts.fxml"));
                    AnchorPane page = (AnchorPane) loader.load();
                    Stage stage = new Stage();
                    Scene scene = new Scene(page);
                    stage.setScene(scene);
                    stage.setTitle("Parts");
                    stage.show();
                    //((Stage) customersButton.getScene().getWindow()).close();
                } catch (Exception ex) {
                    Logger.getLogger(BookingController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        

        
        if(Authentication.isAdmin() == false) {
            employeesButton.setDisable(true);
        }
        
    }    
    
    public void logout() {
        Authentication.logout();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/authentication/AuthenticationFXML.fxml"));
            Pane page = (Pane) loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(page);
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.show();
            
            } catch (Exception ex) {
                ex.printStackTrace();
                //Logger.getLogger(EmployeeFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        ((Stage) customersButton.getScene().getWindow()).close();
    }
    
    public void showEmployees() {
        try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/authentication/EmployeeFXML.fxml"));
                    AnchorPane page = (AnchorPane) loader.load();
                    Stage stage = new Stage();
                    Scene scene = new Scene(page);
                    stage.setScene(scene);
                    stage.setTitle("Employees");
                    stage.show();
                    //((Stage) customersButton.getScene().getWindow()).close();
                } catch (Exception ex) {
                    Logger.getLogger(EmployeeFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
    
}
