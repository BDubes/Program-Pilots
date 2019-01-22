package manager;

import javafx.beans.property.SimpleStringProperty;

public class ManagerValues {
  private String EmployeeName;
  private String EmployeeName2;
  private String EmpScheduleDate2;
  private String EmpShiftNum2;
  private String EmpScheduleDate;
  private String EmpScheduleTime;
  private String EmpShiftNum;
  private String EmpShiftType;
  private SimpleStringProperty ShiftID;
  private SimpleStringProperty EmployeeID;
  private SimpleStringProperty RequestID;
  private SimpleStringProperty RequestName;
  private SimpleStringProperty RequestFrom;
  private SimpleStringProperty RequestTo;
  private SimpleStringProperty RequestsInfo;
  private SimpleStringProperty RequestApproved;
  private SimpleStringProperty RecordsEmpID;
  private SimpleStringProperty RecordsName;
  private SimpleStringProperty RecordsHours;
  private SimpleStringProperty RecordPay;

  public ManagerValues() {

  }

  public ManagerValues(String EmployeeName2, String EmpScheduleDate2,
      String EmpShiftNum2, String EmployeeName, String EmpScheduleDate,
      String EmpScheduleTime, String EmpShiftNum, String EmpShiftType,
      String ShiftID, String EmployeeID, String RequestID, String RequestName,
      String RequestFrom, String RequestTo, String RequestsInfo,
      String RequestApproved, String RecordsEmpID, String RecordsName,
      String RecordsHours, String RecordPay) {
    super();
    this.EmployeeName2 = EmployeeName2;// new
                                       // SimpleStringProperty(EmployeeName2);
    this.EmpScheduleDate2 = EmpScheduleDate2;// new
                                             // SimpleStringProperty(EmpScheduleDate2);
    this.EmpShiftNum2 = EmpShiftNum2;// new SimpleStringProperty(EmpShiftNum2);
    this.EmployeeName = EmployeeName; // new SimpleStringProperty(EmployeeName);
    this.EmpScheduleDate = EmpScheduleDate;// new
                                           // SimpleStringProperty(EmpScheduleDate);
    this.EmpScheduleTime = EmpScheduleTime;// new
                                           // SimpleStringProperty(EmpScheduleTime);
    this.EmpShiftNum = EmpShiftNum;// new SimpleStringProperty(EmpShiftNum);
    this.EmpShiftType = EmpShiftType;// new SimpleStringProperty(EmpShiftType);
    this.ShiftID = new SimpleStringProperty(ShiftID);
    this.EmployeeID = new SimpleStringProperty(EmployeeID);
    this.RequestID = new SimpleStringProperty(RequestID);
    this.RequestName = new SimpleStringProperty(RequestName);
    this.RequestFrom = new SimpleStringProperty(RequestFrom);
    this.RequestTo = new SimpleStringProperty(RequestTo);
    this.RequestsInfo = new SimpleStringProperty(RequestsInfo);
    this.RequestApproved = new SimpleStringProperty(RequestApproved);
    this.RecordsEmpID = new SimpleStringProperty(RecordsEmpID);
    this.RecordsName = new SimpleStringProperty(RecordsName);
    this.RecordsHours = new SimpleStringProperty(RecordsHours);
    this.RecordPay = new SimpleStringProperty(RecordPay);

  }

  public String getEmployeeName2() {
    return EmployeeName2;// .get();
  }

  public void setEmployeeName2(String EmployeeName2) {
    this.EmployeeName2 = EmployeeName2;// .set(EmployeeName2);
  }

  public String getEmpScheduleDate2() {
    return EmpScheduleDate2;// .get();
  }

  public void setEmpScheduleDate2(String EmpScheduleDate2) {
    this.EmpScheduleDate2 = EmpScheduleDate2; // .set(EmpScheduleDate2);
  }

  public String getEmpShiftNum2() {
    return EmpShiftNum2;// .get();
  }

  public void setEmpShiftNum2(String EmpShiftNum2) {
    this.EmpShiftNum2 = EmpShiftNum2;// .set(EmpShiftNum2);
  }

  public String getEmployeeName() {
    return EmployeeName;// .get();
  }

  public void setEmployeeName(String EmployeeName) {
    this.EmployeeName = EmployeeName;// .set(EmployeeName);
  }

  public String getEmpScheduleDate() {
    return EmpScheduleDate;// .get();
  }

  public void setEmpScheduleDate(String EmpScheduleDate) {
    this.EmpScheduleDate = EmpScheduleDate;// .set(EmpScheduleDate);
  }

  public String getEmpScheduleTime() {
    return EmpScheduleTime;// .get();
  }

  public void setEmpScheduleTime(String EmpScheduleTime) {
    this.EmpScheduleTime = EmpScheduleTime;// .set(EmpScheduleTime);
  }

  public String getEmpShiftNum() {
    return EmpShiftNum;// .get();
  }

  public void setEmpShiftNum(String EmpShiftNum) {
    this.EmpShiftNum = EmpShiftNum;// .set(EmpShiftNum);
  }

  public String getEmpShiftType() {
    return EmpShiftType;// .get();
  }

  public void setEmpShiftType(String EmpShiftType) {
    this.EmpShiftType = EmpShiftType;// .set(EmpShiftType);
  }

  public String getShiftID() {
    return ShiftID.get();
  }

  public void setShiftID(String ShiftID) {
    this.ShiftID.set(ShiftID);
  }

  public String getEmployeeID() {
    return EmployeeID.get();
  }

  public void setEmployeeID(String EmployeeID) {
    this.EmployeeID.set(EmployeeID);
  }

  public String getRequestID() {
    return RequestID.get();
  }

  public void setRequestID(String RequestID) {
    this.RequestID.set(RequestID);
  }

  public String getRequestName() {
    return RequestName.get();
  }

  public void setRequestName(String RequestName) {
    this.RequestName.set(RequestName);
  }

  public String getRequestFrom() {
    return RequestFrom.get();
  }

  public void setRequestFrom(String RequestFrom) {
    this.RequestFrom.set(RequestFrom);
  }

  public String getRequestTo() {
    return RequestTo.get();
  }

  public void setRequestTo(String RequestTo) {
    this.RequestTo.set(RequestTo);
  }

  public String getRequestsInfo() {
    return RequestsInfo.get();
  }

  public void setRequestsInfo(String RequestsInfo) {
    this.RequestsInfo.set(RequestsInfo);
  }

  public String getRequestApproved() {
    return RequestApproved.get();
  }

  public void setRequestApproved(String RequestApproved) {
    this.RequestApproved.set(RequestApproved);
  }

  public String getRecordsEmpID() {
    return RecordsEmpID.get();
  }

  public void setRecordsEmpID(String RecordsEmpID) {
    this.RecordsEmpID.set(RecordsEmpID);
  }

  public String getRecordsName() {
    return RecordsName.get();
  }

  public void setRecordsName(String RecordsName) {
    this.RecordsName.set(RecordsName);
  }

  public String getRecordsHours() {
    return RecordsHours.get();
  }

  public void setRecordsHours(String RecordsHours) {
    this.RecordsHours.set(RecordsHours);
  }

  public String getRecordPay() {
    return RecordPay.get();
  }

  public void setRecordPay(String RecordPay) {
    this.RecordPay.set(RecordPay);
  }

}
