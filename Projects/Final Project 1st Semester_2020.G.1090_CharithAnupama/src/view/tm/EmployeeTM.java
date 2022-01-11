package view.tm;

public class EmployeeTM {

    private String empID;
    private String name;
    private String address;
    private String contact;
    private String nic;

    public EmployeeTM() {
    }

    public EmployeeTM(String empID, String name, String address, String contact, String nic) {
        this.setEmpID(empID);
        this.setName(name);
        this.setAddress(address);
        this.setContact(contact);
        this.setNic(nic);
    }

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }
}
