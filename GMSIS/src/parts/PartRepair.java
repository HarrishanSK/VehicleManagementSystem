/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parts;
import org.joda.time.DateTime;

import org.joda.time.LocalDate;

/**
 *
 * @author elsahu
 */
public class PartRepair {
    int part_id, booking_id, repair_id;
    String part_installDate, part_expireDate, registration_number, customer_name;

    
    
    
    public PartRepair(int p, int b){
        this.part_id = p;
        this.booking_id = b;
        
        //set today's date to installDate
        DateTime now = new DateTime();
        LocalDate in = now.toLocalDate();
        part_installDate = in.toString("yyyy-MM-dd");
        LocalDate ex = in.plusYears(1);
        part_expireDate = ex.toString("yyyy-MM-dd");
        
        
    }
    
    public PartRepair(int p, int b, String in, String ex){
        this.part_id = p;
        this.booking_id = b;
        this.part_installDate = in;
        this.part_expireDate = ex;
    }
    //getPartRepairs
    public PartRepair(int repairID,int bookingID, String registration_number, String fullname, int partID, String installDate, String expireDate){
        this.repair_id = repairID;
        this.part_id = partID;
        this.booking_id = bookingID;
        this.registration_number = registration_number;
        this.customer_name = fullname;
        
        this.part_installDate = installDate;
        this.part_expireDate = expireDate;   
    }
    
    public int getPartID(){
        return part_id;
    }
    
    public int getRepairID(){
        return repair_id;
    }
    
    public void setPartID(int p){
        part_id = p;
    }
    
    public int getBookingID(){
        return booking_id;
    }
    
    
    public void setBookingID(int p){
        booking_id = p;
    }
    

    public String getRegistrationNumber(){       
        return registration_number;
    }
    
 
    
    public String getCustomerName(){
        return customer_name;
    }
    
    public void setCustomerName(String p){
        
        this.customer_name = p;
    }
 
    
    public String getInstallDate(){
        return part_installDate;
    }

    public String getExpireDate(){
        return part_expireDate;
    }
    
    

}
