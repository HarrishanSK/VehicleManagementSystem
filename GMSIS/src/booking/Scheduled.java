/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package booking;
import java.util.*;
import java.util.Calendar;
/**
 *
 * @author Yousf
 */
public class Scheduled extends Booking {
    
   public Scheduled(int bookingIdentification,int customerIdentification, int vehicleIdentification, int employeeIdentification, String bookingType, String bookingDate,String returnDate, String time, int hoursWorked, double amountPaid){
    super(bookingIdentification,customerIdentification, vehicleIdentification, employeeIdentification, bookingType, bookingDate, returnDate,time, hoursWorked, amountPaid);
    
    }
}
