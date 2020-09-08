/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vehicle;

import Database.Database;
import booking.Booking;
import customer.Customer;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.collections.*;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.CheckBox;
import java.time.Instant;
import java.time.ZoneId;
import static java.time.temporal.TemporalQueries.localDate;
import java.util.Date;
import javafx.util.Callback;
import javafx.util.StringConverter;
import static org.joda.time.format.ISODateTimeFormat.date;
import java.lang.Object;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Region;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;
import javafx.util.converter.LocalDateStringConverter;
import java.time.*;
import java.util.Optional;

/**
 * FXML Controller class
 *
 * @author Harri
 */
public class AddVehicleController implements Initializable {
    
    public VehicleController v;
    @FXML private Button cancelButton;
    @FXML private Button addButton;
    
    @FXML private ChoiceBox vehicleTypeChoiceBox;
    @FXML private TextField resgistrationNumberTextField;
    @FXML private TextField modelTextField;
    @FXML private TextField makeTextField;
    @FXML private TextField engineSizeTextField;
    @FXML private TextField fuelTypeTextField;
    @FXML private TextField colourTextField;
    
    @FXML private TextField motRenewalDateTextField;
    @FXML private TextField currentMileageTextField;
    @FXML private TextField lastServiceDateField;
    @FXML private RadioButton warrantyRadioButton;
    @FXML private CheckBox warrantyCheckBox;
    @FXML private TextField customerTextField;
    
    
    @FXML private TextField nameOfWarrantyCompanyTextField;
    @FXML private TextField addressOfWarrantyCompanyTextField;
    @FXML private TextField expiryDateOfWarrantyTextField;
    
    @FXML private DatePicker MOTdatepicker;
        @FXML private DatePicker LSDdatepicker;
            @FXML private DatePicker EXPdatepicker;
                @FXML private TextField vehicleIDTextField;   
            
                        private ObservableList<VehicleTemplates> vehicleTemplates = FXCollections.observableArrayList((Database.getInstance()).getVehicleTemplates());  
            @FXML private Pane warrantyPane;
                @FXML private ChoiceBox<VehicleTemplates> vehicleTemplatesChoiceBox;
                   // private ObservableList<VehicleTemplates> vehicleTemplates = FXCollections.observableArrayList((Database.getInstance()).getVehicleTemplates());       
    
    /**
     * Initializes the controller class.
     */
   @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        warrantyPane.setVisible(false);
        vehicleTypeChoiceBox.getItems().addAll(FXCollections.observableArrayList("Car", "Van","Truck"));
       // vehicleTypeChoiceBox.getSelectionModel().selectFirst();
        
         //vehicles = FXCollections.observableArrayList((Database.getInstance()).getVehicleForCustomer(customerID));
          ObservableList<VehicleTemplates> vehicleTemplates = FXCollections.observableArrayList((Database.getInstance()).getVehicleTemplates());  
                vehicleTemplatesChoiceBox.setItems(vehicleTemplates);
                vehicleTemplatesChoiceBox.setConverter(new StringConverter<VehicleTemplates>() {
                    @Override
                    public String toString(VehicleTemplates object) {
                        return object.getMake() + "  " + object.getModel() + "  " + object.getEngineSize() + " " + object.getFuelType();
                    }

                    @Override
                    public VehicleTemplates fromString(String string) {
                        int userId = Integer.parseInt(string.split(": ")[0]);
                        return Database.getInstance().getVehicleTemplate(userId);
                    }

                });
                

                vehicleTemplatesChoiceBox.getSelectionModel().selectFirst();
            //}
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {//once the cancel button is clicked...close window
            @Override public void handle(ActionEvent e) {
                Stage stage = (Stage) addButton.getScene().getWindow();
                stage.close();
            }
        });           
                
        vehicleTemplatesChoiceBox.setOnAction(new EventHandler<ActionEvent>() {//once vehicle template is chosen
            @Override public void handle(ActionEvent e) {
                                int index = vehicleTemplatesChoiceBox.getSelectionModel().getSelectedIndex();
                VehicleTemplates vt = ((Database.getInstance()).getVehicleTemplate(index + 1)); 
                System.out.println(vt);
                
                makeTextField.setText(vt.getMake());
                modelTextField.setText(vt.getModel());
                engineSizeTextField.setText(vt.getEngineSize());
                fuelTypeTextField.setText(vt.getFuelType());
                
                System.out.println(vt.getVehicleType());
                VehicleType vehicleType = vt.getVehicleType();
                if(vehicleType.equals(VehicleType.Car)) {
                        vehicleTypeChoiceBox.getSelectionModel().select(0);
                    }else if(vehicleType.equals(VehicleType.Van)) {     
                        vehicleTypeChoiceBox.getSelectionModel().select(1);
                    }else if (vehicleType.equals(VehicleType.Truck)){
                        vehicleTypeChoiceBox.getSelectionModel().select(2);
                }
            }
        });

        
                warrantyCheckBox.setOnAction(new EventHandler<ActionEvent>() { //once add button is clicked ...
            @Override public void handle(ActionEvent e) {
                        warrantyPane.setVisible(true);
            }
            });
        
        addButton.setOnAction(new EventHandler<ActionEvent>() { //once add button is clicked ...
            @Override public void handle(ActionEvent e) {
                
               
                String registrationNumber = resgistrationNumberTextField.getText();
                String model = modelTextField.getText();
                String make = makeTextField.getText();
                String engineSize = engineSizeTextField.getText();
                String fuelType = fuelTypeTextField.getText();
                String colour = colourTextField.getText();
                String motRenewalDate = ""; 
                String currentMileageStr = "";
                int currentMileage = 0;
                String lastServiceDate = "";
                int warranty = 0;
                String customerIDstr = "";
                 int customerID = 0;
                   
               if(currentMileageTextField.getText().equals("") || MOTdatepicker.getValue() == null || LSDdatepicker.getValue() == null || customerTextField.getText().equals(""))
                {
                    alertFill("all");
                }
                else
                {
                    currentMileageStr = currentMileageTextField.getText();
                    currentMileage = Integer.parseInt(currentMileageStr);   
                    
                    motRenewalDate = MOTdatepicker.getValue().toString();
                    
                    lastServiceDate = LSDdatepicker.getValue().toString();
                    
                    customerIDstr = customerTextField.getText(); 
                    customerID = Integer.parseInt(customerIDstr);
                }
                
                String nameOfWarrantyCompany = " ";
                String addressOfWarrantyCompany =" ";
                String expiryDateOfWarrantyCompany = "";
                
                
                if(warrantyCheckBox.isSelected())// CHECK IF CHECKBOX IS SELECTED
                {
                    warranty = 1;  
                    System.out.println("TRUE : WARRANTY");
 
                     warrantyPane.setVisible(true);
                              if( EXPdatepicker.getValue() == null || nameOfWarrantyCompanyTextField.getText().equals("") || addressOfWarrantyCompanyTextField.getText().equals("")){
                                     alertFill("all warranty");
                                }
                                else
                                {
                                     nameOfWarrantyCompany = nameOfWarrantyCompanyTextField.getText();
                                     addressOfWarrantyCompany = addressOfWarrantyCompanyTextField.getText();
                                     expiryDateOfWarrantyCompany = EXPdatepicker.getValue().toString();//expiryDateOfWarrantyTextField.getText(); 
                               }
                    
                }
                else{
                    warranty = 0;
                    System.out.println("FALSE:  WARRANTY");
                }
                
                
                VehicleType vehicleType = VehicleType.Car;
                String vcl = (String) vehicleTypeChoiceBox.getValue();
                if(vcl.equals("Van")) {
                    vehicleType = VehicleType.Van;
                }
                if(vcl.equals("Truck"))
                {
                    vehicleType = VehicleType.Truck;
                }
                
                if(vehicleType.equals("") || registrationNumber.equals("") || model.equals("") || make.equals("") || engineSize.equals("") || colour.equals("") || motRenewalDate.equals("") || currentMileage <=0 || lastServiceDate.equals("") ) {
                    System.out.println("Not all fields are filled out");
                    //alertFill();
                } 
                else {
                    Vehicle vehicle = new Vehicle(vehicleType, registrationNumber, model, make, engineSize, fuelType, colour, motRenewalDate, currentMileage, lastServiceDate, warranty, nameOfWarrantyCompany, addressOfWarrantyCompany, expiryDateOfWarrantyCompany, customerID); 
                    Database.getInstance().addVehicle(vehicle);
                    v.loadTable();
                    Stage stage = (Stage) addButton.getScene().getWindow();
                    stage.close();
                }
            }
        });
    }    
    
    public void alertFill(String s)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Incomplete");
        alert.setHeaderText("Please fill out "+s+" the details!");
        alert.setContentText("...");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            
        }
    }
    
    public void loadFromCustomer(Customer customer) {
        customerTextField.setText(""+customer.getId());
    }
    
    public VehicleTemplates getTemplate(int index)
    {
        VehicleTemplates vt = null;
        
        if(vehicleTemplatesChoiceBox.getSelectionModel().isSelected(index)) {
             vt = ((Database.getInstance()).getVehicleTemplate(index + 1)); 
            }    
        else{
            
        }
        return vt;
        }

    }
    

