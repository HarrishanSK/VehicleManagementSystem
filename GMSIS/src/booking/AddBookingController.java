/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package booking;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.collections.*;
import javafx.scene.control.*;

import Database.*;
import java.time.LocalDate;
import javafx.stage.Stage;
import customer.*;
import authentication.*;
import java.text.ParseException;
import java.time.DayOfWeek;
import static java.time.DayOfWeek.SATURDAY;
import java.time.Instant;
import java.time.Month;
import java.time.ZoneId;
import static java.time.temporal.TemporalQueries.localDate;
import java.util.Date;
import javafx.beans.binding.ListBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Callback;
import javafx.util.StringConverter;
import static org.joda.time.format.ISODateTimeFormat.date;
import vehicle.*;

/**
 * FXML Controller class
 *
 * @author Yousf
 */
public class AddBookingController implements Initializable {

    public BookingController book;
    @FXML
    private Button addButton;
    @FXML
    private Button closeButton;
    @FXML
    private DatePicker date_picker;
    @FXML
    private DatePicker date_picker1;

    @FXML
    private ChoiceBox bookingType;
    @FXML
    private ChoiceBox hour;
    @FXML
    private ChoiceBox minute;
    @FXML
    public ChoiceBox<Customer> customer_id;
    @FXML
    private ChoiceBox<Vehicle> vehicle_id;
    @FXML
    private ChoiceBox<Employee> mechanic_id;

    private ObservableList<Customer> customers = FXCollections.observableArrayList((Database.getInstance()).getCustomers());
    private ObservableList<Vehicle> vehicles;
    private ObservableList<Employee> employees = FXCollections.observableArrayList((Database.getInstance()).getEmployees());

    private boolean flag = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        date_picker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(date.isBefore(LocalDate.now()) || date.getDayOfWeek() == DayOfWeek.SUNDAY || date.isEqual(LocalDate.now()) || date.isEqual(LocalDate.of(2017, Month.APRIL, 14)) || date.isEqual(LocalDate.of(2017, Month.APRIL, 17)) || date.isEqual(LocalDate.of(2017, Month.MAY, 1)) || date.isEqual(LocalDate.of(2017, Month.MAY, 29)) || date.isEqual(LocalDate.of(2017, Month.AUGUST, 28)) || date.isEqual(LocalDate.of(2017, Month.DECEMBER, 25)) || date.isEqual(LocalDate.of(2017, Month.DECEMBER, 26)) || date.isEqual(LocalDate.of(2018, Month.JANUARY, 1)) || date.isEqual(LocalDate.of(2018, Month.MARCH, 30)) || date.isEqual(LocalDate.of(2018, Month.APRIL, 2)) || date.isEqual(LocalDate.of(2018, Month.MAY, 28)) || date.isEqual(LocalDate.of(2018, Month.AUGUST, 27)) || date.isEqual(LocalDate.of(2018, Month.DECEMBER, 25)) || date.isEqual(LocalDate.of(2018, Month.DECEMBER, 26)));

            }
        });
        date_picker.setEditable(false);

        date_picker.setShowWeekNumbers(false);
        
        date_picker1.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                try {

                    super.updateItem(date, empty);
                    setDisable(date.isBefore(LocalDate.now()) || date.isEqual(LocalDate.now()) || date.isBefore(date_picker.getValue()) || date.getDayOfWeek() == DayOfWeek.SUNDAY || date.isEqual(LocalDate.of(2017, Month.APRIL, 14)) || date.isEqual(LocalDate.of(2017, Month.APRIL, 17)) || date.isEqual(LocalDate.of(2017, Month.MAY, 1)) || date.isEqual(LocalDate.of(2017, Month.MAY, 29)) || date.isEqual(LocalDate.of(2017, Month.AUGUST, 28)) || date.isEqual(LocalDate.of(2017, Month.DECEMBER, 25)) || date.isEqual(LocalDate.of(2017, Month.DECEMBER, 26)) || date.isEqual(LocalDate.of(2018, Month.JANUARY, 1)) || date.isEqual(LocalDate.of(2018, Month.MARCH, 30)) || date.isEqual(LocalDate.of(2018, Month.APRIL, 2)) || date.isEqual(LocalDate.of(2018, Month.MAY, 28)) || date.isEqual(LocalDate.of(2018, Month.AUGUST, 27)) || date.isEqual(LocalDate.of(2018, Month.DECEMBER, 25)) || date.isEqual(LocalDate.of(2018, Month.DECEMBER, 26)));

                } catch (NullPointerException e) {

                }

            }
        });

        date_picker1.setEditable(false);
        date_picker1.setShowWeekNumbers(false);
        bookingType.getItems().addAll(FXCollections.observableArrayList("Diagnosis and Repair", "Scheduled Maintenance"));
        bookingType.getSelectionModel().selectFirst();

        customer_id.getItems().addAll(customers);
        customer_id.setConverter(new StringConverter<Customer>() {
            @Override
            public String toString(Customer object) {
                return object.getId() + ": " + object.getFullName();
            }

            @Override
            public Customer fromString(String string) {
                int userId = Integer.parseInt(string.split(": ")[0]);
                return Database.getInstance().getCustomer(userId);
            }

        });
        customer_id.getSelectionModel().selectFirst();

        mechanic_id.setItems(employees);
        mechanic_id.setConverter(new StringConverter<Employee>() {
            @Override
            public String toString(Employee object) {
                return object.getId() + ": " + object.getFirstName();
            }

            @Override
            public Employee fromString(String string) {
                int userId = Integer.parseInt(string.split(": ")[0]);
                return Database.getInstance().getEmployee(userId);
            }

        });
        mechanic_id.getSelectionModel().selectFirst();

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                 
                 if(date_picker1.getValue().isBefore(date_picker.getValue())){
                 Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setContentText("Please choose a RETURN DATE that corresponds with the BOOKING DATE.");
                    alert.showAndWait();
                    Stage stage = (Stage) addButton.getScene().getWindow();
                    stage.show();
                 }
                 else{
                try {
                    String date = date_picker.getValue().toString();

                    String hours = hour.getValue().toString();

                    String mins = minute.getValue().toString();
                    String bookingTime = hours + ":" + mins;
                    String returnDate = date_picker1.getValue().toString();
                    int mechanicID = mechanic_id.getValue().getId();
                    int vehicleID = vehicle_id.getValue().getVehicleId();
                    int customerID = customer_id.getValue().getId();
                    String bookingTypes = (String) bookingType.getValue();
                    int hoursWorked = 0;
                    double amountPaid = 0;

                    Booking bookings = new Booking(customerID, vehicleID, mechanicID, bookingTypes, date, returnDate, bookingTime, hoursWorked, amountPaid);
                    Database.getInstance().addBooking(bookings);

                    book.loadTable();
                    Stage stage = (Stage) addButton.getScene().getWindow();
                    stage.close();
                } catch (NullPointerException E) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setContentText("Please Fill Out All Fields");
                    alert.showAndWait();
                    Stage stage = (Stage) addButton.getScene().getWindow();
                    stage.show();
                }
            }
            }

        });

        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Stage stage = (Stage) addButton.getScene().getWindow();
                stage.close();
            }
        });

        hour.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                if (t1 == null) {
                    return;
                }
                String hours = t1.toString();
                if (hours.equalsIgnoreCase("17")) {
                    minute.getItems().clear();
                    minute.getItems().addAll(FXCollections.observableArrayList("00", "10", "20"));
                    minute.getSelectionModel().selectFirst();
                } else {
                    minute.getItems().clear();
                    minute.getItems().addAll(FXCollections.observableArrayList("00", "10", "20", "30", "40", "50"));
                    minute.getSelectionModel().selectFirst();

                }
            }

        });
        customer_id.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                if (t1 == null) {
                    return;
                }
                int customerID = customer_id.getValue().getId();

                vehicles = FXCollections.observableArrayList((Database.getInstance()).getVehicleForCustomer(customerID));
                vehicle_id.setItems(vehicles);
                vehicle_id.setConverter(new StringConverter<Vehicle>() {
                    @Override
                    public String toString(Vehicle object) {
                        return object.getVehicleId() + ": " + object.getRegistrationNumber() + "- " + object.getMake();
                    }

                    @Override
                    public Vehicle fromString(String string) {
                        int userId = Integer.parseInt(string.split(": ")[0]);
                        return Database.getInstance().getVehicle(userId);
                    }

                });

                vehicle_id.getSelectionModel().selectFirst();
            }

        });

    }

    @FXML
    public void fillDropDown(ActionEvent event) throws ParseException {
        String day = date_picker.getValue().getDayOfWeek().toString();

        dropDown(day);
    }

    public void dropDown(String day) {
        hour.getItems().clear();

        if (day.equalsIgnoreCase("Saturday")) {
            hour.getItems().addAll(FXCollections.observableArrayList("09", "10", "11"));
            hour.getSelectionModel().selectFirst();

        } else {
            hour.getItems().addAll(FXCollections.observableArrayList("09", "10", "11", "12", "13", "14", "15", "16", "17"));
            hour.getSelectionModel().selectFirst();

        }
    }

}
