package employee;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EmployeeValues {
  private SimpleStringProperty EmployeeName;
  private SimpleStringProperty EmpScheduleDate;
  private SimpleStringProperty EmpShiftNum;
  private SimpleStringProperty EmpShiftType;
  private SimpleStringProperty EmpEmployeeId;
  private SimpleStringProperty EmployeeName2;
  private SimpleStringProperty EmpRequestFrom;
  private SimpleStringProperty EmpRequestTo;
  private SimpleStringProperty EmpRequests;
  private SimpleStringProperty EmpApproved;

  public EmployeeValues() {

    this.EmployeeName = new SimpleStringProperty();
    this.EmpScheduleDate = new SimpleStringProperty();
    this.EmpShiftNum = new SimpleStringProperty();
    this.EmpShiftType = new SimpleStringProperty();
    this.EmpEmployeeId = new SimpleStringProperty();
    this.EmployeeName2 = new SimpleStringProperty();
    this.EmpRequestFrom = new SimpleStringProperty();
    this.EmpRequestTo = new SimpleStringProperty();
    this.EmpRequests = new SimpleStringProperty();
    this.EmpApproved = new SimpleStringProperty();
  }

  public String getEmployeeName2() {
    return EmployeeName2.get();
  }

  public void setEmployeeName2(String EmployeeName2) {
    this.EmployeeName2.set(EmployeeName2);
  }

  public StringProperty EmployeeName2() {
    return EmployeeName2;
  }

  public String getEmployeeName() {
    return EmployeeName.get();
  }

  public void setEmployeeName(String EmployeeName) {
    this.EmployeeName.set(EmployeeName);
  }

  public StringProperty EmployeeName() {
    return EmployeeName;
  }

  public String getEmpScheduleDate() {
    return EmpScheduleDate.get();
  }

  public void setEmpScheduleDate(String EmpScheduleDate) {
    this.EmpScheduleDate.set(EmpScheduleDate);
  }

  public StringProperty EmpScheduleDate() {
    return EmpScheduleDate;
  }

  public String getEmpShiftNum() {
    return EmpShiftNum.get();
  }

  public void setEmpShiftNum(String EmpShiftNum) {
    this.EmpShiftNum.set(EmpShiftNum);
  }

  public StringProperty EmpShiftNum() {
    return EmpShiftNum;
  }

  public String getEmpShiftType() {
    return EmpShiftType.get();
  }

  public void setEmpShiftType(String EmpShiftType) {
    this.EmpShiftType.set(EmpShiftType);
  }

  public StringProperty EmpShiftType() {
    return EmpShiftType;
  }

  public String getEmpEmployeeId() {
    return EmpEmployeeId.get();
  }

  public void setEmpEmployeeId(String EmpEmployeeId) {
    this.EmpEmployeeId.set(EmpEmployeeId);
  }

  public StringProperty EmpEmployeeId() {
    return EmpEmployeeId;
  }

  public String getEmpRequestFrom() {
    return EmpRequestFrom.get();
  }

  public void setEmpRequestFrom(String EmpRequestFrom) {
    this.EmpRequestFrom.set(EmpRequestFrom);
  }

  public StringProperty EmpRequestFrom() {
    return EmpRequestFrom;
  }

  public String getEmpRequestTo() {
    return EmpRequestTo.get();
  }

  public void setEmpRequestTo(String EmpRequestTo) {
    this.EmpRequestTo.set(EmpRequestTo);
  }

  public StringProperty EmpRequestTo() {
    return EmpRequestTo;
  }

  public String getEmpRequests() {
    return EmpRequests.get();
  }

  public void setEmpRequests(String EmpRequests) {
    this.EmpRequests.set(EmpRequests);
  }

  public StringProperty EmpRequests() {
    return EmpRequests;
  }

  public String getEmpApproved() {
    return EmpApproved.get();
  }

  public void setEmpApproved(String EmpApproved) {
    this.EmpApproved.set(EmpApproved);
  }

  public StringProperty EmpApproved() {
    return EmpApproved;
  }

}
