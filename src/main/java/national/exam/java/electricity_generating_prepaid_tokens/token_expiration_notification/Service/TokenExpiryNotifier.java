package national.exam.java.electricity_generating_prepaid_tokens.token_expiration_notification.Service;

import national.exam.java.electricity_generating_prepaid_tokens.electricity_purchase.PurchasedToken;
import national.exam.java.electricity_generating_prepaid_tokens.electricity_purchase.Repository.PurchasedTokenRepository;
import national.exam.java.electricity_generating_prepaid_tokens.electricity_purchase.TokenStatus;
import national.exam.java.electricity_generating_prepaid_tokens.meter_number_management.MeterNumber;
import national.exam.java.electricity_generating_prepaid_tokens.token_expiration_notification.Notification;
import national.exam.java.electricity_generating_prepaid_tokens.token_expiration_notification.Repository.NotificationRepository;
import national.exam.java.electricity_generating_prepaid_tokens.user_registration.Repository.UserRepository;
import national.exam.java.electricity_generating_prepaid_tokens.user_registration.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TokenExpiryNotifier {

    @Autowired
    private PurchasedTokenRepository tokenRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private NotificationRepository notificationRepo;

    @Autowired
    private JavaMailSender mailSender;

    // Run every hour
    @Scheduled(cron = "0 0 * * * ?")
    public void checkExpiringTokens() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime fiveHoursFromNow = now.plusHours(5);

        List<PurchasedToken> tokens = tokenRepo.findByPurchasedDateBetween(now, fiveHoursFromNow);

        for (PurchasedToken token : tokens) {
            if (token.getTokenStatus() != TokenStatus.NEW) continue;

            MeterNumber meterNumber = token.getMeterNumber(); // Now it's an object, not String
            User user = meterNumber.getUser();

            String message = String.format(
                    "Dear %s %s,\n\nREG reminds you that the token in meter number %s will expire in 5 hours.",
                    user.getFirstName(), user.getLastName(), meterNumber.getNumber()
            );

            saveNotification(meterNumber.getNumber(), message, user.getId());
            sendEmail(user.getEmail(), "Token Expiration Reminder", message);
        }
    }

    private void saveNotification(String meterNumber, String message, Long userId) {
        Notification notification = new Notification();
        notification.setMeterNumber(meterNumber);
        notification.setMessage(message);
        notification.setIssuedDate(LocalDateTime.now());

        notificationRepo.save(notification);
    }

    private void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
}