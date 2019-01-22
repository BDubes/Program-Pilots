package manager;

import javafx.beans.property.SimpleStringProperty;

public class EmployeeRecords {

  private SimpleStringProperty firstName;
  private SimpleStringProperty lastName;
  private SimpleStringProperty id;
  private String hoursWeek;
  private SimpleStringProperty payWeek;

  public EmployeeRecords(String id, String firstName, String lastName, String payWeek
      , String hoursWeek) {
    this.id = new SimpleStringProperty(id);
    this.firstName = new SimpleStringProperty(firstName);
    this.lastName = new SimpleStringProperty(lastName);
    this.payWeek = new SimpleStringProperty(payWeek);
    this.hoursWeek = hoursWeek;
  }

  public String getFirstName() {
    return firstName.get();
  }

  public void setFirstName(String firstName) {
    this.firstName.set(firstName);
  }

  public String getLastName() {
    return lastName.get();
  }

  public void setLastName(String lastName) {
    this.lastName.set(lastName);
  }

  public String getId() {
    return id.get();
  }

  public void setId(String id) {
    this.id.set(id);
  }

  public String getHoursWeek() {
    return hoursWeek;
  }

  public void setHoursWeek(String hoursWeek) {
    System.out.println("how many hours : " + hoursWeek);
    this.hoursWeek = hoursWeek;
  }

  public String getPayWeek() {
    return payWeek.get();
  }

  public void setPayWeek(String payWeek) {
    this.payWeek.set(payWeek);
  }

}
