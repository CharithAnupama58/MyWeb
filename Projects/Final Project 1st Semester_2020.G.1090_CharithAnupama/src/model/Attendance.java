package model;

public class Attendance {
    private String EmpID;
    private String name;
    private String date;

    public Attendance() {
    }

    public Attendance(String empID, String name, String date) {
        setEmpID(empID);
        this.setName(name);
        this.setDate(date);
    }

    public String getEmpID() {
        return EmpID;
    }

    public void setEmpID(String empID) {
        EmpID = empID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
