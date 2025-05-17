package national.exam.java.electricity_generating_prepaid_tokens.electricity_purchase.Repository;

import national.exam.java.electricity_generating_prepaid_tokens.electricity_purchase.PurchasedToken;
import national.exam.java.electricity_generating_prepaid_tokens.meter_number_management.MeterNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface PurchasedTokenRepository extends JpaRepository<PurchasedToken, Long> {

    PurchasedToken findByToken(String token);

    List<PurchasedToken> findByMeterNumber(MeterNumber meterNumber);

    boolean existsByToken(String token);

    @Query(value = "SELECT * FROM purchased_tokens t WHERE DATE_ADD(t.purchased_date, INTERVAL t.token_value_days DAY) <= ?1", nativeQuery = true)
    List<PurchasedToken> findExpiringTokens(LocalDateTime expiryTime);

    @Query(value = "SELECT * FROM purchased_tokens t WHERE DATE_ADD(t.purchased_date, INTERVAL t.token_value_days DAY) BETWEEN ?1 AND ?2", nativeQuery = true)
    List<PurchasedToken> findExpiringTokens(LocalDateTime now, LocalDateTime fiveHoursFromNow);

    List<PurchasedToken> findByPurchasedDateBetween(LocalDateTime start, LocalDateTime end);
}