/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parts;

import Database.Database;
import booking.Booking;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;


/**
 *
 * @author yachen
 */
public class RequirePlacementController implements Initializable {
    
    @FXML private Button cancelButton;
    @FXML private Button confirmButton;
    @FXML private TextField partName;
    @FXML private ChoiceBox<Booking> chooseVehicle;
    
    
    Part part;
    PartsController partCon;
    RequirePlacementController requireCon;
    private ObservableList<Booking> bookingList = FXCollections.observableArrayList((Database.getInstance()).getBookings());
    private ObservableList<Part> partList = FXCollections.observableArrayList((Database.getInstance()).getParts());
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        
        chooseVehicle.setItems(bookingList);
        chooseVehicle.setConverter(new StringConverter<Booking>() {
            @Override
            public String toString(Booking object) {
                //get vehicle id
                int vehicleID = object.getVehicleID();
                int customerID = object.getCustomerID();
                //call the database to get the vehicle registration number:
                
                String vehicle = Database.getInstance().getResult("select registration_number from 'vehicle' where vehicle_id = '"+Integer.toString(vehicleID)+"';","registration_number");
                String customer = Database.getInstance().getResult("select full_name from 'customer' where customer_id = '"+Integer.toString(customerID)+"';","full_name");     
                return Integer.toString(object.getBookingID())+": "+vehicle +"-"+ customer;
                
            }

            @Override
            public Booking fromString(String string) {
                int userId = Integer.parseInt(string);
                try {
                    return Database.getInstance().getBooking(userId);
                } catch (Exception ex) {
                    Logger.getLogger(RequirePlacementController.class.getName()).log(Level.SEVERE, null, ex);
                }
                return null;
                
            }
            
        });
        chooseVehicle.getSelectionModel().selectFirst();
        
        cancelButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override public void handle(ActionEvent e)
            {
                Stage stage = (Stage) confirmButton.getScene().getWindow();
                stage.close();
            }
        });
        
        confirmButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                //get part id
                int partID = part.getID();
                //get booking id
                int bookingID = chooseVehicle.getValue().getBookingID();
                //add part to booking
                PartRepair parts= new PartRepair(partID, bookingID);
                Database.getInstance().addPartRepair(parts);
                
                //Take one part from stock, update stock
                Database.getInstance().callDatabase("update 'part' set part_stock = '"+(part.getStock()-1) +"' where part_id = '"+ partID +"'");
                partCon.loadTable();
                partCon.loadTable2();
                Stage stage = (Stage) confirmButton.getScene().getWindow();
                stage.close();
            }
        });
    
    }
    
    
    
    public void loadPartID(Part part) {
        this.part = part;
        partName.setText(String.valueOf(part.getName()));
    }
}
