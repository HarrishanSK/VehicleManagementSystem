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
import parts.*;

import Database.*;
import java.util.*;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * FXML Controller class
 *
 * @author Pahel
 */
public class chargeController implements Initializable {
    
    SpecialistController S;
    SpecialistRepair repair;
    @FXML private Button addButton;
    @FXML private TextField amount;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                        
                boolean flag = false;
                if(amount.getText().equals("")) 
                {
                    //Error message 
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setContentText("Please enter the additional centre cost to be added!");
                    alert.showAndWait();
                } 
                else{
                try{    
                    double cost = Double.parseDouble(amount.getText());
                }
                catch(NumberFormatException a){
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Please enter a numeric amount!");
                        alert.showAndWait();
                        flag = true;
                    }
                
                if(flag==false){
                    
                    double cost = Double.parseDouble(amount.getText());
                    if(repair.getRepairType().equals("Individual Part")){
                    cost = cost + repair.getRepairCost();
                    String query = "update 'specialist_bookings' set status = 'complete' where repairID = '" + repair.getRepairId() +"'";
                    String query2 = "update 'specialist_bookings' set repairCost = '" + cost + "' where repairID = '" + repair.getRepairId() +"'";
                          Database.getInstance().editSpcBooking(query);
                          Database.getInstance().editSpcBooking(query2);
                          S.loadTable();
                          Alert alert = new Alert(AlertType.INFORMATION);
                          alert.setTitle("Completed Repair Costs");
                          alert.setHeaderText("Completed Individual Part Repair Cost");
                          String bookingInfo = "Booking ID: " + repair.getBookingId() + "\nCustomer ID: " + repair.getCustomerId() + "\nCustomer Name: " + repair.getFullName() + "\nVehicle ID: " + repair.getVehicleId();
                          Part part = Database.getInstance().getPart(repair.getPartsId());
                          String split = "\n-------------------------------------------------------";
                          String partInfo = "Part: " + part.getName() + "\nCost: " + cost + "\nTotal Cost: " + cost;
                          String msg = "\nAbove specialist cost will now be added to the customer's bill! Access the updated bill on the Customer module!";
                          alert.setContentText(bookingInfo + split + partInfo + "\n" +msg);
                          alert.showAndWait();
                    }
                    else{
                          ArrayList<SpecialistRepair> rep = Database.getInstance().getSpcBookings("SELECT * FROM ('specialist_bookings' Inner Join 'customer' on specialist_bookings.customer_id = customer.customer_id) Inner Join 'vehicle' on specialist_bookings.vehicle_id=vehicle.vehicle_id where booking_id = '" + repair.getBookingId() +"'");
                          Alert alert = new Alert(AlertType.INFORMATION);
                          alert.setTitle("Completed Vehicle Repair Costs");
                          alert.setHeaderText("Completed Vehicle Repair Costs");
                          String bookingInfo = "Booking ID: " + repair.getBookingId() + "\nCustomer ID: " + repair.getCustomerId() + "\nCustomer Name: " + repair.getFullName() + "\nVehicle ID: " + repair.getVehicleId();
                          String split = "\n-------------------------------------------------------";
                          String partInfo=""; double total = 0;
                          int n = rep.size();
                          cost = cost/n;
                          for(int i=0;i<rep.size();i++){
                              Part part = Database.getInstance().getPart(rep.get(i).getPartsId());
                              double newCost = rep.get(i).getRepairCost() + cost;
                              String query = "update 'specialist_bookings' set status = 'complete' where repairID = '" + rep.get(i).getRepairId() +"'";
                              String query2 = "update 'specialist_bookings' set repairCost = '" + newCost + "' where repairID = '" + rep.get(i).getRepairId() +"'";
                              Database.getInstance().editSpcBooking(query);
                              Database.getInstance().editSpcBooking(query2);
                              S.loadTable();
                              partInfo += "\nPart: " + part.getName() + "     Cost: " + newCost;
                          total += newCost;
                          }
                          String tot = "\nTotal Cost: " + total;
                          String msg = "\nAbove specialist costs will now be added to the customer's bill! Access the updated bill on the Customer module!";
                          alert.setContentText(bookingInfo + split + partInfo + "\n" + tot + "\n" + "\n" +msg);
                          alert.showAndWait();
                    }
                    S.loadTable();
                    Stage stage = (Stage) addButton.getScene().getWindow();
                    stage.close();
                }
              }
            }
                    
                    
                
            
        });
    }

      public void detail(SpecialistRepair repair){
          this.repair = repair;
      }    
    
} 
 