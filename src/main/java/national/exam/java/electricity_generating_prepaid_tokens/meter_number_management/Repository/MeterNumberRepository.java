package national.exam.java.electricity_generating_prepaid_tokens.meter_number_management.Repository;

import national.exam.java.electricity_generating_prepaid_tokens.meter_number_management.MeterNumber;
import national.exam.java.electricity_generating_prepaid_tokens.user_registration.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeterNumberRepository extends JpaRepository<MeterNumber,Long> {
    MeterNumber findByNumber(String number);

    List<MeterNumber> findByUserId(Long userId);
    boolean existsByMeterNumber(String meterNumber);
}
