package view.tm;

public class AttendanceTM {
    private String EmpID;
    private String name;
    private String date;

    public AttendanceTM() {
    }

    public AttendanceTM(String EmpID, String name, String date) {
        this.setEmpID(EmpID);
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
