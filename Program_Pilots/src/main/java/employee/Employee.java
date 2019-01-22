package employee;

public class Employee {
  private int id;
  private String first_name;
  private String last_name;
  private String account_type;
  private double pay;
  private int overtime_hours;
  private double overtime_pay;

  public Employee(int id, String first_name, String last_name,
      String account_type, double pay, int overtime_hours,
      double overtime_pay) {
    this.id = id;
    this.first_name = first_name;
    this.last_name = last_name;
    this.account_type = account_type;
    this.pay = pay;
    this.overtime_hours = overtime_hours;
    this.overtime_pay = overtime_pay;
  }

  public void print() {
    System.out.println("First name: " + first_name + "\nLast name: " + last_name
        + "\nAccount type: " + account_type + "\nPay: " + pay
        + "\nHours to qualify for overtime: " + overtime_hours
        + "\nPay per hour in overtime: " + overtime_pay + "\n");
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getFirst_name() {
    return first_name;
  }

  public void setFirst_name(String first_name) {
    this.first_name = first_name;
  }

  public String getLast_name() {
    return last_name;
  }

  public void setLast_name(String last_name) {
    this.last_name = last_name;
  }

  public String getAccount_type() {
    return account_type;
  }

  public void setAccount_type(String account_type) {
    this.account_type = account_type;
  }

  public double getPay() {
    return pay;
  }

  public void setPay(double pay) {
    this.pay = pay;
  }

  public int getOvertime_hours() {
    return overtime_hours;
  }

  public void setOvertime_hours(int overtime_hours) {
    this.overtime_hours = overtime_hours;
  }

  public double getOvertime_pay() {
    return overtime_pay;
  }

  public void setOvertime_pay(double overtime_pay) {
    this.overtime_pay = overtime_pay;
  }
}