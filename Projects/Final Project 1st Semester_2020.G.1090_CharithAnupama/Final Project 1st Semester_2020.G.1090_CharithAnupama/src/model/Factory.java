package model;

public class Factory {
    private String date;
    private double totalIncome;

    public Factory() {
    }

    public Factory(String date, double totalIncome) {
        this.setDate(date);
        this.setTotalIncome(totalIncome);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }
}
