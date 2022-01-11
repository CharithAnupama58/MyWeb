package dto;

public class CourseDTO {
    private String cID;
    private String name;
    private int duration;
    private double fee;

    public CourseDTO() {
    }

    public CourseDTO(String cID, String name, int duration, double fee) {
        this.setcID(cID);
        this.setName(name);
        this.setDuration(duration);
        this.setFee(fee);
    }

    public String getcID() {
        return cID;
    }

    public void setcID(String cID) {
        this.cID = cID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }
}
