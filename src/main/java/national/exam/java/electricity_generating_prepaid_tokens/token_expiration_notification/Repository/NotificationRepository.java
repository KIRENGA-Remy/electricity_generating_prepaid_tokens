package national.exam.java.electricity_generating_prepaid_tokens.token_expiration_notification.Repository;

import national.exam.java.electricity_generating_prepaid_tokens.token_expiration_notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByMeterNumber(String meterNumber);
}
