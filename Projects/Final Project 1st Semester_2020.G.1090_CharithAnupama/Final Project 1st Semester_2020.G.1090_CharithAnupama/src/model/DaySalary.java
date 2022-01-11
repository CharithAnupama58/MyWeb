package model;

public class DaySalary {
    private String empId;
    private double salary;

    public DaySalary() {
    }

    public DaySalary(String empId, double salary) {
        this.setEmpId(empId);
        this.setSalary(salary);
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
