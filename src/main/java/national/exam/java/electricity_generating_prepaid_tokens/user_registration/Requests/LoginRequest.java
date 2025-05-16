package national.exam.java.electricity_generating_prepaid_tokens.user_registration.Requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
@Schema(description = "Request payload to login a user")
public class LoginRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email address")
    @Schema(description = "Email address of the user", example = "user@example.com")
    private String email;

    @NotBlank(message = "Password is required")
    @Schema(description = "Password for the user account", example = "Password123!")
    private String password;
}