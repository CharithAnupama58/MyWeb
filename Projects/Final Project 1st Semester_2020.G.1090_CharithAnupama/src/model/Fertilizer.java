package model;

public class Fertilizer {
    private String fertilizerID;
    private String fertilizerName;
    private int packSize;
    private double unitPrice;

    public Fertilizer() {
    }

    public Fertilizer(String fertilizerID, String fertilizerName, int packSize, double unitPrice) {
        this.setFertilizerID(fertilizerID);
        this.setFertilizerName(fertilizerName);
        this.setPackSize(packSize);
        this.setUnitPrice(unitPrice);
    }

    public String getFertilizerID() {
        return fertilizerID;
    }

    public void setFertilizerID(String fertilizerID) {
        this.fertilizerID = fertilizerID;
    }

    public String getFertilizerName() {
        return fertilizerName;
    }

    public void setFertilizerName(String fertilizerName) {
        this.fertilizerName = fertilizerName;
    }

    public int getPackSize() {
        return packSize;
    }

    public void setPackSize(int packSize) {
        this.packSize = packSize;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
