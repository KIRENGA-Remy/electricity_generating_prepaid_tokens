package national.exam.java.electricity_generating_prepaid_tokens.meter_number_management;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import national.exam.java.electricity_generating_prepaid_tokens.user_registration.User;
import org.hibernate.annotations.OnDelete;

@Entity
@Table(name = "meter_numbers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeterNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String number;
    @ToString.Exclude
    @ManyToOne
    private User user;
    public MeterNumber(String number, User user) {
        this.number = number;
        this.user = user;
    }
}
