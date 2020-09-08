/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customer;

import Database.Database;
import booking.Booking;
import java.util.ArrayList;

public class Customer {
    
    //
    private int id = 0;
    private String fullName;
    private String address;
    private String postcode;
    private String phoneNumber;
    private String email;
    private CustomerType customerType;
    
    public Customer(String fullName, String address, String postcode, String phoneNumber, String email, CustomerType customerType) {
        this.fullName = fullName;
        this.address = address;
        this.postcode = postcode;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.customerType = customerType;
    }
    
    public Customer(int id, String fullName, String address, String postcode, String phoneNumber, String email, CustomerType customerType) {
        this.id = id;
        this.fullName = fullName;
        this.address = address;
        this.postcode = postcode;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.customerType = customerType;
    }
    
    public int getId() {
        return id;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getPostcode() {
        return postcode;
    }
    
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public CustomerType getCustomerType() {
        return customerType;
    }
    
    public boolean getIsSettled() {
        ArrayList<Booking> bookings = Database.getInstance().getBookingsForCustomer(this);
        for(int i = 0; i < bookings.size(); i++) {
            if(bookings.get(i).getIsSettled() == false) {
                return false;
            }
        }
    
        return true;
    }
    
    public void setCustomerType(String customerType) {
        if(customerType.equals("Private")) {
            this.customerType = CustomerType.Private;
        } else {
            this.customerType = CustomerType.Business;
        }
    }
    
}