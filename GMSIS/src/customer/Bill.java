/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customer;

import booking.*;

/**
 *
 * @author matteo
 */
public class Bill {
    
    private int id = 0;
    private Booking booking;
    private double amountPaid;
    
    public Bill(Booking booking, double amountPaid) {
        this.booking = booking;
        this.amountPaid = amountPaid;
    }
    
    public Bill(int id, Booking booking, double amountPaid) {
        this.id = id;
        this.booking = booking;
        this.amountPaid = amountPaid;
    }
    
    public int getId() {
        return id;
    }
    
    public Booking getBooking() {
        return booking;
    }
    
    public double getAmountPaid() {
        return amountPaid;
    }
    
    public void pay(double amount) {
        amountPaid += amount;
    }
    
    
}
