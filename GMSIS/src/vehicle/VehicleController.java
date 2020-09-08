/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vehicle;

import parts.*;

import Database.Database;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.stage.*;
import customer.*;
import authentication.*;
import booking.Booking;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
//import vehicle.AddVehicleController;
import vehicle.Vehicle;
//import vehicle.EditVehicleFXMLController;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

/**
 * FXML Controller class
 *
 * @author Harri
 */
public class VehicleController implements Initializable {
    Vehicle vehicle;
    //General
    @FXML private Button findVehicleButton;
    @FXML private Button addVehicleButton;
    @FXML private Button deleteVehicleButton;
    @FXML private Button editVehicleButton;
    @FXML private TextField searchTextField;
        @FXML private Button backButton;
 
    
    //Customer full_name,address,postcode,phone,email,type
    @FXML private TextField  customerNameTextField;
    @FXML private TextField  customerAddressTextField;
    @FXML private TextField  customerPostcodeTextField;    
    @FXML private TextField  customerEmailTextField;
    @FXML private TextField  customerPhoneTextField;
    @FXML private TextField  customerTypeTextField;
      
    //Bookings
    private String nextBookingDate;
    @FXML private TextField  nextBookingDateTextField;
    @FXML private TextField  EnterFutureBookingDate;
    @FXML private Button futureBookingsButton;
    @FXML private Button pastBookingsButton;
    @FXML private Button allBookingsButton;
    
    //Warranty
    @FXML private TextField nameOfWarrantyCompanyTextField;
    @FXML private TextField  addressOfWarrantyCompanyTextField;
    @FXML private TextField expiryDateOfWarrantyCompanyTextField;
    @FXML private TextField infoWarrantyTextField;

    //Booking and parts tables lists
    int bookflag = 0;
    @FXML public TableView<Vehicle> tableView = new TableView<Vehicle>();
    private ObservableList<Vehicle> vehicles = FXCollections.observableArrayList((Database.getInstance()).getVehicles());
    private ObservableList<Booking> futureBookings ;// FXCollections.observableArrayList((Database.getInstance()).getFutureBookingsandVehicle(vehicle));
    private ObservableList<Booking> pastBookings;// = FXCollections.observableArrayList((Database.getInstance()).getFutureBookingsandVehicle(vehicle));
 
    
  //TABLES ON MAIN PAGE
     @FXML public TableView<Vehicle> futureBookingsTableView = new TableView<Vehicle>();
     @FXML public TableView<Booking> bookingsTableView = new TableView<Booking>();
     @FXML public TableView<Part> partsTableView = new TableView<Part>();
     
     int bookingID;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) { //intialise data from database
        //VEHICLE TABLE COLUMNS-----------------------------------
        TableColumn vehicleIDColumn = new TableColumn("VehicleID");
        vehicleIDColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleID"));

        TableColumn customerColumn = new TableColumn("CustomerID");
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));  
        
        TableColumn vehicleTypeColumn = new TableColumn("VehicleType");
        vehicleTypeColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
        
        TableColumn registrationNumberColumn = new TableColumn("RegistrationNumber");
        registrationNumberColumn.setCellValueFactory(new PropertyValueFactory<>("registrationNumber"));
        
        TableColumn modelColumn = new TableColumn("Model");
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        
        TableColumn makeColumn = new TableColumn("Make");
        makeColumn.setCellValueFactory(new PropertyValueFactory<>("make"));
        
        TableColumn engineSizeColumn = new TableColumn("EngineSize");
        engineSizeColumn.setCellValueFactory(new PropertyValueFactory<>("engineSize"));
        
        TableColumn fuelTypeColumn = new TableColumn("FuelType");
        fuelTypeColumn.setCellValueFactory(new PropertyValueFactory<>("fuelType"));
        
        TableColumn colourColumn = new TableColumn("Colour");
        colourColumn.setCellValueFactory(new PropertyValueFactory<>("colour"));
        
        TableColumn motRenewalDateColumn = new TableColumn("MotRenewalDate");
        motRenewalDateColumn.setCellValueFactory(new PropertyValueFactory<>("motRenewalDate"));
        
        TableColumn currentMileageColumn = new TableColumn("CurrentMileage");
        currentMileageColumn.setCellValueFactory(new PropertyValueFactory<>("currentMileage"));
        
        TableColumn lastServiceDateColumn = new TableColumn("LastServiceDate");
        lastServiceDateColumn.setCellValueFactory(new PropertyValueFactory<>("lastServiceDate"));
        
        TableColumn warrantyColumn = new TableColumn("Warranty");
        warrantyColumn.setCellValueFactory(new PropertyValueFactory<>("warranty"));
       
        TableColumn nameOfWarrantyCompanyColumn = new TableColumn("NameOfWarrantyCompany");
        nameOfWarrantyCompanyColumn.setCellValueFactory(new PropertyValueFactory<>("nameOfWarrantyCompany"));
        
        TableColumn addressOfWarrantyCompanyColumn = new TableColumn("AddressOfWarrantyCompany");
        addressOfWarrantyCompanyColumn.setCellValueFactory(new PropertyValueFactory<>("addressOfWarrantyCompany"));        
        
        TableColumn expiryDateOfWarrantyCompanyColumn = new TableColumn("ExpiryDateOfWarrantyCompany");
        expiryDateOfWarrantyCompanyColumn.setCellValueFactory(new PropertyValueFactory<>("expiryDateOfWarrantyCompany"));
        
  
        
        tableView.setEditable(true);
        tableView.getColumns().addAll(vehicleIDColumn, customerColumn, vehicleTypeColumn, registrationNumberColumn, modelColumn, makeColumn, engineSizeColumn, fuelTypeColumn, colourColumn, motRenewalDateColumn, currentMileageColumn, lastServiceDateColumn, warrantyColumn, nameOfWarrantyCompanyColumn, addressOfWarrantyCompanyColumn, expiryDateOfWarrantyCompanyColumn);
        loadTable();       
        
        
        deleteVehicleButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                deleteVehicle();
                editVehicleButton.setDisable(true);
                deleteVehicleButton.setDisable(true);
            }
        });
        
        editVehicleButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                 editVehicle();
                         editVehicleButton.setDisable(true);
                        deleteVehicleButton.setDisable(true);
            }
        });
        
        addVehicleButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                addVehicle();
                editVehicleButton.setDisable(true);
                deleteVehicleButton.setDisable(true);
            }
        });
        
        findVehicleButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                search();
            }    
        });
        
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
           searchTextField.setText("");
          String query = "";
          vehicles = FXCollections.observableArrayList((Database.getInstance()).searchVehicles(query));        
          tableView.setItems(vehicles);
            }    
        });
        
        allBookingsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                loadBookingsTable(vehicle);
            }
        });
        
        futureBookingsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
         futureBookings = FXCollections.observableArrayList((Database.getInstance()).getFutureBookingsandVehicle(vehicle));//gets all past bookings from database        
         updateFutureBookings();// gets past bookings for specified vehicleID
         bookingsTableView.setItems(futureBookings);

         }
        });
        
        pastBookingsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
         pastBookings = FXCollections.observableArrayList((Database.getInstance()).getPastBookingsandVehicle(vehicle));//gets all past bookings from database        
         updatePastBookings();// gets past bookings for specified vehicleID
         bookingsTableView.setItems(pastBookings);
         }      
        });
 
    } 
    
    public void updatePastBookings(){//updates past bookings to chosen vehicle
             ObservableList<Booking> newPastBookings1 = pastBookings;
        //attempt to create new list which stores bookings with vehicle id = vehicle.vehicleid of selected vehicle
        int listSize = pastBookings.size(); 
          for (int i = listSize - 1; i>=0 ; i--) {
           int vid = newPastBookings1.get(i).getVehicleID();  
            if ( vid != vehicle.getVehicleID())
             {
               pastBookings.remove(i);
             }
          }
     }
    
     public void updateFutureBookings(){//updates future bookings to chosen vehicle
            ObservableList<Booking> newFutureBookings1 = futureBookings;
        //attempt to create new list which stores bookings with vehicle id = vehicle.vehicleid of selected vehicle
        int listSize = futureBookings.size();
         for (int i =listSize - 1 ; i >=0; i--) {
           int vid = newFutureBookings1.get(i).getVehicleID();  
            if ( vid != vehicle.getVehicleID())
             {
               futureBookings.remove(i);
             }
           
          }
      }
    
    public void clickedRow(){//Once a row is clicked...
        editVehicleButton.setDisable(false);//enable edit button
        deleteVehicleButton.setDisable(false);//enable delete button
        vehicle  = tableView.getSelectionModel().getSelectedItem();// make vehicle = to chosen vehicle in list
        
        loadBookingsTable(vehicle);//loads bookings in bookings table for chosen vehicle
        getCustomer();//gets customer details for chosen vehicle
        loadPartsTable(vehicle);//loads parts in parts table for chosen vehicle
         getNextBookingDate(); //GETS NEXT BOOKINGS DATE 
         
         //WARRANTY
         if( vehicle.getWarranty() == 1)
         {
             infoWarrantyTextField.setText("This vehicle has a warranty");
             nameOfWarrantyCompanyTextField.setText(vehicle.getNameOfWarrantyCompany());
             addressOfWarrantyCompanyTextField.setText(vehicle.getAddressOfWarrantyCompany());
             expiryDateOfWarrantyCompanyTextField.setText(vehicle.getExpiryDateOfWarrantyCompany());
         
         }
         else
         {
             infoWarrantyTextField.setText("This vehicle does not have a warranty");
             nameOfWarrantyCompanyTextField.setText("");
             addressOfWarrantyCompanyTextField.setText("");
             expiryDateOfWarrantyCompanyTextField.setText("");
         
         }

         if(bookflag == 0){ //flag prevents columns from being added again and again after being added for the first time
             bookflag = 1;
         //BOOKINGS TABLE COLUMNS-----------------------------------------
           TableColumn bookingIDColumn = new TableColumn("BookingID");
        bookingIDColumn.setCellValueFactory(new PropertyValueFactory<>("bookingID"));   

                        TableColumn customerIDColumn = new TableColumn("CustomerID");
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));   
        
                TableColumn vehicleIDColumn = new TableColumn("VehicleID");
        vehicleIDColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleID"));

                TableColumn employeeIDColumn = new TableColumn("EmployeeID");
        employeeIDColumn.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
        
                 TableColumn bookingTypeColumn = new TableColumn("BookingType");
        bookingTypeColumn.setCellValueFactory(new PropertyValueFactory<>("bookingType"));
        
                 TableColumn returnDateColumn = new TableColumn("ReturnDate");
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

                 TableColumn bookingDateColumn = new TableColumn("BookingDateD");
        bookingDateColumn.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));

                 TableColumn bookingTimeColumn = new TableColumn("BookingTime");
        bookingTimeColumn.setCellValueFactory(new PropertyValueFactory<>("bookingTime"));

                 TableColumn hoursWorkedColumn = new TableColumn("HoursWorked");
        hoursWorkedColumn.setCellValueFactory(new PropertyValueFactory<>("hoursWorked"));

                 TableColumn amountPaidColumn = new TableColumn("AmountPaid");
        amountPaidColumn.setCellValueFactory(new PropertyValueFactory<>("amountPaid"));        

        
        TableColumn bookingDatesColumn = new TableColumn("BookingDates");
        bookingDatesColumn.setCellValueFactory(new PropertyValueFactory<>("bookingDates")); 

        
        bookingsTableView.setEditable(true);
        bookingsTableView.getColumns().addAll(vehicleIDColumn, bookingIDColumn, bookingTypeColumn, bookingDateColumn, returnDateColumn, bookingTimeColumn, amountPaidColumn);

        //-----------------------
        //PARTS TABLE COLUMNS---------------------------------------------
                  TableColumn partNameColumn = new TableColumn("PartName");
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));

                 TableColumn partDescriptionColumn = new TableColumn("PartDescription");
        partDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));

                 TableColumn costColumn = new TableColumn("Cost");
        costColumn.setCellValueFactory(new PropertyValueFactory<>("Cost"));        

        
        TableColumn scostColumn = new TableColumn("Scost");
        scostColumn.setCellValueFactory(new PropertyValueFactory<>("SpecialistCost"));        
        
          partsTableView.setEditable(true);
        partsTableView.getColumns().addAll(partNameColumn, partDescriptionColumn, costColumn, scostColumn);
       //---------------------------------
         
         }    
    
   }

    public void getCustomer()//gets customer details for chosen vehicle
    {
     int CID = tableView.getSelectionModel().getSelectedItem().getCustomerID();
              
     Customer customer =((Database.getInstance()).getCustomer(CID)); 
     customerNameTextField.setText( customer.getFullName());
     customerAddressTextField.setText( customer.getAddress());
     customerPostcodeTextField.setText( customer.getPostcode());
     customerPhoneTextField.setText("0" + customer.getPhoneNumber());
     customerEmailTextField.setText( customer.getEmail());    
     
     CustomerType type = customer.getCustomerType();
     String Ctype;
      if(type.equals(CustomerType.Business)) {
        Ctype = "Business";
      }else{
        Ctype = "Private";
      }
    
         customerTypeTextField.setText(Ctype);
    }
    
    public void loadBookingsTable(Vehicle vehicle1){
        ObservableList<Booking> bookings = FXCollections.observableArrayList((Database.getInstance()).getBookingsForVehicle(vehicle1));  
        bookings = FXCollections.observableArrayList((Database.getInstance()).getBookingsForVehicle(vehicle1));        
        bookingsTableView.setItems(bookings); 
     }
    
    public void loadPartsTable(Vehicle vehicle1){//LOADS PARTS TABLE BASED ON CHOSEN VEHICLE
           ObservableList<Part> partsList = FXCollections.observableArrayList((Database.getInstance()).getPartsForVehicle(vehicle1));  
     partsTableView.setItems(partsList);
    }
    
    public void loadTable() {//LOADS THE VEHICLE TABLE
        vehicles = FXCollections.observableArrayList((Database.getInstance()).getVehicles());        
        tableView.setItems(vehicles);
    }
    
    public void search() {//SEARCHES USING QUERY
        String query = searchTextField.getText();
        vehicles = FXCollections.observableArrayList((Database.getInstance()).searchVehicles(query));        
        tableView.setItems(vehicles);
    }
    
    
    
        public void addVehicle() {//METHOD USED TO ADD VEHICLE TO DATABASE
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vehicle/AddVehicle.fxml"));
            Pane page = (Pane) loader.load();
            
            AddVehicleController addV = (AddVehicleController) loader.getController();
            addV.v = this;
            
            Stage stage = new Stage();
            Scene scene = new Scene(page);
            stage.setScene(scene);
            stage.setTitle("Add Vehicle");
            stage.show();
            
        } catch (Exception ex) {
            Logger.getLogger(VehicleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    public void deleteVehicle() {   //DELETE CHOSEN VEHICLE AFTER SHOWING CONFIRMATION ALERT   
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText("Are you sure you want to delete this Vehicle Record?");
        alert.setContentText("This will permenantly delete this Vehicle Record from the database.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
          int chosenVehicleRecord = tableView.getSelectionModel().getSelectedIndex();
        if(chosenVehicleRecord >= 0) {
            Vehicle v = vehicles.get(chosenVehicleRecord);
            Database.getInstance().deleteVehicle(v);
            loadTable();
         }
        }
    }
    
    public void getNextBookingDate()
    { 
       //GET NEXT BOOKING DATE
             ObservableList<Booking> futureBks= FXCollections.observableArrayList((Database.getInstance()).getFutureBookingsandVehicle(vehicle));//gets all past bookings from database        
             ObservableList<Booking> newFutureBookings1 = futureBks;
        //attempt to create new list which stores bookings with vehicle id = vehicle.vehicleid of selected vehicle
        int listSize = futureBks.size();
         for (int i =listSize - 1 ; i >=0; i--) {
           int vid = newFutureBookings1.get(i).getVehicleID();  
            if ( vid != vehicle.getVehicleID())
             {
               futureBks.remove(i);
             } 
          }
  
        if( futureBks.size() > 0){
         nextBookingDate = futureBks.get(0).getBookingDate();
         nextBookingDateTextField.setText(nextBookingDate);
        }
        else{
             nextBookingDateTextField.setText("No Next Booking Date");
        }
    }
    
    
        public void editVehicle() {             
               int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0) {
            Vehicle vehicle = tableView.getSelectionModel().getSelectedItem();//tableView.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/vehicle/EditVehicle.fxml"));
                AnchorPane page = (AnchorPane) loader.load();
                EditVehicleController editV = (EditVehicleController) loader.getController();
                editV.v = this;
                editV.loadVehicle(vehicle);
                Stage stage = new Stage();
                Scene scene = new Scene(page);
                stage.setScene(scene);
                stage.setTitle("Edit Vehicle");
                stage.show();
            } catch (Exception ex) {
                Logger.getLogger(VehicleController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                
        }
    } 
    
   
    

