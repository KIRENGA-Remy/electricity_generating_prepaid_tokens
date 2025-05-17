package national.exam.java.electricity_generating_prepaid_tokens.electricity_purchase;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "purchased_tokens")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchasedToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 6)
    private String meter_number;
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

