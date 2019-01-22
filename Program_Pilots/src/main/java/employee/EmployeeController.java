package employee;

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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import popup.Popup;
import test.MainTest;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

import database.DBManager;

public class EmployeeController implements Initializable {

  public static String[][] schedule;
  public static String[][] requests;
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
  private BarChart<String, Number> EmpPayChart = new BarChart<>(xAxis, yAxis);
  @FXML
  private BarChart<String, Number> EmpHourChart = new BarChart<>(xAxis, yAxis);

  @FXML
  public DatePicker EmployeeStartDate;
  @FXML
  public DatePicker EmployeeEndDate;

  @FXML
  public TableView<EmployeeValues> empSchedule;
  @FXML
  public TableColumn<EmployeeValues, String> employeeName;
  @FXML
  public TableColumn<EmployeeValues, String> empScheduleDate;
  @FXML
  public TableColumn<EmployeeValues, String> empShiftNum;
  @FXML
  public TableColumn<EmployeeValues, String> empShiftType;
  @FXML
  public TableColumn<EmployeeValues, String> empEmployeeId;
  @FXML
  public TableColumn<EmployeeValues, String> employeeName2;
  @FXML
  public TableColumn<EmployeeValues, String> empRequestFrom;
  @FXML
  public TableColumn<EmployeeValues, String> empRequestTo;
  @FXML
  public TableColumn<EmployeeValues, String> empRequests;
  @FXML
  public TableColumn<EmployeeValues, String> empApproved;

  @FXML
  public TableView<EmployeeValues> requestsTable;

  @FXML
  private Button EmpRequestButton;
  @FXML
  private TextField EmpRequestReason;
  @FXML
  private DatePicker EmpRequestFromDP;
  @FXML
  private DatePicker EmpRequestToDP;

  @FXML
  private Text employeeWelcome;

  @FXML
  private Tab logout;

  public void logout() throws IOException {
    MainTest.logOut();
    Popup.display("Logout", user.getFirst_name() + ", you are now logged out.");
  }

  @FXML
  public void makeRequest() {

    if (EmpRequestFromDP.getValue() == null
        || EmpRequestToDP.getValue() == null) {
      System.out.println("err");
      Popup.display("Error", "You need a to and from date.");
    } else if (EmpRequestFromDP.getValue().isAfter(EmpRequestToDP.getValue())) {
      System.out.println("err");
      Popup.display("Error", "From date cannot be after to date");
    } else if (EmpRequestReason.getText().equals("")) {
      System.out.println("Empty!");
      Popup.display("Error", "You need a reason for timeoff.");
    } else {
     
      DBManager.createRequest(
          java.sql.Date.valueOf(EmpRequestFromDP.getValue()),
          java.sql.Date.valueOf(EmpRequestToDP.getValue()), user.getId(),
          EmpRequestReason.getText());
      EmpRequestFromDP.setValue(null);
      EmpRequestToDP.setValue(null);
      EmpRequestReason.setText("");
      Popup.display("Success", "You've successfully registered your request!");
    }

  }

  private void populateRequestTable() {
    EmployeeValues request_view;
    final ObservableList<EmployeeValues> requestsList = FXCollections
        .observableArrayList();
    if (requests != null) {
      for (int j = 0; j < requests.length; j++) {
        request_view = new EmployeeValues();
        request_view.setEmpRequestFrom(requests[j][0]);
        request_view.setEmpRequestTo(requests[j][1]);
        request_view.setEmpRequests(requests[j][2]);
        request_view.setEmpApproved(requests[j][3]);
        requestsList.add(request_view);
      }
    }

    requestsTable.setItems(requestsList);
  }

  private void populateScheduleTable() {
    EmployeeValues schedule_view;

    final ObservableList<EmployeeValues> valuesList = FXCollections
        .observableArrayList();
    if (schedule != null) {
      for (int j = 0; j < schedule.length; j++) {
        schedule_view = new EmployeeValues();
        schedule_view.setEmployeeName(schedule[j][0]);
        schedule_view.setEmpScheduleDate(schedule[j][1]);
        schedule_view.setEmpShiftNum(schedule[j][2]);
        schedule_view.setEmpShiftType(schedule[j][3]);
        valuesList.add(schedule_view);
      }
    }

    empSchedule.setItems(valuesList);
  }

  public void createSchedule() {
    if (EmployeeStartDate.getValue() != null
        && EmployeeEndDate.getValue() != null) {
      EmployeeValues schedule_view;

      schedule = DBManager.employeeScheduleTable(user.getId(),
          java.sql.Date.valueOf(EmployeeStartDate.getValue()),
          java.sql.Date.valueOf(EmployeeEndDate.getValue()));
      final ObservableList<EmployeeValues> valuesList = FXCollections
          .observableArrayList();
      if (schedule != null) {
        for (int j = 0; j < schedule.length; j++) {
          schedule_view = new EmployeeValues();
          schedule_view.setEmployeeName(schedule[j][0]);
          schedule_view.setEmpScheduleDate(schedule[j][1]);
          schedule_view.setEmpShiftNum(schedule[j][2]);
          schedule_view.setEmpShiftType(schedule[j][3]);
          valuesList.add(schedule_view);
        }
      }

      empSchedule.setItems(valuesList);
    }
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

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    employeeWelcome.setText(
        "Welcome " + user.getFirst_name() + " " + user.getLast_name() + "!");

    populatePayGraph();
    populateHourGraph();
    populateScheduleTable();
    populateRequestTable();
    int checker = pay.length;
    int checker2 = pay.length;

    if (checker != 0) {
      series0.getData().add(new Data<String, Number>(pay[0][2], finalPay[0]));
      EmpPayChart.getData().add(series0);
      checker--;
    }
    if (checker != 0) {
      series1.getData().add(new Data<String, Number>(pay[1][2], finalPay[1]));
      EmpPayChart.getData().add(series1);
      checker--;
    }
    if (checker != 0) {
      series2.getData().add(new Data<String, Number>(pay[2][2], finalPay[2]));
      EmpPayChart.getData().add(series2);
      checker--;
    }
    if (checker != 0) {
      series3.getData().add(new Data<String, Number>(pay[3][2], finalPay[3]));
      EmpPayChart.getData().add(series3);
      checker--;
    }
    if (checker != 0) {
      series4.getData().add(new Data<String, Number>(pay[4][2], finalPay[4]));
      EmpPayChart.getData().add(series4);
      checker--;
    }
    if (checker != 0) {
      series5.getData().add(new Data<String, Number>(pay[5][2], finalPay[5]));
      EmpPayChart.getData().add(series5);
      checker--;
    }
    if (checker != 0) {
      series6.getData().add(new Data<String, Number>(pay[6][2], finalPay[6]));
      EmpPayChart.getData().add(series6);
      checker--;
    }

    if (checker2 != 0) {
      series7.getData().add(new Data<String, Number>(pay[0][2], finalHours[0]));
      EmpHourChart.getData().add(series7);
      checker2--;
    }
    if (checker2 != 0) {
      series8.getData().add(new Data<String, Number>(pay[1][2], finalHours[1]));
      EmpHourChart.getData().add(series8);
      checker2--;
    }
    if (checker2 != 0) {
      series9.getData().add(new Data<String, Number>(pay[2][2], finalHours[2]));
      EmpHourChart.getData().add(series9);
      checker2--;
    }
    if (checker2 != 0) {
      series10.getData()
          .add(new Data<String, Number>(pay[3][2], finalHours[3]));
      EmpHourChart.getData().add(series10);
      checker2--;
    }
    if (checker2 != 0) {
      series11.getData()
          .add(new Data<String, Number>(pay[4][2], finalHours[4]));
      EmpHourChart.getData().add(series11);
      checker2--;
    }
    if (checker2 != 0) {
      series12.getData()
          .add(new Data<String, Number>(pay[5][2], finalHours[5]));
      EmpHourChart.getData().add(series12);
      checker2--;
    }
    if (checker2 != 0) {
      series13.getData()
          .add(new Data<String, Number>(pay[6][2], finalHours[6]));
      EmpHourChart.getData().add(series13);
      checker2--;
    }
    populateScheduleTable();

    employeeName.setCellValueFactory(
        new PropertyValueFactory<EmployeeValues, String>("employeeName"));
    empScheduleDate.setCellValueFactory(
        new PropertyValueFactory<EmployeeValues, String>("empScheduleDate"));
    empShiftNum.setCellValueFactory(
        new PropertyValueFactory<EmployeeValues, String>("empShiftNum"));
    empShiftType.setCellValueFactory(
        new PropertyValueFactory<EmployeeValues, String>("empShiftType"));

    empRequestFrom.setCellValueFactory(
        new PropertyValueFactory<EmployeeValues, String>("empRequestFrom"));
    empRequestTo.setCellValueFactory(
        new PropertyValueFactory<EmployeeValues, String>("empRequestTo"));
    empRequests.setCellValueFactory(
        new PropertyValueFactory<EmployeeValues, String>("empRequests"));
    empApproved.setCellValueFactory(
        new PropertyValueFactory<EmployeeValues, String>("empApproved"));

  }
}
