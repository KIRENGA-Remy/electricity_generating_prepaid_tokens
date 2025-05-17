package national.exam.java.electricity_generating_prepaid_tokens.electricity_purchase.Repository;

import national.exam.java.electricity_generating_prepaid_tokens.electricity_purchase.PurchasedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchasedTokenRepository extends JpaRepository<PurchasedToken, Long> {
    PurchasedToken finfByToken(String token);
    List<PurchasedToken> findByMeterNumber(String meterNumber);
    boolean existsByToken(String token);

    PurchasedToken findByToken(String token);
}
