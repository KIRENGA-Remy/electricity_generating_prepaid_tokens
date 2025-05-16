package national.exam.java.electricity_generating_prepaid_tokens.user_registration.Requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import national.exam.java.electricity_generating_prepaid_tokens.user_registration.Address;
import lombok.Data;

@Data
@Schema(description = "Request payload to register a new user")
public class RegisterRequest {
    @NotBlank(message = "Firstname is required")
    @JsonProperty("first_name")
    @Schema(description = "First name of the user", example = "John")
    private String firstName;
    @NotBlank(message = "Lastname is required")
    @JsonProperty("last_name")
    @Schema(description = "Last name of the user", example = "Christella")
    private String lastName;
    @NotBlank(message = "Email is required")
    @Email
    @Schema(description = "Email address of the user", example = "example@gmail.com")
    private String email;
    @NotBlank(message = "Password is required")
    @Schema(description = "Password of the user account", example = "Password@123")
    private String password;
    @NotBlank(message = "Confirm password please!")
    @JsonProperty("confirm_password")
    @Schema(description = "Confirm password same as password", example = "Password@123")
    private String confirmPassword;
    @NotBlank(message = "Address is required")
    @Schema(description = "Address of the user", example = "123 Main St")
    private Address address;
    @JsonProperty("phone_number")
    @Schema(description = "Phone number of the user", example = "0791234567")
    private String phoneNumber;
    private String role;
}
