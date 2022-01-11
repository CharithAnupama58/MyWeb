package dto;

public class RegisterDetailsDTO {
    private String rId;
    private String cID;
    private String sID;
    private String date;

    public RegisterDetailsDTO() {
    }

    public RegisterDetailsDTO(String rId, String cID, String sID, String date) {
        this.setrId(rId);
        this.setcID(cID);
        this.setsID(sID);
        this.setDate(date);
    }

    public String getrId() {
        return rId;
    }

    public void setrId(String rId) {
        this.rId = rId;
    }

    public String getcID() {
        return cID;
    }

    public void setcID(String cID) {
        this.cID = cID;
    }

    public String getsID() {
        return sID;
    }

    public void setsID(String sID) {
        this.sID = sID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
