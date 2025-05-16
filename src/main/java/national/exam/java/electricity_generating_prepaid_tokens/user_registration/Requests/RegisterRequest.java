package national.exam.java.electricity_generating_prepaid_tokens.user_registration.Requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import national.exam.java.electricity_generating_prepaid_tokens.user_registration.Address;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "Firstname is required")
    @JsonProperty("first_name")
    private String firstName;
    @NotBlank(message = "Lastname is required")
    @JsonProperty("last_name")
    private String lastName;
    @NotBlank(message = "Email is required")
    @Email
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
    @NotBlank(message = "Confirm password please!")
    @JsonProperty("confirm_password")
    private String confirmPassword;
    @NotBlank(message = "Address is required")
    private Address address;
    @JsonProperty("phone_number")
    private String phoneNumber;
    private String role;
}
