/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package authentication;

import Database.*;

/**
 *
 * @author matteo
 */
public class Authentication {
    
    private static Employee user = null; 
    
    public static boolean login(int employee_id, String password) {
        Employee employee = Database.getInstance().getEmployee(employee_id);
        if(employee != null) {
            if(employee.getPassword().equals(password)) {
                user = employee;
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    
    public static boolean isAdmin() {
        if(user == null) {
            return false;
        } else {
            return user.getEmployeeType() == EmployeeType.Admin;
        }
    }
        
    public static void logout() {
        user = null;
    }
    
    public static boolean isEqual(Employee employee) {
        return user == employee;
    }
    
}
