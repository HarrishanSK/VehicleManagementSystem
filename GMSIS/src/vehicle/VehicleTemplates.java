/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vehicle;

/*
import Database.Database;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
*/

//import vehicle

/**
 *
 * @author Harri
 * 
 */
          //  private ObservableList<VehicleTemplates> vehicleTemplates = FXCollections.observableArrayList((Database.getInstance()).getVehicleTemplates());  

   
public class VehicleTemplates{// extends Application {
    private int VehicleTemplateID;
    VehicleType vehicleType;
    private String make;
    private String model;
    private String engineSize;
    private String fuelType;
    
    //private ObservableList<VehicleTemplates> vehicleTemplates = FXCollections.observableArrayList((Database.getInstance()).getVehicleTemplates());  
    
    
     public VehicleTemplates(int VehicleTemplateID, VehicleType vehicleType1,String Model, String Make, String EngineSize, String FuelType){
        this.vehicleType = vehicleType1;
        this.make = Make;       
        this.model = Model;
        this.engineSize = EngineSize;
        this.fuelType = FuelType;
    }
    
     public VehicleTemplates(VehicleType vehicleType1,String Model, String Make, String EngineSize, String FuelType){
        this.make = Make;
        this.model = Model; 
        this.engineSize = EngineSize;
        this.fuelType = FuelType;
    }
     
     
     public int getVehicleTemplateID()
     {
         return VehicleTemplateID;
     }
      public void setVehicleTemplateID(int vehicleTemplateID)
     {
         this.VehicleTemplateID =  vehicleTemplateID;
     }    
     
      
      public VehicleType getVehicleType()
     {
         return vehicleType;
     }
      public void setVehicleType(VehicleType vehicleType1)
     {
         this.vehicleType =  vehicleType1;
     }    
      
     public String getMake()
     {
         return make;
     }
      public void setMake(String Make)
     {
         this.make = Make;
     } 
      
      
      public String getModel()
     {
         return model;
     }
      public void setModel(String Model)
     {
         this.model = Model;
     }  
      
      
     public String getEngineSize()
     {
         return engineSize;
     }
      public void setEngineSize(String EngineSize)
     {
         this.engineSize = EngineSize;
     }  
      
      
      public String getFuelType()
     {
         return fuelType;
     }
      public void setFuelType(String FuelType)
     {
         this.fuelType = FuelType;
     }  
      
      
      
     
}
    

