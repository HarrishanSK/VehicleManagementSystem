/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parts;

/**
 *
 * @author Yachen
 */
import Database.Database;
import customer.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

import javafx.scene.control.TextField;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import vehicle.Vehicle;

public class PartsController implements Initializable {

    @FXML
    private Button searchButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField cost;

    @FXML
    private TextField scost;
    
    
    //part stock part detail:
    @FXML
    private TextField nameDe;
    
    @FXML
    private TextField costDe;
    
    @FXML
    private TextField scostDe;
    
    @FXML
    private TextArea descriptionDe;
    
    @FXML
    private TableColumn scostCol;
    
    @FXML
    private TextField stock;
    
    @FXML
    private TableColumn stockCol;

    @FXML
    private TextArea description;

    @FXML
    private Button addButton;

    @FXML
    private TableColumn costCol;

    @FXML
    private TableColumn descriptionCol;

    @FXML
    private Button requireButton;

    @FXML
    private TableColumn idCol;

    @FXML
    private TextField searchField;
    
    @FXML
    private Button editButton;
    
    @FXML
    private Button addStockButton;

    @FXML
    private TableColumn nameCol;

    @FXML
    private TextField name;
    
    @FXML
    private Label error;
    
    @FXML
    private Label error1;
    
    //part repair :
    @FXML
    private TextField searchVehicle;
    

    @FXML
    private Button search2;
    
    
    @FXML
    private TableColumn repairIDCol;
    
    @FXML
    private TableColumn bookingIDCol;
    
    @FXML
    private TableColumn vehicleIDCol;
    
    @FXML
    private TableColumn customerIDCol;
    
    @FXML
    private TableColumn partIDCol;
    
    @FXML
    private TableColumn installDateCol;
    
    @FXML
    private TableColumn expireDateCol;
    
    @FXML
    private Button deleteButton2;
    
    //part detail field
    @FXML 
    private TextField name2;
    
    @FXML 
    private TextArea describe2;
    
    @FXML 
    private TextField cost2;
    
    @FXML 
    private TextField scost2;
    
    //customer detail field
    @FXML 
    private TextField cusName;
    
    @FXML 
    private TextField cusAddress;
    
    @FXML 
    private TextField cusPhone;
    
    @FXML 
    private TextField cusEmail;
    
    
    //vehicle detail field
    
    @FXML 
    private TextField veNumber;
    
    @FXML 
    private TextField veModel;
    
    @FXML 
    private TextField veMake;
    
    @FXML 
    private TextField veEngine;
    
    //new part delivery
    
    @FXML
    private TableColumn date3;
    
    @FXML
    private TableColumn delivery3;
    
    @FXML
    private TableColumn description3;
    
    @FXML
    private TableColumn cost3;
    
    @FXML
    private TableColumn newStock3;
    
    @FXML 
    public TableView<Part> tableView = new TableView<Part>();
    public ObservableList<Part> partsList = FXCollections.observableArrayList((Database.getInstance()).getParts());
    
    @FXML 
    public TableView<PartRepair> tableView2 = new TableView<PartRepair>();
    public ObservableList<PartRepair> partsList2 = FXCollections.observableArrayList((Database.getInstance()).getPartRepairs(""));
    
    @FXML 
    public TableView<Part> tableView3 = new TableView<Part>();
    public ObservableList<Part> partsList3 = FXCollections.observableArrayList((Database.getInstance()).getPartDeliveries());
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
        costCol.setCellValueFactory(new PropertyValueFactory<>("Cost"));
        scostCol.setCellValueFactory(new PropertyValueFactory<>("specialistCost"));
        stockCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        tableView.setEditable(true);
        loadTable();
        
        repairIDCol.setCellValueFactory(new PropertyValueFactory<>("RepairID"));
        bookingIDCol.setCellValueFactory(new PropertyValueFactory<>("BookingID"));
        
        vehicleIDCol.setCellValueFactory(new PropertyValueFactory<>("RegistrationNumber"));
        
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("PartID"));
        installDateCol.setCellValueFactory(new PropertyValueFactory<>("InstallDate"));
        expireDateCol.setCellValueFactory(new PropertyValueFactory<>("ExpireDate"));
        
        
        tableView2.setEditable(true);
        loadTable2();
        date3.setCellValueFactory(new PropertyValueFactory<>("DeliveryDate"));
        delivery3.setCellValueFactory(new PropertyValueFactory<>("Name"));
        description3.setCellValueFactory(new PropertyValueFactory<>("Description"));
        cost3.setCellValueFactory(new PropertyValueFactory<>("Cost"));
        newStock3.setCellValueFactory(new PropertyValueFactory<>("StockChange"));
        
        tableView3.setEditable(false);
        loadTable3();

        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                deletePart();
            }
        });
        
        editButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                editPart();
            }
        });
        
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                addPart();
            }
        });
        
        addStockButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                
                addStock();
            }
        });
        
        requireButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent e) {
                requirePlacement();
            }
        });
        
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                String query = searchField.getText();
                partsList = FXCollections.observableArrayList((Database.getInstance()).searchPart(query));
                tableView.setItems(partsList);
            }
        });
        
        search2.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                //enter vehicle name
                String vehicleName = searchVehicle.getText();
                
                partsList2 = FXCollections.observableArrayList((Database.getInstance()).getPartRepairs(vehicleName));
                tableView2.setItems(partsList2);
            }
        });
        
        deleteButton2.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                deletePartRepair();
            }
        });

        tableView2.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            
            if (tableView2.getSelectionModel().getSelectedItem() != null) {
                
                try {
                    displayPart();
                } catch (SQLException ex) {
                    Logger.getLogger(PartsController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        
        tableView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            
            if (tableView.getSelectionModel().getSelectedItem() != null) {
                displayPartDetail();
            }
        });
        
    }

    public void loadTable() {
        partsList = FXCollections.observableArrayList((Database.getInstance()).getParts());        
        tableView.setItems(partsList);
    }
    
    public void loadTable2() {
        partsList2 = FXCollections.observableArrayList((Database.getInstance()).getPartRepairs(""));
        tableView2.setItems(partsList2);
    }
    
    public void loadTable3() {
        partsList3 = FXCollections.observableArrayList((Database.getInstance()).getPartDeliveries());
        tableView3.setItems(partsList3);
    }
    
    public void addPart() {
        if(name.getText().equals("")|| description.getText().equals("")|| cost.getText().equals("") ||scost.getText().equals("")){
            error.setText("Please fill in all the fields");
        }
        else{
             try{

                String part_name = name.getText();
                String part_description = description.getText(); 
                double part_cost = Double.parseDouble(cost.getText());
                double specialist_cost = Double.parseDouble(scost.getText());   
                int part_stock = Integer.parseInt(stock.getText());
                Part part = new Part(part_name, part_description, part_cost, specialist_cost, part_stock);
                Part partRecord = new Part(part_name, part_description, part_cost, part_stock);
                Database.getInstance().addPartDelivery(partRecord);
                Database.getInstance().addPart(part);
                loadTable();
                loadTable3();          
                }
        //alert window pops out when user misses any field
        catch(NumberFormatException e){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setHeaderText("WARNING");
            alert.setContentText("Please enter a number for cost/specialist cost/stock.");
            alert.showAndWait();
        }
        }
        
        
       
    }
    
   public void addStock() {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0) {
            Part part = tableView.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/parts/AddStock.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            AddStockController addStockCon = (AddStockController) loader.getController();
            addStockCon.partCon = this;
            addStockCon.loadPart(part);
            Stage stage = new Stage();
            Scene scene = new Scene(page);
            stage.setScene(scene);
            stage.setTitle("Add stock");
            stage.show();
            
        } catch (IOException ex) {
            Logger.getLogger(PartsController.class.getName()).log(Level.SEVERE, null, ex);
            }
       }

       
    }
        
    public void deletePart(){
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirm to delete");
            alert.setHeaderText("Delete");
            alert.setContentText("Are you sure you want to delete this part?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get( )== ButtonType .OK){
                //user chose ok
                Database.getInstance().deletePart(partsList.get(selectedIndex)); 
                
                
                
                loadTable();
                loadTable3();
                loadTable2();
            }
            else{
                //user chose cancel or closed the dialog
            }
            
        }
    }
    
    public void editPart() {


        PartRepair partRe = tableView2.getSelectionModel().getSelectedItem();

        int partID = partRe.getPartID();
        Part part = Database.getInstance().getPart(partID);
        
        
        try{ 
            String part_name = name2.getText();
            String part_description = describe2.getText();
            Double part_cost = Double.parseDouble(cost2.getText());
            Double part_scost = Double.parseDouble(scost2.getText());
            //update new info
            part.setName(part_name);
            part.setDescription(part_description);
            part.setCost(part_cost);
            part.setSpecialistCost(part_scost);
            Database.getInstance().editPart(part);
            loadTable();
            Alert alert1 = new Alert(AlertType.INFORMATION);
            alert1.setTitle("Message");
            alert1.setContentText("The part detail has been updated!");
            alert1.showAndWait();
        }catch(NumberFormatException ee){
            error1.setText("Please enter a number for cost/specialist cost!");  
        }
        
    
    }
    
    public void requirePlacement() {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0) {
            Part part = tableView.getSelectionModel().getSelectedItem();
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/parts/RequirePlacement.fxml"));
                AnchorPane page = (AnchorPane) loader.load();
                RequirePlacementController requireCon = (RequirePlacementController) loader.getController();
                requireCon.partCon = this;
                requireCon.part = tableView.getSelectionModel().getSelectedItem();
                requireCon.loadPartID(part);
                Stage stage = new Stage();
                Scene scene = new Scene(page);
                stage.setScene(scene);
                stage.setTitle("Require part");
                stage.show();
                }
             catch (IOException ex) {
                Logger.getLogger(PartsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            Alert alert1 = new Alert(AlertType.INFORMATION);
            alert1.setTitle("Error");
            alert1.setContentText("Please select a part from the table");
            alert1.showAndWait();
        }
        
           
    }
    
    public void deletePartRepair(){
        int selectedIndex = tableView2.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirm to delete");
            alert.setHeaderText("Delete");
            alert.setContentText("Are you sure you want to delete this part repair booking?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get( )== ButtonType .OK){
                //user chose ok
                Database.getInstance().deletePartRepair(partsList2.get(selectedIndex));                
                loadTable2();
            }
            else{
                //user chose cancel or closed the dialog
            }
            
        }
    }
    
    public void displayPart() throws SQLException{
        //part details:
        
        PartRepair partRe = tableView2.getSelectionModel().getSelectedItem();

        int partID = partRe.getPartID();
        Part part = Database.getInstance().getPart(partID);
        
        name2.setText(part.getName());
        describe2.setText(part.getDescription());
        cost2.setText(Double.toString(part.getCost()));
        scost2.setText(Double.toString(part.getSpecialistCost()));

        //customer details:
        int bookingID = partRe.getBookingID();
        String customerID = Database.getInstance().getResult("select customer_id from 'booking' where booking_id = '"+bookingID+"'", "customer_id");
        Customer customer = Database.getInstance().getCustomer(Integer.parseInt(customerID));
        cusName.setText(customer.getFullName());
        
        cusAddress.setText(customer.getAddress());
        cusPhone.setText(customer.getPhoneNumber());
        cusEmail.setText(customer.getEmail());
             
        //vehicle details:
        String vehicleID = Database.getInstance().getResult("select vehicle_id from 'booking' where booking_id = '"+bookingID+"'", "vehicle_id");
        Vehicle vehicle = Database.getInstance().getVehicle(Integer.parseInt(vehicleID));
        veNumber.setText(vehicle.getRegistrationNumber());
        veModel.setText(vehicle.getModel());
        veMake.setText(vehicle.getMake());
        veEngine.setText(vehicle.getEngineSize());
        
    }
    
    public void displayPartDetail(){
        Part partDetail = tableView.getSelectionModel().getSelectedItem();
        nameDe.setText(partDetail.getName());
        costDe.setText(Double.toString(partDetail.getCost()));
        scostDe.setText(Double.toString(partDetail.getSpecialistCost()));
        descriptionDe.setText(partDetail.getDescription());
        
    }
    
}
    

