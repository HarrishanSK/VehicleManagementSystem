/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package booking;

import java.net.URL;
import java.util.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.event.*;
import Database.*;

import authentication.AuthenticationFXMLController;
import authentication.Employee;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.*;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import vehicle.*;
import customer.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Callback;
import parts.Part;

/**
 * FXML Controller class
 *
 * @author yousf
 */
public class BookingController implements Initializable {

    @FXML
    private Button deleteButton;
    @FXML
    private Button addButton;
    @FXML
    private Button editButton;
    @FXML
    private Button vehicleButton;
    @FXML
    private Button repairButton;
    @FXML
    private Button partsUsed;
    @FXML
    private Button details;

    @FXML
    private RadioButton radio_future;
    @FXML
    private RadioButton radio_past;
    @FXML
    private RadioButton radio_all;
    @FXML
    private RadioButton radio_hourly;
    @FXML
    private RadioButton radio_monthly;
    @FXML
    private RadioButton radio_daily;

    @FXML
    private TableColumn<Booking, Number> customerName;
    @FXML
    private TableColumn<Booking, Number> vehicleReg;
    @FXML
    private TableColumn<Booking, String> bookingDate;
    @FXML
    private TableColumn<Booking, String> returnBooking;
    @FXML
    private TableColumn<Booking, String> bookingTime;
    @FXML
    private TableColumn<Booking, String> bookingType;
    @FXML
    private TableColumn<Booking, String> vehicleManufacturer;

    @FXML
    private ToggleGroup tableViewing;

    @FXML
    private Button searchButton;
    @FXML
    private TextField searchField;
    Part parts;
    Employee employees;
    LocalDate dateComp;

    @FXML
    public TableView<Booking> tableView = new TableView<Booking>();
    private ObservableList<Booking> bookings = FXCollections.observableArrayList((Database.getInstance()).getBookingsandCustomer());

    private ObservableList<Booking> bookingsFuture = FXCollections.observableArrayList((Database.getInstance()).getFutureBookingsandCustomer());
    private ObservableList<Booking> bookingsPast = FXCollections.observableArrayList((Database.getInstance()).getPastBookingsandCustomer());
    private ObservableList<Booking> bookingsHourly;
    private ObservableList<Booking> bookingsDaily;
    private ObservableList<Booking> bookingsWeekly;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        customerName.setCellValueFactory(new PropertyValueFactory<>("FullName"));

        vehicleReg.setCellValueFactory(new PropertyValueFactory<>("RegistrationNumber"));

        bookingDate.setCellValueFactory(new PropertyValueFactory<>("BookingDate"));

        returnBooking.setCellValueFactory(new PropertyValueFactory<>("ReturnDate"));

        bookingTime.setCellValueFactory(new PropertyValueFactory<>("BookingTime"));

        bookingType.setCellValueFactory(new PropertyValueFactory<>("BookingType"));

        vehicleManufacturer.setCellValueFactory(new PropertyValueFactory<>("Manufacturer"));

        tableView.setEditable(true);

        loadTable();

        tableViewing = new ToggleGroup();
        radio_all.setToggleGroup(tableViewing);
        radio_past.setToggleGroup(tableViewing);
        radio_future.setToggleGroup(tableViewing);
        radio_hourly.setToggleGroup(tableViewing);
        radio_daily.setToggleGroup(tableViewing);
        radio_monthly.setToggleGroup(tableViewing);
        radio_all.setSelected(true);
        tableViewing.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
                if (tableViewing.getSelectedToggle() == radio_all) {
                    loadTable();
                } else if (tableViewing.getSelectedToggle() == radio_past) {
                    pastBooking();

                } else if (tableViewing.getSelectedToggle() == radio_future) {
                    futureBooking();

                } else if (tableViewing.getSelectedToggle() == radio_hourly) {
                    List<String> choices = new ArrayList<>();
                    choices.add("9");
                    choices.add("10");
                    choices.add("11");
                    choices.add("12");
                    choices.add("13");
                    choices.add("14");
                    choices.add("15");
                    choices.add("16");
                    choices.add("17");

                    ChoiceDialog<String> dialog = new ChoiceDialog<>("9", choices);
                    dialog.setTitle("Time Selector");
                    dialog.setHeaderText("Timing");
                    dialog.setContentText("Please choose an hour:");
                    Optional<String> result = dialog.showAndWait();
                    if(result.isPresent()){
                    result.ifPresent(letter -> hourlyBooking(letter));
                    }
                    else{
                    loadTable();
                    }
                }
                else if(tableViewing.getSelectedToggle()== radio_monthly){
                /* dateComp = LocalDate.now();
                    String week = dateComp.getMonth().toString();
                System.out.println(week);*/
                  List<String> choices = new ArrayList<>();
                    choices.add("JANUARY");
                    choices.add("FEBRUARY");
                    choices.add("MARCH");
                    choices.add("APRIL");
                    choices.add("MAY");
                    choices.add("JUNE");
                    choices.add("JULY");
                    choices.add("AUGUST");
                    choices.add("SEPTEMBER");
                    choices.add("OCTOBER");
                    choices.add("NOVEMBER");
                    choices.add("DECEMBER");

                    ChoiceDialog<String> dialog = new ChoiceDialog<>("JANUARY", choices);
                    dialog.setTitle("Month Selector");
                    dialog.setHeaderText("Months");
                    dialog.setContentText("Please choose a Month:");
                    Optional<String> result = dialog.showAndWait();
                    if(result.isPresent()){
                    result.ifPresent(letter -> monthlyBooking(letter));
                    }
                    else{
                    loadTable();
                    }
                
                }
                else if(tableViewing.getSelectedToggle()==radio_daily){
                
                List<String> choices = new ArrayList<>();
                    choices.add("MONDAY");
                    choices.add("TUESDAY");
                    choices.add("WEDNESDAY");
                    choices.add("THURSDAY");
                    choices.add("FRIDAY");
                    choices.add("SATURDAY");
                    

                    ChoiceDialog<String> dialog = new ChoiceDialog<>("MONDAY", choices);
                    dialog.setTitle("Day Selector");
                    dialog.setHeaderText("Days");
                    dialog.setContentText("Please choose a Day:");
                    Optional<String> result = dialog.showAndWait();
                    if(result.isPresent()){
                    result.ifPresent(letter -> dailyBooking(letter));
                    }
                    else{
                    loadTable();
                    }
                }
                
            }

        });

        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                deleteBooking();
            }
        });

        editButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                editBooking();
            }
        });

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                addBooking();
            }
        });

        vehicleButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                vehicleButton();
            }
        });

        repairButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                repairButton();
            }
        });

        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                search();
            }
        });

        partsUsed.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                partUsed();
            }
        });

        details.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
                if (selectedIndex >= 0) {
                    try{
                    Booking bookings = tableView.getSelectionModel().getSelectedItem();
                    employees = Database.getInstance().getEmployee(bookings.getEmployeeID());
                    ArrayList<Integer> partID = new ArrayList<Integer>();
                    try {
                        partID = Database.getInstance().getPartID(bookings.getBookingID());
                    } catch (SQLException ex) {
                        Logger.getLogger(BookingController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    int id = 0;
                    String names = "";
                    for (int i = 0; i < partID.size(); i++) {
                        if (partID.get(i) != 0) {
                            id = partID.get(i);

                            parts = Database.getInstance().getPart(id);

                            names = parts.getName() + "\n" + names;

                        }
                    }

                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setHeaderText("Booking Information");
                    alert.setTitle("Booking Details");

                    String bookingInfo = "Booking ID: " + bookings.getBookingID() + "\nCustomer Name: " + bookings.getFullName() + "\nRegistration Number: " + bookings.getRegistrationNumber() + "\nManufacturer: " + bookings.getManufacturer() + "\nMechanic Name: " + employees.getFirstName() + "\nMechanic Hours Worked: " + bookings.getHoursWorked() + "\nBooking Type: " + bookings.getBookingType() + "\nBooking Date & Time: " + bookings.getBookingDate() + " " + bookings.getBookingTime() + "\nVehicle Return Date: " + bookings.getReturnDate();
                    String split = "\n-------------------------------------------------------";
                    String partInfo = "Parts Used: \n" + names;
                    alert.setContentText(bookingInfo + split + partInfo);

                    alert.show();
                    }
                     catch(NullPointerException E){
                 Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setContentText("An error has occured whilst retrieving the information for this booking");
                    alert.showAndWait();
                 }
                } else {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setContentText("Please select a booking from the table");
                    alert.showAndWait();

                }

            }//
        });

    }

    public void loadTable() {
        bookings = FXCollections.observableArrayList((Database.getInstance()).getBookingsandCustomer());
        tableView.setItems(bookings);
    }

    public void search() {

        String query = searchField.getText();
        bookings = FXCollections.observableArrayList((Database.getInstance()).searchBookings(query));
        tableView.setItems(bookings);
    }

    public void editBooking() {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            Booking bookings = tableView.getSelectionModel().getSelectedItem();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            String date = bookings.getReturnDate();

//convert String to LocalDate
            LocalDate localDate = LocalDate.parse(date, formatter);
            dateComp = LocalDate.now();
            if (dateComp.isAfter(localDate)) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setContentText("You cannot edit past bookings. You can only edit mileage via update mileage button");
                alert.showAndWait();

            } else {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/booking/editBooking.fxml"));
                    AnchorPane page = (AnchorPane) loader.load();
                    EditBookingController editB = (EditBookingController) loader.getController();
                    editB.book = this;
                    editB.booking = tableView.getSelectionModel().getSelectedItem();
                    editB.loadBooking(bookings);
                    Stage stage = new Stage();
                    Scene scene = new Scene(page);
                    stage.setScene(scene);
                    stage.setTitle("Edit Booking");
                    stage.show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Logger.getLogger(BookingController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setContentText("Please select a booking from the table");
            alert.showAndWait();
        }
    }

    public void deleteBooking() {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Delete Booking");
            alert.setContentText("Are you sure you want to delete this?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

                if (selectedIndex >= 0) {
                    Booking bookings1 = bookings.get(selectedIndex);
                    Database.getInstance().deleteBooking(bookings1);
                    loadTable();
                }
            } else {
                //
            }
        } else {
            Alert alert1 = new Alert(AlertType.INFORMATION);
            alert1.setTitle("Error");
            alert1.setContentText("Please select a booking from the table");
            alert1.showAndWait();
        }

    }

    public void addBooking() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/booking/addBooking.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            AddBookingController addB = (AddBookingController) loader.getController();
            addB.book = this;

            Stage stage = new Stage();
            Scene scene = new Scene(page);
            stage.setScene(scene);
            stage.setTitle("Add Booking");
            stage.show();

        } catch (Exception ex) {

            Logger.getLogger(BookingController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void vehicleButton() {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            Booking bookings = tableView.getSelectionModel().getSelectedItem();
            
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/booking/VehicleDetails.fxml"));
                AnchorPane page = (AnchorPane) loader.load();
                VehicleDetailsController editB = (VehicleDetailsController) loader.getController();
                editB.books = this;
                editB.booking = tableView.getSelectionModel().getSelectedItem();
                editB.loadBooking(bookings);
                Stage stage = new Stage();
                Scene scene = new Scene(page);
                stage.setScene(scene);
                stage.setTitle("Vehicle Details");
                stage.show();

            } catch (Exception ex) {
                Logger.getLogger(BookingController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        } else {
            Alert alert1 = new Alert(AlertType.INFORMATION);
            alert1.setTitle("Error");
            alert1.setContentText("Please select a booking from the table");
            alert1.showAndWait();
        }
        

    }

    public void repairButton() {

        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            Booking bookings = tableView.getSelectionModel().getSelectedItem();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            String date = bookings.getBookingDate();

//convert String to LocalDate
            LocalDate localDate = LocalDate.parse(date, formatter);
            dateComp = LocalDate.now();
            if (dateComp.isBefore(localDate)) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setContentText("You cannot enter mechanic hours on a future booking");
                alert.showAndWait();

            } 
            else{
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/booking/RepairSection.fxml"));
                AnchorPane page = (AnchorPane) loader.load();
                RepairSectionController editB = (RepairSectionController) loader.getController();
                editB.books = this;
                editB.bookings = tableView.getSelectionModel().getSelectedItem();
                Stage stage = new Stage();
                Scene scene = new Scene(page);
                stage.setScene(scene);
                stage.setTitle("Mechanic Hours");
                stage.show();
            } catch (Exception ex) {
                Logger.getLogger(BookingController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        } else {
            Alert alert1 = new Alert(AlertType.INFORMATION);
            alert1.setTitle("Error");
            alert1.setContentText("Please select a booking from the table");
            alert1.showAndWait();

        }
    }

    public void partUsed() {

        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        System.out.println(selectedIndex);
        if (selectedIndex >= 0) {
            Booking bookings = tableView.getSelectionModel().getSelectedItem();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            String date = bookings.getBookingDate();

//convert String to LocalDate
            LocalDate localDate = LocalDate.parse(date, formatter);
            dateComp = LocalDate.now();
            if (dateComp.isBefore(localDate)) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setContentText("You cannot add a part to a future booking");
                alert.showAndWait();

            } 
            else{
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/booking/PartsUsed.fxml"));
                AnchorPane page = (AnchorPane) loader.load();
                PartsUsedController editB = (PartsUsedController) loader.getController();
                editB.books = this;
                editB.bookings = tableView.getSelectionModel().getSelectedItem();
                Stage stage = new Stage();
                Scene scene = new Scene(page);
                stage.setScene(scene);
                stage.setTitle("Parts Used");
                stage.show();
            } catch (Exception ex) {
                Logger.getLogger(BookingController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        } else {
            Alert alert1 = new Alert(AlertType.INFORMATION);
            alert1.setTitle("Error");
            alert1.setContentText("Please select a booking from the table");
            alert1.showAndWait();

        }
    }

    
    public void futureBooking() {
        customerName.setCellValueFactory(new PropertyValueFactory<>("FullName"));

        vehicleReg.setCellValueFactory(new PropertyValueFactory<>("RegistrationNumber"));

        bookingDate.setCellValueFactory(new PropertyValueFactory<>("BookingDate"));

        returnBooking.setCellValueFactory(new PropertyValueFactory<>("ReturnDate"));

        bookingTime.setCellValueFactory(new PropertyValueFactory<>("BookingTime"));

        bookingType.setCellValueFactory(new PropertyValueFactory<>("BookingType"));

        vehicleManufacturer.setCellValueFactory(new PropertyValueFactory<>("Manufacturer"));

        tableView.setEditable(true);

        bookingsFuture = FXCollections.observableArrayList((Database.getInstance()).getFutureBookingsandCustomer());
        tableView.setItems(bookingsFuture);

    }

    public void pastBooking() {
        customerName.setCellValueFactory(new PropertyValueFactory<>("FullName"));

        vehicleReg.setCellValueFactory(new PropertyValueFactory<>("RegistrationNumber"));

        bookingDate.setCellValueFactory(new PropertyValueFactory<>("BookingDate"));

        returnBooking.setCellValueFactory(new PropertyValueFactory<>("ReturnDate"));

        bookingTime.setCellValueFactory(new PropertyValueFactory<>("BookingTime"));

        bookingType.setCellValueFactory(new PropertyValueFactory<>("BookingType"));

        vehicleManufacturer.setCellValueFactory(new PropertyValueFactory<>("Manufacturer"));

        tableView.setEditable(true);

        bookingsPast = FXCollections.observableArrayList((Database.getInstance()).getPastBookingsandCustomer());
        tableView.setItems(bookingsPast);

    }

    public void hourlyBooking(String hours) {
        
        bookingsHourly = FXCollections.observableArrayList((Database.getInstance()).getHourlyBookingsandCustomer(hours));
        
        customerName.setCellValueFactory(new PropertyValueFactory<>("FullName"));

        vehicleReg.setCellValueFactory(new PropertyValueFactory<>("RegistrationNumber"));

        bookingDate.setCellValueFactory(new PropertyValueFactory<>("BookingDate"));

        returnBooking.setCellValueFactory(new PropertyValueFactory<>("ReturnDate"));

        bookingTime.setCellValueFactory(new PropertyValueFactory<>("BookingTime"));

        bookingType.setCellValueFactory(new PropertyValueFactory<>("BookingType"));

        vehicleManufacturer.setCellValueFactory(new PropertyValueFactory<>("Manufacturer"));

        tableView.setEditable(true);

        tableView.setItems(bookingsHourly);

    }

    public void monthlyBooking(String month) {
        bookingsWeekly = FXCollections.observableArrayList((Database.getInstance()).getMonthlyBookingsandCustomer(month));
        customerName.setCellValueFactory(new PropertyValueFactory<>("FullName"));

        vehicleReg.setCellValueFactory(new PropertyValueFactory<>("RegistrationNumber"));

        bookingDate.setCellValueFactory(new PropertyValueFactory<>("BookingDate"));

        returnBooking.setCellValueFactory(new PropertyValueFactory<>("ReturnDate"));

        bookingTime.setCellValueFactory(new PropertyValueFactory<>("BookingTime"));

        bookingType.setCellValueFactory(new PropertyValueFactory<>("BookingType"));

        vehicleManufacturer.setCellValueFactory(new PropertyValueFactory<>("Manufacturer"));

        tableView.setEditable(true);

        bookingsWeekly = FXCollections.observableArrayList((Database.getInstance()).getMonthlyBookingsandCustomer(month));
        tableView.setItems(bookingsWeekly);

    }

    public void dailyBooking(String day) {
        bookingsDaily = FXCollections.observableArrayList((Database.getInstance()).getDailyBookingsandCustomer(day));
        customerName.setCellValueFactory(new PropertyValueFactory<>("FullName"));

        vehicleReg.setCellValueFactory(new PropertyValueFactory<>("RegistrationNumber"));

        bookingDate.setCellValueFactory(new PropertyValueFactory<>("BookingDate"));

        returnBooking.setCellValueFactory(new PropertyValueFactory<>("ReturnDate"));

        bookingTime.setCellValueFactory(new PropertyValueFactory<>("BookingTime"));

        bookingType.setCellValueFactory(new PropertyValueFactory<>("BookingType"));

        vehicleManufacturer.setCellValueFactory(new PropertyValueFactory<>("Manufacturer"));

        tableView.setEditable(true);

        bookingsDaily = FXCollections.observableArrayList((Database.getInstance()).getDailyBookingsandCustomer(day));
        tableView.setItems(bookingsDaily);

    }
}
