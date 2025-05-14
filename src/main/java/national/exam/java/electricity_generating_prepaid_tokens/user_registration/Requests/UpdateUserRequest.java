package national.exam.java.electricity_generating_prepaid_tokens.user_registration.Requests;

import lombok.Data;
import national.exam.java.electricity_generating_prepaid_tokens.user_registration.Address;

@Data
public class UpdateUserRequest {
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Address address;
}
