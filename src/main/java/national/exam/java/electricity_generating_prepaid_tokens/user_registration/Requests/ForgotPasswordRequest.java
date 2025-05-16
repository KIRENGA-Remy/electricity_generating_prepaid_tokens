package national.exam.java.electricity_generating_prepaid_tokens.user_registration.Requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Request payload to register a new user")
public class ForgotPasswordRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email address")
    @Schema(description = "Your email address to reset password", example = "user@example.com")
    private String email;
}
