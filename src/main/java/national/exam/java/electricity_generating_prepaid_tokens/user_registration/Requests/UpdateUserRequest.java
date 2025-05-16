package national.exam.java.electricity_generating_prepaid_tokens.user_registration.Requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import national.exam.java.electricity_generating_prepaid_tokens.user_registration.Address;

@Data
@Schema(description = "Request payload to update user account")
public class UpdateUserRequest {
    @Schema(description = "Email address of the user", example = "user@example.com")
    private String email;
    @Schema(description = "First name of the user", example = "John")
    private String firstName;
    @Schema(description = "Last name of the user", example = "Doe")
    private String lastName;
    @Schema(description = "Phone number of the user", example = "0791234567")
    private String phoneNumber;
    @Schema(description = "Address of the user", example = "123 Main St")
    private Address address;
}
