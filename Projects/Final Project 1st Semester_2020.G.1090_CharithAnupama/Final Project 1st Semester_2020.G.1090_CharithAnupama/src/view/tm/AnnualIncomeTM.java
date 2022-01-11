package view.tm;

public class AnnualIncomeTM {
    private String year;
    private String kg;

    public AnnualIncomeTM() {
    }

    public AnnualIncomeTM(String year, String kg) {
        this.setYear(year);
        this.setKg(kg);
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getKg() {
        return kg;
    }

    public void setKg(String kg) {
        this.kg = kg;
    }
}
