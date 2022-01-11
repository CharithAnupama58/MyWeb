package view.tm;

public class BagTM {
    private String bagNo;
    private double kilo;
    private double bagWeight;
    private double leavesWeight;

    public BagTM() {
    }

    public BagTM(String bagNo, double kilo, double bagWeight, double leavesWeight) {
        this.setBagNo(bagNo);
        this.setKilo(kilo);
        this.setBagWeight(bagWeight);
        this.setLeavesWeight(leavesWeight);
    }

    public String getBagNo() {
        return bagNo;
    }

    public void setBagNo(String bagNo) {
        this.bagNo = bagNo;
    }

    public double getKilo() {
        return kilo;
    }

    public void setKilo(double kilo) {
        this.kilo = kilo;
    }

    public double getBagWeight() {
        return bagWeight;
    }

    public void setBagWeight(double bagWeight) {
        this.bagWeight = bagWeight;
    }

    public double getLeavesWeight() {
        return leavesWeight;
    }

    public void setLeavesWeight(double leavesWeight) {
        this.leavesWeight = leavesWeight;
    }
}
