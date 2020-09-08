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

public class VehicleUnderWarrenty {
    private String NameOfWarrentyCompany ;
    private String AddressOfWarrentyCompany;
    private String ExpiryDateOfWarrenty;
    public VehicleUnderWarrenty(String NameOfWarrentyCompany, String AddressOfWarrentyCompany ,String ExpiryDate  )
    {
        this.NameOfWarrentyCompany = NameOfWarrentyCompany ;
        this.AddressOfWarrentyCompany = AddressOfWarrentyCompany;
        this.ExpiryDateOfWarrenty = ExpiryDateOfWarrenty;
        
    } 
}
