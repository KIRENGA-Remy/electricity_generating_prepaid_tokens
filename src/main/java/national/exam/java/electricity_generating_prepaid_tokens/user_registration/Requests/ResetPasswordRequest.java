package national.exam.java.electricity_generating_prepaid_tokens.user_registration.Requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Request payload to reset password")
public class ResetPasswordRequest {
    @NotBlank(message = "Token is required")
    @Schema(description = "Token sent on you email")
    private String token;
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @Schema(description = "New password to replace forgotten one", example = "Password123!")
    private String newPassword;
}
