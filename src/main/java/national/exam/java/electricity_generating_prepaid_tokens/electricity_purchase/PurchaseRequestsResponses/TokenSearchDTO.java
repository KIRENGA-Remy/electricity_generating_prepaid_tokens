package national.exam.java.electricity_generating_prepaid_tokens.electricity_purchase.PurchaseRequestsResponses;

import national.exam.java.electricity_generating_prepaid_tokens.electricity_purchase.TokenStatus;

import java.time.LocalDateTime;

public class TokenSearchDTO {
    private String formattedToken;
    private int days;
    private TokenStatus status;
    private LocalDateTime purchaseDate;

    public TokenSearchDTO(String formattedToken, int days, TokenStatus status, LocalDateTime purchaseDate) {
        this.formattedToken = formattedToken;
        this.days = days;
        this.status = status;
        this.purchaseDate = purchaseDate;
    }

    public String getFormattedToken() {
        return formattedToken;
    }

    public int getDays() {
        return days;
    }

    public TokenStatus getStatus() {
        return status;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }
}
