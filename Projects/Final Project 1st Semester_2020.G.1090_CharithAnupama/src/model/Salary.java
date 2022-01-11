package model;

public class Salary {
    private String salaryId;
    private String empId;
    private String month;
    private double priceForADay;
    private int workingDayCount;
    private double salary;

    public Salary() {
    }

    public Salary(String salaryId, String empId, String month, double priceForADay, int workingDayCount, double salary) {
        this.setSalaryId(salaryId);
        this.setEmpId(empId);
        this.setMonth(month);
        this.setPriceForADay(priceForADay);
        this.setWorkingDayCount(workingDayCount);
        this.setSalary(salary);
    }

    public String getSalaryId() {
        return salaryId;
    }

    public void setSalaryId(String salaryId) {
        this.salaryId = salaryId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public double getPriceForADay() {
        return priceForADay;
    }

    public void setPriceForADay(double priceForADay) {
        this.priceForADay = priceForADay;
    }

    public int getWorkingDayCount() {
        return workingDayCount;
    }

    public void setWorkingDayCount(int workingDayCount) {
        this.workingDayCount = workingDayCount;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
