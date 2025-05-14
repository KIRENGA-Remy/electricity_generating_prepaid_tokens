package national.exam.java.electricity_generating_prepaid_tokens.user_registration;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    private String city;
    private String country;
    private String postalCode;
    private String street;

    public Address() {
    }

    public Address(String city, String country, String postalCode, String street) {
        this.city = city;
        this.country = country;
        this.postalCode = postalCode;
        this.street = street;
    }
}
