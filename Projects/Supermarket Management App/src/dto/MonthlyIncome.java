package dto;

public class MonthlyIncome {
    private int month;
    private double income;

    public MonthlyIncome() {
    }

    public MonthlyIncome(int month, double income) {
        this.setMonth(month);
        this.setIncome(income);
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }
}
