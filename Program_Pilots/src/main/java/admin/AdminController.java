package admin;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import database.DBManager;
import employee.Employee;
import test.MainTest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import login.LoginController;
import manager.EmployeeRecords;
import manager.ManagerController;
import popup.Popup;

public class AdminController implements Initializable {

  /* Fields in "Create employee" tab */
  @FXML
  private TextField EmpFName;
  @FXML
  private TextField EmpLName;
  @FXML
  private TextField PasswordEmp;
  @FXML
  private TextField EmpPay;
  @FXML
  private TextField EmpOverPay;
  @FXML
  private TextField EmpOverPayTime;
  @FXML
  private Button CreateEmp;

  /* Fields in "Create Manager" tab */
  @FXML
  private TextField ManFName;
  @FXML
  private TextField ManLName;
  @FXML
  private TextField ManUserName;
  @FXML
  private TextField ManPass;
  @FXML
  private TextField ManPay;
  @FXML
  private TextField ManOvertimePay;
  @FXML
  private TextField ManOvertimeHours;
  @FXML
  private Button CreateMan;

  @FXML
  private Button LogInButton;

  @FXML
  private TextField Username;

  @FXML
  private PasswordField Password;

  @FXML
  private Text welcome;

  @FXML
  private Tab logoutTab;

  private HashMap<String, String> Mlogins = new HashMap<>(); // manager logins
  private HashMap<String, String> Elogins = new HashMap<>(); // employee logins
  private HashMap<String, String> Alogins = new HashMap<>(); // admin logins

  public static Employee user;

  private String passRegex = "(?=^.{8,16}$)((?=.*\\d)(?=.*[A-Z])(?=.*[a-z])|(?=.*\\d)(?=.*[^A-Za-z0-9])(?=.*[a-z])|(?=.*[^A-Za-z0-9])(?=.*[A-Z])(?=.*[a-z])|(?=.*\\d)(?=.*[A-Z])(?=.*[^A-Za-z0-9]))^.*";
  // old regex "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,16}$";
  private String namesRegex = "^.{1,20}$";
  private String usernameRegex = "^.{6,16}$";
  private String intRegex = "^[0-9]+$";
  private String doubleRegex = "^\\d*\\.?\\d*$";

  @FXML
  private TableView<EmployeeRecords> Records;
  @FXML
  private TableColumn<EmployeeRecords, String> RecordsEmpID;
  @FXML
  private TableColumn<EmployeeRecords, String> firstName;
  @FXML
  private TableColumn<EmployeeRecords, String> lastName;
  @FXML
  private TableColumn<EmployeeRecords, String> RecordPay;
  @FXML
  private TableColumn<EmployeeRecords, String> RecordsHours;

  public AdminController() {
    Mlogins.put("Mitchell", "stuff");
    Mlogins.put("Brett", "password");
    Mlogins.put("Chris", "otherpassword");
    Mlogins.put("Moises", "Meowth");
    Elogins.put("Andrea", "jenifer");
    Elogins.put("Anna", "professor");
    Alogins.put("Cat", "rawr");
  }

  public void NewManager() throws IOException {

    boolean correctInput = false;

    if (!ManFName.getText().matches(namesRegex)) {
      Popup.display("Error", "Incorrect first name.");
    } else if (!ManLName.getText().matches(namesRegex)) {
      Popup.display("Error", "Incorrect last name.");
    } else if (!ManPass.getText().matches(passRegex)) {
      Popup.display("Error", "Incorrect password.");
    } else if (!ManUserName.getText().matches(usernameRegex)) {
      Popup.display("Error", "Incorrect username.");
    } else if (ManPay.getText().equals("")
        || !ManPay.getText().matches(doubleRegex)) {
      Popup.display("Error", "Incorrect pay.");
    } else if (ManOvertimePay.getText().equals("")
        || !ManOvertimePay.getText().matches(doubleRegex)) {
      Popup.display("Error", "Incorrect overtime pay.");
    } else if (!ManOvertimeHours.getText().matches(intRegex)) {
      Popup.display("Error", "Incorrect overtime hours.");
    } else {

      correctInput = true;
    }

    if (correctInput) {
      System.out.println("Man works!");
      DBManager.storeManager(ManUserName.getText(), ManPass.getText(),
          ManFName.getText(), ManLName.getText(),
          Double.parseDouble(ManPay.getText()),
          Integer.parseInt(ManOvertimeHours.getText()),
          Double.parseDouble(ManOvertimePay.getText()));
      Popup.display("Success", "You have added a manager.");
      ManUserName.setText("");
      ManPass.setText("");
      ManFName.setText("");
      ManLName.setText("");
      ManPay.setText("");
      ManOvertimeHours.setText("");
      ManOvertimePay.setText("");
    } else {
      System.out.println("Failed to add a manager");
    }
  }

  public void NewEmployee() throws IOException {

    boolean correctInput = false;

    if (!EmpFName.getText().matches(namesRegex)) {
      Popup.display("Error", "Incorrect first name.");
    } else if (!EmpLName.getText().matches(namesRegex)) {
      Popup.display("Error", "Incorrect last name.");
    } else if (!PasswordEmp.getText().matches(passRegex)) {
      Popup.display("Error", "Incorrect password.");
    } else if (EmpPay.getText().equals("")
        || !EmpPay.getText().matches(doubleRegex)) {
      Popup.display("Error", "Incorrect pay.");
    } else if (EmpOverPay.getText().equals("")
        || !EmpOverPay.getText().matches(doubleRegex)) {
      Popup.display("Error", "Incorrect overtime pay.");
    } else if (!EmpOverPayTime.getText().matches(intRegex)) {
      Popup.display("Error", "Incorrect overtime hours.");
    } else {
      Popup.display("Success", "You have added an employee.");
      correctInput = true;
    }

    if (correctInput) {
      System.out.println("employee works!");
      DBManager.storeEmployee(PasswordEmp.getText(), EmpFName.getText(),
          EmpLName.getText(), Double.parseDouble(EmpPay.getText()),
          Integer.parseInt(EmpOverPayTime.getText()),
          Double.parseDouble(EmpOverPay.getText()));
      PasswordEmp.setText("");
      EmpFName.setText("");
      EmpLName.setText("");
      EmpPay.setText("");
      EmpOverPayTime.setText("");
      EmpOverPay.setText("");
    } else {
      System.out.println("Failed to add an employee");
    }

  }

  public void logout() throws IOException {
    MainTest.logOut();
    Popup.display("Logout", user.getFirst_name() + ", you are now logged out.");
  }

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    // TODO Auto-generated method stub
    welcome.setText(
        "Welcome " + user.getFirst_name() + " " + user.getLast_name() + "!");
    // System.out.println();
    // stores a list of all current employees for future reference
    HashMap<Integer, Employee> employeeLists = DBManager.loadEmployees();

    // default obersvablearraylist for the records table
    final ObservableList<EmployeeRecords> data = FXCollections
        .observableArrayList();

    // adds employees to the observablearraylist
    for (Map.Entry<Integer, Employee> entry : employeeLists.entrySet()) {
      data.add(new EmployeeRecords(entry.getKey().toString(),
          entry.getValue().getFirst_name(), entry.getValue().getLast_name(),
          Double.toString(entry.getValue().getPay()),
          Double.toString(entry.getValue().getOvertime_hours())));
    }

    // fills in tables in records
    RecordsEmpID.setCellValueFactory(
        new PropertyValueFactory<EmployeeRecords, String>("id"));
    firstName.setCellValueFactory(
        new PropertyValueFactory<EmployeeRecords, String>("firstName"));
    lastName.setCellValueFactory(
        new PropertyValueFactory<EmployeeRecords, String>("lastName"));
    RecordsHours.setCellValueFactory(
        new PropertyValueFactory<EmployeeRecords, String>("hoursWeek"));
    RecordPay.setCellValueFactory(
        new PropertyValueFactory<EmployeeRecords, String>("payWeek"));

    Records.setItems(data); // set the items into the table

  }
}