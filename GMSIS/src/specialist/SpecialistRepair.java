/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package specialist;

import java.util.*;
import java.util.Calendar;

/**
 *
 * @author Pahel
 */
public class SpecialistRepair {
    
    private String outstanding;
    private String deliveryDate;
    private String returnDate;
    int repairId;
    private int bookingId;
    private int centreId;
    private int customerId;
    private int vehicleId;
    //private ArrayList<Integer> partsId;
    private int partsId;
    private String repairType;
    private String fullName;
    private String regNo;
    
    private double repairCost;
    
    public SpecialistRepair (){
        
        outstanding = "";
        bookingId = 0;
        customerId = 0;
        vehicleId = 0;
        //partsId = new ArrayList<Integer>();
        partsId = 0;
        repairType = "";
        repairCost = 0.0;
    }
    
    public SpecialistRepair(int bookingId, int customerId, int vehicleId, int centreId, String repairType, int partsId, String deliveryDate, String returnDate, String status, double repairCost) {
        
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.vehicleId = vehicleId;
        this.deliveryDate = deliveryDate;
        this.returnDate = returnDate;
        this.centreId = centreId;
        this.repairType = repairType;
        this.partsId = partsId;
        outstanding = status;
        this.repairCost = repairCost;
             
    }
    
    public SpecialistRepair(int repairId, int bookingId, int customerId, int vehicleId, int centreId, String repairType, int partsId, String deliveryDate, String returnDate, String status, double repairCost) {
        
        this.repairId = repairId;
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.vehicleId = vehicleId;
        this.deliveryDate = deliveryDate;
        this.returnDate = returnDate;
        this.centreId = centreId;
        this.repairType = repairType;
        this.partsId = partsId;
        outstanding = status;
        this.repairCost = repairCost;
        
        
        
    }
    
    public SpecialistRepair(int repairId, int bookingId, int customerId, int vehicleId, int centreId, String repairType, int partsId, String deliveryDate, String returnDate, String status, String fullName, String regNo, double repairCost) {
        
        this.repairId = repairId;
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.vehicleId = vehicleId;
        this.deliveryDate = deliveryDate;
        this.returnDate = returnDate;
        this.centreId = centreId;
        this.repairType = repairType;
        this.partsId = partsId;
        outstanding = status;
        this.fullName = fullName;
        this.regNo = regNo;
        this.repairCost = repairCost;
        
        
        
    }
    
    public double getRepairCost(){
        return repairCost;
    }
    
    public void setRepairCost(double repairCost){
        this.repairCost = repairCost;
    }
    
    public String getFullName(){
        return fullName;
    }
    
    public String getRegNo(){
        return regNo;
    }
    
    public int getRepairId(){
        return repairId;
    }
    
    public String getStatus(){
        return outstanding;
    }
    
    public void setStatus(String outstanding){
         this.outstanding = outstanding;
    }
    
    public int getBookingId(){
        return bookingId;
    }
    
    public void setBookingId(int bookingId){
         this.bookingId = bookingId;
    }
    
    public int getCustomerId(){
        return customerId;
    }
    
    public void setCustomerId(int customerId){
         this.customerId = customerId;
    }
    
    public int getVehicleId(){
        return vehicleId;
    }
    
    public void setVehicleId(int vehicleId){
         this.vehicleId = vehicleId;
    }
    
    public int getPartsId(){
        return partsId;
    }
    
    public void setPartsId(int partsId){
         this.partsId = partsId;
    }
    
    public String getRepairType(){
        return repairType;
    }
    
    public void setRepairType(String repairType){
         this.repairType = repairType;
    }
    
    public String getDeliveryDate(){
        return deliveryDate;
    }
    
    public void setDeliveryDate(String deliveryDate){
         this.deliveryDate = deliveryDate;
    }
    
    public String getReturnDate(){
        return returnDate;
    }
    
    public void setReturnDate(String returnDate){
         this.returnDate = returnDate;
    }
    
    public int getCentreId(){
        return centreId;
    }
    
    public void setCentreId(int centreId){
         this.centreId = centreId;
    }

}
