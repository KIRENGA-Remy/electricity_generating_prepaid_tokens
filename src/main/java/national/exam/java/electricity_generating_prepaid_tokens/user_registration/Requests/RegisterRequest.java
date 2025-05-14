package national.exam.java.electricity_generating_prepaid_tokens.user_registration.Requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import national.exam.java.electricity_generating_prepaid_tokens.user_registration.Address;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "Firstname is required")
    private String firstName;
    @NotBlank(message = "Lastname is required")
    private String lastName;
    @NotBlank(message = "Email is required")
    @Email
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
    @NotBlank(message = "Confirm password please!")
    private String confirmPassword;
    @NotBlank(message = "Address is required")
    private Address address;
    private String phoneNumber;
    private String role;
}
