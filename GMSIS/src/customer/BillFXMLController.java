/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customer;

import booking.*;
import parts.*;
import Database.*;
import authentication.*;

import java.util.*;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author matteo
 */
public class BillFXMLController implements Initializable {

    @FXML private TextArea textArea;
    @FXML private Label totalCostLabel;
    @FXML private Label dueCostLabel;
    @FXML private TextField payTextField;
    
    private Booking booking;
    private double amountPaid;
    private double totalCost;
    
    public CustomerFXMLController c;
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }   
    
    public void load(Booking booking) {
        this.booking = booking;
        this.amountPaid = booking.getAmountPaid();
        this.totalCost = booking.getTotalCost();
        update();
    }
    
    public void update() {
        //
        this.textArea.setText(booking.getBillString());
        this.totalCostLabel.setText(this.totalCost+"");
        this.dueCostLabel.setText((this.totalCost-this.amountPaid)+"");
        
        System.out.println("amount paid is " + this.amountPaid);
    }
    
    public void pay() {
        try {
            double value = Double.parseDouble(this.payTextField.getText());
            if(value + this.amountPaid <= this.totalCost) {
                this.amountPaid += value;
                this.booking.setAmountPaid(this.amountPaid);
                save();
                update();
            } else {
                //
            }
        } catch(Exception e) {
            
        }
    }
    
    public void save() {
        Database.getInstance().editBooking(this.booking);
        c.loadTable();
    }
    
}
