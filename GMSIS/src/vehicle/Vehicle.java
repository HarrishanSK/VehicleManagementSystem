/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vehicle;

/**
 *
 * @author Harri
 */
public class Vehicle {
    private VehicleType vehicleType;
    private int vehicleID, bookingID,customerID;
    private String registrationNumber;
    private String model;
    private String make;
    private String engineSize;
    private String fuelType;
    private String colour;
    private String motRenewalDate;
    private int currentMileage;
    private String lastServiceDate;
    private int warranty;
    private String nameOfWarrantyCompany;
    private String addressOfWarrantyCompany;
    private String expiryDateOfWarrantyCompany;
    private int vehicleTemplateID;
    
    public Vehicle(VehicleType vehicleType, String RegistrationNumber, String model, String make, String EngineSize, String FuelType, String colour, String MotRenewalDate, int CurrentMileage, String LastServiceDate, int warranty){
        this.vehicleType = vehicleType;
        this.registrationNumber = RegistrationNumber;
        this.model = model;
        this.make = make;
        this.engineSize = EngineSize;
        this.fuelType = FuelType;
        this.colour = colour;
        this.motRenewalDate = MotRenewalDate;
        this.currentMileage = CurrentMileage;
        this.lastServiceDate = LastServiceDate;
        this.warranty = warranty;
    }
    
        public Vehicle(int vehicleID, VehicleType vehicleType, String RegistrationNumber, String model, String make, String EngineSize, String FuelType, String colour, String MotRenewalDate, int CurrentMileage, String LastServiceDate, int Warranty){
        this.vehicleID = vehicleID;
        this.vehicleType = vehicleType;
        this.registrationNumber = RegistrationNumber;
        this.model = model;
        this.make = make;
        this.engineSize = EngineSize;
        this.fuelType = FuelType;
        this.colour = colour;
        this.motRenewalDate = MotRenewalDate;
        this.currentMileage = CurrentMileage;
        this.lastServiceDate = LastServiceDate;
        this.warranty = Warranty;
    }
         public Vehicle(int vehicleID, VehicleType vehicleType, String RegistrationNumber, String model, String make, String EngineSize, String FuelType, String colour, String MotRenewalDate, int CurrentMileage, String LastServiceDate,int Warranty, String NameOfWarrantyCompany, String AddressOfWarrantyCompany, String ExpiryDateOfWarrantyCompany, int CustomerID){
        this.vehicleID = vehicleID;
        this.vehicleType = vehicleType;
        this.registrationNumber = RegistrationNumber;
        this.model = model;
        this.make = make;
        this.engineSize = EngineSize;
        this.fuelType = FuelType;
        this.colour = colour;
        this.motRenewalDate = MotRenewalDate;
        this.currentMileage = CurrentMileage;
        this.lastServiceDate = LastServiceDate;
        this.warranty = Warranty;
        this.nameOfWarrantyCompany = NameOfWarrantyCompany;
        this.addressOfWarrantyCompany = AddressOfWarrantyCompany;
        this.expiryDateOfWarrantyCompany = ExpiryDateOfWarrantyCompany;        
        this.customerID = CustomerID;
    }
         
         public Vehicle(VehicleType vehicleType, String RegistrationNumber, String model, String make, String EngineSize, String FuelType, String colour, String MotRenewalDate, int CurrentMileage, String LastServiceDate,int Warranty, String NameOfWarrantyCompany, String AddressOfWarrantyCompany, String ExpiryDateOfWarrantyCompany, int CustomerID){
        this.vehicleType = vehicleType;
        this.registrationNumber = RegistrationNumber;
        this.model = model;
        this.make = make;
        this.engineSize = EngineSize;
        this.fuelType = FuelType;
        this.colour = colour;
        this.motRenewalDate = MotRenewalDate;
        this.currentMileage = CurrentMileage;
        this.lastServiceDate = LastServiceDate;
        this.warranty = Warranty;
        this.nameOfWarrantyCompany = NameOfWarrantyCompany;
        this.addressOfWarrantyCompany = AddressOfWarrantyCompany;
        this.expiryDateOfWarrantyCompany = ExpiryDateOfWarrantyCompany;        
        this.customerID = CustomerID;
    }
         
             public Vehicle(VehicleType vehicleType, String RegistrationNumber, String model, String make, String EngineSize, String FuelType, String colour, String MotRenewalDate, int CurrentMileage, String LastServiceDate,int Warranty, String NameOfWarrantyCompany, String AddressOfWarrantyCompany, String ExpiryDateOfWarrantyCompany, int CustomerID, int VehicleTemplateID){
        this.vehicleType = vehicleType;
        this.registrationNumber = RegistrationNumber;
        this.model = model;
        this.make = make;
        this.engineSize = EngineSize;
        this.fuelType = FuelType;
        this.colour = colour;
        this.motRenewalDate = MotRenewalDate;
        this.currentMileage = CurrentMileage;
        this.lastServiceDate = LastServiceDate;
        this.warranty = Warranty;
        this.nameOfWarrantyCompany = NameOfWarrantyCompany;
        this.addressOfWarrantyCompany = AddressOfWarrantyCompany;
        this.expiryDateOfWarrantyCompany = ExpiryDateOfWarrantyCompany;        
        this.customerID = CustomerID;
        this.vehicleTemplateID = VehicleTemplateID;
    }

    //public int isWarranty() {
      //  return warranty;
    //}

    public int getVehicleTemplateID() {
        return vehicleTemplateID;
    }

    public void setWarranty(int warranty) {
        this.warranty = warranty;
    }

    public void setVehicleTemplateID(int vehicleTemplateID) {
        this.vehicleTemplateID = vehicleTemplateID;
    }
    
    
         
   // public Vehicle(int VehicleId, VehicleType vehicleType, String RegistrationNumber, String Model, String Make, String EngineSize, String FuelType, String Colour, String MotRenewalDate, int CurrentMileage, String LastServiceDate) {
     //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    //}
    
        
    public int getVehicleId() {
        return vehicleID;
    }
     public void setVehicleId(int VehicleId) {
        this.vehicleID = VehicleId;
    }
     
    public int getBookingID(){
    return bookingID;
    }
    public void setBookingID(int booking){
    this.bookingID=booking;
    }
    
    public String getModel() {
        return model;
    }
    public void setModel(String Model) {
        this.model = Model;           
    }
    
    public String getRegistrationNumber(){
            return registrationNumber;
    }
     public void setRegistrationNumber(String RegistrationNumber) {
        this.registrationNumber =  RegistrationNumber;         
    }
    
    public String getMake() {
        return make;
    }
    public void setMake(String Make) {
        this.make = Make;           
    }
    
    public String getEngineSize() {
        return engineSize;
    }
     public void setEngineSize(String EngineSize) {
        this.model = EngineSize;           
    }
    
    public String getFuelType() {
        return fuelType;
    }
     public void setFuelType(String FuelType) {
        this.fuelType = FuelType;          
    }
    
    public String getColour() {
        return colour;
    }
    public void setColour(String Colour) {
        this.colour = Colour;           
    }
    
    public String getMotRenewalDate() {
        return motRenewalDate;
    }
    public void setMotRenewalDate(String MotRenewalDate) {
        this.motRenewalDate = MotRenewalDate;          
    }
    
    public int getCurrentMileage(){
        return currentMileage;
    }
    
    public void setCurrentMileage(int currentMileage){
      this.currentMileage=currentMileage;
    }
    
    public String getLastServiceDate() {
        return lastServiceDate;
    }
    public void setLastServiceDate(String LastServiceDate) {
        this.lastServiceDate = LastServiceDate;     
    }
    //WARRANTY DEAILS OF VEHICLES
    public int getWarranty() {
        return warranty;
    }
    //public void setWarranty(int Warranty) {
    //    this.warranty = Warranty;     
    //}    

    public String getNameOfWarrantyCompany() {
        return nameOfWarrantyCompany;
    }
    public void setNameOfWarrantyCompany(String NameOfWarrantyCompany) {
        this.nameOfWarrantyCompany = NameOfWarrantyCompany;     
    }    
    public String getAddressOfWarrantyCompany() {
        return addressOfWarrantyCompany;
    }
    public void setAddressOfWarrantyCompany(String AddressOfWarrantyCompany) {
        this.addressOfWarrantyCompany = AddressOfWarrantyCompany;     
    }   

    public String getExpiryDateOfWarrantyCompany() {
        return expiryDateOfWarrantyCompany;
    }
    public void setExpiryDateOfWarrantyCompany(String ExpiryDateOfWarrantyCompany) {
        this.expiryDateOfWarrantyCompany = ExpiryDateOfWarrantyCompany;     
    }   
    //---------------------------------------------------
    public VehicleType getVehicleType() {
        return vehicleType;
    }
    public void setVehicleType(VehicleType VehicleType) {
        this.vehicleType = VehicleType;           
    }
    
    public int getVehicleID(){
        return vehicleID;   
    }
    public void setVehicleID(int VehicleID) {
        this.vehicleID = VehicleID;         
    }
    
    public int getCustomerID(){
        return customerID;   
    }
    
    public void setCustomerID(int CustomerID) {
        this.customerID = CustomerID;         
    }
    
    public void setVehicleType(String vehicleType) {
        
        if(vehicleType.equals("Car")) 
        {
            this.vehicleType = VehicleType.Car;
        } 
        else if(vehicleType.equals("Van")) 
        {
            this.vehicleType = VehicleType.Van;
        }
        else
        {
            this.vehicleType = VehicleType.Truck;    
        }
    }
 }
    

