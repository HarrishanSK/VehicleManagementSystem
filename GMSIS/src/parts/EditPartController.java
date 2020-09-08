/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parts;

import Database.Database;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Yachen
 */
public class EditPartController implements Initializable{
    @FXML private Button cancelButton;
    @FXML private Button confirmButton;
    
    @FXML private TextField name;
    @FXML private TextField newName;
    @FXML private TextArea description;
    @FXML private TextArea newDescription;
    @FXML private TextField cost;
    @FXML private TextField newCost;
    @FXML private TextField scost;
    @FXML private TextField newScost;
    
    
    @FXML private Label head;
    private ObservableList<Part> partsList = FXCollections.observableArrayList((Database.getInstance()).getParts());
    

    Part part ;
    PartsController partCon;
    EditPartController editPCon;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        confirmButton.setOnAction(new EventHandler<ActionEvent>() {         
            @Override public void handle(ActionEvent e) {  
                
                String part_name = newName.getText();
                String part_description = newDescription.getText();
                Double part_cost = Double.parseDouble(newCost.getText());
                Double part_scost = Double.parseDouble(newScost.getText());
                
                if(part_name.equals("") || part_description.equals("") || part_cost == 0 || part_scost == 0){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText("ERROR!");
                    alert.setContentText("Please fill in all the field.");
                    alert.showAndWait();
            
                }
                else{
                    part.setName(part_name);
                    part.setDescription(part_description);
                    part.setCost(part_cost);
                    part.setSpecialistCost(part_scost);
                    Database.getInstance().editPart(part);
                    partCon.loadTable();
                    Stage stage = (Stage) confirmButton.getScene().getWindow();
                    stage.close();   
                }
                 
            }
        });
        
        
        cancelButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override public void handle(ActionEvent e)
            {
                Stage stage = (Stage) confirmButton.getScene().getWindow();
                stage.close();
            }
        });
    }
    
    public void loadPart(Part part) {
        this.part = part;
        name.setText(String.valueOf(part.getName()));
        description.setText(String.valueOf(part.getDescription()));
        cost.setText(Double.toString(part.getCost()));
        scost.setText(Double.toString(part.getSpecialistCost()));   
        
    }
}
    

