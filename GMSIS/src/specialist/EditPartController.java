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
import java.awt.event.ActionListener;
import javafx.stage.Stage;
import parts.*;
import javafx.util.StringConverter;
import java.util.ArrayList;
import javafx.scene.control.Alert.AlertType;
import parts.*;



/**
 *
 * @author Pahel
 */
public class EditPartController implements Initializable {
    
    SpecialistController S;
    SpecialistRepair repair;
    @FXML private Button cancelButton;
    @FXML private Button editButton;
    @FXML private TextField cost;
    @FXML private TextField desc;
    @FXML private ComboBox selectPart;
    ArrayList<SpecialistRepair> prevRepairs = new ArrayList<SpecialistRepair>();
    ArrayList<Part> partsUsed = new ArrayList<Part>();
    
    private ObservableList<Part> parts = FXCollections.observableArrayList((Database.getInstance()).getParts());

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        selectPart.setItems(parts);
        selectPart.setConverter(new StringConverter<Part>() {
            @Override
            public String toString(Part object) {
                return object.getName();
            }

            @Override
            public Part fromString(String string) {
                String name = String.valueOf(string);
                
                       int i = selectPart.getSelectionModel().getSelectedIndex();  
                       return parts.get(i);
            }
            
        });
        
        selectPart.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override public void handle(ActionEvent e)
            {
                Part part = parts.get(selectPart.getSelectionModel().getSelectedIndex());
                desc.setText(part.getDescription());
                cost.setText(String.valueOf(part.getSpecialistCost()));
                
                int i = selectPart.getSelectionModel().getSelectedIndex();
                int p = parts.get(i).getID();
                
                PartRepair rep = Database.getInstance().getPartRepair(p, repair.getBookingId());
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
               
        editButton.setOnAction(new EventHandler<ActionEvent>() {
            
            
            @Override public void handle(ActionEvent e) { 
                
                boolean flag=false;
                int i = selectPart.getSelectionModel().getSelectedIndex();
                
                if(i!=-1){
                    int part = parts.get(i).getID();
                
                    if(repair.getRepairType().equals("Vehicle"))
                    {
                        
                        
                        for(int j=0;j<prevRepairs.size();j++)
                        {
                           if(part==prevRepairs.get(j).getPartsId()){
                           flag=true;
                        }
                      }
                
                     }
                
                    if(flag){
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Part Required");
                    alert.setHeaderText("This part is already present in this Vehicle repair!");

                    alert.showAndWait();
                    Stage stage = (Stage) editButton.getScene().getWindow();
                    stage.show();
                     }
                else{
                    
                    double cost = parts.get(i).getSpecialistCost();
                    PartRepair partRep = Database.getInstance().getPartRepair(part, repair.getBookingId());
                    int stock = parts.get(i).getStock();
                    
                    if(partRep!=null)
                    { 
                        Database.getInstance().deletePartRepair(part, repair.getBookingId());
                        Database.getInstance().editStock(stock, part);     
                    }
                    
                    repair.setPartsId(part);
                    repair.setRepairCost(cost);
                                        
                    Database.getInstance().editSpcBooking("update 'specialist_bookings' set part_id = '"+ repair.getPartsId() +"' where repairID = '" + repair.getRepairId() +"'");
                    Database.getInstance().editSpcBooking("update 'specialist_bookings' set repairCost = '"+ repair.getRepairCost() +"' where repairID = '" + repair.getRepairId() +"'");
                    
                    S.loadTable();
                    Stage stage = (Stage) editButton.getScene().getWindow();
                    stage.close();
                }
            }
                else{
                     Alert alert = new Alert(AlertType.ERROR);
                     alert.setTitle("Edit Part");
                     alert.setContentText("Please select a part first!");
                     alert.showAndWait();
                }
            }
        });
        
        cancelButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override public void handle(ActionEvent e)
            {
                Stage stage = (Stage) editButton.getScene().getWindow();
                stage.close();
            }
        });
        
    }    
    
    public void bookingDetails(SpecialistRepair repair) {
        this.repair = repair;
        prevRepairs = Database.getInstance().getSpcBookings("select * from ('specialist_bookings' Inner Join 'customer' on specialist_bookings.customer_id = customer.customer_id) Inner Join 'vehicle' on specialist_bookings.vehicle_id=vehicle.vehicle_id where booking_id = '"+ repair.getBookingId() +"'");
    }
    
}
