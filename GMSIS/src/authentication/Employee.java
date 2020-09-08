/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package authentication;

/**
 *
 * @author matteo
 */
public class Employee {
    
    private int employee_id = 0;
    private String firstName;
    private String lastName;
    private String password;
    private double wage;
    private EmployeeType employeeType;
    
    public Employee(int employee_id, String firstName, String lastName, String password, double wage, EmployeeType employeeType) {
        this.employee_id = employee_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.wage = wage;
        this.password = password;
        this.employeeType = employeeType;
    }
    
    public Employee(String firstName, String lastName, String password, double wage, EmployeeType employeeType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.wage = wage;
        this.employeeType = employeeType;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public int getId() {
        return employee_id;
    }
    
    public double getWage() {
        return wage;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public EmployeeType getEmployeeType() {
        return employeeType;
    }
    
}
