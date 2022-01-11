package dto;




public class StudentDTO{
    private String stId;
    private String stName;
    private String stAddress;
    private String stNic;
    private String stTel;

    public StudentDTO() {
    }

    public StudentDTO(String stId, String stName, String stAddress, String stNic, String stTel) {
        this.setStId(stId);
        this.setStName(stName);
        this.setStAddress(stAddress);
        this.setStNic(stNic);
        this.setStTel(stTel);
    }

    public String getStId() {
        return stId;
    }

    public void setStId(String stId) {
        this.stId = stId;
    }

    public String getStName() {
        return stName;
    }

    public void setStName(String stName) {
        this.stName = stName;
    }

    public String getStAddress() {
        return stAddress;
    }

    public void setStAddress(String stAddress) {
        this.stAddress = stAddress;
    }

    public String getStNic() {
        return stNic;
    }

    public void setStNic(String stNic) {
        this.stNic = stNic;
    }

    public String getStTel() {
        return stTel;
    }

    public void setStTel(String stTel) {
        this.stTel = stTel;
    }
}
