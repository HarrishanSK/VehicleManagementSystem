/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vehicle;
import Database.Database;
import customer.Customer;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.LocalDateStringConverter;
import java.time.*;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
/**
 * FXML Controller class
 *
 * @author Harri
 */
public class EditVehicleController implements Initializable {

    /**
     * Initializes the controller class.
     */
     VehicleController v;
     Vehicle vehicle;
    @FXML private Button cancelButton;
    @FXML private Button editButton;
    
    @FXML private ChoiceBox vehicleTemplatesChoiceBox;
    @FXML private ChoiceBox vehicleTypeChoiceBox;
    @FXML private TextField resgistrationNumberTextField;
    @FXML private TextField modelTextField;
    @FXML private TextField makeTextField;
    @FXML private TextField engineSizeTextField;
    @FXML private TextField fuelTypeTextField;
    @FXML private TextField colourTextField;
    
    @FXML private TextField motRenewalDateTextField;
    @FXML private TextField currentMileageTextField;
    @FXML private TextField lastServiceDateTextField;
    @FXML private RadioButton warrantyRadioButton;
    @FXML private CheckBox warrantyCheckBox;
    @FXML private TextField vehicleIDTextField;
    @FXML private TextField customerTextField;
    
    @FXML private TextField nameOfWarrantyCompanyTextField;
    @FXML private TextField addressOfWarrantyCompanyTextField;
    @FXML private TextField expiryDateOfWarrantyTextField;
    
        @FXML private TextField warrantyTextField;
    
    @FXML private DatePicker MOTdatepicker;
        @FXML private DatePicker LSDdatepicker;
            @FXML private DatePicker EXPdatepicker;
            
        private ObservableList<VehicleTemplates> vehicleTemplates = FXCollections.observableArrayList((Database.getInstance()).getVehicleTemplates());       
    //@FXML private ChoiceBox<Vehicle> vehicleTemplatesChoiceBox;
            
            @FXML private Pane warrantyPane;
               // @FXML private ChoiceBox vehicleIDChoiceBox;
                LocalDate  expd;
                LocalDate  lsd ;
                LocalDate  motd;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //warrantyPane.setVisible(false);
        vehicleTypeChoiceBox.getItems().addAll(FXCollections.observableArrayList("Car", "Van","Truck"));
        vehicleTypeChoiceBox.getSelectionModel().selectFirst();

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
        
        editButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                
                String registrationNumber = resgistrationNumberTextField.getText();
                String model = modelTextField.getText();
                String make = makeTextField.getText();
                String engineSize = engineSizeTextField.getText();
                String fuelType = fuelTypeTextField.getText();
                String colour = colourTextField.getText();
                String motRenewalDate = motRenewalDateTextField.getText(); 
                String currentMileageStr = "";
                int currentMileage = 0;
                String lastServiceDate = lastServiceDateTextField.getText(); 
                int warranty = 0;
                String customerIDstr = "";
                 int customerID = 0;
                String vehicleIDstr = "";
                 int vehicleID=0;
                 int preventFlag = 0;
                   
               if(currentMileageTextField.getText().equals("") || customerTextField.getText().equals(""))
                {
                    alertFill("all");
                }
                else
                {
                    currentMileageStr = currentMileageTextField.getText();
                    currentMileage = Integer.parseInt(currentMileageStr);   
                    
                    //motRenewalDate = MOTdatepicker.getValue().toString();
                    
                    //lastServiceDate = LSDdatepicker.getValue().toString();
                    
                    customerIDstr = customerTextField.getText(); 
                    customerID = Integer.parseInt(customerIDstr);
                    
                    vehicleIDstr = vehicleIDTextField.getText();
                    vehicleID = Integer.parseInt(vehicleIDstr);
                }
                
                String nameOfWarrantyCompany = " ";
                String addressOfWarrantyCompany =" ";
                String expiryDateOfWarrantyCompany = "";
                
                
                if(warrantyCheckBox.isSelected())// CHECK IF CHECKBOX IS SELECTED
                {
                    warranty = 1;  
                    System.out.println("TRUE : WARRANTY");
 
                     warrantyPane.setVisible(true);
                              if( expiryDateOfWarrantyTextField.getText().equals(" ")|| nameOfWarrantyCompanyTextField.getText().equals(" ") || addressOfWarrantyCompanyTextField.getText().equals(" ")){
                                   preventFlag=1;   // preventFlag is used to prevent code from finishing form after showing alert message
                                  alertFill(" all warranty ");
                                }
                                else
                                {
                                    preventFlag = 0;
                                     nameOfWarrantyCompany = nameOfWarrantyCompanyTextField.getText();
                                     addressOfWarrantyCompany = addressOfWarrantyCompanyTextField.getText();
                                     expiryDateOfWarrantyCompany = expiryDateOfWarrantyTextField.getText(); 
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
                
                vehicle.setVehicleType(vehicleType);
                
                if(vehicleType.equals("") || registrationNumber.equals("") || model.equals("") || make.equals("") || engineSize.equals("") || colour.equals("") || motRenewalDate.equals("") || currentMileage <0 || lastServiceDate.equals("")) {
                    System.out.println("Not all fields are filled out");
                } 
                else if(preventFlag == 0) {
                    System.out.println("hELLO");
                    Vehicle vehicle = new Vehicle(vehicleID, vehicleType, registrationNumber, model, make, engineSize, fuelType, colour, motRenewalDate, currentMileage, lastServiceDate, warranty, nameOfWarrantyCompany, addressOfWarrantyCompany, expiryDateOfWarrantyCompany, customerID); 
                    Database.getInstance().editVehicle(vehicle);
                    v.loadTable();
                    Stage stage = (Stage) editButton.getScene().getWindow();
                    stage.close();
                }
            }
        });
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Stage stage = (Stage) editButton.getScene().getWindow();
                stage.close();
            }
        });
    }  
    
        public void alertFill(String s)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Incomplete");
        alert.setHeaderText("Please fill out " + s + " the details!");
        alert.setContentText("...");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            
        }
    }
    
    public void loadVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;

        
//        LSDdatepicker.getValue().toString();// lastServiceDateField.getText();
        
                resgistrationNumberTextField.setText(vehicle.getRegistrationNumber());
                modelTextField.setText(vehicle.getModel());
                makeTextField.setText(vehicle.getMake());
                engineSizeTextField.setText(vehicle.getEngineSize());
                fuelTypeTextField.setText(vehicle.getFuelType());
                colourTextField.setText(vehicle.getColour());
                
                motRenewalDateTextField.setText(vehicle.getMotRenewalDate());
                currentMileageTextField.setText(Integer.toString(vehicle.getCurrentMileage()));
                lastServiceDateTextField.setText(vehicle.getLastServiceDate());
                
                //LSDdatepicker.setValue(lsd);
               // EXPdatepicker.setValue(expd);
                //MOTdatepicker.setValue(motd);
                
                customerTextField.setText(Integer.toString(vehicle.getCustomerID()));
                vehicleIDTextField.setText(Integer.toString(vehicle.getVehicleID()));
                
               //warrantyTextField.setText(vehicle.getWarranty().toString());
               
                //LSDdatepicker.setValue(new LocalDate(vehicle.getLastServiceDate()));
                
                //LSDdatepicker.setValue(fromString(vehicle.getLastServiceDate()));
                    nameOfWarrantyCompanyTextField.setText(vehicle.getNameOfWarrantyCompany());
                    addressOfWarrantyCompanyTextField.setText(vehicle.getAddressOfWarrantyCompany());
                    expiryDateOfWarrantyTextField.setText(vehicle.getExpiryDateOfWarrantyCompany());
                    
                if(vehicle.getWarranty() == 0)// vehicle.getNameOfWarrantyCompany().equals(" "))//vehicle.getWarranty().equals(false))
                {
                    warrantyCheckBox.setSelected(false);   

                    
                }
                else{
                    warrantyCheckBox.setSelected(true);  
                    nameOfWarrantyCompanyTextField.setText(vehicle.getNameOfWarrantyCompany());
                    addressOfWarrantyCompanyTextField.setText(vehicle.getAddressOfWarrantyCompany());
                    expiryDateOfWarrantyTextField.setText(vehicle.getExpiryDateOfWarrantyCompany());
                    // warrantyCheckBox.setSelected(true); 
                    //nameOfWarrantyCompanyTextField.setText("");
                    //addressOfWarrantyCompanyTextField.setText("");
                    //expiryDateOfWarrantyTextField.setText("");
                }
                
                
                

                
           if(vehicle.getVehicleType().equals(VehicleType.Car)) {
            vehicleTypeChoiceBox.getSelectionModel().select(0);
            }    
           else if(vehicle.getVehicleType().equals(VehicleType.Van)) {
            vehicleTypeChoiceBox.getSelectionModel().select(1);
            }
           else{
            vehicleTypeChoiceBox.getSelectionModel().select(2);
            }   
                
                
    
    }
}    
    

