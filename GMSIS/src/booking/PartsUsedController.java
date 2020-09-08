/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package booking;

import Database.Database;
import authentication.Employee;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import parts.Part;
import parts.PartRepair;
import vehicle.Vehicle;

/**
 * FXML Controller class
 *
 * @author Yousf
 */
public class PartsUsedController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ChoiceBox<Part> part_id;

    @FXML
    private Button submitButton;
   
    @FXML
    private Button close;

    Booking bookings;
    BookingController books;
    ArrayList<Integer> partsUsed = new ArrayList<Integer>();
    private ObservableList<Part> parts = FXCollections.observableArrayList((Database.getInstance()).getParts());

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        part_id.setItems(parts);
        part_id.setConverter(new StringConverter<Part>() {
            @Override
            public String toString(Part object) {
                return object.getID() + ": " + object.getName();
            }

            @Override
            public Part fromString(String string) {
                int userId = Integer.parseInt(string.split(": ")[0]);
                
                    return Database.getInstance().getPart(userId);
                
            }

        });
        part_id.getSelectionModel().selectFirst();

 close.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Stage stage = (Stage) close.getScene().getWindow();
                stage.close();
            }
        });

                

                
                    
              
           
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    partsUsed = Database.getInstance().getPartID(bookings.getBookingID());
                } catch (SQLException ex) {
                    Logger.getLogger(PartsUsedController.class.getName()).log(Level.SEVERE, null, ex);
                }
                int part = part_id.getValue().getID();
                boolean flag=false;
                for(int i=0;i<partsUsed.size();i++){
                if(part==partsUsed.get(i)){
                flag=true;
                }
                }
                
                if(flag){
                Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Part Editing");
                    alert.setHeaderText("This part has already been selected for this vehicle.");

                    alert.showAndWait();
                    Stage stage = (Stage) submitButton.getScene().getWindow();
                    stage.show();
                }
                else{
                
                    int bookingID = bookings.getBookingID();

                    PartRepair parts = new PartRepair(part, bookingID);
                    Database.getInstance().addPartRepair(parts);

                    Stage stage = (Stage) submitButton.getScene().getWindow();
                    stage.close();
                }

            }

        });

    }

}
