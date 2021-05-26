package utilClasses;

public class Customer {
    private int id;
    private int city_id;
    private String userName;
    private String address;
    private String contactPhone;
    private String email;
    private String password;

    public Customer(int city, String userName, String address, String contactPhone, String email, String password) {
        this.userName = userName;
        this.city_id = city;
        this.address = address;
        this.contactPhone = contactPhone;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", city_id=" + city_id +
                ", userName='" + userName + '\'' +
                ", address='" + address + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCityID() {
        return city_id;
    }

    public void setCityID(int city_id) {
        this.city_id = city_id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String name) {
        this.userName = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
