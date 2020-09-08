/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parts;

import Database.Database;
import booking.Booking;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import vehicle.Vehicle;
/**
 * FXML Controller class
 *
 * @author elsahu
 */

public class CheckStockController implements Initializable{

    @FXML private Button editButton;

    @FXML private Button requireButton;

    @FXML private Button deleteButton;

    @FXML private Button addButton;

    @FXML private Button backButton;
    
    @FXML private Button searchButton;
    
    @FXML private TextField searchField;
    
    
    @FXML private TableColumn<Part, Number> id;
    @FXML private TableColumn<Part, String> name;
    @FXML private TableColumn<Part, String> description;
    @FXML private TableColumn<Part, String>  installDate;
    @FXML private TableColumn<Part, String>  expireDate;
    @FXML private TableColumn<Part, String>  stockStatus;
    
    

    @FXML
    public TableView<Part> tableView = new TableView<Part>();
    private ObservableList<Part> parts = FXCollections.observableArrayList((Database.getInstance()).getParts());
    
    @FXML
    public void handleBackButtonAction(ActionEvent event) throws Exception{
        Parent check_stock_parent = FXMLLoader.load(getClass().getResource("PartFXML.fxml"));
        Scene check_stock_scene = new Scene(check_stock_parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        stage.setScene(check_stock_scene);
        stage.show();
    }
    @FXML
    public void handleAddButtonAction(ActionEvent event) throws Exception{
        Parent check_stock_parent = FXMLLoader.load(getClass().getResource("AddNewPart.fxml"));
        Scene check_stock_scene = new Scene(check_stock_parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(check_stock_scene);
        stage.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        name.setCellValueFactory(new PropertyValueFactory<>("part_id"));
        tableView.setEditable(true);
        
        loadTable();
        
    
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
        
        requireButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent e) {
                requirePlacement();
            }
        });
    
}
    
     public void loadTable() {
        
    }
     
     public void deletePart(){
         
     }
     
     public void editPart(){
         
     }
     
     public void requirePlacement(){
         
     }
    
    

}
