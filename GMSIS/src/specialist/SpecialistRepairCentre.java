/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package specialist;

import java.util.*;
import vehicle.*;
import parts.*;
import customer.*;

/**
 *
 * @author Pahel
 */
public class SpecialistRepairCentre {
    
    private int centreId;
    private String name;
    private String address;
    private String phone;
    private String email;
    
    public SpecialistRepairCentre(){
        
        centreId = 0;
        name = "";
        address = "";
        phone = "";
        email = "";
    }
            
    public SpecialistRepairCentre(String name, String address, String phone, String email){
        
        this.name=name;
        this.address=address;
        this.phone=phone;
        this.email=email;
    }
    
    public SpecialistRepairCentre(int id,String name, String address, String phone, String email){
        
        centreId=id;
        this.name=name;
        this.address=address;
        this.phone=phone;
        this.email=email;
    }
    
    public int getId() {
        return centreId;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){
         this.name=name;
    }
    
    public String getAddress(){
        return address;
    }
    
    public void setAddress(String address){
        this.address=address;
    }
    
    public String getPhone(){
        return phone;
    }
    
    public void setPhone(String phone){
        this.phone=phone;
    }
    
    public String getEmail(){
        return email;
    }
    
    public void setEmail(String email){
        this.email=email;
    }
     
}
