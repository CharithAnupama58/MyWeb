package model;

public class Customer {
    private String customerId;
    private String customerName;
    private String address;
    private String contact;
    private String nic;

    public Customer() {
    }

    public Customer(String customerId, String customerName, String address, String contact, String nic) {
        this.setCustomerId(customerId);
        this.setCustomerName(customerName);
        this.setAddress(address);
        this.setContact(contact);
        this.setNic(nic);
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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
