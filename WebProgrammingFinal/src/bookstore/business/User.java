package bookstore.business;

import java.io.Serializable;

public class User implements Serializable {
    private Long userId;
    private String firstName;
    private String lastName;
    private String passwd;
    private String email;
    private String address1;
    private String address2;
    private String city;
    private String st;
    private String zip;
    private String country;

    public User() {
        firstName = "";
        lastName = "";
        passwd = "";
        email = "";
        address1 = "";
        address2 = "";
        city = "";
        st = "";
        zip = "";
        country = "";
    }
    
    public Long getId() {
        return userId;
    }

    public void setId(Long userId) {
        this.userId = userId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        if(firstName == null) {
            firstName = "";
        }
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        if(lastName == null) {
            lastName = "";
        }
        return lastName;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getPasswd() {
        if(passwd == null) {
            passwd = "";
        }
        return passwd;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        if(email == null) {
            email = "";
        }
        return email;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress1() {
        if(address1 == null) {
            address1 = "";
        }
        return address1;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress2() {
        if(address2 == null) {
            address2 = "";
        }
        return address2;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        if (city == null) {
            city = "";
        }
        return city;
    }

    // State is a reserved SQL word, so the field is called "st" instead.
    public void setState(String state) {
        st = state;
    }

    public String getState() {
        if(st == null) {
            st = "";
        }
        return st;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getZip() {
        if(zip == null) {
            zip = "";
        }
        return zip;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        if(country == null) {
            country = "";
        }
        return country;
    }

    // this works, but it mixes the Model and the View
    public String getAddressHTMLFormat() {
        String address = firstName + " " + lastName + "<br>";

        address += address1 + "<br>";

        if (address2 == null || address2.equals("") || address2.equals(" ")) {
            address += "";
        } else {
            address += address2 + "<br>";
        }

        address += city + ", " + st + " " + zip + "<br>"
                + country;

        return address;
    }

}
