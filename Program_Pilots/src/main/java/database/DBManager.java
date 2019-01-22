package database;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import employee.*;

public class DBManager {
  // Public variables for strings used in database.
  private static final String JDBC_DRIVER = "org.h2.Driver";
  private static String DB_LOCATION = "jdbc:h2:"
      + System.getProperty("user.dir") + "/db/ProgramPilotsDB";
  private static String DBUsername = "sa";
  private static String DBPassword = "";

  /**
   * Creates a database if one does not exist. Throws exception if database
   * exists.
   */
  public static void createDB(String username, String password,
      String firstName, String lastName) {
    String createAccount = "create table account("
        + "  id int PRIMARY KEY AUTO_INCREMENT,"
        + "  first_name varchar(20) NOT NULL,"
        + "  last_name varchar(20) NOT NULL,"
        + "  account_type varchar(3) NOT NULL," + "  pay double NOT NULL,"
        + "  overtime_hours int," + "  overtime_pay double,"
        + "  active boolean NOT NULL," + ");";

    String createLogin = "create table login("
        + "  username varchar(17) PRIMARY KEY,"
        + "  password varchar(20) NOT NULL,"
        + "  account_id int unique NOT NULL,"
        + "  FOREIGN KEY(account_id) references account(id)," + ");";

    String createShift = "create table shift("
        + "  shiftnum int PRIMARY KEY AUTO_INCREMENT,"
        + "  start_time TIME NOT NULL," + "  end_time TIME NOT NULL" + ");";

    String createSchedule = "create table schedule("
        + "  id int PRIMARY KEY AUTO_INCREMENT,"
        + "  schedule_date date NOT NULL," + "  shift int NOT NULL,"
        + "  FOREIGN KEY (shift) REFERENCES shift(shiftnum)" + ");";

    String createWorks = "create table works(" + "  employee_id int NOT NULL,"
        + "  schedule_id int NOT NULL," + "  schedule_type varchar(1) NOT NULL,"
        + "  primary key (employee_id, schedule_id),"
        + "  foreign key (employee_id) references account(id),"
        + "  foreign key (schedule_id) references schedule(id)," + ");";

    String createRegularSchedule = "create table regular_schedule("
        + "  id int PRIMARY KEY AUTO_INCREMENT,"
        + "  weekday varchar(3) NOT NULL, " + "  shift int NOT NULL, "
        + "  foreign key (SHIFT) references shift(shiftnum)" + ");";

    String createRequests = "create table request("
        + "  id int NOT NULL AUTO_INCREMENT," + "  from_date date NOT NULL,"
        + "  to_date date NOT NULL," + "  employee_id int NOT NULL,"
        + "  approved bool," + "  details VARCHAR(100),"
        + "  FOREIGN KEY(employee_id) references account(id)" + ");";

    String createRegularlyWorks = "create table regularly_works("
        + "  employee_id int NOT NULL," + "  regular_schedule_id int NOT NULL,"
        + "  primary key (employee_id, regular_schedule_id),"
        + "  foreign key (employee_id) references account(id),"
        + "  foreign key (regular_schedule_id) references regular_schedule(id)"
        + ");";

    String createAdminAcc = "insert into account (FIRST_NAME, LAST_NAME, ACCOUNT_TYPE, PAY, OVERTIME_HOURS, OVERTIME_PAY, ACTIVE) values ('"
        + firstName + "', '" + lastName + "', 'adm', 0.00, 0, 0.00, FALSE);";

    String createAdminLogin = "insert into login (USERNAME, PASSWORD, ACCOUNT_ID) values ('"
        + username + "', '" + password + "', 1);";

    try {
      Class.forName(JDBC_DRIVER);

      Connection connection = DriverManager.getConnection(DB_LOCATION,
          DBManager.DBUsername, DBManager.DBPassword);
      Statement statement = connection.createStatement();

      statement.executeUpdate(createAccount);

      statement.executeUpdate(createLogin);

      statement.executeUpdate(createShift);

      statement.executeUpdate(createSchedule);

      statement.executeUpdate(createWorks);

      statement.executeUpdate(createRegularSchedule);

      statement.executeUpdate(createRequests);

      statement.executeUpdate(createRegularlyWorks);

      statement.executeUpdate(createAdminAcc);

      statement.executeUpdate(createAdminLogin);

      statement.close();

      connection.close();

    } catch (SQLException e) {
      System.err.println("Database already exists.");
      return;
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    System.out.println("Database created.");
  }

  /**
   * Deletes database if it exists. Throws exception if database does not exist.
   */
  public static void deleteDB() {
    try {
      Class.forName(JDBC_DRIVER);

      Connection connection = DriverManager.getConnection(DB_LOCATION,
          DBUsername, DBPassword);
      Statement statement = connection.createStatement();

      statement.executeUpdate("drop table account;");

      statement.executeUpdate("drop table login;");

      statement.executeUpdate("drop table schedule;");

      statement.executeUpdate("drop table works;");

      statement.executeUpdate("drop table regular_schedule;");

      statement.executeUpdate("drop table request;");

      statement.executeUpdate("drop table regularly_works;");

      statement.executeUpdate("drop table shift;");

      statement.close();

      connection.close();
    } catch (SQLException e) {
      System.err.println("Database does not exist.");
      return;
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    System.out.println("Database deleted.");
  }

  /**
   * Loads employees in the database into HashMap.
   *
   * @return HashMap attaching employee to id.
   */
  public static HashMap<Integer, Employee> loadEmployees() {
    HashMap<Integer, Employee> employees = new HashMap<Integer, Employee>();

    try {
      Class.forName(JDBC_DRIVER);

      Connection connection = DriverManager.getConnection(DB_LOCATION,
          DBUsername, DBPassword);
      Statement statement = connection.createStatement();

      ResultSet data = statement.executeQuery("SELECT id, first_name, "
          + "last_name, account_type, " + "pay, overtime_hours, "
          + "overtime_pay FROM account" + " WHERE active = 't';");

      while (data.next()) {
        employees.put(data.getInt("id"),
            new Employee(data.getInt("id"), data.getString("first_name"),
                data.getString("last_name"), data.getString("account_type"),
                data.getDouble("pay"), data.getInt("overtime_hours"),
                data.getDouble("overtime_pay")));
      }

      statement.close();

      connection.close();
    } catch (SQLException e) {
      System.err.println("Database does not exist.");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

    return employees;
  }

  /**
   * Adds a new employee to the database.
   *
   * @param firstName
   *          First name of employee.
   * @param lastName
   *          Last name of employee.
   * @param pay
   *          Pay per hour (in dollars).
   * @param overtimeHours
   *          Hours to be worked to qualify for overtime pay.
   * @param overtimePay
   *          Pay per hour for overtime hours (in dollars).
   * @return returns a boolean whether a new database entry has been added or
   *         not.
   */
  public static boolean storeEmployee(String password, String firstName,
      String lastName, double pay, int overtimeHours, double overtimePay) {
    int length = 0;
    String username = firstName.charAt(0) + lastName;
    if (username.length() > 17) {
      username = username.substring(0, 16);
    }

    try {
      Class.forName(JDBC_DRIVER);

      Connection connection = DriverManager.getConnection(DB_LOCATION,
          DBUsername, DBPassword);
      Statement statement = connection.createStatement();
      Random rand = new Random();

      statement.executeUpdate(
          "INSERT INTO account (FIRST_NAME, LAST_NAME, ACCOUNT_TYPE, PAY, OVERTIME_HOURS, OVERTIME_PAY, ACTIVE) values ('"
              + firstName + "', '" + lastName + "', " + "'emp', " + pay + ", "
              + overtimeHours + ", " + overtimePay + ", 't')");

      ResultSet data = statement
          .executeQuery("SELECT SCOPE_IDENTITY() from ACCOUNT");

      if (data.next()) {
        length = data.getInt(1);
      }

      statement.executeUpdate(
          "INSERT INTO login (USERNAME, PASSWORD, ACCOUNT_ID) values ('"
              + username.toLowerCase() + "', '" + password + "', '" + length
              + "')");

      statement.close();

      connection.close();
    } catch (SQLException e) {
      System.err.println("Database does not exist.");
      return false;
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return true;
  }

  /**
   * Adds new manager to database.
   *
   * @param firstName
   *          First name of manager.
   * @param lastName
   *          Last name of manager.
   * @param pay
   *          Pay per hour (in dollars).
   * @param overtimeHours
   *          Hours required to earn overtime pay.
   * @param overtimePay
   *          Pay per hour of overtime (in dollars).
   */
  public static void storeManager(String username, String password,
      String firstName, String lastName, double pay, int overtimeHours,
      double overtimePay) {
    try {
      Class.forName(JDBC_DRIVER);

      Connection connection = DriverManager.getConnection(DB_LOCATION,
          DBUsername, DBPassword);
      Statement statement = connection.createStatement();
      Random rand = new Random();

      statement.executeUpdate(
          "INSERT INTO account (FIRST_NAME, LAST_NAME, ACCOUNT_TYPE, PAY, OVERTIME_HOURS, OVERTIME_PAY, ACTIVE) values ('"
              + firstName + "', '" + lastName + "', " + "'mgr', " + pay + ", "
              + overtimeHours + ", " + overtimePay + ", 't')");

      ResultSet data = statement
          .executeQuery("SELECT SCOPE_IDENTITY() from ACCOUNT");

      int length = 0;

      if (data.next()) {
        length = data.getInt(1);
      }

      statement.executeUpdate(
          "INSERT INTO login (USERNAME, PASSWORD, ACCOUNT_ID) values ('"
              + username.toLowerCase() + "', '" + password + "', '" + length
              + "')");

      statement.close();

      connection.close();
    } catch (SQLException e) {
      System.err.println("Database does not exist.");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * Deactivates employee in database.
   *
   * @param id
   *          id of employee to be deactivated.
   */
  public static void deactivateEmployee(int id) {
    try {
      Class.forName(JDBC_DRIVER);

      Connection connection = DriverManager.getConnection(DB_LOCATION,
          DBUsername, DBPassword);
      Statement statement = connection.createStatement();
      Random rand = new Random();

      statement.executeUpdate("");

      statement.close();

      connection.close();
    } catch (SQLException e) {
      System.err.println("Database does not exist.");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  public static void activateEmployee() {
    try {
      Class.forName(JDBC_DRIVER);

      Connection connection = DriverManager.getConnection(DB_LOCATION,
          DBUsername, DBPassword);
      Statement statement = connection.createStatement();
      Random rand = new Random();

      statement.executeUpdate("");

      statement.close();

      connection.close();
    } catch (SQLException e) {
      System.err.println("Database does not exist.");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * Test driver to store employees in the database.
   */
  public static Employee loadEmployee(int id) {
    Employee user = null;
    try {
      Class.forName(JDBC_DRIVER);

      Connection connection = DriverManager.getConnection(DB_LOCATION,
          DBUsername, DBPassword);
      Statement statement = connection.createStatement();

      ResultSet data = statement.executeQuery(
          "select ID, FIRST_NAME, LAST_NAME, ACCOUNT_TYPE, PAY, OVERTIME_HOURS, OVERTIME_PAY from ACCOUNT where ID = "
              + id + ";");

      if (data.next()) {
        user = new Employee(data.getInt("ID"), data.getString("FIRST_NAME"),
            data.getString("LAST_NAME"), data.getString("ACCOUNT_TYPE"),
            data.getDouble("PAY"), data.getInt("OVERTIME_HOURS"),
            data.getDouble("OVERTIME_PAY"));
      }

      statement.close();

      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
      System.err.println("Database does not exist.");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return user;
  }

  /**
   * @param username
   *          employee's DBUsername.
   * @param password
   *          employee's DBPassword.
   * @return employee if login is valid, void if login is not valid.
   */
  public static Employee login(String username, String password) {
    Employee user = null;
    try {
      Class.forName(JDBC_DRIVER);

      Connection connection = DriverManager.getConnection(DB_LOCATION,
          DBUsername, DBPassword);
      Statement statement = connection.createStatement();

      ResultSet data = statement.executeQuery("SELECT ACCOUNT_ID FROM login"
          + "  WHERE username = '" + username.toLowerCase() + "'"
          + "  AND password = '" + password + "';");

      if (data.next()) {
        user = loadEmployee(data.getInt("ACCOUNT_ID"));
      }

      statement.close();

      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
      System.err.println("Database does not exist.");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

    return user;
  }

  public static HashMap<String, Integer> loadEmployeeNames() {
    HashMap<String, Integer> nameMap = new HashMap<>();
    try {
      Class.forName(JDBC_DRIVER);

      Connection connection = DriverManager.getConnection(DB_LOCATION,
          DBUsername, DBPassword);
      Statement statement = connection.createStatement();

      ResultSet data = statement.executeQuery(
          "select FIRST_NAME, LAST_NAME, ID from ACCOUNT where ACCOUNT_TYPE = 'emp' and ACTIVE = TRUE;");

      while (data.next()) {
        if (!nameMap.containsKey(
            data.getString("FIRST_NAME") + " " + data.getString("LAST_NAME"))) {
          nameMap.put(
              data.getString("FIRST_NAME") + " " + data.getString("LAST_NAME"),
              data.getInt("ID"));
        } else {
          boolean put = false;
          int i = 2;
          while (!put) {
            if (!nameMap.containsKey(data.getString("FIRST_NAME") + " "
                + data.getString("LAST_NAME") + " " + i)) {
              nameMap.put(
                  data.getString("FIRST_NAME") + " "
                      + data.getString("LAST_NAME") + " " + i,
                  data.getInt("ID"));
              put = true;
            }
            i++;
          }
        }
      }

      statement.close();

      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
      System.err.println("Database does not exist.");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

    return nameMap;
  }

  public static void adjustEmployeePay(int id, double pay, int overtimeHours,
      double overtimePay) {
    try {
      Class.forName(JDBC_DRIVER);

      Connection connection = DriverManager.getConnection(DB_LOCATION,
          DBUsername, DBPassword);
      Statement statement = connection.createStatement();

      statement.executeUpdate("update ACCOUNT set PAY = " + pay
          + ", OVERTIME_HOURS = " + overtimeHours + ", OVERTIME_PAY = "
          + overtimePay + " where ID = " + id + ";");

      statement.close();

      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
      System.err.println("Database does not exist.");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * @param userID
   *          ID of user whose schedule is to be fetched.
   * @param startDate
   * @param endDate
   * @return 2-dimensional array containing all the fields specified.
   */
  public static String[][] employeeScheduleTable(int userID, Date startDate,
      Date endDate) {
    String[][] schedule = null;
    try {
      Class.forName(JDBC_DRIVER);

      Connection connection = DriverManager.getConnection(DB_LOCATION,
          DBUsername, DBPassword);
      Statement statement = connection.createStatement(
          ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

      ResultSet data = statement
          .executeQuery("SELECT " + "  ACCOUNT.FIRST_NAME, "
              + "  SCHEDULE.SCHEDULE_DATE, " + "  SCHEDULE.SHIFT, "
              + "  WORKS.SCHEDULE_TYPE " + "FROM" + "  SCHEDULE"
              + "  INNER JOIN WORKS ON WORKS.SCHEDULE_ID = SCHEDULE.ID"
              + "  INNER JOIN ACCOUNT ON ACCOUNT.ID = WORKS.EMPLOYEE_ID "
              + "WHERE" + "  ACCOUNT.ID = '" + userID + "'"
              + "  AND ACCOUNT.ACCOUNT_TYPE = 'emp'"
              + "  AND SCHEDULE.SCHEDULE_DATE BETWEEN '" + startDate + "'"
              + "  AND '" + endDate + "'");

      int i = 0;
      while (data.next()) {
        i++;
      }
      data.first();

      schedule = new String[i][4];

      for (int j = 0; j < i; j++) {
        schedule[j][0] = data.getString("account.first_name");
        schedule[j][1] = data.getString("schedule.schedule_date");
        schedule[j][2] = data.getString("schedule.shift");
        schedule[j][3] = data.getString("works.schedule_type");
        data.next();
      }

      statement.close();

      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
      System.err.println("Database does not exist.");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

    return schedule;
  }

  /**
   * Creates request.
   *
   * @param from
   *          Start date of requested time off.
   * @param to
   *          End date of requested time off.
   * @param employeeId
   *          ID of employee requesting time off.
   * @param details
   *          Details of request for time off.
   * @return boolean whether the request was successfully submitted or not.
   */
  public static boolean createRequest(Date from, Date to, int employeeId,
      String details) {
    try {
      Class.forName(JDBC_DRIVER);

      Connection connection = DriverManager.getConnection(DB_LOCATION,
          DBUsername, DBPassword);
      Statement statement = connection.createStatement();

      statement.executeUpdate(
          "INSERT INTO REQUEST(FROM_DATE, TO_DATE, EMPLOYEE_ID, DETAILS)\n"
              + "VALUES ('" + from + "', '" + to + "', " + employeeId + ", '"
              + details + "')");

      statement.close();

      connection.close();
    } catch (SQLException e) {
      System.err.println("Database does not exist.");
      return false;
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return true;
  }

  public static String[][] BarGraphPay(int userID) {
    String[][] pay = null;
    try {
      Class.forName(JDBC_DRIVER);

      Connection connection = DriverManager.getConnection(DB_LOCATION,
          DBUsername, DBPassword);
      Statement statement = connection.createStatement(
          ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

      ResultSet data = statement.executeQuery(
          "SELECT" + "  SHIFT.START_TIME, " + "  SHIFT.END_TIME, "
              + "SCHEDULE.SCHEDULE_DATE, " + "ACCOUNT.PAY " + "FROM" + "  SHIFT"
              + "  INNER JOIN SCHEDULE ON SCHEDULE.SHIFT = SHIFT.ID "
              + "  INNER JOIN WORKS ON WORKS.SCHEDULE_ID = SCHEDULE.ID "
              + "  INNER JOIN ACCOUNT ON ACCOUNT.ID = WORKS.EMPLOYEE_ID " +

              "WHERE" + "  WORKS.EMPLOYEE_ID = '" + userID + "'");

      int i = 0;
      while (data.next()) {
        i++;
      }
      data.first();

      pay = new String[i][4];

      for (int j = 0; j < i; j++) {
        pay[j][0] = data.getString("SHIFT.START_TIME");
        pay[j][0] = pay[j][0].substring(0, 2);

        System.out.print(pay[j][0] + "  ");
        pay[j][1] = data.getString("SHIFT.END_TIME");
        pay[j][1] = pay[j][1].substring(0, 2);
        System.out.print(pay[j][1] + "  ");

        pay[j][2] = data.getString("SCHEDULE.SCHEDULE_DATE");
        System.out.print(pay[j][2] + "  ");

        pay[j][3] = data.getString("ACCOUNT.PAY");
        System.out.print(pay[j][3] + "\n");

        data.next();
      }

      statement.close();

      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
      System.err.println("Database does not exist.");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

    return pay;
  }

  public static String[][] personalRequests(int userID) {
    String[][] request = null;

    try {
      Class.forName(JDBC_DRIVER);
      Connection connection = DriverManager.getConnection(DB_LOCATION,
          DBUsername, DBPassword);
      Statement statement = connection.createStatement(
          ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

      ResultSet data = statement.executeQuery("SELECT FROM_DATE, TO_DATE, "
          + "DETAILS, APPROVED FROM REQUEST WHERE EMPLOYEE_ID = " + userID);

      int i = 0;
      while (data.next()) {
        i++;
      }
      data.first();

      request = new String[i][4];

      for (int j = 0; j < i; j++) {
        request[j][0] = data.getString("FROM_DATE");

        request[j][1] = data.getString("TO_DATE");

        request[j][2] = data.getString("DETAILS");

        request[j][3] = data.getString("APPROVED");

        data.next();
      }

      statement.close();

      connection.close();

    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }

    return request;
  }

  public static String[][] populateSchedule(Date startDate, Date endDate) {
    String[][] schedule = null;

    try {
      Class.forName(JDBC_DRIVER);
      Connection connection = DriverManager.getConnection(DB_LOCATION,
          DBUsername, DBPassword);
      Statement statement = connection.createStatement(
          ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

      ResultSet data = statement.executeQuery("SELECT "
          + "concat (FIRST_NAME, ' ',LAST_NAME) as NAME, SCHEDULE_DATE, "
          + "concat (START_TIME, '-', END_TIME), SCHEDULE.SHIFT,  "
          + "SCHEDULE_TYPE from ACCOUNT inner join works on "
          + "ACCOUNT.id = WORKS.EMPLOYEE_ID inner join SCHEDULE "
          + "on SCHEDULE.id = SCHEDULE_ID inner join SHIFT "
          + "on SHIFT.id = SCHEDULE.id where SCHEDULE_DATE " + "BETWEEN '"
          + startDate + "' AND '" + endDate + "'");

      int i = 0;
      while (data.next()) {
        i++;
      }
      data.first();

      schedule = new String[i][5];

      for (int j = 0; j < i; j++) {
        schedule[j][0] = data.getString("NAME");

        schedule[j][1] = data.getString("SCHEDULE_DATE");

        schedule[j][2] = data.getString("concat(START_TIME, '-', END_TIME)");

        schedule[j][3] = data.getString("SHIFT");

        schedule[j][4] = data.getString("SCHEDULE_TYPE");

        data.next();
      }

      statement.close();

      connection.close();

    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }

    return schedule;
  }

}