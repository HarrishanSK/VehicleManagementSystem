/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package authentication;

import Database.*;
import customer.Customer;
import customer.CustomerFXMLController;
import customer.EditCustomerFXMLController;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import java.util.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author matteo
 */
public class EmployeeFXMLController implements Initializable {

    @FXML private TextField searchTextField;        
    @FXML private Button addButton;
    @FXML private Button deleteButton;
    @FXML private Button changeButton;
    
    @FXML public TableView<Employee> table = new TableView<Employee>();
    private ObservableList<Employee> employees;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        if(Authentication.isAdmin()) {
            
        } else {
            addButton.setDisable(true);
            deleteButton.setDisable(true);
            changeButton.setDisable(true);
        }      
        
        
        TableColumn idColumn = new TableColumn("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        TableColumn firstNameColumn = new TableColumn("First Name");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        
        TableColumn lastNameColumn = new TableColumn("Last Name");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        
        TableColumn wageColumn = new TableColumn("Wage");
        wageColumn.setCellValueFactory(new PropertyValueFactory<>("wage"));
        
        TableColumn typeColumn = new TableColumn("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("employeeType"));
        
        table.setEditable(true);
        table.getColumns().addAll(idColumn, firstNameColumn, lastNameColumn, wageColumn, typeColumn); 
        loadTable();
    }    
    
    public void loadTable() {
        employees = FXCollections.observableArrayList((Database.getInstance()).getEmployees());        
        table.setItems(employees);
    }
    
    public void showAddEmployee() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/authentication/AddEmployeeFXML.fxml"));
            Pane page = (Pane) loader.load();
            AddEmployeeFXMLController aec = (AddEmployeeFXMLController) loader.getController();
            aec.setEmployeeController(this);
            Stage stage = new Stage();
            Scene scene = new Scene(page);
            stage.setScene(scene);
            stage.setTitle("Add Employee");
            stage.show();
        } catch (Exception ex) {
            Logger.getLogger(EmployeeFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void showChangePassword() {
        int selectedIndex = table.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0) {
            Employee employee = table.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/authentication/ChangePasswordFXML.fxml"));
                Pane page = (Pane) loader.load();
                ChangePasswordFXMLController cpc = (ChangePasswordFXMLController) loader.getController();
                cpc.setEmployee(employee);
                Stage stage = new Stage();
                Scene scene = new Scene(page);
                stage.setScene(scene);
                stage.setTitle("Change Password");
                stage.show();
            } catch (Exception ex) {
                Logger.getLogger(EmployeeFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void delete() {
        int selectedIndex = table.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0) {
            Employee emp = table.getSelectionModel().getSelectedItem();
        
        if(emp != null) {
            if(!Authentication.isEqual(emp)) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete employee?");
                alert.setHeaderText("Delete an employee");
                alert.setContentText("Are you sure to delete this employee?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    (Database.getInstance()).deleteEmployee(emp);
                    loadTable();
                } else {
                    //
                }
            } 
        }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("You cannot delete yourself");
                alert.setContentText("You cannot remove yourself from the list of employees/users.");
                alert.showAndWait();
            }
        }
        else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setContentText("Please select an employee first!");
                alert.showAndWait();
            
        }
        
        
    }
    
}
