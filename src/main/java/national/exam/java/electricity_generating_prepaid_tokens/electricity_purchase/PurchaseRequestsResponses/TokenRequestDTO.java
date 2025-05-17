package national.exam.java.electricity_generating_prepaid_tokens.electricity_purchase.PurchaseRequestsResponses;


import national.exam.java.electricity_generating_prepaid_tokens.meter_number_management.MeterNumber;

public class TokenRequestDTO {
    private String meter_number;
    private double amount;

    public String getMeter_number() {
        return meter_number;
    }

    public void setMeter_number(String meter_number) {
        this.meter_number = meter_number;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
