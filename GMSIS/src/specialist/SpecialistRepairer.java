/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package specialist;

import java.util.*;
import vehicle.*;

/**
 *
 * @author Pahel
 */
public class SpecialistRepairer {
    
    private String name;
    private int id;
    private int centre_id;
    
    public SpecialistRepairer(String name){
        
        this.name=name;
    }
    
    public SpecialistRepairer(int id, int centre_id, String name){
        
        this.id=id;
        this.name=name;
        this.centre_id=centre_id;
    }
    
    public String getName(){
        
        return name;
    }
    
    public int getId(){
        
        return id;
    }
    
    public int getCentreId(){
        
        return centre_id;
    }
    
    /*public List<Vehicle> getVehicles(SpecialistRepairer repairer){
        
        List<Vehicle> vehicles = new List<Vehicle>();
        
        return vehicles;
        
    }*/
    
}
