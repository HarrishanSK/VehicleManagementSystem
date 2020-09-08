/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package specialist;

import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import booking.*;

import java.net.URL;
import java.util.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import Database.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.*;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.time.LocalDate;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.util.StringConverter;
import parts.*;
import vehicle.*;
import authentication.*;
/**
 * FXML Controller class
 *
 * @author Pahel
 */
public class SpecialistController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML private Button addCentre;
    @FXML private Button editExistingCentre;
    @FXML private Button deleteExistingCentre;
    
    @FXML private Button search;
    @FXML private TextField searchQueryTextField;
    
    @FXML private TableColumn nameColumn;
    @FXML private TableColumn addressColumn;
    @FXML private TableColumn phoneColumn;
    @FXML private TableColumn emailColumn;
    
    
    //INDIVIDUAL REPAIRS COLUMNS
    @FXML private TableColumn regNoColumn;
    @FXML private TableColumn customerNameColumn;
    @FXML private TableColumn bookingIDColumn;
    @FXML private TableColumn centreIDColumn;
    @FXML private TableColumn partIDColumn;
    @FXML private TableColumn delDateColumn;
    @FXML private TableColumn retDateColumn;
    @FXML private TableColumn statusColumn;
    @FXML private TableColumn repairTypeColumn;
    
    //CENTRE REPAIR ASSIGNMENTS COLUMNS
    @FXML private TableColumn table2CustomerColumn;
    @FXML private TableColumn table2VehicleColumn;
    @FXML private TableColumn table2StatusColumn;
    @FXML private TableColumn table2PartColumn;
    
    //SEARCH TABLE COLUMNS
    @FXML private TableColumn searchCentreColumn;
    @FXML private TableColumn searchNameColumn;
    @FXML private TableColumn searchRegNoColumn;
    @FXML private TableColumn searchPartIdColumn;
    @FXML private TableColumn searchBookingIdColumn;
    
    //INDIVIDUAL REPAIR ATTRIBUTES
    @FXML private Button partRepairEditButton;
    @FXML private Button partRepairDeleteButton;
    @FXML private Button partRepairUpdateButton; 
    
    //VEHICLE REPAIRS COLUMNS & ATTRIBUTES
    @FXML private Button vehicleRepairSearchButton;
    @FXML private TextField vehicleIdTextfield;
    @FXML private Label vehicleIdLabel;
    
    @FXML private Button vehicleRepairEditButton;
    @FXML private Button vehicleRepairAddButton;
    @FXML private Button vehicleRepairDeleteButton;
    
    @FXML private TableColumn regNoVehicleColumn;
    @FXML private TableColumn customerNameVehicleColumn;
    @FXML private TableColumn bookingIDVehicleColumn;
    @FXML private TableColumn centreIDVehicleColumn;
    @FXML private TableColumn partIDVehicleColumn;
    @FXML private TableColumn statusVehicleColumn;
    
    //Centre Repair Page
    @FXML private Button display;
    @FXML private Button displayOutstanding;
    @FXML private Button displayFinished;
    @FXML private Button allCentre;
    @FXML private ComboBox whichCentre;
    @FXML private TextField rID4;
    @FXML private TextField bID4;
    @FXML private TextField pName4;
    @FXML private TextArea pDesc4;
    @FXML private TextField rCost4;
    @FXML private TextField cName4;
    @FXML private TextField rType4;
    @FXML private TextField rStatus4;
    @FXML private TextField vMake4;
    @FXML private TextField vModel4;
    @FXML private TextField dDate4;
    @FXML private TextField rDate4;

    //Repairs Page
    @FXML private Button makeSpecialistBookingButton;
    @FXML private Button searchButton;
    @FXML private TextField bookingIdTextfield;
    @FXML private Label bookingIdLabel;
    @FXML private TextField query;
    
    @FXML private TextField rID3;
    @FXML private TextField pName3;
    @FXML private TextArea pDesc3;
    @FXML private TextField rCost3;
    @FXML private TextField cName3;
    @FXML private TextField vMake3;
    @FXML private TextField vModel3;

    //Vehicle Repairs Page
    @FXML private TextField rID;
    @FXML private TextField pName;
    @FXML private TextArea pDesc;
    @FXML private TextField rCost;
    @FXML private TextField cName;
    @FXML private TextField rType;
    @FXML private TextField vMake;
    @FXML private TextField vModel;
    @FXML private TextField dDate;
    @FXML private TextField rDate;
    
    //Search Page
    @FXML private ComboBox searchCentre;    
    @FXML private TextField rID2;
    @FXML private TextField pName2;
    @FXML private TextArea pDesc2;
    @FXML private TextField rCost2;
    @FXML private TextField cName2;
    @FXML private TextField rType2;
    @FXML private TextField rStatus2;
    @FXML private TextField vMake2;
    @FXML private TextField vModel2;
    @FXML private TextField dDate2;
    @FXML private TextField rDate2;

    
    Alert alert;
        
    @FXML public TableView<SpecialistRepairCentre> tableView = new TableView<SpecialistRepairCentre>();
    private ObservableList<SpecialistRepairCentre> specialistCentres = FXCollections.observableArrayList((Database.getInstance()).getSpecialistCentres());
    
    @FXML public TableView<SpecialistRepair> repairsView;
    private ObservableList<SpecialistRepair> repairs = FXCollections.observableArrayList((Database.getInstance()).getSpcBookings("SELECT * FROM ('specialist_bookings' Inner Join 'customer' on specialist_bookings.customer_id = customer.customer_id) Inner Join 'vehicle' on specialist_bookings.vehicle_id=vehicle.vehicle_id"));  
    
    @FXML public TableView<SpecialistRepair> table2;
    private ObservableList<SpecialistRepair> repairs2;
    
    @FXML public TableView<SpecialistRepair> searchTable;
    private ObservableList<SpecialistRepair> searchRepairs;
    
    @FXML public TableView<SpecialistRepair> vehicleRepairsView;
    private ObservableList<SpecialistRepair> vehicleRepairs;  

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        //SPECIALIST CENTRES TABLE
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableView.setEditable(true);
        
        
        //SPECIALIST REPAIRS TABLE
        regNoColumn.setCellValueFactory(new PropertyValueFactory<>("RegNo"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("FullName"));
        bookingIDColumn.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        centreIDColumn.setCellValueFactory(new PropertyValueFactory<>("centreId"));
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("partsId"));
        delDateColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryDate"));
        retDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        repairTypeColumn.setCellValueFactory(new PropertyValueFactory<>("repairType"));
        repairsView.setEditable(true);
        
        
        
        //CENTRE REPAIRS TABLE
        table2CustomerColumn.setCellValueFactory(new PropertyValueFactory<>("FullName"));
        table2VehicleColumn.setCellValueFactory(new PropertyValueFactory<>("RegNo"));
        table2StatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        table2PartColumn.setCellValueFactory(new PropertyValueFactory<>("partsId"));
        table2.setEditable(true);
        
        
        //SEARCH RESULTS TABLE
        searchCentreColumn.setCellValueFactory(new PropertyValueFactory<>("centreId"));
        searchNameColumn.setCellValueFactory(new PropertyValueFactory<>("FullName"));
        searchRegNoColumn.setCellValueFactory(new PropertyValueFactory<>("RegNo"));
        searchPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("partsId"));
        searchBookingIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        searchTable.setEditable(true);
        
        //VEHICLE REPAIRS SEARCH
        centreIDVehicleColumn.setCellValueFactory(new PropertyValueFactory<>("centreId"));
        customerNameVehicleColumn.setCellValueFactory(new PropertyValueFactory<>("FullName"));
        regNoVehicleColumn.setCellValueFactory(new PropertyValueFactory<>("RegNo"));
        partIDVehicleColumn.setCellValueFactory(new PropertyValueFactory<>("partsId"));
        bookingIDVehicleColumn.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        statusVehicleColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        vehicleRepairsView.setEditable(true);
        
        loadTable();
        
        whichCentre.setItems(specialistCentres);
        searchCentre.setItems(specialistCentres);
        
        whichCentre.setConverter(new StringConverter<SpecialistRepairCentre>() {
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
        
        searchCentre.setConverter(new StringConverter<SpecialistRepairCentre>() {
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
        
        //Add change listener for Centre Repairs Page
        table2.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            //Check whether item is selected and set value of selected item to Label
            if (table2.getSelectionModel().getSelectedItem() != null) {
                
                int selectedIndex = table2.getSelectionModel().getSelectedIndex();
                SpecialistRepair repair = repairs2.get(selectedIndex);
                
                Part part = null;
                part = Database.getInstance().getPart(repair.getPartsId());
                
                SpecialistRepairCentre centre = Database.getInstance().getCentre("select * from 'specialist_centre' where centreId = '" + repair.getCentreId() + "'");
                Vehicle vehicle = Database.getInstance().getVehicle(repair.getVehicleId());
                        
                rID4.setText(String.valueOf(repair.getRepairId()));
                bID4.setText(String.valueOf(repair.getBookingId()));
                pName4.setText(part.getName());
                pDesc4.setText(part.getDescription());
                rCost4.setText(String.valueOf(repair.getRepairCost()));
                cName4.setText(centre.getName());
                rType4.setText(repair.getRepairType());
                rStatus4.setText(repair.getStatus());
                vMake4.setText(vehicle.getMake());
                vModel4.setText(vehicle.getModel());
                dDate4.setText(repair.getDeliveryDate());
                rDate4.setText(repair.getReturnDate());
                
            }
        });
        
        //Add change listener for Repairs Page
        repairsView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            //Check whether item is selected and set value of selected item to Label
            if (repairsView.getSelectionModel().getSelectedItem() != null) {
                
                int selectedIndex = repairsView.getSelectionModel().getSelectedIndex();
                SpecialistRepair repair = repairs.get(selectedIndex);
                
                Part part = null;
                part = Database.getInstance().getPart(repair.getPartsId());
                
                SpecialistRepairCentre centre = Database.getInstance().getCentre("select * from 'specialist_centre' where centreId = '" + repair.getCentreId() + "'");
                Vehicle vehicle = Database.getInstance().getVehicle(repair.getVehicleId());
                        
                rID3.setText(String.valueOf(repair.getRepairId()));
                pName3.setText(part.getName());
                pDesc3.setText(part.getDescription());
                rCost3.setText(String.valueOf(repair.getRepairCost()));
                cName3.setText(centre.getName());
                vMake3.setText(vehicle.getMake());
                vModel3.setText(vehicle.getModel());                
            }
        });
        
        //Add change listener for Vehicle Repairs Page
        vehicleRepairsView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            //Check whether item is selected and set value of selected item to Label
            if (vehicleRepairsView.getSelectionModel().getSelectedItem() != null) {
                
                int selectedIndex = vehicleRepairsView.getSelectionModel().getSelectedIndex();
                SpecialistRepair repair = vehicleRepairs.get(selectedIndex);
                
                Part part = null;
                part = Database.getInstance().getPart(repair.getPartsId());
                SpecialistRepairCentre centre = Database.getInstance().getCentre("select * from 'specialist_centre' where centreId = '" + repair.getCentreId() + "'");
                Vehicle vehicle = Database.getInstance().getVehicle(repair.getVehicleId());
                        
                rID.setText(String.valueOf(repair.getRepairId()));
                pName.setText(part.getName());
                pDesc.setText(part.getDescription());
                rCost.setText(String.valueOf(repair.getRepairCost()));
                cName.setText(centre.getName());
                rType.setText(repair.getRepairType());
                vMake.setText(vehicle.getMake());
                vModel.setText(vehicle.getModel());
                dDate.setText(repair.getDeliveryDate());
                rDate.setText(repair.getReturnDate());
                
            }
        });
        
        //Add change listener for Search Page
        searchTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            //Check whether item is selected and set value of selected item to Label
            if (searchTable.getSelectionModel().getSelectedItem() != null) {
                
                int selectedIndex = searchTable.getSelectionModel().getSelectedIndex();
                SpecialistRepair repair = searchRepairs.get(selectedIndex);
                
                Part part = null;
                part = Database.getInstance().getPart(repair.getPartsId());
                SpecialistRepairCentre centre = Database.getInstance().getCentre("select * from 'specialist_centre' where centreId = '" + repair.getCentreId() + "'");
                Vehicle vehicle = Database.getInstance().getVehicle(repair.getVehicleId());
                        
                rID2.setText(String.valueOf(repair.getRepairId()));
                pName2.setText(part.getName());
                pDesc2.setText(part.getDescription());
                rCost2.setText(String.valueOf(repair.getRepairCost()));
                cName2.setText(centre.getName());
                rType2.setText(repair.getRepairType());
                rStatus2.setText(repair.getStatus());
                vMake2.setText(vehicle.getMake());
                vModel2.setText(vehicle.getModel());
                dDate2.setText(repair.getDeliveryDate());
                rDate2.setText(repair.getReturnDate());
                
            }
        });
        
        addCentre.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                
                if(Authentication.isAdmin())
                {
                if(specialistCentres.size()<10)
                addSpecialistCentre();
                
                else
                {
                    showAlert("Only 10 centres allowed! Please delete one first.");
                }
                }
                else
                    showAlert("Only admins can add new specialist centres!");
            }
        });
        
        editExistingCentre.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if(Authentication.isAdmin())
                editCentre();
                
                else
                showAlert("Only admins can edit existing specialist centres!");
 
            }
        });
        
        deleteExistingCentre.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                
                if(Authentication.isAdmin())
                deleteCentre();  
                
                else
                showAlert("Only admins can delete existing specialist centres!");
     
            }
        });
        
        partRepairDeleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                
                partRepairDelete();               
     
            }
        });
        
        vehicleRepairAddButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                
                int selectedIndex = vehicleRepairsView.getSelectionModel().getSelectedIndex();
                if(selectedIndex >= 0) {
                    
                    SpecialistRepair repair = vehicleRepairs.get(selectedIndex);
                    
                    if(repair.getRepairType().equals("Vehicle"))
                    {
                    
                      
                      if(repair.getStatus().equals("unfinished"))
                      vehicleAddPart(repair);        
                      else
                      {
                          showAlert("Sorry, this repair is already complete!");
                      }
                   }
                    else
                    {
                        showAlert("Sorry, you can't add parts to an individual part repair!");
                    }
                }
                else
                {
                    showAlert("Please select a repair first!");
                }             
     
            }
        });
        
        //EDITING PART IN EXISTING INDIVIDUAL TYPE REPAIR
        partRepairEditButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                
                int selectedIndex = repairsView.getSelectionModel().getSelectedIndex();
                if(selectedIndex >= 0) {
                    
                      SpecialistRepair repair = repairs.get(selectedIndex);
                      if(repair.getStatus().equals("unfinished"))
                      editPart(repair);        
                      else
                      {
                          showAlert("Sorry, this repair is already complete!");
                      }
                }
                else
                {
                    showAlert("Please select a repair first!");
                }             
     
            }
        });
        
        //EDITING PART IN EXISTING VEHICLE TYPE REPAIR
        vehicleRepairEditButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                
                int selectedIndex = vehicleRepairsView.getSelectionModel().getSelectedIndex();
                if(selectedIndex >= 0) {
                    
                      SpecialistRepair repair = vehicleRepairs.get(selectedIndex);
                      if(repair.getStatus().equals("unfinished"))
                      vehicleEditPart(repair);        
                      else
                      {
                          showAlert("Sorry, this repair is already complete!");
                      }
                }
                else
                {
                    showAlert("Please select a repair first!");
                }             
     
            }
        });
        
        vehicleRepairDeleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                
                int selectedIndex = vehicleRepairsView.getSelectionModel().getSelectedIndex();
                if(selectedIndex >= 0) {
                    
                      SpecialistRepair repair = vehicleRepairs.get(selectedIndex);
                      String type=repair.getRepairType();
                      if(repair.getStatus().equals("unfinished"))
                      {
                          Alert alert = new Alert(AlertType.CONFIRMATION);
                          alert.setTitle("Delete Confirmation");
                          alert.setHeaderText("Delete Part");
                          if(type.equals("Vehicle"))
                            alert.setContentText("Are you sure you want to delete this part from the Vehicle repair?");
                          else
                            alert.setContentText("Are you sure you want to delete this Individual Part repair?");
                          
                          Optional<ButtonType> result = alert.showAndWait();
                          if(result.get( )== ButtonType.OK){
                          String query = "delete from 'specialist_bookings' where repairID = '"+ (repair.getRepairId()) + "'";
                          Database.getInstance().deleteSpcBooking(query);
                          loadTable();
            }
                      }       
                      else
                      {
                          showAlert("Sorry, completed repairs can't be edited!");
                      }
                }
                else
                {
                    showAlert("Please select a repair first!");
                }             
     
            }
        });
        
        makeSpecialistBookingButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                
                if(bookingIdTextfield.getText().equals(""))
                {
                    showAlert("Please enter booking id first!");
                }
                else
                {
                
                    int id = Integer.parseInt((bookingIdTextfield.getText()));
                    Booking booking = null;
                    booking = (Database.getInstance()).getBooking(id);
                    SpecialistRepair check = (Database.getInstance()).getSpcBooking("select * from ('specialist_bookings' Inner Join 'customer' on specialist_bookings.customer_id = customer.customer_id) Inner Join 'vehicle' on specialist_bookings.vehicle_id=vehicle.vehicle_id where booking_id = '" + id + "'");
                    if(check!=null)
                    {
                        showAlert("This id already has a specialist repair booking!");
                    }
                    else{
                        
                    if(booking==null)
                    {
                        showAlert("Your booking id doesn't exist!");
                    }
                    else if((booking.getBookingType()).equals("Diagnosis and Repair"))
                    {
                        String date=(LocalDate.now()).toString();
                        if(booking.getBookingDate().equals(date) || (booking.getBookingDate()).compareTo(date)<0)
                         makeBooking(booking);  
                        else
                        {
                            showAlert("Your diagnostic session is currently pending!");
                        }
                    }
                    else
                    {
                        showAlert("Sorry, your booking type is not 'Diagnosis & Repair'!");
                    }
                    }
                }
            }
        });
        
        partRepairUpdateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                
                    partRepairStatus();

            }
        });
        
        display.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                
                int i = whichCentre.getSelectionModel().getSelectedIndex();
                if(i!=-1)
                {
                int id1 = specialistCentres.get(i).getId();

                repairs2 = FXCollections.observableArrayList((Database.getInstance()).getSpcBookings("select * from ('specialist_bookings' Inner Join 'customer' on specialist_bookings.customer_id = customer.customer_id) Inner Join 'vehicle' on specialist_bookings.vehicle_id=vehicle.vehicle_id where centreID = '" + id1 + "'"));
                Collections.sort(repairs2, compare_byBookingID);
                table2.setItems(repairs2);
                }
                else
                {
                    showAlert("Please select a centre first!");
                }
                
            }
        });
        
        displayOutstanding.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                
                int i = whichCentre.getSelectionModel().getSelectedIndex();
                if(i!=-1)
                {
                int id1 = specialistCentres.get(i).getId();
                repairs2 = FXCollections.observableArrayList((Database.getInstance()).getSpcBookings("select * from ('specialist_bookings' Inner Join 'customer' on specialist_bookings.customer_id = customer.customer_id) Inner Join 'vehicle' on specialist_bookings.vehicle_id=vehicle.vehicle_id where centreID = '" + id1 + "' AND status = 'unfinished'"));
                Collections.sort(repairs2, compare_byBookingID);
                table2.setItems(repairs2);
            }
                else
                {
                    showAlert("Please select a centre first!");
                }
                
            }
        });
        
        displayFinished.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                
                int i = whichCentre.getSelectionModel().getSelectedIndex();
                if(i!=-1)
                {
                int id1 = specialistCentres.get(i).getId();
                repairs2 = FXCollections.observableArrayList((Database.getInstance()).getSpcBookings("select * from ('specialist_bookings' Inner Join 'customer' on specialist_bookings.customer_id = customer.customer_id) Inner Join 'vehicle' on specialist_bookings.vehicle_id=vehicle.vehicle_id where centreID = '" + id1 + "' AND status = 'complete'"));
                Collections.sort(repairs2, compare_byBookingID);
                table2.setItems(repairs2);
            }
                else
                {
                    showAlert("Please select a centre first!");
                }
                
            }
        });
        
        allCentre.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                
                repairs2 = FXCollections.observableArrayList((Database.getInstance()).getSpcBookings("select * from ('specialist_bookings' Inner Join 'customer' on specialist_bookings.customer_id = customer.customer_id) Inner Join 'vehicle' on specialist_bookings.vehicle_id=vehicle.vehicle_id where status = 'unfinished'"));
                Collections.sort(repairs2, compare_byBookingID);
                table2.setItems(repairs2);
 
            }
        });
        
        vehicleRepairSearchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                
                if(vehicleIdTextfield.getText().equals(""))
                {
                    showAlert("Please enter search query first!");
                }
                else{
                String searchQuery = vehicleIdTextfield.getText();
                vehicleRepairs = FXCollections.observableArrayList((Database.getInstance()).getSpcBookings("select * from 'specialist_bookings', "
                        + "'customer', 'vehicle' where specialist_bookings.customer_id=customer.customer_id and "
                        + "specialist_bookings.vehicle_id=vehicle.vehicle_id and (vehicle.registration_number like '%" + searchQuery + "%')"));  
                Collections.sort(vehicleRepairs, compare_byBookingID);
                vehicleRepairsView.setItems(vehicleRepairs);
                }
                
            }
        });
        
        search.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                
                if(searchQueryTextField.getText().equals(""))
                {
                    showAlert("Please enter search query first!");
                }
                else{
                    String searchQuery = searchQueryTextField.getText();
                    int i = searchCentre.getSelectionModel().getSelectedIndex();
                    if(i!=-1)
                    {
                        int id1 = specialistCentres.get(i).getId();

                        searchRepairs = FXCollections.observableArrayList((Database.getInstance()).getSpcBookings("select * from 'specialist_bookings', "
                        + "'customer', 'vehicle' where specialist_bookings.customer_id=customer.customer_id and "
                        + "specialist_bookings.vehicle_id=vehicle.vehicle_id and (customer.full_name like '%" + 
                        searchQuery + "%' or vehicle.registration_number like '%" + searchQuery + "%') and centreID = '" + id1 + "'"));
                        Collections.sort(searchRepairs, compare_byBookingID);
                        searchTable.setItems(searchRepairs);
                    }
                    else
                    {
                        searchRepairs = FXCollections.observableArrayList((Database.getInstance()).getSpcBookings("select * from 'specialist_bookings', "
                        + "'customer', 'vehicle' where specialist_bookings.customer_id=customer.customer_id and "
                        + "specialist_bookings.vehicle_id=vehicle.vehicle_id and (customer.full_name like '%" + 
                        searchQuery + "%' or vehicle.registration_number like '%" + searchQuery + "%')"));                    }
                        Collections.sort(searchRepairs, compare_byBookingID);
                        searchTable.setItems(searchRepairs);
                }
                
            }
        });
        
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                search();
            }
        });
        
        
    }   
    
    public void search() {

        String query1 = query.getText();
        repairs = FXCollections.observableArrayList((Database.getInstance()).getSpcBookings("select * from 'specialist_bookings', "
                        + "'customer', 'vehicle' where specialist_bookings.customer_id=customer.customer_id and "
                        + "specialist_bookings.vehicle_id=vehicle.vehicle_id and (customer.full_name like '%" + 
                        query1 + "%' or vehicle.registration_number like '%" + query1 + "%')")); 
        Collections.sort(repairs, compare_byBookingID);
        repairsView.setItems(repairs);
    }
    
    Comparator<? super SpecialistRepair> compare_byBookingID = new Comparator<SpecialistRepair>() {
        @Override
        public int compare(SpecialistRepair o1, SpecialistRepair o2) {
            return o1.getBookingId() - o2.getBookingId();
        }
    };
    
    public void loadTable() {
        
        specialistCentres = FXCollections.observableArrayList((Database.getInstance()).getSpecialistCentres());        
        tableView.setItems(specialistCentres);
        whichCentre.setItems(specialistCentres);
        searchCentre.setItems(specialistCentres);
        table2.getItems().clear();
        
        repairs = FXCollections.observableArrayList((Database.getInstance()).getSpcBookings("SELECT * FROM ('specialist_bookings' Inner Join 'customer' on specialist_bookings.customer_id = customer.customer_id) Inner Join 'vehicle' on specialist_bookings.vehicle_id=vehicle.vehicle_id"));        
        Collections.sort(repairs, compare_byBookingID);
        repairsView.setItems(repairs);
        
        searchTable.getItems().clear();
       
        String searchQuery = vehicleIdTextfield.getText();
        if(searchQuery.equals(""))
        {
            vehicleRepairsView.getItems().clear();
        }
        else{
        vehicleRepairs = FXCollections.observableArrayList((Database.getInstance()).getSpcBookings("select * from 'specialist_bookings', "
                        + "'customer', 'vehicle' where specialist_bookings.customer_id=customer.customer_id and "
                        + "specialist_bookings.vehicle_id=vehicle.vehicle_id and (vehicle.registration_number like '%" + searchQuery + "%')"));
        Collections.sort(vehicleRepairs, compare_byBookingID);
        vehicleRepairsView.setItems(vehicleRepairs);
        }

    }
    
    public void addSpecialistCentre() {
        
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/specialist/AddCentre.fxml"));
            Pane page = (Pane) loader.load();
            
            AddCentreController addC = (AddCentreController) loader.getController();
            addC.S = this;
            
            Stage stage = new Stage();
            Scene scene = new Scene(page);
            stage.setScene(scene);
            stage.setTitle("Add New Specialist Centre");
            stage.show();
            
        } catch (Exception ex) {
            Logger.getLogger(SpecialistController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void editCentre() {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0) {
            SpecialistRepairCentre centre = tableView.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/specialist/EditCentre.fxml"));
                Pane page = (Pane) loader.load();
                EditCentreController editC = (EditCentreController) loader.getController();
                editC.S = this;
                editC.displayCentre(centre);
                Stage stage = new Stage();
                Scene scene = new Scene(page);
                stage.setScene(scene);
                stage.setTitle("Edit Specialist Centre");
                stage.show();
            } catch (Exception ex) {
                Logger.getLogger(SpecialistController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        else
            {
                showAlert("Please select a centre first!");
            }
    }
    
    public void deleteCentre() {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Delete Confirmation");
            alert.setHeaderText("Delete Specialist Centre");
            alert.setContentText("Are you sure you want to delete this centre?");
                        
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get( )== ButtonType.OK){
            Database.getInstance().deleteSPC(specialistCentres.get(selectedIndex));
            String query = "delete from 'specialist_bookings' where centreID = '"+ (specialistCentres.get(selectedIndex).getId()) + "'";
            Database.getInstance().deleteSpcBooking(query);
            loadTable();
            }
        }
        else
            {
                showAlert("Please select a centre first!");
            }
    }
    
    public void makeBooking(Booking booking) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/specialist/MakeSpecialistBooking.fxml"));
                Pane page = (Pane) loader.load();
                MakeSpecialistBookingController editC = (MakeSpecialistBookingController) loader.getController();
                editC.S = this;
                editC.displayDetails(booking);
                Stage stage = new Stage();
                Scene scene = new Scene(page);
                stage.setScene(scene);
                stage.setTitle("Make Specialist Booking");
                stage.show();
            } catch (Exception ex) {
                Logger.getLogger(SpecialistController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
    public void partRepairDelete() {
        int selectedIndex = repairsView.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0) {
            SpecialistRepair repair = repairs.get(selectedIndex);
            String status = repair.getStatus();
            if(status.equalsIgnoreCase("unfinished"))
            {
                  Alert alert = new Alert(AlertType.CONFIRMATION);
                  alert.setTitle("Delete Confirmation");
                  alert.setHeaderText("Delete Repair");
                  if(repair.getRepairType().equals("Vehicle"))
                    alert.setContentText("All parts included in this Vehicle repair will be removed. Are you sure you want to delete this Vehicle repair?");
                  else
                    alert.setContentText("Are you sure you want to delete this Individual Part repair?");
                  Optional<ButtonType> result = alert.showAndWait();
                  if(result.get( )== ButtonType.OK){
                  //deletes booking from table whereever booking ID matches. Thus, individual will only have 1 occurrence but vehicle will have many same IDs and all will be removed.
                  String query = "delete from 'specialist_bookings' where booking_id = '"+ (repairs.get(selectedIndex).getBookingId()) + "'";
                  Database.getInstance().deleteSpcBooking(query);
                  loadTable();
                }
              
            }
            else
            {
                showAlert("Sorry, finished repairs can't be deleted!");
            }
        }
        else
            {
                showAlert("Please select a repair first!");
            }
    }
    
    public void partRepairStatus() {
        int selectedIndex = repairsView.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0) 
        {
            SpecialistRepair repair = repairs.get(selectedIndex);
            
            if(repair.getStatus().equals("unfinished"))
            {
            
               String delDate = repair.getDeliveryDate();
               String date = String.valueOf(LocalDate.now());
               if(delDate.compareTo(date)<=0)
               {
                   if(repair.getRepairType().equals("Individual Part"))
                   {
            
                      Alert alert = new Alert(AlertType.CONFIRMATION);
                      alert.setTitle("Status Confirmation");
                      alert.setHeaderText("Individual Part Repair Status");
                      alert.setContentText("Are you sure you want to mark this repair completed?");
                        
                      Optional<ButtonType> result = alert.showAndWait();
                      if(result.get( )== ButtonType.OK)
                      {
                          try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/specialist/charge.fxml"));
                                 Pane page = (Pane) loader.load();
                                chargeController editC = (chargeController) loader.getController();
                                editC.S = this;
                                editC.detail(repair);
                                Stage stage = new Stage();
                                Scene scene = new Scene(page);
                                stage.setScene(scene);
                                stage.setTitle("Specialist Centre Charge");
                                stage.show();
                                } catch (Exception ex) {
                                     Logger.getLogger(SpecialistController.class.getName()).log(Level.SEVERE, null, ex);
                                 }                          

                      }
                  }
                   else
                   {
                      Alert alert = new Alert(AlertType.CONFIRMATION);
                      alert.setTitle("Status Confirmation");
                      alert.setHeaderText("Vehicle Repair Status");
                      alert.setContentText("All parts present in this Vehicle repair will be marked complete. Are you sure you want to mark this repair as complete?");
                        
                      Optional<ButtonType> result = alert.showAndWait();
                      if(result.get( )== ButtonType.OK)
                      {
                          try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/specialist/charge.fxml"));
                                 Pane page = (Pane) loader.load();
                                chargeController editC = (chargeController) loader.getController();
                                editC.S = this;
                                editC.detail(repair);
                                Stage stage = new Stage();
                                Scene scene = new Scene(page);
                                stage.setScene(scene);
                                stage.setTitle("Specialist Centre Charge");
                                stage.show();
                                } catch (Exception ex) {
                                     Logger.getLogger(SpecialistController.class.getName()).log(Level.SEVERE, null, ex);
                                 } 
                      }
                   }
               }
               else
               {
                   showAlert("Repair not yet conducted!");
               }
            }
            else
            {
                showAlert("This repair is already completed!");
            }
        }
        else
            {
                
                showAlert("Please select a repair first!");
            }
       }
    
    public void vehicleAddPart(SpecialistRepair repair) {
            
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/specialist/AddPart.fxml"));
                Pane page = (Pane) loader.load();
                AddPartController editC = (AddPartController) loader.getController();
                editC.S = this;
                editC.bookingDetails(repair);
                Stage stage = new Stage();
                Scene scene = new Scene(page);
                stage.setScene(scene);
                stage.setTitle("Make Specialist Booking");
                stage.show();
            } catch (Exception ex) {
                Logger.getLogger(SpecialistController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
    public void vehicleEditPart(SpecialistRepair repair) {
            
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/specialist/EditPart.fxml"));
                Pane page = (Pane) loader.load();
                EditPartController editC = (EditPartController) loader.getController();
                editC.S = this;
                editC.bookingDetails(repair);
                Stage stage = new Stage();
                Scene scene = new Scene(page);
                stage.setScene(scene);
                stage.setTitle("Edit Part in Repair");
                stage.show();
            } catch (Exception ex) {
                Logger.getLogger(SpecialistController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
    public void editPart(SpecialistRepair repair) {
            
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/specialist/EditPart.fxml"));
                Pane page = (Pane) loader.load();
                EditPartController editC = (EditPartController) loader.getController();
                editC.S = this;
                editC.bookingDetails(repair);
                Stage stage = new Stage();
                Scene scene = new Scene(page);
                stage.setScene(scene);
                stage.setTitle("Edit Part in Repair");
                stage.show();
            } catch (Exception ex) {
                Logger.getLogger(SpecialistController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
            public void showAlert(String msg){
                
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setContentText(msg);
                    alert.showAndWait();
                
                
            }
            
   
    }

   
