package national.exam.java.electricity_generating_prepaid_tokens.electricity_purchase.PurchaseRequestsResponses;

public class TokenResponseDTO {
    private String token;
    private int days;

    public TokenResponseDTO(String token, int days) {
        this.token = token;
        this.days = days;
    }

    public String getToken() {
        return token;
    }
    public int getDays() {
        return days;
    }
}
