/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package specialist;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.collections.*;
import javafx.scene.control.*;

import Database.*;
import javafx.stage.Stage;
import parts.*;
import java.util.ArrayList;
import javafx.scene.control.Alert.AlertType;
import javafx.util.StringConverter;


/**
 *
 * @author Pahel
 */
public class AddPartController implements Initializable {
    
    SpecialistController S;
    SpecialistRepair repair;
    ArrayList<SpecialistRepair> prevRepairs = new ArrayList<SpecialistRepair>();
    ArrayList<Part> partsUsed = new ArrayList<Part>();
    @FXML private TextField cost;
    @FXML private TextField desc;
    @FXML private Button addButton;
    @FXML private Button cancelButton;
    @FXML private ComboBox<Part> newPartComboBox;
    
    private ObservableList<Part> parts = FXCollections.observableArrayList((Database.getInstance()).getParts());

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        newPartComboBox.setItems(parts);
        newPartComboBox.setConverter(new StringConverter<Part>() {
            @Override
            public String toString(Part object) {
                return object.getName();
            }

            @Override
            public Part fromString(String string) {
                String name = String.valueOf(string);
                
                       int i = newPartComboBox.getSelectionModel().getSelectedIndex();  
                       return parts.get(i);
            }
            
        });
        
        newPartComboBox.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override public void handle(ActionEvent e)
            {
                Part part = parts.get(newPartComboBox.getSelectionModel().getSelectedIndex());
                desc.setText(part.getDescription());
                cost.setText(String.valueOf(part.getSpecialistCost()));
                
                PartRepair rep = Database.getInstance().getPartRepair(part.getID(), repair.getBookingId());
                if(rep!=null)
                {
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Specialist Repair Update");
                        alert.setContentText("This part was previously selected as a replacement part for normal repairs. If you proceed, the normal repair will be removed and the original part will proceed for specialist repairs!");
                        alert.showAndWait();
                        
                }
                
            }
        });
        
        // TODO
               
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            
            
            @Override public void handle(ActionEvent e) {
                                
                    PartRepair partRep = null;
                    int bID = repair.getBookingId();
                    int i = newPartComboBox.getSelectionModel().getSelectedIndex();
                    if(i!=-1){
                    int part = parts.get(i).getID();                    

                    int stock = parts.get(i).getStock();
                
                    partRep = Database.getInstance().getPartRepair(part, bID);

                    if(partRep!=null)
                    { 
                        Database.getInstance().deletePartRepair(part, bID);
                        Database.getInstance().editStock(stock, part);     
                    }
                    
                    double cost = parts.get(i).getSpecialistCost();
                    
                    int cID = repair.getCustomerId();
                    int vID = repair.getVehicleId();
                    int centreID = repair.getCentreId();
                    
                    String repairType = repair.getRepairType();
                    String delDate = repair.getDeliveryDate();
                    String retDate = repair.getReturnDate();
                                        
                
                    SpecialistRepair repair = new SpecialistRepair(bID, cID, vID, centreID, repairType, part, delDate, retDate, "unfinished", cost);
                    Database.getInstance().addSpcBooking(repair);
                    S.loadTable();
                    Stage stage = (Stage) addButton.getScene().getWindow();
                    stage.close();
               }
                else{
                     Alert alert = new Alert(AlertType.ERROR);
                     alert.setTitle("Add Part");
                     alert.setContentText("Please select a part first!");
                     alert.showAndWait();
                } 
            }
        });
        
        cancelButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override public void handle(ActionEvent e)
            {
                Stage stage = (Stage) addButton.getScene().getWindow();
                stage.close();
            }
        });
        
    }    
    
    public void bookingDetails(SpecialistRepair repair) {
        this.repair = repair;
        if(repair.getRepairType().equals("Vehicle")){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Vehicle Repair Parts");
            alert.setContentText("You won't be allowed to add parts that are already present in this Vehicle repair!");
            alert.showAndWait();
        }
        
        prevRepairs = Database.getInstance().getSpcBookings("select * from ('specialist_bookings' Inner Join 'customer' on specialist_bookings.customer_id = customer.customer_id) Inner Join 'vehicle' on specialist_bookings.vehicle_id=vehicle.vehicle_id where booking_id = '"+ repair.getBookingId() +"'");
        for(int i=0;i<prevRepairs.size();i++){
            
                partsUsed.add(Database.getInstance().getPart((prevRepairs.get(i)).getPartsId()));
                for(int j=0;j<parts.size();j++)
                {
                    if(partsUsed.get(i).getID()==parts.get(j).getID())
                    {
                        parts.remove(j);
                        break;
                    }
                }
            } 
    }
    
}
