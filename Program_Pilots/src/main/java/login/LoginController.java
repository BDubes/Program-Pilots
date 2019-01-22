package login;

import java.io.IOException;

import test.MainTest;
import database.DBManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import popup.Popup;

public class LoginController {

  @FXML
  private Button LogInButton;

  @FXML
  private TextField UsernameField;

  @FXML
  private PasswordField PasswordField;

  public void LogIn() throws IOException {
    if (LogInButton.getOnAction() != null) {
      employee.Employee user;
      if ((user = DBManager.login(UsernameField.getText(),
          PasswordField.getText())) != null) {
        switch (user.getAccount_type()) {
          case "emp":
            System.out.println("Logged in.");
            MainTest.showEmployee(user);
            break;
          case "mgr":
            System.out.println("Logged in.");
            MainTest.showManager(user);
            break;
          case "adm":
            System.out.println("Logged in.");
            MainTest.showAdmin(user);
            break;
        }
      } else {
        Popup.display("Incorrect Login", "Incorrect username or password");
        System.out.println("Invalid Login");
      }
    }
  }
}
