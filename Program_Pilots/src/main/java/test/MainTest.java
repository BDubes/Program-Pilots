package test;

import java.io.IOException;

import database.DBManager;
import employee.Employee;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import manager.ManagerController;
import employee.EmployeeController;
import admin.AdminController;

import java.util.Calendar;
import java.util.Date;

public class MainTest extends Application {

  private static Stage mainStage;
  private static Stage loginStage;
  private AnchorPane mainLayout;

  public static void main(String[] args) {
    launch(args);

  }

  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader
        .load(MainTest.class.getResource("../login/Login.fxml"));
    Scene scene = new Scene(root);
    mainStage = primaryStage;
    loginStage = mainStage;
    mainStage.setScene(scene);
    mainStage.setTitle("Program Pilots");
    mainStage.show();

  }

  public static void logOut() throws IOException {


    Parent root = FXMLLoader
        .load(MainTest.class.getResource("../login/Login.fxml"));
    Scene scene = new Scene(root);
    loginStage.setScene(scene);
    loginStage.setTitle("Program Pilots");
    loginStage.show();
  }

  public static void showManager(Employee user) throws IOException {
    ManagerController.pay = DBManager.BarGraphPay(user.getId());

    Calendar calendar = Calendar.getInstance();
    java.util.Date currentDate = calendar.getTime();
    calendar.add(Calendar.DAY_OF_YEAR, 7);
    java.util.Date weekLaterDate = calendar.getTime();
    calendar.add(Calendar.DAY_OF_YEAR, -7);
    ManagerController.user = user;
    ManagerController.fullSchedule = DBManager.populateSchedule(
        new java.sql.Date(currentDate.getTime()),
        new java.sql.Date(weekLaterDate.getTime()));
    ManagerController.schedule1 = DBManager.employeeScheduleTable(user.getId(),
        new java.sql.Date(currentDate.getTime()),
        new java.sql.Date(weekLaterDate.getTime()));
    ManagerController.pay = DBManager.BarGraphPay(user.getId());
    Parent root = FXMLLoader
        .load(MainTest.class.getResource("../manager/Manager.fxml"));
    Scene scene = new Scene(root);
    mainStage.setScene(scene);
    mainStage.show();
  }

  public static void showAdmin(Employee user) throws IOException {
    AdminController.user = user;
    Parent root = FXMLLoader
        .load(MainTest.class.getResource("../admin/Admin.fxml"));
    Scene scene = new Scene(root);
    mainStage.setScene(scene);
    mainStage.show();
  }

  public static void showEmployee(Employee user) throws IOException {
    Calendar calendar = Calendar.getInstance();
    java.util.Date currentDate = calendar.getTime();
    calendar.add(Calendar.DAY_OF_YEAR, 7);
    java.util.Date weekLaterDate = calendar.getTime();
    calendar.add(Calendar.DAY_OF_YEAR, -7);
    EmployeeController.user = user;
    EmployeeController.requests = DBManager.personalRequests(user.getId());
    EmployeeController.schedule = DBManager.employeeScheduleTable(user.getId(),
        new java.sql.Date(currentDate.getTime()),
        new java.sql.Date(weekLaterDate.getTime()));
    EmployeeController.pay = DBManager.BarGraphPay(user.getId());
    Parent root = FXMLLoader
        .load(MainTest.class.getResource("../employee/Employee.fxml"));
    Scene scene = new Scene(root);
    mainStage.setScene(scene);
    mainStage.show();
  }

  
}
