package Models.User;

public class Address {
    private final String city;
    private final String postcode;
    private final String street;

    public Address(String city, String postcode, String street) {
        this.city = city;
        this.postcode = postcode;
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getStreet() {
        return street;
    }
    
}
