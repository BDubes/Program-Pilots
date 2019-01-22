package manager;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import database.DBManager;
import employee.Employee;
import employee.EmployeeValues;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import popup.Popup;
import test.MainTest;

public class ManagerController implements Initializable {

  @FXML
  public Text managerWelcome;
  @FXML
  public TableView<ManagerValues> schedule;
  @FXML
  public TableView<ManagerValues> employee;
  @FXML
  public TableView<ManagerValues> requests;
  @FXML
  public TableView<EmployeeRecords> records;
  @FXML
  public TableColumn<ManagerValues, String> employeeName2;
  @FXML
  public TableColumn<ManagerValues, String> empScheduleDate2;
  @FXML
  public TableColumn<ManagerValues, String> empShiftNum2;
  @FXML
  public TableColumn<ManagerValues, String> employeeName;
  @FXML
  public TableColumn<ManagerValues, String> empScheduleDate;
  @FXML
  public TableColumn<ManagerValues, String> empScheduleTime;
  @FXML
  public TableColumn<ManagerValues, String> empShiftNum;
  @FXML
  public TableColumn<ManagerValues, String> empShiftType;
  @FXML
  public TableColumn<ManagerValues, String> shiftID;
  @FXML
  public TableColumn<ManagerValues, String> employeeID;
  @FXML
  public TableColumn<ManagerValues, String> requestID;
  @FXML
  public TableColumn<ManagerValues, String> requestName;
  @FXML
  public TableColumn<ManagerValues, String> requestFrom;
  @FXML
  public TableColumn<ManagerValues, String> requestTo;
  @FXML
  public TableColumn<ManagerValues, String> requestsInfo;
  @FXML
  public TableColumn<ManagerValues, String> requestApproved;
  @FXML
  public TableColumn<EmployeeRecords, String> recordsEmpID;
  @FXML
  public TableColumn<EmployeeRecords, String> recordsName;
  @FXML
  public TableColumn<EmployeeRecords, String> recordsHours;
  @FXML
  public TableColumn<EmployeeRecords, String> recordsFirstName;
  @FXML
  public TableColumn<EmployeeRecords, String> recordsLastName;
  @FXML
  public TableColumn<EmployeeRecords, String> recordPay;

  @FXML
  private TextField empFName;
  @FXML
  private TextField empPassword;
  @FXML
  private TextField empLName;
  @FXML
  private TextField empOverPayTime;
  @FXML
  private TextField empPay;
  @FXML
  private TextField empOverPay;

  @FXML
  private ComboBox<String> employeeComboBox = new ComboBox<String>();

  @FXML
  public DatePicker StartDate;

  @FXML
  public DatePicker EndDate;

  @FXML
  private Button adjust;

  @FXML
  private Tab adjustEmpPay;

  @FXML
  private Tab managerLogout;

  @FXML
  private TextField AdjustEEmpPay;
  @FXML
  private TextField AdjustEmpOvertime;
  @FXML
  private TextField AdjustEmpOvertimeHrs;

  private static boolean wasLoaded = false;

  private HashMap<String, Integer> employeeNames;

  public static String[][] fullSchedule;
  public static String[][] schedule1;
  public static String[][] pay;
  public static Employee user;
  double[] finalPay = new double[pay.length];
  int[] finalHours = new int[pay.length];

  CategoryAxis xAxis = new CategoryAxis();
  NumberAxis yAxis = new NumberAxis();

  private final static XYChart.Series series0 = new XYChart.Series();
  private final static XYChart.Series series1 = new XYChart.Series();
  private final static XYChart.Series series2 = new XYChart.Series();
  private final static XYChart.Series series3 = new XYChart.Series();
  private final static XYChart.Series series4 = new XYChart.Series();
  private final static XYChart.Series series5 = new XYChart.Series();
  private final static XYChart.Series series6 = new XYChart.Series();
  private final static XYChart.Series series7 = new XYChart.Series();
  private final static XYChart.Series series8 = new XYChart.Series();
  private final static XYChart.Series series9 = new XYChart.Series();
  private final static XYChart.Series series10 = new XYChart.Series();
  private final static XYChart.Series series11 = new XYChart.Series();
  private final static XYChart.Series series12 = new XYChart.Series();
  private final static XYChart.Series series13 = new XYChart.Series();

  @FXML
  private BarChart<String, Number> PayGraph = new BarChart<>(xAxis, yAxis);
  @FXML
  private BarChart<String, Number> HoursGraph = new BarChart<>(xAxis, yAxis);

  private String passRegex = "(?=^.{8,16}$)((?=.*\\d)(?=.*[A-Z])(?=.*[a-z])|(?=.*\\d)(?=.*[^A-Za-z0-9])(?=.*[a-z])|(?=.*[^A-Za-z0-9])(?=.*[A-Z])(?=.*[a-z])|(?=.*\\d)(?=.*[A-Z])(?=.*[^A-Za-z0-9]))^.*";
  // old regex "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,16}$";
  private String namesRegex = "^.{1,20}$";
  // private String usernameRegex = "^.{6,16}$";
  private String intRegex = "^[0-9]+$";
  private String doubleRegex = "^\\d*\\.?\\d*$";
  private String payRegex = "\\D+|^$";

  @FXML
  public void Print(ActionEvent event) {

  }

  /**
   * loads employee names into the combo box in "adjust employee Pay"
   */
  @FXML
  public void loadComboBox() {

  }

  @FXML
  public void CreateSchedule(ActionEvent event) {
    populateSchedule();
  }

  @FXML
  public void ApproveRequest(ActionEvent event) {

  }

  @FXML
  public void RemoveRequest(ActionEvent event) {

  }

  public void logout() throws IOException {
    MainTest.logOut();
    Popup.display("Logout", user.getFirst_name() + ", you are now logged out.");
  }

  @FXML
  public void NewEmp(ActionEvent event) {

    boolean correctInput = false;

    if (!empFName.getText().matches(namesRegex)) {
      Popup.display("Error", "Incorrect first name.");
    } else if (!empLName.getText().matches(namesRegex)) {
      Popup.display("Error", "Incorrect last name.");
    } else if (!empPassword.getText().matches(passRegex)) {
      Popup.display("Error", "Incorrect password.");
    } else if (empPay.getText().equals("")
        || !empPay.getText().matches(doubleRegex)) {
      Popup.display("Error", "Incorrect pay.");
    } else if (empOverPay.getText().equals("")
        || !empOverPay.getText().matches(doubleRegex)) {
      Popup.display("Error", "Incorrect overtime pay.");
    } else if (!empOverPayTime.getText().matches(intRegex)) {
      Popup.display("Error", "Incorrect overtime hours.");
    } else {
      Popup.display("Success", "You have added an employee.");
      correctInput = true;
    }

    if (correctInput) {
      System.out.println("employee works!");
      DBManager.storeEmployee(empPassword.getText(), empFName.getText(),
          empLName.getText(), Double.parseDouble(empPay.getText()),
          Integer.parseInt(empOverPayTime.getText()),
          Double.parseDouble(empOverPay.getText()));
      empPassword.setText("");
      empFName.setText("");
      empLName.setText("");
      empPay.setText("");
      empOverPayTime.setText("");
      empOverPay.setText("");
    } else {
      System.out.println("Failed to add an employee");
    }
  }

  /**
   * Adjusts the pay of a selected empployee, then updates database
   * 
   * @param event
   */
  @FXML
  public void NewPay(ActionEvent event) {

    // checks for valid pay
    if (employeeComboBox.getValue() == null) {
      Popup.display("Error!", "You forgot to choose an employee!");
    } else if (AdjustEEmpPay.getText().matches(payRegex)
        || Double.parseDouble(AdjustEEmpPay.getText()) < 0) {
      Popup.display("Error!", "Invalid pay!");
      AdjustEEmpPay.setText("");
    } else if (AdjustEmpOvertime.getText().matches(payRegex)
        || Integer.parseInt(AdjustEmpOvertime.getText()) < 0) {
      Popup.display("Error!", "Invalid Overtime!");
      AdjustEmpOvertime.setText("");
    } else if (AdjustEmpOvertimeHrs.getText().matches(payRegex)
        || Double.parseDouble(AdjustEmpOvertimeHrs.getText()) < 0) {
      Popup.display("Error!", "Invalid Overtime Hours!");
      AdjustEmpOvertimeHrs.setText("");
    } else { // if it makes it this far, the pay is valid, so adjust it
      DBManager.adjustEmployeePay(
          employeeNames.get(employeeComboBox.getValue()),
          Double.parseDouble(AdjustEEmpPay.getText()),
          Integer.parseInt(AdjustEmpOvertime.getText()),
          Double.parseDouble(AdjustEmpOvertimeHrs.getText()));
      loadData(); // refresh tables
      // alert the user that the pay has been changes
      Popup.display("Succes", "You've successfully changed "
          + employeeComboBox.getValue() + "'s pay!");
      // clear all the text boxes
      AdjustEEmpPay.setText("");
      AdjustEmpOvertime.setText("");
      AdjustEmpOvertimeHrs.setText("");
    }

  }

  /**
   * refreshes all table/combobox data
   */
  public void loadData() {
    managerWelcome.setText(
        "Welcome " + user.getFirst_name() + " " + user.getLast_name() + "!");

    // stores a list of all current employees for future reference
    HashMap<Integer, Employee> employeeLists = DBManager.loadEmployees();

    // initialize obersvablearraylist for the records table
    final ObservableList<EmployeeRecords> data = FXCollections
        .observableArrayList();

    // adds employees to the observablearraylist
    for (Map.Entry<Integer, Employee> entry : employeeLists.entrySet()) {
      data.add(new EmployeeRecords(entry.getKey().toString(),
          entry.getValue().getFirst_name(), entry.getValue().getLast_name(),
          Double.toString(entry.getValue().getPay()),
          Double.toString(entry.getValue().getOvertime_hours())));
    }

    // initializes combo box in employee pay
    if (!wasLoaded) {
      employeeNames = DBManager.loadEmployeeNames();
      for (Map.Entry<String, Integer> entry : employeeNames.entrySet()) {
        employeeComboBox.getItems().addAll(entry.getKey());
      }
      System.out.println("combo loaded");
      wasLoaded = true;
    }

    // fills in tables in records

    recordsEmpID.setCellValueFactory(
        new PropertyValueFactory<EmployeeRecords, String>("id"));
    recordsFirstName.setCellValueFactory(
        new PropertyValueFactory<EmployeeRecords, String>("firstName"));
    recordsLastName.setCellValueFactory(
        new PropertyValueFactory<EmployeeRecords, String>("lastName"));
    recordPay.setCellValueFactory(
        new PropertyValueFactory<EmployeeRecords, String>("payWeek"));
    recordsHours.setCellValueFactory(
        new PropertyValueFactory<EmployeeRecords, String>("hoursWeek"));
    records.setItems(data); // set the items into the table
  }

  private void populatePayGraph() {
    ObservableList<XYChart.Data<String, Number>> antutu = FXCollections
        .observableArrayList();
    XYChart.Series<String, Number> series = new XYChart.Series<>();
    for (int j = 0; j < pay.length; j++) {
      if (Integer.parseInt(pay[j][0]) > Integer.parseInt(pay[j][1])) {
        finalPay[j] = (Integer.parseInt(pay[j][1]) + 24
            - (Integer.parseInt(pay[j][0]))) * Double.parseDouble(pay[j][3]);
      } else {
        finalPay[j] = (Integer.parseInt(pay[j][1])
            - Integer.parseInt(pay[j][0])) * Double.parseDouble(pay[j][3]);
      }
      series.getData().add(new Data<String, Number>(pay[j][2], finalPay[j]));

    }
  }

  private void populateHourGraph() {
    ObservableList<XYChart.Data<String, Number>> antutu = FXCollections
        .observableArrayList();
    XYChart.Series<String, Number> series = new XYChart.Series<>();
    for (int j = 0; j < pay.length; j++) {
      if (Integer.parseInt(pay[j][0]) > Integer.parseInt(pay[j][1])) {
        finalHours[j] = (Integer.parseInt(pay[j][1]) + 24
            - (Integer.parseInt(pay[j][0])));
      } else {
        finalHours[j] = (Integer.parseInt(pay[j][1])
            - Integer.parseInt(pay[j][0]));
      }
      series.getData().add(new Data<String, Number>(pay[j][2], finalPay[j]));

    }
  }

  private void populateScheduleTable() {
    ManagerValues schedule_view;
    // schedule = DBManager.employeeScheduleTable(user.getId(), )
    // Takes array and sets the to the observable list
    final ObservableList<ManagerValues> valuesList = FXCollections
        .observableArrayList();
    if (schedule1 != null) {
      for (int j = 0; j < schedule1.length; j++) {
        schedule_view = new ManagerValues();
        schedule_view.setEmployeeName(schedule1[j][0]);
        schedule_view.setEmpScheduleDate(schedule1[j][1]);
        schedule_view.setEmpShiftNum(schedule1[j][2]);
        schedule_view.setEmpShiftType(schedule1[j][3]);
        valuesList.add(schedule_view);
      }
    }

    schedule.setItems(valuesList);
  }

  private void populateSchedule() {
    ManagerValues schedule_view;
    // schedule = DBManager.employeeScheduleTable(user.getId(), )
    // Takes array and sets the to the observable list

    if (StartDate.getValue() != null && EndDate.getValue() != null) {
      if (!(StartDate.getValue().isAfter(EndDate.getValue()))) {
        // new java.sql.Date(currentDate.getTime()),
        fullSchedule = DBManager.populateSchedule(
            java.sql.Date.valueOf(StartDate.getValue()),
            java.sql.Date.valueOf(EndDate.getValue()));
      } else {
        Popup.display("Error", "From can't be after To.");
      }
    }
    final ObservableList<ManagerValues> valuesList = FXCollections
        .observableArrayList();
    if (fullSchedule != null) {
      for (int j = 0; j < fullSchedule.length; j++) {
        schedule_view = new ManagerValues();
        schedule_view.setEmployeeName2(fullSchedule[j][0]);
        schedule_view.setEmpScheduleDate2(fullSchedule[j][1]);
        schedule_view.setEmpScheduleTime(fullSchedule[j][3]);
        schedule_view.setEmpShiftNum2(fullSchedule[j][2]);
        schedule_view.setEmpShiftType(fullSchedule[j][4]);
        valuesList.add(schedule_view);
      }
    }

    schedule.setItems(valuesList);
  }

  @SuppressWarnings("unchecked")
  public void initialize(URL arg0, ResourceBundle arg1) {

    // System.out.println(user.getFirst_name());

    populatePayGraph();
    populateHourGraph();
    populateScheduleTable();
    populateSchedule();
    int checker = pay.length;
    int checker2 = pay.length;

    if (checker != 0) {
      series0.getData().add(new Data<String, Number>(pay[0][2], finalPay[0]));
      PayGraph.getData().add(series0);
      checker--;
    }
    if (checker != 0) {
      series1.getData().add(new Data<String, Number>(pay[1][2], finalPay[1]));
      PayGraph.getData().add(series1);
      checker--;
    }
    if (checker != 0) {
      series2.getData().add(new Data<String, Number>(pay[2][2], finalPay[2]));
      PayGraph.getData().add(series2);
      checker--;
    }
    if (checker != 0) {
      series3.getData().add(new Data<String, Number>(pay[3][2], finalPay[3]));
      PayGraph.getData().add(series3);
      checker--;
    }
    if (checker != 0) {
      series4.getData().add(new Data<String, Number>(pay[4][2], finalPay[4]));
      PayGraph.getData().add(series4);
      checker--;
    }
    if (checker != 0) {
      series5.getData().add(new Data<String, Number>(pay[5][2], finalPay[5]));
      PayGraph.getData().add(series5);
      checker--;
    }
    if (checker != 0) {
      series6.getData().add(new Data<String, Number>(pay[6][2], finalPay[6]));
      PayGraph.getData().add(series6);
      checker--;
    }

    if (checker2 != 0) {
      series7.getData().add(new Data<String, Number>(pay[0][2], finalHours[0]));
      HoursGraph.getData().add(series7);
      checker2--;
    }
    if (checker2 != 0) {
      series8.getData().add(new Data<String, Number>(pay[1][2], finalHours[1]));
      HoursGraph.getData().add(series8);
      checker2--;
    }
    if (checker2 != 0) {
      series9.getData().add(new Data<String, Number>(pay[2][2], finalHours[2]));
      HoursGraph.getData().add(series9);
      checker2--;
    }
    if (checker2 != 0) {
      series10.getData()
          .add(new Data<String, Number>(pay[3][2], finalHours[3]));
      HoursGraph.getData().add(series10);
      checker2--;
    }
    if (checker2 != 0) {
      series11.getData()
          .add(new Data<String, Number>(pay[4][2], finalHours[4]));
      HoursGraph.getData().add(series11);
      checker2--;
    }
    if (checker2 != 0) {
      series12.getData()
          .add(new Data<String, Number>(pay[5][2], finalHours[5]));
      HoursGraph.getData().add(series12);
      checker2--;
    }
    if (checker2 != 0) {
      series13.getData()
          .add(new Data<String, Number>(pay[6][2], finalHours[6]));
      HoursGraph.getData().add(series13);
      checker2--;
    }

    loadData();

    employeeName2.setCellValueFactory(
        new PropertyValueFactory<ManagerValues, String>("employeeName2"));
    empScheduleDate2.setCellValueFactory(
        new PropertyValueFactory<ManagerValues, String>("empScheduleDate2"));
    empScheduleTime.setCellValueFactory(
        new PropertyValueFactory<ManagerValues, String>("empScheduleTime"));
    empShiftNum2.setCellValueFactory(
        new PropertyValueFactory<ManagerValues, String>("empShiftNum2"));
    empShiftType.setCellValueFactory(
        new PropertyValueFactory<ManagerValues, String>("empShiftType"));

  }
}
