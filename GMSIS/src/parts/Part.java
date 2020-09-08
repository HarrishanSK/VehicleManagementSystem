package parts;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

/**
- *
- * @author Yachen
- */

public class Part {

	private int partID ;
        private int partStock, stockChange;
	private String partName;
	private String partDescription;
	private double partCost, specialistCost;
	private String partInstallDate;
	private String partExpireDate;
        private String deliveryDate;
        
            
    public Part(String name, String description, double cost, double scost, int stock){
        this.partName = name;
        this.partDescription = description;
        this.partCost = cost;
        this.specialistCost = scost;
        this.partStock = stock;
    }//the constructor used for add new part in stock
    
    public Part(String name, String description, double cost, double scost){
        this.partName = name;
        this.partDescription = description;
        this.partCost = cost;
        this.specialistCost = scost;
    }//the constructor used for add new part in stock
    
    
    
    public Part(String name, String description, String install, String expire, double cost, double scost){
        this.partName = name;
        this.partDescription = description;    
        this.partInstallDate = install;
        this.partExpireDate = expire;
        this.partCost = cost;
        this.specialistCost = scost;
    }
    
    public Part(int ID, String name, String description,double cost, double scost, int stock){
        this.partID = ID;
        this.partName = name;
        this.partDescription = description; 
        this.partCost = cost;
        this.specialistCost = scost;
        this.partStock = stock;
    }
    
     public Part( String name, String description,double cost, int change){
        DateTime now = new DateTime();
        LocalDate in = now.toLocalDate();
        this.deliveryDate = in.toString("yyyy-MM-dd");
        this.partName = name;
        this.partDescription = description; 
        this.partCost = cost;
        this.stockChange = change;
        
    }//delivery part
    
     public Part(String date, String name, String description,double cost, int change){
       
        this.deliveryDate = date;
        this.partName = name;
        this.partDescription = description; 
        this.partCost = cost;
        this.stockChange = change;
        
    }//get delivery part
   
    public int getID(){
        return partID;
    }
        
    public void setID(int id){
        partID = id;
    }
    
    public int getStock(){
        return partStock;
    }
        
    public void setStock(int n){
        partStock = partStock + n;
    }
    
    public int getStockChange(){
        return stockChange;
    }
        
    public void setStockChange(int n){
        stockChange = n;
    }
    
    public String getDeliveryDate(){

        DateTime now = new DateTime();
        LocalDate in = now.toLocalDate();
        this.deliveryDate = in.toString("yyyy-MM-dd");

        return deliveryDate;
    }
    
    public void setDeliveryDate(String s){
        deliveryDate = s;
    }
    
       
    public String getName(){
        return partName;
    }
        
    public void setName(String n){
        partName = n;
    }
        
    public String getDescription(){
        return partDescription;
    }
        
    public void setDescription(String d){
        partDescription = d;
    }
        
    public double getCost(){
        return partCost;
    }
        
    public void setCost(double c){
        partCost = c;
    }

    public String getInstallDate() {
        return partInstallDate;
    }
        
    public void setInstallDate(String d){
        partInstallDate = d;
    }

    public String getExpireDate() {
        return partExpireDate;
    }
        
    public void setExpireDate(String d){
        partExpireDate = d;
    }
    
    public double getSpecialistCost(){
        return specialistCost;
    }
    
    public void setSpecialistCost(double s){
        specialistCost = s;
    }
}