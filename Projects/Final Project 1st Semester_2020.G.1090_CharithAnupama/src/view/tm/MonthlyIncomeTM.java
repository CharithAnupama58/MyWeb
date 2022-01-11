package view.tm;

public class MonthlyIncomeTM {
    private String date;
    private String kilo;

    public MonthlyIncomeTM() {
    }

    public MonthlyIncomeTM(String date, String kilo) {
        this.setDate(date);
        this.setKilo(kilo);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getKilo() {
        return kilo;
    }

    public void setKilo(String kilo) {
        this.kilo = kilo;
    }
}
