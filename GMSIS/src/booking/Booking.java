/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package booking;
import Database.Database;
import authentication.Employee;
import java.util.*;
import java.util.Calendar;
import parts.Part;
import specialist.SpecialistRepair;



/**
 *
 * @author Yousf
 */
public class Booking {
    private int bookingID, customerID, vehicleID, employeeID, hours_worked;
    private String bookingType, bookingDate, bookingTime, returnBookingDate, FullName,RegistrationNumber, manufacturer;
    private double amount_paid;
    
    
    public Booking(int bookingIdentification,int customerIdentification, int vehicleIdentification, int employeeIdentification, String bookingType, String bookingDate, String returnBooking, String bookingTime,  int hours, double amountPaid){
    this.bookingID=bookingIdentification;
    this.customerID= customerIdentification;
    this.vehicleID=vehicleIdentification;
    this.employeeID=employeeIdentification;
    this.bookingType=bookingType;
    this.bookingDate=bookingDate;
    this.bookingTime=bookingTime;
    this.returnBookingDate= returnBooking;
    this.hours_worked=hours;
    this.amount_paid=amountPaid;
    }
    
    public Booking(int customerIdentification, int vehicleIdentification, int employeeIdentification, String bookingType, String bookingDate, String returnBooking, String bookingTime,  int hours, double amountPaid){
    
    this.customerID= customerIdentification;
    this.vehicleID=vehicleIdentification;
    this.employeeID=employeeIdentification;
    this.bookingType=bookingType;
    this.bookingDate=bookingDate;
    this.bookingTime=bookingTime;
    this.returnBookingDate= returnBooking;
    this.hours_worked=hours;
    this.amount_paid=amountPaid;
    }
    
    public Booking(int bookingIdentification,int customerIdentification, int vehicleIdentification, int employeeIdentification, String bookingType, String bookingDate, String returnBooking, String bookingTime, String fName, String reg, String model){
    this.bookingID=bookingIdentification;
    this.customerID= customerIdentification;
    this.vehicleID=vehicleIdentification;
    this.employeeID=employeeIdentification;
    this.bookingType=bookingType;
    this.bookingDate=bookingDate;
    this.bookingTime=bookingTime;
    this.returnBookingDate= returnBooking;
    this.FullName=fName;
    this.RegistrationNumber=reg;
    this.manufacturer=model;
    }
    
     
    public int getBookingID(){
    return bookingID;
    }
    public int getCustomerID(){
    return customerID;
    }
    public void setCustomerID(int customer){
    this.customerID=customer;
    }
    
    public int getVehicleID(){
    return vehicleID;
    }
    public void setVehicleID(int vehicle){
    this.vehicleID=vehicle;
    }
    
    public int getEmployeeID(){
    return employeeID;
    }
    public void setEmployeeID(int employee){
    this.employeeID=employee;
    }
    
    public String getBookingType(){
    return bookingType;
    }
    public void setBookingType(String booking){
    this.bookingType=booking;
    }
    
    public String getBookingDate(){
    return bookingDate;
    }
    public void setBookingDate(String date){
    this.bookingDate=date;
    }
    
    public String getReturnDate(){
    return returnBookingDate;
    }
    public void setReturnDate(String date){
    this.returnBookingDate=date;
    }
    
    public String getBookingTime(){
    return bookingTime;
    }
    
    public void setBookingTime(String time){
    this.bookingTime=time;
    }
    
    public String getFullName(){
    return FullName;
    }
    public void setFullName(String name){
    this.FullName=name;
    }
    public String getRegistrationNumber(){
    return RegistrationNumber;
    }
    public void setRegistrationNumber(String reg){
    this.RegistrationNumber=reg;
    }
    
    public int getHoursWorked(){
    return hours_worked;
    }
    
    public void setHoursWorked(int hours){
    this.hours_worked=hours;
    }
    
    public double getAmountPaid(){
    return amount_paid;
    }
    
    public void setAmountPaid(double paid){
    this.amount_paid=paid;
    }
    
    public String getManufacturer(){
    return manufacturer;
    }
    
    public void setManufacturer(String model){
    this.manufacturer=model;
    }
    
    public double getServiceCost() {
        Employee emp = Database.getInstance().getEmployee(this.getEmployeeID());
        double wage = emp.getWage();
        double hw = (double) this.getHoursWorked();
        return wage * hw;
    }
    
    public boolean getWarranty() {
        return Database.getInstance().getVehicle(this.getVehicleID()).getWarranty() == 1;
    }
    
    public boolean getIsSettled() {  
        ArrayList<SpecialistRepair> sp_reps = Database.getInstance().getCompletedSpcBookings("select * from 'specialist_bookings' where booking_id = '"+this.getBookingID()+"'");
        if(sp_reps.isEmpty()){
            if(getWarranty()){
                return true;
            }
            else
                return this.amount_paid == this.getTotalCost();
        }
        else
        {
            if(sp_reps.get(0).getStatus().equals("unfinished")) {
            return false;
          }
            else
            {
                if(getWarranty())
                    return true;
                else
                    return this.amount_paid == this.getTotalCost();
            }
        }
    }
    
    public double getTotalCost() {
        double total = 0;
        if(this.getBookingType().equals("Diagnosis and Repair")) {
            
            
            // get part repaired/replaced normally
            ArrayList<Part> parts = (Database.getInstance()).getPartsForBooking(this.getBookingID());
            for(int i = 0; i < parts.size(); i++) {
                total += parts.get(i).getCost();
                
            }          
            
            // get hours worked
            
            
            total += getServiceCost();
            
            
            // get specialist assignments
            

            ArrayList<SpecialistRepair> sp_reps = Database.getInstance().getCompletedSpcBookings("select * from 'specialist_bookings' where booking_id = '"+this.getBookingID()+"'");
            if(sp_reps.isEmpty()==false) {
                if(sp_reps.get(0).getStatus().equals("complete")) {
                    for(int i = 0; i < sp_reps.size(); i++) {
                        total += sp_reps.get(i).getRepairCost();
                    }
                }
            }
        } else if(this.getBookingType().equals("Scheduled Maintenance")) {
            total += getServiceCost();
        } else {
                // invalid type
        }
        return total;
    }
    
    public String getBillString() {
        String b = "";
        
        if(getWarranty()) {
            b += "Covered by warranty.\n\n";
        }
        
        if(this.getBookingType().equals("Diagnosis and Repair")) {
            
            // get part repaired/replaced normally
            b += "Replacement of damaged parts:\n";
            ArrayList<Part> parts = (Database.getInstance()).getPartsForBooking(this.getBookingID());
            for(int i = 0; i < parts.size(); i++) {
                b += " - " + parts.get(i).getName() + " : £" + parts.get(i).getCost() + "\n";
            }          
            
            // get hours worked
            
            b += "\nService Cost:\n";
            b += "£" + getServiceCost() + "\n";
            
            
            // get specialist assignments

            b += "\nSpecialist Repairs:\n";
            ArrayList<SpecialistRepair> sp_reps = Database.getInstance().getCompletedSpcBookings("select * from 'specialist_bookings' where booking_id = '"+this.getBookingID()+"'");
            if(sp_reps.isEmpty()==false){
               if(sp_reps.get(0).getStatus().equals("complete")){
               for(int i = 0; i < sp_reps.size(); i++) {
                //sp_reps.get(i).getRepairCost()
                b += "\n - " + (Database.getInstance()).getPart(sp_reps.get(i).getPartsId()).getName() + " : £" + sp_reps.get(i).getRepairCost();
               }
            } else if(sp_reps.get(0).getStatus().equals("unfinished"))
                b +="- Specialist repairs ongoing. Cost will be added once they're complete.";
            } else {
                b +="  No specialist repairs at the moment.";
            }
                
        } else if(this.getBookingType().equals("Scheduled Maintenance")) {
            b += "\nService Cost:\n";
            b += "£" + getServiceCost() + "\n";
        } else {
            // invalid type
        }
        return b;
    }
    
 
}
