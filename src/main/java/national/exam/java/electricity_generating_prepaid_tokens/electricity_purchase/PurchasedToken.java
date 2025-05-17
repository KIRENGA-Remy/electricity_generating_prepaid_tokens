package national.exam.java.electricity_generating_prepaid_tokens.electricity_purchase;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import national.exam.java.electricity_generating_prepaid_tokens.meter_number_management.MeterNumber;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchasedToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(nullable = false, name = "meter_number")
    @ManyToOne
    private MeterNumber meterNumber;
    @Column(nullable = false, unique = true, length = 16)
    private String token;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TokenStatus tokenStatus;
    @Column(name = "token_value_days",nullable = false)
    private int tokenValueDays;
    @Column(name = "purchased_date", nullable = false)
    private LocalDateTime purchasedDate;
    @Column(nullable = false)
    private double amount;
}

