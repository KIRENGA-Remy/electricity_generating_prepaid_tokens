package national.exam.java.electricity_generating_prepaid_tokens.electricity_purchase.Repository;

import national.exam.java.electricity_generating_prepaid_tokens.electricity_purchase.PurchasedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PurchasedTokenRepository extends JpaRepository<PurchasedToken, Long> {
    PurchasedToken finfByToken(String token);
    List<PurchasedToken> findByMeterNumber(String meterNumber);
    boolean existsByToken(String token);

    PurchasedToken findByToken(String token);
//    @Query("SELECT t FROM purchased_token t WHERE t.purchased_date + t.token_value_days * 24 <= :expiryTime ")
//    List<PurchasedToken> findExpiringTokens(@Param("expiryTime") LocalDateTime expiryTime);

    @Query("SELECT t FROM purchased_token t JOIN FETCH t.meter_number m JOIN FETCH m.user WHERE DATE_ADD(t.purchased_date, INTERVAL t.token_value_days DAY) BETWEEN :now AND :fiveHoursFromNow")
    List<PurchasedToken> findExpiringTokens(@Param("now") LocalDateTime now, @Param("fiveHoursFromNow") LocalDateTime fiveHoursFromNow);

    List<PurchasedToken> findByPurchasedDateBetween(LocalDateTime start, LocalDateTime end);
}
