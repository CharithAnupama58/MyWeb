package dto;

public class CustomerIncomeDTO {
    private String id;
    private Double income;

    public CustomerIncomeDTO() {
    }

    public CustomerIncomeDTO(String id, Double income) {
        this.setId(id);
        this.setIncome(income);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }
}
