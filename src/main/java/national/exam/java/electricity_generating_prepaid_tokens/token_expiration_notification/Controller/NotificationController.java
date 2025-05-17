package national.exam.java.electricity_generating_prepaid_tokens.token_expiration_notification.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import national.exam.java.electricity_generating_prepaid_tokens.token_expiration_notification.Notification;
import national.exam.java.electricity_generating_prepaid_tokens.token_expiration_notification.Repository.NotificationRepository;
import national.exam.java.electricity_generating_prepaid_tokens.token_expiration_notification.Service.TokenExpiryNotifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@Tag(name = "Notifications", description = "View and manage notifications")
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private TokenExpiryNotifier tokenExpiryNotifier;

    // GET /api/notifications/user/{meterNumber}
    @GetMapping("/user/{meterNumber}")
    @Operation(summary = "Get all notifications for a meter number")
    public List<Notification> getNotificationsByMeter(@PathVariable String meterNumber) {
        return notificationRepository.findByMeterNumber(meterNumber);
    }

    // GET /api/notifications/check-expiry
    @GetMapping("/check-expiry")
    @Operation(summary = "Manually trigger token expiry check")
    public String manualCheck() {
        tokenExpiryNotifier.checkExpiringTokens(); // Triggers logic
        return "Token expiry check completed";
    }
}