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
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author elsahu
 */
public class AddStockController implements Initializable {

    @FXML
    private Button cancelButton;
    
    @FXML
    private Button confirmButton;
    
    @FXML
    private TextField partName;
    
    @FXML
    private TextField number;
    
    @FXML
    private Label error;
    
    private ObservableList<Part> partsList = FXCollections.observableArrayList((Database.getInstance()).getParts());
    
    Part part;
    PartsController partCon;
    AddStockController addCon;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        confirmButton.setOnAction(new EventHandler<ActionEvent>() {         
            @Override public void handle(ActionEvent e) {  
                if(number.getText().equals("")){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("ERROR");
                        alert.setHeaderText("ERROR");
                        alert.setContentText("Please fill in the field.");
                        alert.showAndWait();

                    }
                
                try{
                    int stock = Integer.parseInt(number.getText());
                    
                    
                        part.setStock(stock);
                        Database.getInstance().editPart(part);
                        System.out.println(part.getStock());
                        System.out.println(part.getName());

                        Part partRecord = new Part(part.getName(), part.getDescription(), part.getCost(), stock);
                        Database.getInstance().addPartDelivery(partRecord);
                        Stage stage = (Stage) confirmButton.getScene().getWindow();
                        partCon.loadTable();
                        partCon.loadTable3();  
                        stage.close();   
                    
                }catch(NumberFormatException ee){
                    error.setText("Please enter a number for new stock.");
                }
                 
            }
        });

        cancelButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override public void handle(ActionEvent e)
            {
                Stage stage = (Stage) cancelButton.getScene().getWindow();
                stage.close();
            }
        });
        
    }    
    
    public void loadPart(Part part) {
        this.part = part;
        partName.setText(String.valueOf(part.getName()));
    }
    
}
