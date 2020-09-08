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
import javafx.util.StringConverter;

import Database.*;
import java.time.LocalDate;
import javafx.stage.Stage;

import java.util.Locale;
import javafx.scene.control.DatePicker;
import javafx.application.Application;
import javafx.scene.control.DateCell;
import javafx.util.Callback;

import parts.*;
import booking.*;
import vehicle.*;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert.AlertType;
/**
 * FXML Controller class
 *
 * @author Pahel
 */
public class MakeSpecialistBookingController implements Initializable {
    
    SpecialistController S; 
    Booking booking;
    @FXML private Button cancelButton;
    @FXML private Button addButton;
    @FXML private Label HeadingLabel;
    @FXML private Label RepairTypeLabel;
    @FXML private Label DeliveryDateLabel;
    @FXML private Label ReturnDateLabel;
    @FXML private Label CentreSelectLabel;
    @FXML private Label PartSelectLabel;
    @FXML private DatePicker DeliveryDatePicker;
    @FXML private DatePicker ReturnDatePicker;
    @FXML private ComboBox<SpecialistRepairCentre> CentreSelectComboBox;
    @FXML private ComboBox RepairTypeComboBox;
    @FXML private ComboBox<Part> PartSelectComboBox;
    
    @FXML private TextField BookingIdTextField;
    @FXML private TextField CustomerIdTextField;
    
    int bID, cID, vID; int part;

    private ObservableList<SpecialistRepairCentre> centres = FXCollections.observableArrayList((Database.getInstance()).getSpecialistCentres());
    private ObservableList<Part> parts = FXCollections.observableArrayList((Database.getInstance()).getParts());
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        /*final Callback<DatePicker, DateCell> dayCellFactory1 = 
            new Callback<DatePicker, DateCell>() {
                @Override
                public DateCell call(final DatePicker datePicker) {
                    return new DateCell() {
                        @Override
                        public void updateItem(LocalDate item, boolean empty) {
                            super.updateItem(item, empty);
                           
                            if (item.isBefore(
                                    LocalDate.now())
                                ) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
                            }   
                    }
                };
            }
        };*/
        
        DeliveryDatePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if(date.isBefore(LocalDate.now()) || date.getDayOfWeek() == DayOfWeek.SUNDAY || date.isEqual(LocalDate.now())){
                setDisable(true);
                setStyle("-fx-background-color: #ffc0cb;");
                }
            }
        });
        
        DeliveryDatePicker.setShowWeekNumbers(false);
        
        //DeliveryDatePicker.setDayCellFactory(dayCellFactory1);
        
        /*final Callback<DatePicker, DateCell> dayCellFactory = 
            new Callback<DatePicker, DateCell>() {
                @Override
                public DateCell call(final DatePicker datePicker) {
                    return new DateCell() {
                        @Override
                        public void updateItem(LocalDate item, boolean empty) {
                            super.updateItem(item, empty);
                           
                            if (item.isBefore(
                                    DeliveryDatePicker.getValue())
                                ) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
                            }   
                    }
                };
            }
        };*/
        
        ReturnDatePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if(date.getDayOfWeek() == DayOfWeek.SUNDAY || date.isBefore(LocalDate.now().plusDays(7))){
                setDisable(true);
                setStyle("-fx-background-color: #ffc0cb;");
            }
            }
        });
        
        ReturnDatePicker.setShowWeekNumbers(false);
        
        //ReturnDatePicker.setDayCellFactory(dayCellFactory);
        
        DeliveryDatePicker.valueProperty().addListener((ov, oldValue, newValue) -> {
            ReturnDatePicker.setValue(newValue.plusDays(6));
            ReturnDatePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if(date.getDayOfWeek() == DayOfWeek.SUNDAY || date.isBefore(DeliveryDatePicker.getValue().plusDays(6))){
                setDisable(true);
                setStyle("-fx-background-color: #ffc0cb;");
            }
            }
        });
        });
        
        
        RepairTypeComboBox.getItems().addAll(FXCollections.observableArrayList("Vehicle", "Individual Part"));
        
        PartSelectComboBox.setItems(parts);
        PartSelectComboBox.setConverter(new StringConverter<Part>() {
            @Override
            public String toString(Part object) {
                return object.getName();
            }

            @Override
            public Part fromString(String string) {
                String name = String.valueOf(string);
                
                       int i = PartSelectComboBox.getSelectionModel().getSelectedIndex();  
                       return parts.get(i);
            }
            
        });
        
        CentreSelectComboBox.setItems(centres);
        
        CentreSelectComboBox.setConverter(new StringConverter<SpecialistRepairCentre>() {
            @Override
            public String toString(SpecialistRepairCentre object) {
                return object.getName();
            }

            @Override
            public SpecialistRepairCentre fromString(String string) {
                String name = String.valueOf(string);
                return Database.getInstance().getCentre("select * from 'specialist_centre' where name = '"+ name + "'");
            }

            
        });
        
        PartSelectComboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                
                int i = PartSelectComboBox.getSelectionModel().getSelectedIndex();
                int part = parts.get(i).getID();
                
                PartRepair rep = Database.getInstance().getPartRepair(part, bID);
                if(rep!=null)
                {
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Specialist Repair Update");
                        alert.setContentText("This part was previously selected as a replacement part for normal repairs. If you proceed, the normal repair will be removed and the original part will proceed for specialist repairs!");
                        alert.showAndWait();
                }
            }
          });
        
        //PartSelectComboBox.getItems().addAll(FXCollections.observableArrayList("Part 1", "Part 2"));
        //PartSelectComboBox.getSelectionModel().selectFirst();
        
        //CentreSelectComboBox.getItems().addAll(centres);   CHANGED THIS
        //CentreSelectComboBox.getSelectionModel().selectFirst();
        
        //int customerID = CentreSelectComboBox.getValue().getId();
        
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                
                PartRepair partRep = null;

                int j = CentreSelectComboBox.getSelectionModel().getSelectedIndex();
                int i = PartSelectComboBox.getSelectionModel().getSelectedIndex();
                
                if(i==-1 || j==-1 || RepairTypeComboBox.getSelectionModel().getSelectedIndex()==-1 || DeliveryDatePicker.getValue()==null || ReturnDatePicker.getValue()==null){
                    
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Selection Error");
                    alert.setContentText("Please select all fields!");
                    alert.showAndWait();
                
                }
                
                else
                {
                
                int part = parts.get(i).getID();
                
                int stock = parts.get(i).getStock();
                
                String delDate = DeliveryDatePicker.getValue().toString();
                String retDate = ReturnDatePicker.getValue().toString();
                
                partRep = Database.getInstance().getPartRepair(part, bID);

                if(partRep!=null)
                { 
                        Database.getInstance().deletePartRepair(part, bID);
                        Database.getInstance().editStock(stock, part);     
                }
                
                String repairType = RepairTypeComboBox.getValue().toString();
                
                //int i = PartSelectComboBox.getSelectionModel().getSelectedIndex();
                //int part = parts.get(i).getID();
                
                double cost = parts.get(i).getSpecialistCost();
                 
                int centreID = centres.get(j).getId();
                         
               
                    SpecialistRepair repair = new SpecialistRepair(bID, cID, vID, centreID, repairType, part, delDate, retDate, "unfinished", cost);
                    Database.getInstance().addSpcBooking(repair);
                    S.loadTable();
                    Stage stage = (Stage) addButton.getScene().getWindow();
                    stage.close();
     
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
    
    public void displayDetails(Booking booking) {
        this.booking = booking;
        bID = booking.getBookingID();
        cID = booking.getCustomerID();
        vID = booking.getVehicleID();
        //BookingIdTextField.setText(String.valueOf(bID));
        //CustomerIdTextField.setText(String.valueOf(cID));
    }
    
}
