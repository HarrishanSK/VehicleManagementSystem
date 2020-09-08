/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customer;

import java.net.URL;
import java.util.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.event.*;
import Database.*;
import authentication.AuthenticationFXMLController;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.*;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import booking.*;
import java.awt.event.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseButton;
import vehicle.*;

/**
 * FXML Controller class
 *
 * @author matteo
 */
public class CustomerFXMLController implements Initializable {

    @FXML private Button deleteButton;
    @FXML private Button addButton;
    @FXML private Button editButton;
    
    @FXML private Button billButton;
    
    @FXML private Button searchButton;
    @FXML private TextField searchTextField;
    @FXML private ChoiceBox typeBox;
    
    @FXML public TableView<Booking> bookingTable = new TableView<Booking>();
    private ObservableList<Booking> bookings = FXCollections.observableArrayList();
    
    @FXML public TableView<Vehicle> vehicleTable = new TableView<Vehicle>();
    private ObservableList<Vehicle> vehicles = FXCollections.observableArrayList();
    
    @FXML public TableView<Customer> tableView = new TableView<Customer>();
    private ObservableList<Customer> customers = FXCollections.observableArrayList((Database.getInstance()).getCustomers());
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setChoiceBox();
        setCustomerTable();
        setBookingTable();
        setVehicleTable();
        loadTable();       
        setButtons();
    }  
    
    public void setButtons() {
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                askDelete();
            }
        });
        
        editButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                editCustomer();
            }
        });
        
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                addCustomer();
            }
        });
        
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                search();
            }
        });
    }
    
    public void customerSelected() {
        Customer customer = tableView.getSelectionModel().getSelectedItem();
        
        bookings = FXCollections.observableArrayList((Database.getInstance()).getBookingsForCustomer(customer));        
        bookingTable.setItems(bookings);
        
        vehicles = FXCollections.observableArrayList((Database.getInstance()).getVehiclesForCustomer(customer.getId()));
        vehicleTable.setItems(vehicles);
    }
    
    public void setChoiceBox() {
        typeBox.getItems().addAll(FXCollections.observableArrayList("All", "Private", "Business"));
        typeBox.getSelectionModel().selectFirst();        
    }
    
    public void setBookingTable() {
        TableColumn typeC = new TableColumn("Type");
        typeC.setCellValueFactory(new PropertyValueFactory<>("bookingType"));
        TableColumn dateC = new TableColumn("Date");
        dateC.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));
        TableColumn paidC = new TableColumn("Settlement Status");
        paidC.setCellValueFactory(new PropertyValueFactory<>("isSettled"));
        
        bookingTable.setEditable(true);
        bookingTable.getColumns().addAll(typeC,dateC,paidC);
    }
    
    public void setVehicleTable() {
        TableColumn regno = new TableColumn("Registration Number");
        regno.setCellValueFactory(new PropertyValueFactory<>("registrationNumber"));
        
        TableColumn make = new TableColumn("Make");
        make.setCellValueFactory(new PropertyValueFactory<>("make"));
        
        TableColumn model = new TableColumn("Model");
        model.setCellValueFactory(new PropertyValueFactory<>("model"));
        
        vehicleTable.setEditable(true);
        vehicleTable.getColumns().addAll(regno,make,model);
    }
    
    public void setCustomerTable() {
        TableColumn fullNameColumn = new TableColumn("Name");
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        
        TableColumn addressColumn = new TableColumn("Address");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        
        TableColumn postcodeColumn = new TableColumn("Postcode");
        postcodeColumn.setCellValueFactory(new PropertyValueFactory<>("postcode"));
        
        TableColumn phoneColumn = new TableColumn("Phone");
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        
        TableColumn emailColumn = new TableColumn("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        
        TableColumn typeColumn = new TableColumn("type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("customerType"));
        
        TableColumn settlementColumn = new TableColumn("Settled?");
        settlementColumn.setCellValueFactory(new PropertyValueFactory<>("isSettled"));
        
        tableView.setEditable(true);
        tableView.getColumns().addAll(settlementColumn, fullNameColumn, addressColumn, postcodeColumn, phoneColumn, emailColumn, typeColumn);
    }
    
    public void loadTable() {
        bookings = FXCollections.observableArrayList();        
        bookingTable.setItems(bookings);
        vehicles = FXCollections.observableArrayList();        
        vehicleTable.setItems(vehicles);
        customers = FXCollections.observableArrayList((Database.getInstance()).getCustomers());        
        tableView.setItems(customers);
    }
    
    public void search() {
        String query = searchTextField.getText();
        String ct = (String) typeBox.getValue();
        if(ct.equals("All")) {
            customers = FXCollections.observableArrayList((Database.getInstance()).searchCustomers(query));        
            tableView.setItems(customers);
        } else if(ct.equals("Private")) {
            customers = FXCollections.observableArrayList((Database.getInstance()).searchCustomers(query, CustomerType.Private));        
            tableView.setItems(customers);
        }  else if(ct.equals("Business")) {
            customers = FXCollections.observableArrayList((Database.getInstance()).searchCustomers(query, CustomerType.Business));        
            tableView.setItems(customers);
        }
        //
        bookings = FXCollections.observableArrayList();        
        bookingTable.setItems(bookings);
    }
    
    
    public void editCustomer() {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0) {
            Customer customer = tableView.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/customer/EditCustomerFXML.fxml"));
                Pane page = (Pane) loader.load();
                EditCustomerFXMLController editC = (EditCustomerFXMLController) loader.getController();
                editC.c = this;
                editC.loadCustomer(customer);
                Stage stage = new Stage();
                Scene scene = new Scene(page);
                stage.setScene(scene);
                stage.setTitle("Edit Customer");
                stage.show();
            } catch (Exception ex) {
                Logger.getLogger(CustomerFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void askDelete() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete customer?");
        alert.setHeaderText("Delete a customer");
        alert.setContentText("Are you sure to delete this customer?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            deleteCustomer();
        } else {
            //
        }
    }
    
    public void deleteCustomer() {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0) {
            Customer customer = customers.get(selectedIndex);
            Database.getInstance().deleteCustomer(customer);
            loadTable();
        }
    }
    
    public void addCustomer() {
        try {
            //Pane page = (Pane) FXMLLoader.load(getClass().getResource("/customer/AddCustomerFXML.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/customer/AddCustomerFXML.fxml"));
            Pane page = (Pane) loader.load();
            
            AddCustomerFXMLController addC = (AddCustomerFXMLController) loader.getController();
            addC.c = this;
            
            Stage stage = new Stage();
            Scene scene = new Scene(page);
            stage.setScene(scene);
            stage.setTitle("Add Customer");
            stage.show();
            
        } catch (Exception ex) {
            Logger.getLogger(CustomerFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void showBill() {
        Booking booking = bookingTable.getSelectionModel().getSelectedItem();
        
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/customer/BillFXML.fxml"));
                AnchorPane page = (AnchorPane) loader.load();
                BillFXMLController b = (BillFXMLController) loader.getController();
                b.c = this;
                b.load(booking);
                Stage stage = new Stage();
                Scene scene = new Scene(page);
                stage.setScene(scene);
                stage.setTitle("Bill");
                stage.show();
            } catch (Exception ex) {
                Logger.getLogger(CustomerFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
    }
    
    public void addBooking() {
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/booking/addBooking.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            AddBookingController addB = (AddBookingController) loader.getController();
            addB.book = null;
            
            Stage stage = new Stage();
            Scene scene = new Scene(page);
            stage.setScene(scene);
            stage.setTitle("Add Booking");
            stage.show();
            
        } catch (Exception ex) {
            
            Logger.getLogger(BookingController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addVehicle() {
        Customer customer = tableView.getSelectionModel().getSelectedItem();
        if(customer == null) {
            return;
        }
        
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vehicle/AddVehicle.fxml"));
            Pane page = (Pane) loader.load();
            
            AddVehicleController addV = (AddVehicleController) loader.getController();
            addV.v = null;
            addV.loadFromCustomer(customer);
            
            Stage stage = new Stage();
            Scene scene = new Scene(page);
            stage.setScene(scene);
            stage.setTitle("Add Vehicle");
            stage.show();
            
        } catch (Exception ex) {
            
            Logger.getLogger(BookingController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}






