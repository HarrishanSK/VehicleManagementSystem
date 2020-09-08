package Database;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import customer.*;
import authentication.*;
import parts.*;
import specialist.*;
import booking.*;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import vehicle.*;


public class Database {

	private static final Database INSTANCE = new Database();

	private Connection connection = null;

	private Database() {
            try {
                connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            } catch (SQLException ex) {
                ex.printStackTrace();
		throw new RuntimeException("Database connection failed!", ex);
            }
	}
        
        
        // ---------------- CUSTOMER
        
        public void addCustomer(Customer customer) {
            ArrayList<Customer> customers = new ArrayList<Customer>();
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
                statement.executeUpdate("insert into 'customer' (full_name,address,postcode,phone,email,type) values ('" + customer.getFullName() + "','" + customer.getAddress() + "','" + customer.getPostcode() + "','" + customer.getPhoneNumber() + "','" + customer.getEmail() + "','" + customer.getCustomerType().name() + "')");
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            } 
        }
        
        public void editCustomer(Customer customer) {
            if(customer.getId() <= 0) {
                return;
            }
            ArrayList<Customer> customers = new ArrayList<Customer>();
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
                statement.executeUpdate("update 'customer' set full_name = '" + customer.getFullName() + "',address = '" + customer.getAddress() + "',postcode='" + customer.getPostcode() + "',phone = '" + customer.getPhoneNumber() + "',email = '" + customer.getEmail() + "',type = '" + customer.getCustomerType().name() + "' where customer_id = '" + customer.getId() + "'");
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
        }
        
        public void deleteCustomer(Customer customer) {
            if(customer.getId() <= 0) {
                return;
            }
            ArrayList<Customer> customers = new ArrayList<Customer>();
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
                statement.executeUpdate("delete from 'customer' where customer_id ='" + customer.getId() + "'");
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
        }
        
        public ArrayList<Customer> getCustomers() {
            ArrayList<Customer> customers = new ArrayList<Customer>();
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
                ResultSet resultSet = statement.executeQuery("select * from 'customer'");
                while(resultSet.next()){
                    int id = resultSet.getInt("customer_id");
                    String fullName = resultSet.getString("full_name");
                    String address = resultSet.getString("address");
                    String postcode = resultSet.getString("postcode");
                    String phone = resultSet.getString("phone");
                    String email = resultSet.getString("email");
                    String type = resultSet.getString("type");
                    CustomerType customerType = CustomerType.Private;
                    if(type.equals("Business")) {
                        customerType = CustomerType.Business;
                    }
                    Customer customer = new Customer(id, fullName, address, postcode, phone, email, customerType);
                    customers.add(customer);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
		System.err.println(ex.getMessage());
            }
            return customers;
        }
        
        public ArrayList<Customer> searchCustomers(String query) {
            ArrayList<Customer> customers = new ArrayList<Customer>();
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
                ResultSet resultSet = statement.executeQuery("select * from 'customer' where full_name like '%" + query + "%'");
                while(resultSet.next()){
                    int id = resultSet.getInt("customer_id");
                    String fullName = resultSet.getString("full_name");
                    String address = resultSet.getString("address");
                    String postcode = resultSet.getString("postcode");
                    String phone = resultSet.getString("phone");
                    String email = resultSet.getString("email");
                    String type = resultSet.getString("type");
                    CustomerType customerType = CustomerType.Private;
                    if(type.equals("Business")) {
                        customerType = CustomerType.Business;
                    }
                    Customer customer = new Customer(id, fullName, address, postcode, phone, email, customerType);
                    customers.add(customer);
                }
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
            return customers;
        }
        
        public ArrayList<Customer> searchCustomers(String query, CustomerType customerType) {
            ArrayList<Customer> customers = new ArrayList<Customer>();
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
                ResultSet resultSet = statement.executeQuery("select * from 'customer' where full_name like '%" + query + "%' and type like '%" + customerType.name() + "%'");
                while(resultSet.next()){
                    int id = resultSet.getInt("customer_id");
                    String fullName = resultSet.getString("full_name");
                    String address = resultSet.getString("address");
                    String postcode = resultSet.getString("postcode");
                    String phone = resultSet.getString("phone");
                    String email = resultSet.getString("email");
                    String type = resultSet.getString("type");
                    Customer customer = new Customer(id, fullName, address, postcode, phone, email, customerType);
                    customers.add(customer);
                }
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
            return customers;
        }
        
        public Customer getCustomer(int customerId) {
            Customer customer = null;
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
                ResultSet resultSet = statement.executeQuery("select * from 'customer' where customer_id='" + customerId + "'");
                
                int id = resultSet.getInt("customer_id");
                String fullName = resultSet.getString("full_name");
                String address = resultSet.getString("address");
                String postcode = resultSet.getString("postcode");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                String type = resultSet.getString("type");
                CustomerType customerType = CustomerType.Private;
                if(type.equals("Business")) {
                    customerType = CustomerType.Business;
                }
                customer = new Customer(id, fullName, address, postcode, phone, email, customerType);
                
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
            return customer;
        }
        
        // ---------------- BILL
        
        public void addBill(Bill bill) {
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
                statement.executeUpdate("insert into 'bill' (booking_id, amount_paid) values ('"+bill.getBooking().getBookingID()+"','"+bill.getAmountPaid()+"')");
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
        }
        
        public void editBill(Bill bill) {
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
                statement.executeUpdate("update 'bill' set amount_paid = '"+bill.getAmountPaid()+"' where bill_id = '" + bill.getId() + "')");
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
        }
        
        public Bill getBill(int id) {
            Statement statement;
            Bill bill = null;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
                ResultSet resultSet = statement.executeQuery("select * from 'bill' where bill_id = '" + id + "'");
                while(resultSet.next()){
                    double amountPaid = resultSet.getDouble("amount_paid");
                    int bookingId = resultSet.getInt("booking_id");
                    Booking booking = getBooking(bookingId);
                    bill = new Bill(id,booking,amountPaid);
                }
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
            return bill;
        }
        
        // ---------------- EMPLOYEE
        
        public Employee getEmployee(int employee_id) {
            Employee employee = null;
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
                ResultSet resultSet = statement.executeQuery("select * from 'employee' where employee_id = '" + employee_id + "'");
                while(resultSet.next()){
                    int id = resultSet.getInt("employee_id");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    String password = resultSet.getString("password");
                    double wage = resultSet.getDouble("wage");
                    String type = resultSet.getString("type");
                    EmployeeType employeeType = EmployeeType.User;
                    if(type.equals("Admin")) {
                        employeeType = EmployeeType.Admin;
                    }
                    employee = new Employee(id,firstName,lastName,password,wage,employeeType);
                }
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
            return employee;
        }
        
        public ArrayList<Employee> getEmployees() {
            ArrayList<Employee> employees = new ArrayList<Employee>();
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
                ResultSet resultSet = statement.executeQuery("select * from 'employee'");
                while(resultSet.next()){
                    int id = resultSet.getInt("employee_id");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    double wage = resultSet.getDouble("wage");
                    String password = resultSet.getString("password");
                    String type = resultSet.getString("type");
                    EmployeeType employeeType = EmployeeType.User;
                    if(type.equals("Admin")) {
                        employeeType = EmployeeType.Admin;
                    }
                    Employee employee = new Employee(id,firstName,lastName,password,wage,employeeType);
                    employees.add(employee);
                }
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
            return employees;
        }
        
        public void deleteEmployee(Employee employee) {
            if(!Authentication.isAdmin()) {
               return; 
            }
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
                statement.executeUpdate("delete from 'employee' where employee_id ='" + employee.getId() + "'");
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
        }
        
        public void addEmployee(Employee employee) {
            if(!Authentication.isAdmin()) {
               return; 
            }
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
                statement.executeUpdate("insert into 'employee' (first_name, last_name,password,wage,type) values ('"+employee.getFirstName()+"','"+employee.getLastName()+"','"+employee.getPassword()+"','" + employee.getWage() + "','"+employee.getEmployeeType().name()+"')");
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
        }
        
        public void editEmployee(Employee employee) {
            if(!Authentication.isAdmin()) {
               return; 
            }
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
                //System.out.println("eid: " + employee.getId());
                statement.executeUpdate("update 'employee' set password='"+employee.getPassword()+"' where employee_id='"+employee.getId()+"'");
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
        }
        
        // ---------------- PART
        
        public void addPart(Part part){
            
            Statement statement;
            try{
                statement = connection.createStatement();
                statement.setQueryTimeout(10);
                statement.executeUpdate("insert into 'part' (part_name, part_description,"
                        + "part_cost, specialist_cost, part_stock)values ('" + part.getName() 
                        + "','"+ part.getDescription() 
                        + "','"+ part.getCost() 
                        + "','"+ part.getSpecialistCost()
                        + "','"+ part.getStock()
                        + "');");
            }
            catch (SQLException ex){
                System.err.println(ex.getMessage());
            }
        }   
        
        public void addPartDelivery(Part part){
            
            Statement statement;
            try{
                statement = connection.createStatement();
                statement.setQueryTimeout(10);
                statement.executeUpdate("insert into 'part_delivery' (delivery_date, part_name, part_description,"
                        + "part_cost, part_stockChange)values ('" + part.getDeliveryDate()+"','"+ part.getName() 
                        + "','"+ part.getDescription() 
                        + "','"+ part.getCost() 
                        
                        + "','"+ part.getStockChange()
                        + "');");
                
            }
            catch (SQLException ex){
                System.err.println(ex.getMessage());
            }
        } 
        
        public void addPartRepair(PartRepair partRe){
            ArrayList<PartRepair> partRes = new ArrayList<>();
            Statement statement;
            try{
                statement = connection.createStatement();
                statement.setQueryTimeout(10);
                statement.executeUpdate("insert into 'part_repair' (part_Id, "
                        + "booking_id, part_installDate, part_expireDate) "
                        + "values ('"+ partRe.getPartID()+ "','"+ partRe.getBookingID()
                        + "','" + partRe.getInstallDate() + "','"+partRe.getExpireDate()
                        + "')");
            }
            catch (SQLException ex){
                System.err.println(ex.getMessage());
            }
        }
        
        
        public void editPart(Part part){
            
            Statement statement;
            try{
                statement = connection.createStatement();
                statement.setQueryTimeout(10);

                statement.executeUpdate("update 'part' set part_name = '" + part.getName()+"' where part_id = '" + part.getID() +"'"); 
                statement.executeUpdate("update 'part' set part_description = '" + part.getDescription()+"' where part_id = '" + part.getID() +"'"); 
                statement.executeUpdate("update 'part' set part_cost = '" + part.getCost()+"' where part_id = '" + part.getID() +"'"); 
                statement.executeUpdate("update 'part' set specialist_cost = '" + part.getSpecialistCost()+"' where part_id = '" + part.getID() +"'");  
                statement.executeUpdate("update 'part' set part_stock = '" + part.getStock()+"' where part_id = '" + part.getID() +"'");  
            }
            catch(SQLException ex){
                System.err.println(ex.getMessage());
            }
        }
        
        
        
        
        public void deletePart(Part part){

            Statement statement;
            try{
                
                statement = connection.createStatement();
                statement.setQueryTimeout(10);
                
                statement.executeUpdate("delete from 'part' where part_id = '"+ part.getID() +"'");

                
            }
            catch(SQLException ex){
                System.err.println(ex.getMessage());
            }
        }
        

        
//        public void deletePartDelivery(String part){
//            
//            Statement statement;
//            try{
//                
//                statement = connection.createStatement();
//                statement.setQueryTimeout(10);
//                statement.executeUpdate("delete from 'part_delivery' where part_name = '"+ part +"'");
//                    
//            }
//            catch(SQLException ex){
//                System.err.println(ex.getMessage());
//            }
//        }
        

        
        
        
        public void deletePartRepair(PartRepair partRe){
            if(partRe.getPartID() <= 0){
                return;
            }
            Statement statement;
            try{
                statement = connection.createStatement();
                statement.setQueryTimeout(10);
                statement.executeUpdate("delete from 'part_repair' where repair_ID = '"+partRe.getRepairID()+"'");
            }
            catch(SQLException ex){
                System.err.println(ex.getMessage());
            }
        }
        
        public ArrayList<Part> getParts(){
            ArrayList<Part> parts = new ArrayList<>();
            Statement statement;
            try{
                statement = connection.createStatement();
                statement.setQueryTimeout(10);
                ResultSet resultSet = statement.executeQuery("select * from 'part'");
                while(resultSet.next()){
                    int partID = resultSet.getInt("part_id");
                    String name = resultSet.getString("part_name");
                    String description = resultSet.getString("part_description");
                    double cost = resultSet.getDouble("part_cost");
                    double sCost = resultSet.getDouble("specialist_cost");
                    int stock = resultSet.getInt("part_stock");
                    
                    Part part = new Part(partID, name, description, cost, sCost, stock);
                    
                    parts.add(part);
                }
            }
            catch(SQLException ex){
                System.err.println(ex.getMessage());
            }
            return parts;
        }
        
        public ArrayList<Part> getPartDeliveries(){
            ArrayList<Part> parts = new ArrayList<>();
            Statement statement;
            try{
                statement = connection.createStatement();
                statement.setQueryTimeout(10);
                ResultSet resultSet = statement.executeQuery("select * from 'part_delivery'");
                while(resultSet.next()){
                    String date = resultSet.getNString("delivery_date");
                    String name = resultSet.getString("part_name");
                    String description = resultSet.getString("part_description");
                    double cost = resultSet.getDouble("part_cost");
                    
                    int stock = resultSet.getInt("part_stockChange");
                    
                    Part part = new Part(date, name, description, cost, stock);
                    
                    parts.add(part);
                    
                }
            }
            catch(SQLException ex){
                System.err.println(ex.getMessage());
            }
            return parts;
        }
        
        
        public void callDatabase(String query){
            Statement statement;
            try{
                statement = connection.createStatement();
                statement.setQueryTimeout(10);
                statement.executeUpdate(query); 
            }
            catch(SQLException ex){
                System.err.println(ex.getMessage());
            }
        }
        
        
        
        public String getResult(String query, String dataName){
            Statement statement;
            String result = null;
            try{
                statement = connection.createStatement();
                statement.setQueryTimeout(10);
                ResultSet resultSet = statement.executeQuery(query);
                
                result = resultSet.getString(dataName);
                
            }
            catch(SQLException ex){
                System.err.println(ex.getMessage());
            }
            return result;
        }
        
        public ArrayList<String> getResults(String query, String dataName){
            Statement statement;
            ArrayList<String> result = new ArrayList<String>();
            try{
                statement = connection.createStatement();
                statement.setQueryTimeout(10);
                ResultSet resultSet = statement.executeQuery(query);
                while(resultSet.next()){
                    String bookingID = Integer.toString(resultSet.getInt(dataName));
                    result.add(bookingID);
                    //System.out.println(bookingID);
                }
            }
            catch(SQLException ex){
                System.err.println(ex.getMessage());
            }
            return result;
        }
        
        
        public ArrayList<PartRepair> getPartRepairs(String v){
            ArrayList<PartRepair> partRes = new ArrayList<>();
            Statement statement;
            try{
                statement = connection.createStatement();
                statement.setQueryTimeout(10);
                ResultSet resultSet = statement.executeQuery("SELECT BOOKING.BOOKING_ID,PART_REPAIR.REPAIR_ID, CUSTOMER.FULL_NAME, VEHICLE.REGISTRATION_NUMBER, PART_REPAIR.PART_ID,part_repair.part_installDate, part_repair.part_expireDate " +
                "FROM (('BOOKING' INNER JOIN 'CUSTOMER' ON BOOKING.CUSTOMER_ID = CUSTOMER.CUSTOMER_ID) INNER JOIN 'VEHICLE' ON BOOKING.VEHICLE_ID = VEHICLE.VEHICLE_ID)" +
                "INNER JOIN 'PART_REPAIR' ON BOOKING.BOOKING_ID = PART_REPAIR.BOOKING_ID WHERE VEHICLE.REGISTRATION_NUMBER LIKE '%"+v+"%' OR CUSTOMER.FULL_NAME LIKE '%"+v+"%';");
                while(resultSet.next()){
                    int repairID = resultSet.getInt("repair_id");
                    int partID = resultSet.getInt("part_id");
                    int bookingID = resultSet.getInt("booking_ID");
                    String registration_number = resultSet.getString("REGISTRATION_NUMBER");
                    String full_name = resultSet.getString("FULL_NAME");
                    String installDate = resultSet.getString("part_installDate");
                    String expireDate = resultSet.getString("part_expireDate");
                    PartRepair partRe = new PartRepair(repairID,bookingID, registration_number, full_name, partID, installDate, expireDate);
                    partRes.add(partRe);
                }
            }
            catch(SQLException ex){
                System.err.println(ex.getMessage());
            }
            return partRes;
        }
        
        
        public Part getPart(int partID) {
            try{
                Statement statement;
                statement = connection.createStatement();
                statement.setQueryTimeout(10);
                ResultSet resultSet = statement.executeQuery("select * from 'part' where part_id = '"+ partID + "'");
                String name = resultSet.getString("part_name");
                String description = resultSet.getString("part_description");
                double cost = resultSet.getDouble("part_cost");
                double sCost = resultSet.getDouble("specialist_cost");
                int stock = resultSet.getInt("part_stock");
                Part part = new Part(partID,  name, description, cost, sCost, stock);
                return part;
            } catch(SQLException ex){
                System.err.println(ex.getMessage());
                return null;
            }         
            
        }   
            
        public PartRepair getPartRepair(int partID) throws SQLException {
            Statement statement;
            statement = connection.createStatement();
            statement.setQueryTimeout(10);
            ResultSet resultSet = statement.executeQuery("select * from 'part_repair' where part_id = '"+ partID + "'");
            
            int bookingID = resultSet.getInt("booking_id");
            String installDate = resultSet.getString("part_installDate");
            String expireDate = resultSet.getString("part_expireDate");
            PartRepair partRe = new PartRepair(partID, bookingID, installDate, expireDate);
            
            return partRe;
        }    
        
        public ArrayList<Part> getPartsForBooking(int b_id) {
            Statement statement;
            ArrayList<Part> parts = new ArrayList<Part>();
            try {
                statement = connection.createStatement();
                statement.setQueryTimeout(10);
                ResultSet resultSet = statement.executeQuery("select * from 'part_repair' where booking_id = '"+ b_id + "'");
                while(resultSet.next()){
                    Part p = (Database.getInstance()).getPart(resultSet.getInt("part_id"));
                    parts.add(p);
                }
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
            return parts;
        }    
       
         public ArrayList<Part> searchPart(String query) {
            ArrayList<Part> parts = new ArrayList<Part>();
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
                ResultSet resultSet = statement.executeQuery("select * from 'part' where part_id like '%" + query + "%' or part_name like '%" + query + "%'");
                while(resultSet.next()){
                    
                    int partID = resultSet.getInt("part_id");
                    String name = resultSet.getString("part_name");
                    String description= resultSet.getString("part_description");
                    double cost = resultSet.getDouble("part_cost");
                    double sCost = resultSet.getDouble("specialist_cost");
                    int stock = resultSet.getInt("part_stock");
                    Part part = new Part(partID,  name, description, cost, sCost, stock);
                    parts.add(part);
                }
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
            return parts;
        }
         
         
         
//         public ArrayList<PartRepair> searchPartRepair(String query) {
//            ArrayList<PartRepair> partRes = new ArrayList<PartRepair>();
//            Statement statement;
//            try {
//                statement = connection.createStatement();
//		statement.setQueryTimeout(10);
//                
//                ResultSet resultSet = statement.executeQuery("select * from 'part_repair' where booking_id like '%" + query + "%'");
//                while(resultSet.next()){
//                    int repairID = resultSet.getInt("repair_id");
//                    int partID = resultSet.getInt("part_id");
//                    int bookingID= resultSet.getInt("booking_id");
//                    String registration_number = Database.getInstance().
//                    String installDate = resultSet.getString("part_installDate");
//                    String expireDate = resultSet.getString("part_expireDate");
//                    PartRepair partRe = new PartRepair(repairID, bookingID, installDate, expireDate);
//                    
//                    partRes.add(partRe);
//                }
//            } catch (SQLException ex) {
//		System.err.println(ex.getMessage());
//            }
//            return partRes;
//        }
         
        
        
        

       
        
        //--------SPECIALIST 
        
        public void addSPC(SpecialistRepairCentre SPC){
            Statement statement;
            try{
                statement = connection.createStatement();
                statement.setQueryTimeout(10);
                statement.executeUpdate("insert into 'specialist_centre' (name, address, phone, email) values ('" + SPC.getName() + "','" + SPC.getAddress() + "','"+ SPC.getPhone() + "','"+ SPC.getEmail() +"')");
            }
            catch (SQLException ex){
                System.err.println(ex.getMessage());
            }
        }
        
        public void editSPC(SpecialistRepairCentre SPC) {
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);        
                
                statement.executeUpdate("update 'specialist_centre' set name = '" + SPC.getName()+"' where centreId = '" + SPC.getId() +"'"); 
                
                statement.executeUpdate("update 'specialist_centre' set address = '" + SPC.getAddress()+"' where centreId = '" + SPC.getId() +"'");
                statement.executeUpdate("update 'specialist_centre' set phone = '" + SPC.getPhone()+"' where centreId = '" + SPC.getId() +"'");
                statement.executeUpdate("update 'specialist_centre' set email = '" + SPC.getEmail()+"' where centreId = '" + SPC.getId() +"'");
                
                
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
        }
        
        public void deleteSPC(SpecialistRepairCentre SPC){
            Statement statement;
            try{
                statement = connection.createStatement();
                statement.setQueryTimeout(10);
                statement.executeUpdate("delete from 'specialist_centre' where centreId = '"+SPC.getId() + "'"); 
            }
            catch(SQLException ex){
                System.err.println(ex.getMessage());
            }
        }
        
        public ArrayList<SpecialistRepairCentre> getSpecialistCentres() {
            ArrayList<SpecialistRepairCentre> centres = new ArrayList<SpecialistRepairCentre>();
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
                ResultSet resultSet = statement.executeQuery("select * from 'specialist_centre'");
                while(resultSet.next()){
                    int centreId = resultSet.getInt("centreId");
                    String centreName = resultSet.getString("name");
                    String centreAddress= resultSet.getString("address");
                    String centrePhone= resultSet.getString("phone");
                    String centreEmail= resultSet.getString("email");
                    SpecialistRepairCentre centre = new SpecialistRepairCentre(centreId, centreName, centreAddress, centrePhone, centreEmail);
                    centres.add(centre);
                }
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
            return centres;
        }
        
        public SpecialistRepairCentre getCentre(String query) {
            SpecialistRepairCentre centre = null;
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
                ResultSet resultSet = statement.executeQuery(query);
                
                    int centreId = resultSet.getInt("centreId");
                    String centreName = resultSet.getString("name");
                    String centreAddress= resultSet.getString("address");
                    String centrePhone= resultSet.getString("phone");
                    String centreEmail= resultSet.getString("email");
                    centre = new SpecialistRepairCentre(centreId, centreName, centreAddress, centrePhone, centreEmail);
                
                
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
            return centre;
        }
        
        public void addSpcBooking(SpecialistRepair SR){
            Statement statement;
            try{
                statement = connection.createStatement();
                statement.setQueryTimeout(10);
                statement.executeUpdate("insert into 'specialist_bookings' (booking_id, customer_id, vehicle_id, centreId, repairType, part_id, deliveryDate, returnDate, status, repairCost ) values ('" + SR.getBookingId() + "','" + SR.getCustomerId() + "','"+ SR.getVehicleId() + "','"+ SR.getCentreId() + "','"+ SR.getRepairType() + "','"+ SR.getPartsId() + "','"+ SR.getDeliveryDate() + "','"+ SR.getReturnDate() + "','" + SR.getStatus() + "','" + SR.getRepairCost() + "')");
            }
            catch (SQLException ex){
                System.err.println(ex.getMessage());
            }
        }
        
        public boolean bookingIsSpeacialist(int booking_id){
            Statement statement;
            int total = 0;
            try{
                statement = connection.createStatement();
                statement.setQueryTimeout(10);
                ResultSet resultSet = statement.executeQuery("select * from 'specialist_bookings' where booking_id='"+booking_id+"'");
                while (resultSet.next()) {
                    total += 1;
                }
            }
            catch (SQLException ex){
                System.err.println(ex.getMessage());
            }
            return total != 0;
        }
        
        public void editSpcBooking(String query) {
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);        
                
                statement.executeUpdate(query); 
                
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
        }
        
        public void deleteSpcBooking(String query){
            Statement statement;
            try{
                statement = connection.createStatement();
                statement.setQueryTimeout(10);
                statement.executeUpdate(query); 
            }
            catch(SQLException ex){
                System.err.println(ex.getMessage());
            }
        }
        
        public SpecialistRepair getSpcBooking(String query) {
            SpecialistRepair booking = null;
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
                ResultSet resultSet = statement.executeQuery(query);
                
                    int repairID = resultSet.getInt("repairID");
                    int bookingID = resultSet.getInt("booking_id");
                    int customerID = resultSet.getInt(3);
                    String customerName=resultSet.getString("full_name");
                    String regNo= resultSet.getString("registration_number");
                    int vehicleID = resultSet.getInt(4);
                    int centreID = resultSet.getInt("centreId");
                    int partID = resultSet.getInt("part_id");
                    String delDate = resultSet.getString("deliveryDate");
                    String retDate = resultSet.getString("returnDate");
                    String repairType = resultSet.getString("repairType");
                    String status = resultSet.getString("status");
                    double repairCost = resultSet.getDouble("repairCost");
                    booking = new SpecialistRepair(repairID, bookingID, customerID, vehicleID, centreID, repairType, partID, delDate, retDate, status, customerName, regNo, repairCost);
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
            return booking;
        }
        
        public ArrayList<SpecialistRepair> getSpcBookings(String query) {
            ArrayList<SpecialistRepair> bookings = new ArrayList<SpecialistRepair>();
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
                ResultSet resultSet = statement.executeQuery(query);
                while(resultSet.next()){
                    int repairID = resultSet.getInt("repairID");
                    int bookingID = resultSet.getInt("booking_id");
                    int customerID = resultSet.getInt(3);
                    String customerName=resultSet.getString("full_name");
                    String regNo = resultSet.getString("registration_number");
                    int vehicleID = resultSet.getInt(4);
                    int centreID = resultSet.getInt("centreId");
                    int partID = resultSet.getInt("part_id");
                    String delDate = resultSet.getString("deliveryDate");
                    String retDate = resultSet.getString("returnDate");
                    String repairType = resultSet.getString("repairType");
                    String status = resultSet.getString("status");
                    double repairCost = resultSet.getDouble("repairCost");
                    SpecialistRepair repair = new SpecialistRepair(repairID, bookingID, customerID, vehicleID, centreID, repairType, partID, delDate, retDate, status, customerName, regNo, repairCost);
                    bookings.add(repair);
                }
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
            return bookings;
        }
        
        public PartRepair getPartRepair(int partID, int bookingID){
            PartRepair partRe = null;
            Statement statement;
            try{
            statement = connection.createStatement();
            statement.setQueryTimeout(10);
            ResultSet resultSet = statement.executeQuery("select * from 'part_repair' where part_id = '"+ partID + "' AND booking_id = '"+ bookingID +"'");
            
            String installDate = resultSet.getString("part_installDate");
            String expireDate = resultSet.getString("part_expireDate");
            partRe = new PartRepair(partID, bookingID, installDate, expireDate);
            }
            catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
            return partRe;
        }  
        
        public void deletePartRepair(int partID, int bookingID) {
            try {
            Statement statement;
            statement = connection.createStatement();
            statement.setQueryTimeout(10);
            statement.executeUpdate("delete from 'part_repair' where part_id = '"+ partID + "' AND booking_id = '"+ bookingID +"'");
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }         
            } 
        
        public void editStock(int stock, int partID) {
            Statement statement;
            stock = stock + 1; 
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
            statement.executeUpdate("update 'part' set part_stock = '" + stock +"' where part_id = '" + partID +"'"); 
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            } 
        }
        
        public ArrayList<SpecialistRepair> getCompletedSpcBookings(String query) {
            ArrayList<SpecialistRepair> bookings = new ArrayList<SpecialistRepair>();
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
                ResultSet resultSet = statement.executeQuery(query);
                while(resultSet.next()){
                    int repairID = resultSet.getInt("repairID");
                    int bookingID = resultSet.getInt("booking_id");
                    int customerID = resultSet.getInt("customer_id");
                    int vehicleID = resultSet.getInt("vehicle_id");
                    int centreID = resultSet.getInt("centreId");
                    int partID = resultSet.getInt("part_id");
                    String delDate = resultSet.getString("deliveryDate");
                    String retDate = resultSet.getString("returnDate");
                    String repairType = resultSet.getString("repairType");
                    String status = resultSet.getString("status");
                    double repairCost = resultSet.getDouble("repairCost");
                    SpecialistRepair repair = new SpecialistRepair(repairID, bookingID, customerID, vehicleID, centreID, repairType, partID, delDate, retDate, status, repairCost);
                    bookings.add(repair);
                }
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
            return bookings;
        }
        
        public SpecialistRepair getCompletedSpcBooking(String query) {
            SpecialistRepair booking = null;
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
                ResultSet resultSet = statement.executeQuery(query);
                
                    int repairID = resultSet.getInt("repairID");
                    int bookingID = resultSet.getInt("booking_id");
                    int customerID = resultSet.getInt("customer_id");
                    int vehicleID = resultSet.getInt("vehicle_id");
                    int centreID = resultSet.getInt("centreId");
                    int partID = resultSet.getInt("part_id");
                    String delDate = resultSet.getString("deliveryDate");
                    String retDate = resultSet.getString("returnDate");
                    String repairType = resultSet.getString("repairType");
                    String status = resultSet.getString("status");
                    double repairCost = resultSet.getDouble("repairCost");
                    booking = new SpecialistRepair(repairID, bookingID, customerID, vehicleID, centreID, repairType, partID, delDate, retDate, status, repairCost);
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
            return booking;
        }
        
        //-----------------BOOKING
        
         public void addBooking(Booking bookings) {
            ArrayList<Booking> booking = new ArrayList<Booking>();
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
                statement.executeUpdate("insert into 'booking' (customer_id, vehicle_id, employee_id, booking_type, booking_date, return_date, booking_time, hours_worked, amount_paid) values ('" + bookings.getCustomerID() + "','" + bookings.getVehicleID() + "','" + bookings.getEmployeeID() + "','" + bookings.getBookingType() + "','" + bookings.getBookingDate()+ "','" + bookings.getReturnDate() + "','" + bookings.getBookingTime()+ "','" + bookings.getHoursWorked()+ "','" + bookings.getAmountPaid()+ "')");
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            } 
        }
        
        public void editBooking(Booking bookings) {
            ArrayList<Booking> booking = new ArrayList<Booking>();
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
                statement.executeUpdate("update 'booking' set customer_id = '" + bookings.getCustomerID()+ "',vehicle_id = '" + bookings.getVehicleID()  + "',employee_id = '" + bookings.getEmployeeID() + "',booking_type = '" + bookings.getBookingType() + "',booking_date = '" + bookings.getBookingDate()+  "',return_date = '" + bookings.getReturnDate()+ "',booking_time = '" + bookings.getBookingTime() +"',amount_paid='"+bookings.getAmountPaid()+"' where booking_ID = '" + bookings.getBookingID() + "'");
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
        }
        
        public void editBookingHours(Booking bookings){
        if(bookings.getBookingID() <= 0) {
                return;
            }
            ArrayList<Booking> booking = new ArrayList<Booking>();
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
                statement.executeUpdate("update 'booking' set employee_id = '" + bookings.getEmployeeID()+ "',hours_worked = '" + bookings.getHoursWorked()+"' where booking_id = '" + bookings.getBookingID() + "'");
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
        
        }
        
        public void deleteBooking(Booking bookings) {
            if(bookings.getBookingID() <= 0) {
                return;
            }
            ArrayList<Booking> booking = new ArrayList<Booking>();
            Statement statement;
            
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
                statement.executeUpdate("delete from 'booking' where booking_id ='" + bookings.getBookingID() + "'");
                //statement.executeUpdate("CREATE TRIGGER DELETE_booking AFTER DELETE ON booking BEGIN DELETE FROM bill WHERE booking_id=old.booking_id; DELETE FROM specialist_bookings WHERE booking_id=old.booking_id; DELETE FROM part_repair WHERE booking_id=old.booking_id; END");
                statement.executeUpdate("delete from 'bill' where booking_id='"+bookings.getBookingID()+"'");
                statement.executeUpdate("delete from 'specialist_bookings' where booking_id='"+bookings.getBookingID()+"'");
                statement.executeUpdate("delete from 'part_repair' where booking_id='"+bookings.getBookingID()+"'");
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
        }
        
        public ArrayList<Booking> getBookingsForCustomer(Customer customer) {
            Statement statement;
            ArrayList<Booking> bookings = new ArrayList<Booking>();
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
                ResultSet resultSet = statement.executeQuery("select * from 'booking' where customer_id='"+customer.getId()+"'");
                while(resultSet.next()){
                    int bookingID = resultSet.getInt("booking_id");
                    int customerID= resultSet.getInt("customer_id");
                    int vehicleID= resultSet.getInt("vehicle_id");
                    int employeeID= resultSet.getInt("employee_id");
                    String bookingType= resultSet.getString("booking_type");
                    String bookingDate= resultSet.getString("booking_date");
                    String bookingTime=resultSet.getString("booking_time");
                    String returnDate=resultSet.getString("return_date");
                    int hoursWorked=resultSet.getInt("hours_worked");
                    double amountPaid=resultSet.getInt("amount_paid");
                    Booking booking = new Booking(bookingID, customerID, vehicleID,employeeID, bookingType, bookingDate, returnDate, bookingTime, hoursWorked, amountPaid);
                    bookings.add(booking);
                }
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
            return bookings;
        }
        
        
         public ArrayList<Booking> getBookings() {
            ArrayList<Booking> booking = new ArrayList<Booking>();
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
                ResultSet resultSet = statement.executeQuery("select * from 'booking'");
                while(resultSet.next()){
                    int bookingID = resultSet.getInt("booking_id");
                    int customerID= resultSet.getInt("customer_id");
                    int vehicleID= resultSet.getInt("vehicle_id");
                    int employeeID= resultSet.getInt("employee_id");
                    String bookingType= resultSet.getString("booking_type");
                    String bookingDate= resultSet.getString("booking_date");
                    String bookingTime=resultSet.getString("booking_time");
                    String returnDate=resultSet.getString("return_date");
                    int hoursWorked=resultSet.getInt("hours_worked");
                    double amountPaid=resultSet.getInt("amount_paid");
                    Booking bookings = new Booking(bookingID, customerID, vehicleID,employeeID, bookingType, bookingDate, returnDate, bookingTime, hoursWorked, amountPaid);
                    booking.add(bookings);
                }
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
            return booking;
        }
         
          public ArrayList<Booking> getBookingsandCustomer() {
            ArrayList<Booking> booking = new ArrayList<Booking>();
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
              // ResultSet resultSet = statement.executeQuery("select booking.*, customer.customer_id, vehicle.vehicle_id from booking, customer, vehicle where booking.customer_id = customer.customer_id and booking.vehicle_id = vehicle.vehicle_id");
               ResultSet resultSet = statement.executeQuery("SELECT * FROM ('booking' Inner Join 'customer' on booking.customer_id = customer.customer_id) Inner Join 'vehicle' on booking.vehicle_id=vehicle.vehicle_id");
               //ResultSet resultSet = statement.executeQuery("select * from booking INNER JOIN customer on booking.customer_id=customer.customer_id");
               while(resultSet.next()){
                    int bookingID = resultSet.getInt("booking_id");
                    int customerID= resultSet.getInt(2);
                    int vehicleID= resultSet.getInt(3);
                    String customerName=resultSet.getString("full_name");
                    int employeeID= resultSet.getInt("employee_id");
                    String bookingType= resultSet.getString("booking_type");
                    String regNum= resultSet.getString("registration_number");
                    String model= resultSet.getString("make");
                    String bookingDate= resultSet.getString("booking_date");
                    String bookingTime=resultSet.getString("booking_time");
                    String returnDate=resultSet.getString("return_date");
                    Booking bookings = new Booking(bookingID, customerID, vehicleID,employeeID, bookingType, bookingDate, returnDate, bookingTime, customerName, regNum, model);
                    booking.add(bookings);
                }
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
            return booking;
        }
        
       public ArrayList<Booking> getFutureBookingsandCustomer() {
            ArrayList<Booking> booking = new ArrayList<Booking>();
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
              
               ResultSet resultSet = statement.executeQuery("SELECT * FROM ('booking' Inner Join 'customer' on booking.customer_id = customer.customer_id) Inner Join 'vehicle' on booking.vehicle_id=vehicle.vehicle_id and booking_date > date('now')");
              
               while(resultSet.next()){
                    int bookingID = resultSet.getInt("booking_id");
                    int customerID= resultSet.getInt(2);
                    int vehicleID= resultSet.getInt(3);
                    String customerName=resultSet.getString("full_name");
                    int employeeID= resultSet.getInt("employee_id");
                    String bookingType= resultSet.getString("booking_type");
                    String regNum= resultSet.getString("registration_number");
                    String model= resultSet.getString("make");
                    String bookingDate= resultSet.getString("booking_date");
                    String bookingTime=resultSet.getString("booking_time");
                    String returnDate=resultSet.getString("return_date");
                    Booking bookings = new Booking(bookingID, customerID, vehicleID,employeeID, bookingType, bookingDate, returnDate, bookingTime, customerName, regNum, model);
                    booking.add(bookings);
                }
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
            return booking;
        } 

         
        
        public Booking getBooking(int BookingID) {
            Booking bookings = null;
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
                ResultSet resultSet = statement.executeQuery("select * from 'booking' where booking_id='" + BookingID + "'");
                
                int bookingID = resultSet.getInt("booking_ID");
                    int customerID= resultSet.getInt("customer_id");
                    int vehicleID= resultSet.getInt("vehicle_id");
                    int employeeID= resultSet.getInt("employee_id");
                    String bookingType= resultSet.getString("booking_type");
                    String bookingDate= resultSet.getString("booking_date");
                    String bookingTime=resultSet.getString("booking_time");
                    String returnDate=resultSet.getString("return_date");
                    int hoursWorked=resultSet.getInt("hours_worked");
                    double amountPaid=resultSet.getInt("amount_paid");
                    bookings = new Booking(bookingID, customerID, vehicleID,employeeID, bookingType, bookingDate, returnDate, bookingTime, hoursWorked, amountPaid);
                
                
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
            return bookings;
        }
         
             public ArrayList<Booking> searchBookings(String query) {
            ArrayList<Booking> bookings = new ArrayList<Booking>();
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
                ResultSet resultSet = statement.executeQuery("select * from 'booking', "
                        + "'customer', 'vehicle' where booking.customer_id=customer.customer_id and "
                        + "booking.vehicle_id=vehicle.vehicle_id and (customer.full_name like '%" + 
                        query + "%' or vehicle.registration_number like '%" + query + "%' or vehicle.make "
                                + "like '%" + query + "%')");
                while(resultSet.next()){
                    
                    
                    int bookingID = resultSet.getInt("booking_id");
                    int customerID= resultSet.getInt(2);
                    int vehicleID= resultSet.getInt(3);
                    String customerName=resultSet.getString("full_name");
                    String regNumber=resultSet.getString("registration_number");
                    int employeeID= resultSet.getInt("employee_id");
                    String model= resultSet.getString("make");
                    String bookingType= resultSet.getString("booking_type");
                    String bookingDate= resultSet.getString("booking_date");
                    String bookingTime=resultSet.getString("booking_time");
                    String returnDate=resultSet.getString("return_date");
                    Booking booking = new Booking(bookingID, customerID, vehicleID,employeeID, bookingType, bookingDate,returnDate, bookingTime, customerName, regNumber, model);
                    bookings.add(booking);
                }
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
            return bookings;
        }
             //for bookings:
              public int getVehicleMileage(int VehicleID) {
            int mileage=0;
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
                ResultSet resultSet = statement.executeQuery("select current_mileage from 'vehicle' where vehicle_id='" + VehicleID + "'");
                
               mileage=resultSet.getInt("current_mileage");
                    
                
                
            } catch (SQLException ex) {
		return mileage;
            }
            return mileage;
        }
             
              //For Bookings:
           public void editVehicleMileage(Vehicle vehicles){
        
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
                statement.executeUpdate("update 'vehicle' set current_mileage = '" + vehicles.getCurrentMileage()+"' where vehicle_id = '" + vehicles.getVehicleID()  + "'");
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
        
        }
           //for bookings:
            public Vehicle getVehicleForCustomer(int customerid) {
            Vehicle vehicle = null;
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
                ResultSet resultSet = statement.executeQuery("select * from 'vehicle' where customer_id='" + customerid + "'");                
                int VehicleId = resultSet.getInt("vehicle_id");
                String Vehicle_Type = resultSet.getString("vehicle_type");
                String RegistrationNumber = resultSet.getString("registration_number");
                String Model = resultSet.getString("model");
                String Make = resultSet.getString("make");
                String EngineSize = resultSet.getString("engine_size");
                String FuelType = resultSet.getString("fuel_type");
                String Colour = resultSet.getString("colour");
                String MotRenewalDate = resultSet.getString("mot_renewal_date");
                int CurrentMileage = resultSet.getInt("current_mileage");
                String LastServiceDate = resultSet.getString("last_service_date");
                int Warranty =  resultSet.getInt("warranty"); //resultSet.getBoolean("warranty");
                String NameOfWarrantyCompany = resultSet.getString("name_of_warranty_company");
                String AddressOfWarrantyCompany = resultSet.getString("address_of_warranty_company");          
                String ExpiryDateOfWarrantyCompany = resultSet.getString("expiry_date_of_warranty_company");
                int CustomerID =resultSet.getInt("customer_id");
                
                VehicleType vehicleType = VehicleType.Car;                   
                if(Vehicle_Type.equals("Van")) {
                   vehicleType = VehicleType.Van;
                }else if(Vehicle_Type.equals("Truck")){
                    vehicleType = VehicleType.Truck;
                }
                vehicle = new Vehicle(VehicleId, vehicleType, RegistrationNumber, Model, Make, EngineSize, FuelType, Colour, MotRenewalDate, CurrentMileage, LastServiceDate, Warranty, NameOfWarrantyCompany, AddressOfWarrantyCompany, ExpiryDateOfWarrantyCompany, CustomerID);
            } catch (SQLException ex) {
		
            }
            return vehicle;
        }
          //for bookings:
             public ArrayList<Integer> getPartID(int bookingID) throws SQLException{
            ArrayList<Integer> partsID=new ArrayList<Integer>();
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
                ResultSet resultSet = statement.executeQuery("select part_id from 'part_repair' where booking_id = '"+ bookingID + "'");
                while(resultSet.next()){
                partsID.add(resultSet.getInt("part_id"));
                }
            } catch (SQLException ex) {
		return partsID;
            }
            return partsID;
        }
             
             //for bookings:
             
              public ArrayList<Booking> getPastBookingsandCustomer() {
            ArrayList<Booking> booking = new ArrayList<Booking>();
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
              
               ResultSet resultSet = statement.executeQuery("SELECT * FROM ('booking' Inner Join 'customer' on booking.customer_id = customer.customer_id) Inner Join 'vehicle' on booking.vehicle_id=vehicle.vehicle_id and return_date < date('now')");
              
               while(resultSet.next()){
                    int bookingID = resultSet.getInt("booking_id");
                    int customerID= resultSet.getInt(2);
                    int vehicleID= resultSet.getInt(3);
                    String customerName=resultSet.getString("full_name");
                    int employeeID= resultSet.getInt("employee_id");
                    String bookingType= resultSet.getString("booking_type");
                    String regNum= resultSet.getString("registration_number");
                    String model= resultSet.getString("make");
                    String bookingDate= resultSet.getString("booking_date");
                    String bookingTime=resultSet.getString("booking_time");
                    String returnDate=resultSet.getString("return_date");
                    Booking bookings = new Booking(bookingID, customerID, vehicleID,employeeID, bookingType, bookingDate, returnDate, bookingTime, customerName, regNum, model);
                    booking.add(bookings);
                }
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
            return booking;
        } 
              
              //for bookings:
              
                  public ArrayList<Booking> getHourlyBookingsandCustomer(String Hours) {
            ArrayList<Booking> booking = new ArrayList<Booking>();
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
              
               ResultSet resultSet = statement.executeQuery("SELECT * FROM ('booking' Inner Join 'customer' on booking.customer_id = customer.customer_id) Inner Join 'vehicle' on booking.vehicle_id=vehicle.vehicle_id");
              
               while(resultSet.next()){
                    int bookingID = resultSet.getInt("booking_id");
                    int customerID= resultSet.getInt(2);
                    int vehicleID= resultSet.getInt(3);
                    String customerName=resultSet.getString("full_name");
                    int employeeID= resultSet.getInt("employee_id");
                    String bookingType= resultSet.getString("booking_type");
                    String regNum= resultSet.getString("registration_number");
                    String model= resultSet.getString("make");
                    String bookingDate= resultSet.getString("booking_date");
                    String bookingTime=resultSet.getString("booking_time");
                    String returnDate=resultSet.getString("return_date");
                    if(bookingTime.substring(0,2).contains(Hours)){
                    Booking bookings = new Booking(bookingID, customerID, vehicleID,employeeID, bookingType, bookingDate, returnDate, bookingTime, customerName, regNum, model);
                    booking.add(bookings);
                    }
                    
                  
                }
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
            return booking;
        } 
                  
              //for bookings:
              
                  public ArrayList<Booking> getMonthlyBookingsandCustomer(String month) {
            ArrayList<Booking> booking = new ArrayList<Booking>();
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
              
               ResultSet resultSet = statement.executeQuery("SELECT * FROM ('booking' Inner Join 'customer' on booking.customer_id = customer.customer_id) Inner Join 'vehicle' on booking.vehicle_id=vehicle.vehicle_id");
              
               while(resultSet.next()){
                    int bookingID = resultSet.getInt("booking_id");
                    int customerID= resultSet.getInt(2);
                    int vehicleID= resultSet.getInt(3);
                    String customerName=resultSet.getString("full_name");
                    int employeeID= resultSet.getInt("employee_id");
                    String bookingType= resultSet.getString("booking_type");
                    String regNum= resultSet.getString("registration_number");
                    String model= resultSet.getString("make");
                    String bookingDate= resultSet.getString("booking_date");
                    String bookingTime=resultSet.getString("booking_time");
                    String returnDate=resultSet.getString("return_date");
                    
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                    LocalDate localDate = LocalDate.parse(bookingDate, formatter);
                    String check= localDate.getMonth().toString();
                    
                    if(check.equalsIgnoreCase(month)){
                    Booking bookings = new Booking(bookingID, customerID, vehicleID,employeeID, bookingType, bookingDate, returnDate, bookingTime, customerName, regNum, model);
                    booking.add(bookings);
                    }
                    
                  
                }
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
            return booking;
        } 
        
          //for bookings:
                    public ArrayList<Booking> getDailyBookingsandCustomer(String day) {
            ArrayList<Booking> booking = new ArrayList<Booking>();
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
              
               ResultSet resultSet = statement.executeQuery("SELECT * FROM ('booking' Inner Join 'customer' on booking.customer_id = customer.customer_id) Inner Join 'vehicle' on booking.vehicle_id=vehicle.vehicle_id");
              
               while(resultSet.next()){
                    int bookingID = resultSet.getInt("booking_id");
                    int customerID= resultSet.getInt(2);
                    int vehicleID= resultSet.getInt(3);
                    String customerName=resultSet.getString("full_name");
                    int employeeID= resultSet.getInt("employee_id");
                    String bookingType= resultSet.getString("booking_type");
                    String regNum= resultSet.getString("registration_number");
                    String model= resultSet.getString("make");
                    String bookingDate= resultSet.getString("booking_date");
                    String bookingTime=resultSet.getString("booking_time");
                    String returnDate=resultSet.getString("return_date");
                    
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                    LocalDate localDate = LocalDate.parse(bookingDate, formatter);
                    String check= localDate.getDayOfWeek().toString();
                    
                    if(check.equalsIgnoreCase(day)){
                    Booking bookings = new Booking(bookingID, customerID, vehicleID,employeeID, bookingType, bookingDate, returnDate, bookingTime, customerName, regNum, model);
                    booking.add(bookings);
                    }
                    
                  
                }
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
            return booking;
        } 
         
           
        
         //--------------------------------------------------------- VEHICLE
        
           public void addVehicle(Vehicle vehicle) {
            ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
                statement.executeUpdate("insert into 'vehicle' (vehicle_type, registration_number, model, make, engine_size, fuel_type, colour, mot_renewal_date, current_mileage, last_service_date, warranty, name_of_warranty_company, address_of_warranty_company, expiry_date_of_warranty_company, customer_id) values ('" + vehicle.getVehicleType().name() + "','" + vehicle.getRegistrationNumber() + "','" + vehicle.getModel() + "','" + vehicle.getMake() + "','" + vehicle.getEngineSize() + "','" + vehicle.getFuelType() + "','"+ vehicle.getColour() + "','" + vehicle.getMotRenewalDate()+"','" + vehicle.getCurrentMileage() + "','" + vehicle.getLastServiceDate() +  "','" + vehicle.getWarranty() + "','" + vehicle.getNameOfWarrantyCompany() + "','" + vehicle.getAddressOfWarrantyCompany() + "','" + vehicle.getExpiryDateOfWarrantyCompany() + "','" + vehicle.getCustomerID() + "')"); 
                //String sql = "INSERT INTO vehicle (vehicle_type, registration_number, model, make, engine_size, fuel_type, colour, mot_renewal_date, current_mileage, last_service_date, ) VALUES (?,?,?,?,?,?,?,?,?,?)";
                //statement.executeUpdate(sql);
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            } 
           }
       
            
           public void editVehicle(Vehicle vehicle) {
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
                statement.executeUpdate("update 'vehicle' set  vehicle_type = '" + vehicle.getVehicleType().name() + "',registration_number = '" + vehicle.getRegistrationNumber() + "',model = '" + vehicle.getModel() + "',make = '" + vehicle.getMake() + "',engine_size = '" + vehicle.getEngineSize() + "',fuel_type = '" + vehicle.getFuelType() + "', colour = '" + vehicle.getColour() + "', mot_renewal_date = '" + vehicle.getMotRenewalDate() + "', current_mileage = '" + vehicle.getCurrentMileage() + "', last_service_date = '" + vehicle.getLastServiceDate() + "', warranty = '" + vehicle.getWarranty() + "', name_of_warranty_company = '" + vehicle.getNameOfWarrantyCompany() +  "', address_of_warranty_company = '" + vehicle.getAddressOfWarrantyCompany() +  "', expiry_date_of_warranty_company = '" + vehicle.getExpiryDateOfWarrantyCompany() +  "', customer_id = '" + vehicle.getCustomerID() + "' where vehicle_id = '" + vehicle.getVehicleId()  + "'");
		//System.err.println(ex.getMessage());
            
              } catch (SQLException ex) {
                  System.out.println("Hello");
		System.err.println(ex.getMessage());
            } 
          }
           
          
           
            public void deleteVehicle(Vehicle vehicle) {
            ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
                statement.executeUpdate("delete from 'vehicle' where vehicle_id ='" + vehicle.getVehicleID() + "'");
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
           }
            
            public ArrayList<Vehicle> getVehicles() {
            ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
                ResultSet resultSet = statement.executeQuery("select * from 'vehicle'");
                while(resultSet.next()){
                    int VehicleId = resultSet.getInt("vehicle_id");
                    String Vehicle_Type = resultSet.getString("vehicle_type");
                    String RegistrationNumber = resultSet.getString("registration_number");
                    String Model = resultSet.getString("model");
                    String Make = resultSet.getString("make");
                    String EngineSize = resultSet.getString("engine_size");
                    String FuelType = resultSet.getString("fuel_type");
                    String Colour = resultSet.getString("colour");
                    String MotRenewalDate = resultSet.getString("mot_renewal_date");
                    int CurrentMileage = resultSet.getInt("current_mileage");
                    String LastServiceDate = resultSet.getString("last_service_date");
                    int Warranty = resultSet.getInt("warranty");
                    String NameOfWarrantyCompany = resultSet.getString("name_of_warranty_company");
                    String AddressOfWarrantyCompany = resultSet.getString("address_of_warranty_company");          
                    String ExpiryDateOfWarrantyCompany = resultSet.getString("expiry_date_of_warranty_company");
                    int CustomerID =resultSet.getInt("customer_id");
                    
                    VehicleType vehicleType = VehicleType.Car;                   
                    if(Vehicle_Type.equals("Van")) {
                        vehicleType = VehicleType.Van;
                    }else if(Vehicle_Type.equals("Truck")){
                        vehicleType = VehicleType.Truck;
                    }
                    Vehicle vehicle = new Vehicle(VehicleId, vehicleType, RegistrationNumber, Model, Make, EngineSize, FuelType, Colour, MotRenewalDate, CurrentMileage, LastServiceDate, Warranty, NameOfWarrantyCompany, AddressOfWarrantyCompany, ExpiryDateOfWarrantyCompany, CustomerID);
                    vehicles.add(vehicle);
                }
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
            return vehicles;
        }
            
            public ArrayList<Vehicle> getVehiclesForCustomer(int id) {
            ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
                ResultSet resultSet = statement.executeQuery("select * from 'vehicle' where customer_id='"+id+"'");
                while(resultSet.next()){
                    int VehicleId = resultSet.getInt("vehicle_id");
                    String Vehicle_Type = resultSet.getString("vehicle_type");
                    String RegistrationNumber = resultSet.getString("registration_number");
                    String Model = resultSet.getString("model");
                    String Make = resultSet.getString("make");
                    String EngineSize = resultSet.getString("engine_size");
                    String FuelType = resultSet.getString("fuel_type");
                    String Colour = resultSet.getString("colour");
                    String MotRenewalDate = resultSet.getString("mot_renewal_date");
                    int CurrentMileage = resultSet.getInt("current_mileage");
                    String LastServiceDate = resultSet.getString("last_service_date");
                    int Warranty = resultSet.getInt("warranty");
                    String NameOfWarrantyCompany = resultSet.getString("name_of_warranty_company");
                    String AddressOfWarrantyCompany = resultSet.getString("address_of_warranty_company");          
                    String ExpiryDateOfWarrantyCompany = resultSet.getString("expiry_date_of_warranty_company");
                    int CustomerID =resultSet.getInt("customer_id");
                    
                    VehicleType vehicleType = VehicleType.Car;                   
                    if(Vehicle_Type.equals("Van")) {
                        vehicleType = VehicleType.Van;
                    }else if(Vehicle_Type.equals("Truck")){
                        vehicleType = VehicleType.Truck;
                    }
                    Vehicle vehicle = new Vehicle(VehicleId, vehicleType, RegistrationNumber, Model, Make, EngineSize, FuelType, Colour, MotRenewalDate, CurrentMileage, LastServiceDate, Warranty, NameOfWarrantyCompany, AddressOfWarrantyCompany, ExpiryDateOfWarrantyCompany, CustomerID);
                    vehicles.add(vehicle);
                }
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
            return vehicles;
        }
        
        public Vehicle getVehicle(int vehicleId) {
            Vehicle vehicle = null;
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
                ResultSet resultSet = statement.executeQuery("select * from 'vehicle' where vehicle_id='" + vehicleId + "'");                
                int VehicleId = resultSet.getInt("vehicle_id");
                String Vehicle_Type = resultSet.getString("vehicle_type");
                String RegistrationNumber = resultSet.getString("registration_number");
                String Model = resultSet.getString("model");
                String Make = resultSet.getString("make");
                String EngineSize = resultSet.getString("engine_size");
                String FuelType = resultSet.getString("fuel_type");
                String Colour = resultSet.getString("colour");
                String MotRenewalDate = resultSet.getString("mot_renewal_date");
                int CurrentMileage = resultSet.getInt("current_mileage");
                String LastServiceDate = resultSet.getString("last_service_date");
                int Warranty = resultSet.getInt("warranty");
                String NameOfWarrantyCompany = resultSet.getString("name_of_warranty_company");
                String AddressOfWarrantyCompany = resultSet.getString("address_of_warranty_company");          
                String ExpiryDateOfWarrantyCompany = resultSet.getString("expiry_date_of_warranty_company");
                int CustomerID =resultSet.getInt("customer_id");
                
                VehicleType vehicleType = VehicleType.Car;                   
                if(Vehicle_Type.equals("Van")) {
                   vehicleType = VehicleType.Van;
                }else if(Vehicle_Type.equals("Truck")){
                    vehicleType = VehicleType.Truck;
                }
                vehicle = new Vehicle(VehicleId, vehicleType, RegistrationNumber, Model, Make, EngineSize, FuelType, Colour, MotRenewalDate, CurrentMileage, LastServiceDate, Warranty, NameOfWarrantyCompany, AddressOfWarrantyCompany, ExpiryDateOfWarrantyCompany, CustomerID);
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
            return vehicle;
        }
          
            public ArrayList<Vehicle> searchVehicles(String query) {
             ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
                ResultSet resultSet = statement.executeQuery("select * from 'vehicle' where registration_number like '%" + query + "%' or make like '%" + query + "%' or vehicle_type like '%" + query + "%'");
                while(resultSet.next()){
                    int VehicleId = resultSet.getInt("vehicle_id");
                    String Vehicle_Type = resultSet.getString("vehicle_type");
                    String RegistrationNumber = resultSet.getString("registration_number");
                    String Model = resultSet.getString("model");
                    String Make = resultSet.getString("make");
                    String EngineSize = resultSet.getString("engine_size");
                    String FuelType = resultSet.getString("fuel_type");
                    String Colour = resultSet.getString("colour");
                    String MotRenewalDate = resultSet.getString("mot_renewal_date");
                    int CurrentMileage = resultSet.getInt("current_mileage");
                    String LastServiceDate = resultSet.getString("last_service_date");
                    int Warranty = resultSet.getInt("warranty");
                    String NameOfWarrantyCompany = resultSet.getString("name_of_warranty_company");
                    String AddressOfWarrantyCompany = resultSet.getString("address_of_warranty_company");          
                    String ExpiryDateOfWarrantyCompany = resultSet.getString("expiry_date_of_warranty_company");
                    int CustomerID =resultSet.getInt("customer_id");
                    
                    VehicleType vehicleType = VehicleType.Car;                   
                    if(Vehicle_Type.equals("Van")) {
                        vehicleType = VehicleType.Van;
                    }else if(Vehicle_Type.equals("Truck")){
                        vehicleType = VehicleType.Truck;
                    }
                    Vehicle vehicle = new Vehicle(VehicleId, vehicleType, RegistrationNumber,Model,Make, EngineSize, FuelType, Colour, MotRenewalDate, CurrentMileage, LastServiceDate, Warranty, NameOfWarrantyCompany, AddressOfWarrantyCompany, ExpiryDateOfWarrantyCompany, CustomerID);
                    vehicles.add(vehicle);
                }
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
            return vehicles;
        }
            
         public ArrayList<VehicleTemplates> getVehicleTemplates() {
            ArrayList<VehicleTemplates> vehicleTemplates = new ArrayList<VehicleTemplates>();
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
                ResultSet resultSet = statement.executeQuery("select * from 'vehicle_templates'");
                while(resultSet.next()){
                    int VehicleTemplateId = resultSet.getInt("vehicle_template_ID");
                    String Vehicle_Type = resultSet.getString("vehicle_type");
                    String Model = resultSet.getString("model");
                    String Make = resultSet.getString("make");
                    String EngineSize = resultSet.getString("engine_size");
                    String FuelType = resultSet.getString("fuel_type");
                    
                    VehicleType vehicleType = VehicleType.Car;                   
                    if(Vehicle_Type.equals("Van")) {
                        vehicleType = VehicleType.Van;
                    }else if(Vehicle_Type.equals("Truck")){
                        vehicleType = VehicleType.Truck;
                    }
                    VehicleTemplates vehicleTemp = new VehicleTemplates(VehicleTemplateId, vehicleType, Make, Model, EngineSize, FuelType);
                    vehicleTemplates.add(vehicleTemp);
                }
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
            return vehicleTemplates;
        }
            
            public VehicleTemplates getVehicleTemplate(int vehicleTemplateID) {
            VehicleTemplates vehicleTemplates = null;
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
                ResultSet resultSet = statement.executeQuery("select * from 'vehicle_templates' where vehicle_template_ID='" + vehicleTemplateID + "'");                
                int VehicleTemplateId = resultSet.getInt("vehicle_template_ID");
                String Vehicle_Type = resultSet.getString("vehicle_type");
                String Model = resultSet.getString("model");
                String Make = resultSet.getString("make");
                String EngineSize = resultSet.getString("engine_size");
                String FuelType = resultSet.getString("fuel_type");
                
                
                VehicleType vehicleType = VehicleType.Car;                   
                if(Vehicle_Type.equals("Van")) {
                   vehicleType = VehicleType.Van;
                }else if(Vehicle_Type.equals("Truck")){
                    vehicleType = VehicleType.Truck;
                }
                
                vehicleTemplates = new VehicleTemplates(VehicleTemplateId, vehicleType, Model, Make, EngineSize, FuelType);
                
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
            return vehicleTemplates;
        }
        public ArrayList<Booking> getBookingsForVehicle(Vehicle vehicle) {
            Statement statement;
            ArrayList<Booking> bookings = new ArrayList<Booking>();
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
                ResultSet resultSet = statement.executeQuery("select * from 'booking' where vehicle_id='"+vehicle.getVehicleID()+"'");
                while(resultSet.next()){
                    int bookingID = resultSet.getInt("booking_ID");
                    int customerID= resultSet.getInt("customer_id");
                    int vehicleID= resultSet.getInt("vehicle_id");
                    int employeeID= resultSet.getInt("employee_id");
                    String bookingType= resultSet.getString("booking_type");
                    String bookingDate= resultSet.getString("booking_date");
                    String bookingTime=resultSet.getString("booking_time");
                    String returnDate=resultSet.getString("return_date");
                    int hoursWorked=resultSet.getInt("hours_worked");
                    double amountPaid=resultSet.getInt("amount_paid");
                    Booking booking = new Booking(bookingID, customerID, vehicleID,employeeID, bookingType, bookingDate, returnDate, bookingTime, hoursWorked, amountPaid);
                    bookings.add(booking);
                }
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
            return bookings;
        }
        
        public ArrayList<Part> getPartsForVehicle(Vehicle vehicle) {
            Statement statement;
            ArrayList<Part> parts = new ArrayList<Part>();
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
                ResultSet resultSet = statement.executeQuery("SELECT part_repair.part_id FROM "
                        + "(booking INNER JOIN part_repair ON booking.booking_id=part_repair.booking_id) "
                        + "INNER JOIN vehicle ON booking.vehicle_id=vehicle.vehicle_ID where vehicle.vehicle_ID='"+vehicle.getVehicleID()+"'");
                ArrayList<Integer> partIDs = new ArrayList<Integer>();
                while(resultSet.next()){
                    partIDs.add(resultSet.getInt("part_id"));
                }
                for(int i = 0; i< partIDs.size(); i++){
                    int partID = partIDs.get(i);
                    ResultSet resultSet1 = statement.executeQuery("select * from part where part_id = '"+partID+"'");
                    String partName = resultSet1.getString("part_name");
                    String partDescription = resultSet1.getString("part_description");
                    Double cost = resultSet1.getDouble("part_cost");
                    Double scost = resultSet1.getDouble("specialist_cost");
                    Part part = new Part(partName,partDescription,cost, scost);
                    parts.add(part);
                    
                }
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
            return parts;
        }
                  
        public ArrayList<Booking> getFutureBookingsandVehicle(Vehicle vehicle) {
            ArrayList<Booking> booking = new ArrayList<Booking>();
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
              
               ResultSet resultSet = statement.executeQuery("SELECT * FROM ('booking' Inner Join 'customer' on booking.customer_id = customer.customer_id) Inner Join 'vehicle' on booking.vehicle_id=vehicle.vehicle_id and booking_date > date('now')");// where vehicle_id ='" + vehicle.getVehicleID() + "'");
              
               while(resultSet.next()){
                    int bookingID = resultSet.getInt("booking_ID");
                    int customerID= resultSet.getInt(2);
                    int vehicleID= resultSet.getInt(3);
                    String customerName=resultSet.getString("full_name");
                    int employeeID= resultSet.getInt("employee_id");
                    String bookingType= resultSet.getString("booking_type");
                    String regNum= resultSet.getString("registration_number");
                    String model= resultSet.getString("make");
                    String bookingDate= resultSet.getString("booking_date");
                    String bookingTime=resultSet.getString("booking_time");
                    String returnDate=resultSet.getString("return_date");
                    Booking bookings = new Booking(bookingID, customerID, vehicleID,employeeID, bookingType, bookingDate, returnDate, bookingTime, customerName, regNum, model);
                    booking.add(bookings);
                }
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
            return booking;
        } 
         
                  public ArrayList<Booking> getPastBookingsandVehicle(Vehicle vehicle) {
            ArrayList<Booking> booking = new ArrayList<Booking>();
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
              
               ResultSet resultSet = statement.executeQuery("SELECT * FROM ('booking' Inner Join 'customer' on booking.customer_id = customer.customer_id) Inner Join 'vehicle' on booking.vehicle_id=vehicle.vehicle_id and booking_date < date('now')");// + "' where vehicle_id ='" + vehicle.getVehicleId() + "'");// where vehicle_id ='" + vehicle.getVehicleID() + "'");
              
               while(resultSet.next()){
                    int bookingID = resultSet.getInt("booking_ID");
                    int customerID= resultSet.getInt(2);
                    int vehicleID= resultSet.getInt(3);
                    String customerName=resultSet.getString("full_name");
                    int employeeID= resultSet.getInt("employee_id");
                    String bookingType= resultSet.getString("booking_type");
                    String regNum= resultSet.getString("registration_number");
                    String model= resultSet.getString("make");
                    String bookingDate= resultSet.getString("booking_date");
                    String bookingTime=resultSet.getString("booking_time");
                    String returnDate=resultSet.getString("return_date");  
                    Booking bookings = new Booking(bookingID, customerID, vehicleID,employeeID, bookingType, bookingDate, returnDate, bookingTime, customerName, regNum, model);
                    booking.add(bookings);
                }
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
            return booking;
        } 
         public ArrayList<Booking> getPastBookingsForVehicle(ObservableList<Booking> pastBookings, Vehicle vehicle) {
            ArrayList<Booking> booking = new ArrayList<Booking>();
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
              
             ResultSet resultSet = statement.executeQuery("select * from 'booking' where vehicle_id='" + vehicle.getVehicleID() + "'");                            
               while(resultSet.next()){
                    int bookingID = resultSet.getInt("booking_ID");
                    int customerID= resultSet.getInt(2);
                    int vehicleID= resultSet.getInt(3);
                    String customerName=resultSet.getString("full_name");
                    int employeeID= resultSet.getInt("employee_id");
                    String bookingType= resultSet.getString("booking_type");
                    String regNum= resultSet.getString("registration_number");
                    String model= resultSet.getString("make");
                    String bookingDate= resultSet.getString("booking_date");
                    String bookingTime=resultSet.getString("booking_time");
                    String returnDate=resultSet.getString("return_date");
                    Booking bookings = new Booking(bookingID, customerID, vehicleID,employeeID, bookingType, bookingDate, returnDate, bookingTime, customerName, regNum, model);
                    booking.add(bookings);
                }
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
            return booking;
        } 
           public ArrayList<Booking> searchBookingsVehicle(int query) {
            ArrayList<Booking> booking = new ArrayList<Booking>();
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
              
               ResultSet resultSet = statement.executeQuery("select * from 'booking' where vehicle_id like '%" + query + "%'");
              
               while(resultSet.next()){

                    int bookingID = resultSet.getInt("booking_ID");
                    int customerID= resultSet.getInt(2);
                    int vehicleID= resultSet.getInt(3);
                    //String customerName=resultSet.getString("full_name");
                    int employeeID= resultSet.getInt("employee_id");
                    String bookingType= resultSet.getString("booking_type");
                   // String regNum= resultSet.getString("registration_number");
                   //String model= resultSet.getString("make");
                    String bookingDate= resultSet.getString("booking_date");
                    String bookingTime=resultSet.getString("booking_time");
                    String returnDate=resultSet.getString("return_date");
                    int hoursWorked = resultSet.getInt("hours_worked");
                    int amountPaid = resultSet.getInt("amount_paid");
                       // public Booking(int bookingIdentification,int customerIdentification, int vehicleIdentification, int employeeIdentification, String bookingType, String bookingDate, String returnBooking, String bookingTime,  int hours, double amountPaid){
                    Booking bookings = new Booking(bookingID, customerID, vehicleID,employeeID, bookingType, bookingDate, returnDate,bookingTime, hoursWorked, amountPaid);
                    booking.add(bookings);
                }
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
            return booking;
        } 
         public ArrayList<PartRepair> getPartsPerVehicle(Booking booking) {
            ArrayList<PartRepair> partrepair = new ArrayList<PartRepair>();
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
              
               ResultSet resultSet = statement.executeQuery("SELECT * FROM ('part_repair' Inner Join 'part' on part.part_id = part.part_id) Inner Join 'booking' on part_repair.booking_id=booking.booking_id");// + "' where vehicle_id ='" + vehicle.getVehicleId() + "'");// where vehicle_id ='" + vehicle.getVehicleID() + "'");
              
               while(resultSet.next()){
                    int partID = resultSet.getInt("part_id");
                    int partRepairID= resultSet.getInt(2);
                    int bookingID= resultSet.getInt(3);
                    String customerName=resultSet.getString("full_name");
                    int employeeID= resultSet.getInt("employee_id");
                    String bookingType= resultSet.getString("booking_type");
                    String regNum= resultSet.getString("registration_number");
                    String model= resultSet.getString("make");
                    String bookingDate= resultSet.getString("booking_date");
                    String bookingTime=resultSet.getString("booking_time");
                    String returnDate=resultSet.getString("return_date");  
                    PartRepair partRepair = new PartRepair(bookingID, partID);//employeeID, bookingType, bookingDate, returnDate, bookingTime, customerName, regNum, model);
                    partrepair.add(partRepair);
                }
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
            return partrepair;
        } 
                                  
     //-----------------------------------  
        
        public void createTables() {
            Statement statement;
            try {
                statement = connection.createStatement();
		statement.setQueryTimeout(10);
		statement.executeUpdate("create table if not exists 'customer' ('customer_id' integer primary key, 'full_name' string, 'address' string, 'postcode' string, 'phone' string, 'email' string, 'type' string)");
                statement.executeUpdate("create table if not exists 'bill' ('bill_id' integer primary key, 'booking_id' integer, 'amount_paid' double)");
                statement.executeUpdate("create table if not exists 'employee' ('employee_id' integer primary key, 'first_name' string, 'last_name' string, 'password' string, 'type' string, 'wage' double)");
                statement.executeUpdate("create table if not exists 'part_repair' ('part_id' integer, 'booking_id' integer, 'part_installDate' string, 'part_expireDate' string)");
                statement.executeUpdate("create table if not exists 'part' ('part_id' integer primary key, 'part_name' string, 'part_description' string,  'part_cost' double, 'specialist_cost'double, 'part_stock' integer)");
                statement.executeUpdate("create table if not exists 'part_delivery' ('delivery_date' string ,'part_name' string, 'part_description' string,  'part_cost' double, 'part_stockChange'integer)");
                statement.executeUpdate("create table if not exists 'booking' ('booking_id' integer primary key, 'customer_id' integer, 'vehicle_id' integer,'employee_id' integer,'booking_type' string, 'booking_date' string not null default 'yyyy-mm-dd', 'return_date' string not null default 'yyyy-mm-dd', 'booking_time' string not null default 'hh:mm', 'hours_worked' integer,'amount_paid' double)"); 
                statement.executeUpdate("create table if not exists 'vehicle' ('vehicle_ID' integer primary key,'vehicle_type' string,'registration_number' string, 'model' string, 'make' string, 'engine_size' string, 'fuel_type' string, 'colour' string, 'mot_renewal_date' timestamp, 'current_mileage' integer, 'last_service_date' timestamp, 'warranty' int, 'name_of_warranty_company' string, 'address_of_warranty_company' string,'expiry_date_of_warranty_company' timestamp,'customer_id' integer)");
                statement.executeUpdate("create table if not exists 'vehicle_templates' ('vehicle_template_ID' integer primary key,'vehicle_type' string, 'make' string, 'model' string, 'engine_size' string, 'fuel_type' string)");
                statement.executeUpdate("create table if not exists 'specialist_centre' ('centreID' integer primary key NOT NULL, 'name' string NOT NULL, 'address' string NOT NULL, 'phone' string NOT NULL,'email' string NOT NULL)");
                statement.executeUpdate("create table if not exists 'specialist_bookings' ('repairID' integer primary key NOT NULL, 'booking_id' integer NOT NULL, 'customer_id' integer NOT NULL, 'vehicle_id' integer NOT NULL, 'centreID' integer NOT NULL, 'part_id' integer NOT NULL, 'deliveryDate' string NOT NULL,'returnDate' string NOT NULL, 'status' string NOT NULL, 'repairType' string NOT NULL, 'repairCost' double NOT NULL)");
                
                addSampleEmployees();
                addSampleCustomers();
                addSampleVehicles();
                addSampleVehicleTemplates();
                
            } catch (SQLException ex) {
		System.err.println(ex.getMessage());
            }
        }
	
	public void test_database(){
		Statement statement;
		try {
			statement = connection.createStatement();
			statement.setQueryTimeout(10);
			statement.executeUpdate("drop table if exists `test`");
			statement.executeUpdate("create table `test` (`id` integer, `name` string)");
			statement.executeUpdate("insert into `test` values('1', 'test 1')");
			statement.executeUpdate("insert into `test` values('2', 'test 2')");
			ResultSet rs = statement.executeQuery("select * from `test`");
			System.out.println("id	name");
			while(rs.next()){
				System.out.println(rs.getInt("id")+"	"+rs.getString("name"));
			}
		}
		catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
		finally {
			if (connection != null){
				try{
					connection.close();
				}
				catch(SQLException ex){
					System.err.println(ex.getMessage());
				}
			}
		}
	}
        
         public void addSampleEmployees() {
            try {
                Statement statement = connection.createStatement();
                statement.setQueryTimeout(10);
                statement.executeUpdate("insert or ignore into 'employee' ('employee_id','first_name', 'last_name', 'password', 'wage', 'type') values ('1','John','McCain','pwd1','13.0','Admin')");
                statement.executeUpdate("insert or ignore into 'employee' ('employee_id','first_name', 'last_name', 'password', 'wage', 'type') values ('2','Jane','Carls','pwd2','14.0','User')");
                statement.executeUpdate("insert or ignore into 'employee' ('employee_id','first_name', 'last_name', 'password', 'wage', 'type') values ('3','Mark','Jamies','pwd3','12.0','User')");
                statement.executeUpdate("insert or ignore into 'employee' ('employee_id','first_name', 'last_name', 'password', 'wage', 'type') values ('4','Luke','Clinton','pwd4','13.0','User')");
            } catch(SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        
        public void addSampleCustomers() {
            try {
                Statement statement = connection.createStatement();
                statement.setQueryTimeout(10);
                statement.executeUpdate("insert or ignore into 'customer' ('customer_id','full_name', 'address', 'postcode', 'phone', 'email', 'type')values('1','Matteo Cassia','450 Mile End Road','E1 4GG','0781458793489','matteo.cassia@me.com','Private')");
                statement.executeUpdate("insert or ignore into 'customer' ('customer_id','full_name', 'address', 'postcode', 'phone', 'email', 'type')values('2','Jane Frank','315 Funny Street','AB 123','07889348902380745','jane.frank@gmail.com','Private')");
                statement.executeUpdate("insert or ignore into 'customer' ('customer_id','full_name', 'address', 'postcode', 'phone', 'email', 'type')values('3','Mark Markey','1 Nice Lane','SW 2GB','07882398023903','mark.markey@ey.eu','Private')");
                statement.executeUpdate("insert or ignore into 'customer' ('customer_id','full_name', 'address', 'postcode', 'phone', 'email', 'type')values('4','Microsoft','1 Microsoft Way','MC 456','07890243984578','us@microsoft.com','Business')");
                statement.executeUpdate("insert or ignore into 'customer' ('customer_id','full_name', 'address', 'postcode', 'phone', 'email', 'type')values('5','Apple','1 Infinite Loop','AP 369','07897834598','apple@orange.banana','Business')");   
                statement.executeUpdate("insert or ignore into 'customer' ('customer_id','full_name', 'address', 'postcode', 'phone', 'email', 'type')values('6','Carlota Ortiz','450 Mile End Road','E1 4GG','078783479','c@rlota.com','Private')");
                statement.executeUpdate("insert or ignore into 'customer' ('customer_id','full_name', 'address', 'postcode', 'phone', 'email', 'type')values('7','Antonio Banderas','3459 Beaumont Street','NE 321','0788934893','a.banderas@qmul.ac.uk','Private')");
                statement.executeUpdate("insert or ignore into 'customer' ('customer_id','full_name', 'address', 'postcode', 'phone', 'email', 'type')values('8','Jenny from the Block','32 Fooled by the Rocks Avenue','RF189','0789783498','j.lo@me.com','Private')");
                statement.executeUpdate("insert or ignore into 'customer' ('customer_id','full_name', 'address', 'postcode', 'phone', 'email', 'type')values('9','Fernanda Fernanda','64 Fernanda Lane','67GBF','07812098923546','fernanda@fernanda.com','Private')");
                statement.executeUpdate("insert or ignore into 'customer' ('customer_id','full_name', 'address', 'postcode', 'phone', 'email', 'type')values('10','Beats by Dre','12 Beats Way','F67HG','0788934973','dre@beats.com','Business')");
            } catch(SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        
        public void addSampleVehicles() {
            try {
                Statement statement = connection.createStatement();
                statement.setQueryTimeout(10);
                //Vehicle(VehicleType vehicleType, String RegistrationNumber, String model, String make, String EngineSize, String FuelType, String colour, String MotRenewalDate, int CurrentMileage, String LastServiceDate,int Warranty, String NameOfWarrantyCompany, String AddressOfWarrantyCompany, String ExpiryDateOfWarrantyCompany, int CustomerID){
                System.out.println("TEST: ADD SAMPLE VEHICLES");
                statement.executeUpdate("insert or ignore into 'vehicle' ('vehicle_id', 'vehicle_type', 'registration_number', 'model', 'make', 'engine_size', 'fuel_type', 'colour', 'mot_renewal_date', 'current_mileage', 'last_service_date', 'warranty', 'name_of_warranty_company', 'address_of_warranty_company', 'expiry_date_of_warranty_company', 'customer_id')values('1','Car','BM12TPU', '325D', 'BMW','2.5','Diesel','Red', '2017-03-12', '919', '2017-01-11', '1', 'Greenland Ltd', '1,Crescent Avenue, Uxbridge, UX5 6UP' , '2018-08-11','4')");
                statement.executeUpdate("insert or ignore into 'vehicle' ('vehicle_id', 'vehicle_type', 'registration_number', 'model', 'make', 'engine_size', 'fuel_type', 'colour', 'mot_renewal_date', 'current_mileage', 'last_service_date', 'warranty', 'name_of_warranty_company', 'address_of_warranty_company', 'expiry_date_of_warranty_company', 'customer_id')values('2','Truck','YR11OIJ', 'Fullback', 'Fiat','2.0','Petrol','White', '2019-03-09', '89976', '2017-03-01', '0', ' ', ' ' , ' ', '4')");

                statement.executeUpdate("insert or ignore into 'vehicle' ('vehicle_id', 'vehicle_type', 'registration_number', 'model', 'make', 'engine_size', 'fuel_type', 'colour', 'mot_renewal_date', 'current_mileage', 'last_service_date', 'warranty', 'name_of_warranty_company', 'address_of_warranty_company', 'expiry_date_of_warranty_company', 'customer_id')values('3','Truck','QW12ERT', 'Ranger', 'Ford','2.2','Diesel','Grey', '2018-04-01', '5971', '2017-03-08', '0', ' ', ' ' , ' ','2')"); 
                              statement.executeUpdate("insert or ignore into 'vehicle' ('vehicle_id', 'vehicle_type', 'registration_number', 'model', 'make', 'engine_size', 'fuel_type', 'colour', 'mot_renewal_date', 'current_mileage', 'last_service_date', 'warranty', 'name_of_warranty_company', 'address_of_warranty_company', 'expiry_date_of_warranty_company', 'customer_id')values('4', 'Car', 'OL11IMP', 'Tacoma', 'Toytota', '2.3', 'Petrol', 'Blue', '2017-09-01', '1195', '2017-02-26', '0', ' ', ' ', ' ','2')"); 
                statement.executeUpdate("insert or ignore into 'vehicle' ('vehicle_id', 'vehicle_type', 'registration_number', 'model', 'make', 'engine_size', 'fuel_type', 'colour', 'mot_renewal_date', 'current_mileage', 'last_service_date', 'warranty', 'name_of_warranty_company', 'address_of_warranty_company', 'expiry_date_of_warranty_company', 'customer_id')values('5','Car','YT13NVZ', 'Fiesta', 'Ford','2.2','Petrol','White', '2017-05-20', '2190', '2017-03-06', '1', 'Wellington', '34,Barn Avenue, Nottingham, NU5 6PO', '2017-08-24','1')");
                  statement.executeUpdate("insert or ignore into 'vehicle' ('vehicle_id', 'vehicle_type', 'registration_number', 'model', 'make', 'engine_size', 'fuel_type', 'colour', 'mot_renewal_date', 'current_mileage', 'last_service_date', 'warranty', 'name_of_warranty_company', 'address_of_warranty_company', 'expiry_date_of_warranty_company', 'customer_id')values('6', 'Van', 'LK17WPU', 'Transit', 'Ford', '2.6', 'Diesel', 'Maroon', '2017-09-01', '907', '2017-01-05', '0', ' ', ' ', ' ','2')"); 
                statement.executeUpdate("insert or ignore into 'vehicle' ('vehicle_id', 'vehicle_type', 'registration_number', 'model', 'make', 'engine_size', 'fuel_type', 'colour', 'mot_renewal_date', 'current_mileage', 'last_service_date', 'warranty', 'name_of_warranty_company', 'address_of_warranty_company', 'expiry_date_of_warranty_company', 'customer_id')values('7','Car','KP13RUN', 'Panda', 'Fiat','2.0','Petrol','Red', '2018-02-16', '95384', '2017-23-01', '0', ' ', ' ' , ' ', '3')");
                statement.executeUpdate("insert or ignore into 'vehicle' ('vehicle_id', 'vehicle_type', 'registration_number', 'model', 'make', 'engine_size', 'fuel_type', 'colour', 'mot_renewal_date', 'current_mileage', 'last_service_date', 'warranty', 'name_of_warranty_company', 'address_of_warranty_company', 'expiry_date_of_warranty_company', 'customer_id')values('8','Car','JK06QWE', 'Polo', 'Volkswagen','2.2','Petrol','White', '2018-04-13', '6000', '2017-02-27', '1', 'Riverside Ltd', '13,Redman Avenue, Isleworth, TW9 7PU' , '2020-03-01','8')");
                statement.executeUpdate("insert or ignore into 'vehicle' ('vehicle_id', 'vehicle_type', 'registration_number', 'model', 'make', 'engine_size', 'fuel_type', 'colour', 'mot_renewal_date', 'current_mileage', 'last_service_date', 'warranty', 'name_of_warranty_company', 'address_of_warranty_company', 'expiry_date_of_warranty_company', 'customer_id')values('9','Van','LM14QWE', 'Vito', 'Mercedes','2.5','Diesel','Black', '2018-03-03', '16783', '2017-03-01', '0', ' ', ' ' , ' ', '10')");
                statement.executeUpdate("insert or ignore into 'vehicle' ('vehicle_id', 'vehicle_type', 'registration_number', 'model', 'make', 'engine_size', 'fuel_type', 'colour', 'mot_renewal_date', 'current_mileage', 'last_service_date', 'warranty', 'name_of_warranty_company', 'address_of_warranty_company', 'expiry_date_of_warranty_company', 'customer_id')values('10','Truck', 'FI09UYT', 'Fullback', 'Fiat','2.0','Petrol','Blue', '2019-04-01', '921', '2017-02-11', '0', ' ', ' ' , ' ', '9')");
                statement.executeUpdate("insert or ignore into 'vehicle' ('vehicle_id', 'vehicle_type', 'registration_number', 'model', 'make', 'engine_size', 'fuel_type', 'colour', 'mot_renewal_date', 'current_mileage', 'last_service_date', 'warranty', 'name_of_warranty_company', 'address_of_warranty_company', 'expiry_date_of_warranty_company', 'customer_id')values('11', 'Car', 'VK13KAL', 'Golf', 'Volkswagen', '2.6', 'Diesel', 'White', '2017-09-03', '1123', '2017-01-04','1', 'Greenland Ltd', '1,Crescent Avenue, Uxbridge, UX5 6UP' , '2018-08-11','7')");
                statement.executeUpdate("insert or ignore into 'vehicle' ('vehicle_id', 'vehicle_type', 'registration_number', 'model', 'make', 'engine_size', 'fuel_type', 'colour', 'mot_renewal_date', 'current_mileage', 'last_service_date', 'warranty', 'name_of_warranty_company', 'address_of_warranty_company', 'expiry_date_of_warranty_company', 'customer_id')values('12','Car','BK05LOZ', 'Polo', 'Volkswagen','2.2','Petrol','Pink', '2018-02-02', '19812', '2017-02-21', '1', 'Riverside Ltd', '13,Redman Avenue, Isleworth, TW9 7PU' , '2020-03-01','6')");
                statement.executeUpdate("insert or ignore into 'vehicle' ('vehicle_id', 'vehicle_type', 'registration_number', 'model', 'make', 'engine_size', 'fuel_type', 'colour', 'mot_renewal_date', 'current_mileage', 'last_service_date', 'warranty', 'name_of_warranty_company', 'address_of_warranty_company', 'expiry_date_of_warranty_company', 'customer_id')values('13','Van','TR14NCO', 'Vita', 'Mercedes','2.5','Diesel','Grey', '2018-11-03', '790', '2017-02-01', '0', ' ', ' ' , ' ', '5')");
                statement.executeUpdate("insert or ignore into 'vehicle' ('vehicle_id', 'vehicle_type', 'registration_number', 'model', 'make', 'engine_size', 'fuel_type', 'colour', 'mot_renewal_date', 'current_mileage', 'last_service_date', 'warranty', 'name_of_warranty_company', 'address_of_warranty_company', 'expiry_date_of_warranty_company', 'customer_id')values('14', 'Car', 'FX12XPU', 'Auris', 'Toytota', '2.3', 'Petrol', 'White', '2017-08-02', '1189', '2017-02-21', '0', ' ', ' ', ' ','5')"); 
                statement.executeUpdate("insert or ignore into 'vehicle' ('vehicle_id', 'vehicle_type', 'registration_number', 'model', 'make', 'engine_size', 'fuel_type', 'colour', 'mot_renewal_date', 'current_mileage', 'last_service_date', 'warranty', 'name_of_warranty_company', 'address_of_warranty_company', 'expiry_date_of_warranty_company', 'customer_id')values('15','Car','BM16FJZ', '320D', 'BMW','2.3','Diesel','Green', '2018-03-12', '893', '2017-01-11', '1', 'Greenland Ltd', '1,Crescent Avenue, Uxbridge, UX5 6UP' , '2019-09-19','4')");
                
                
                 } catch(SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        
         public void addSampleVehicleTemplates() {
            try {
                Statement statement = connection.createStatement();
                statement.setQueryTimeout(10);
                //Vehicle(VehicleType vehicleType, String RegistrationNumber, String model, String make, String EngineSize, String FuelType, String colour, String MotRenewalDate, int CurrentMileage, String LastServiceDate,int Warranty, String NameOfWarrantyCompany, String AddressOfWarrantyCompany, String ExpiryDateOfWarrantyCompany, int CustomerID){
                System.out.println("TEST: ADD SAMPLE VEHICLE TEMPLATES");
                statement.executeUpdate("insert or ignore into 'vehicle_templates' ('vehicle_template_ID', 'vehicle_type', 'make', 'model', 'engine_size', 'fuel_type')values('1','New Vehicle ', ' ', ' ',' ',' ')");
                statement.executeUpdate("insert or ignore into 'vehicle_templates' ('vehicle_template_ID', 'vehicle_type', 'make', 'model', 'engine_size', 'fuel_type')values('2','Car', 'BMW', '325D','2.5','Diesel')");
                statement.executeUpdate("insert or ignore into 'vehicle_templates' ('vehicle_template_ID', 'vehicle_type', 'make', 'model', 'engine_size', 'fuel_type')values('3','Car', 'BMW', '320D','2.3','Diesel')");
                statement.executeUpdate("insert or ignore into 'vehicle_templates' ('vehicle_template_ID', 'vehicle_type', 'make', 'model', 'engine_size', 'fuel_type')values('4','Car', 'BMW', '520D','2.5','Diesel')");
                statement.executeUpdate("insert or ignore into 'vehicle_templates' ('vehicle_template_ID', 'vehicle_type', 'make', 'model', 'engine_size', 'fuel_type')values('5','Car', 'BMW', 'I8','2.0','Electric')");
                statement.executeUpdate("insert or ignore into 'vehicle_templates' ('vehicle_template_ID', 'vehicle_type', 'make', 'model', 'engine_size', 'fuel_type')values('6','Car', 'Audi', 'A1','2.0','Diesel')");
                statement.executeUpdate("insert or ignore into 'vehicle_templates' ('vehicle_template_ID', 'vehicle_type', 'make', 'model', 'engine_size', 'fuel_type')values('7','Car', 'Audi', 'A2','2.3','Diesel')");
                statement.executeUpdate("insert or ignore into 'vehicle_templates' ('vehicle_template_ID', 'vehicle_type', 'make', 'model', 'engine_size', 'fuel_type')values('8','Car', 'Audi', 'A3','2.5','Diesel')");
                statement.executeUpdate("insert or ignore into 'vehicle_templates' ('vehicle_template_ID', 'vehicle_type', 'make', 'model', 'engine_size', 'fuel_type')values('9','Car', 'Audi', 'R8','6.0','Diesel')");
                statement.executeUpdate("insert or ignore into 'vehicle_templates' ('vehicle_template_ID', 'vehicle_type', 'make', 'model', 'engine_size', 'fuel_type')values('10','Car', 'Ford', 'Focus','2.5','Diesel')");
                statement.executeUpdate("insert or ignore into 'vehicle_templates' ('vehicle_template_ID', 'vehicle_type', 'make', 'model', 'engine_size', 'fuel_type')values('11','Van', 'Ford', 'Transit','2.3','Diesel')");
                statement.executeUpdate("insert or ignore into 'vehicle_templates' ('vehicle_template_ID', 'vehicle_type', 'make', 'model', 'engine_size', 'fuel_type')values('12','Truck', 'Ford', 'Ranger','2.5','Diesel')");
                statement.executeUpdate("insert or ignore into 'vehicle_templates' ('vehicle_template_ID', 'vehicle_type', 'make', 'model', 'engine_size', 'fuel_type')values('13','Car', 'Ford', 'Mustang','5.0','Diesel')");    
                statement.executeUpdate("insert or ignore into 'vehicle_templates' ('vehicle_template_ID', 'vehicle_type', 'make', 'model', 'engine_size', 'fuel_type')values('14','Van', 'Mercedes', 'Vito','2.5','Diesel')");
                statement.executeUpdate("insert or ignore into 'vehicle_templates' ('vehicle_template_ID', 'vehicle_type', 'make', 'model', 'engine_size', 'fuel_type')values('15','Car', 'Mercedes', 'C-Class','3.0','Diesel')");
                statement.executeUpdate("insert or ignore into 'vehicle_templates' ('vehicle_template_ID', 'vehicle_type', 'make', 'model', 'engine_size', 'fuel_type')values('16','Car', 'Mercedes', 'A-Class','2.5','Diesel')");
                statement.executeUpdate("insert or ignore into 'vehicle_templates' ('vehicle_template_ID', 'vehicle_type', 'make', 'model', 'engine_size', 'fuel_type')values('17','Truck', 'Mercedes', 'Atego','2.3','Diesel')");   
                statement.executeUpdate("insert or ignore into 'vehicle_templates' ('vehicle_template_ID', 'vehicle_type', 'make', 'model', 'engine_size', 'fuel_type')values('18','Car', 'Fiat', 'Panda','1.8','Diesel')");
                statement.executeUpdate("insert or ignore into 'vehicle_templates' ('vehicle_template_ID', 'vehicle_type', 'make', 'model', 'engine_size', 'fuel_type')values('19','Truck', 'Fiat', 'Fullback','2.3','Diesel')");
                statement.executeUpdate("insert or ignore into 'vehicle_templates' ('vehicle_template_ID', 'vehicle_type', 'make', 'model', 'engine_size', 'fuel_type')values('20','Car', 'Fiat', 'Punto','2.0','Diesel')");
                statement.executeUpdate("insert or ignore into 'vehicle_templates' ('vehicle_template_ID', 'vehicle_type', 'make', 'model', 'engine_size', 'fuel_type')values('21','Car', 'Fiat', '124 Spider','2.6','Diesel')");               
                 } catch(SQLException e) {
                System.err.println(e.getMessage());
            }
        }

	public static Database getInstance() {
		return INSTANCE;
	}
	
	public Connection getConnection(){
		return this.connection;
	}
}