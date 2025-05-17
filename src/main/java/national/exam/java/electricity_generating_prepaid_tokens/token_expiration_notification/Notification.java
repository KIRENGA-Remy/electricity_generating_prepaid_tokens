package national.exam.java.electricity_generating_prepaid_tokens.token_expiration_notification;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "meter_number", nullable = false, length = 6)
    private String meterNumber;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;
    @Column(name = "issued_date", nullable = false)
    private LocalDateTime issuedDate;
}
