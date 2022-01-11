package entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Student implements SuperEntity{
    @Id
    private String ID;
    private String name;
    private String address;
    private String nic;
    private String tell;
    @OneToMany(mappedBy = "studentID")
    private List<RegisterDetails> registerDetailsList;

    public Student(String ID, String name, String address, String nic, String tell, List<RegisterDetails> registerDetailsList) {
        this.ID = ID;
        this.name = name;
        this.address = address;
        this.nic = nic;
        this.tell = tell;
        this.registerDetailsList = registerDetailsList;
    }

    public Student() {
    }

    public Student(String stId, String stName, String stAddress, String stNic, String stTel) {
        this.setStId(stId);
        this.setStName(stName);
        this.setStAddress(stAddress);
        this.setStNic(stNic);
        this.setStTel(stTel);
    }

    public String getStId() {
        return ID;
    }

    public void setStId(String stId) {
        this.ID = stId;
    }

    public String getStName() {
        return name;
    }

    public void setStName(String stName) {
        this.name = stName;
    }

    public String getStAddress() {
        return address;
    }

    public void setStAddress(String stAddress) {
        this.address = stAddress;
    }

    public String getStNic() {
        return nic;
    }

    public void setStNic(String stNic) {
        this.nic = stNic;
    }

    public String getStTel() {
        return tell;
    }

    public void setStTel(String stTel) {
        this.tell = stTel;
    }
}
