package national.exam.java.electricity_generating_prepaid_tokens.user_registration.Requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ForgotPasswordRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email address")
    private String email;
}
