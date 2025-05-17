package national.exam.java.electricity_generating_prepaid_tokens.electricity_purchase.PurchaseRequestsResponses;

import java.time.LocalDateTime;

public class TokenValidationDTO {
    private String formattedToken;
    private int days;
    private String meterNumber;
    private LocalDateTime purchaseDate;
    private double amount;

    public TokenValidationDTO(String formattedToken, int days, String meterNumber, LocalDateTime purchaseDate, double amount) {
        this.formattedToken = formattedToken;
        this.days = days;
        this.meterNumber = meterNumber;
        this.purchaseDate = purchaseDate;
        this.amount = amount;
    }

    public String getFormattedToken() {
        return formattedToken;
    }

    public int getDays() {
        return days;
    }

    public String getMeterNumber() {
        return meterNumber;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public double getAmount() {
        return amount;
    }
}