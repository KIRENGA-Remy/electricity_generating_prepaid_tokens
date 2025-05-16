package national.exam.java.electricity_generating_prepaid_tokens.user_registration;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    @JsonProperty("city")
    private String city;
    @JsonProperty("country")
    private String country;
    @JsonProperty("postal_code")
    private String postalCode;
    @JsonProperty("street")
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
