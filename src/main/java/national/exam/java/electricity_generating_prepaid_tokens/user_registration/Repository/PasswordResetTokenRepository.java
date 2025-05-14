package national.exam.java.electricity_generating_prepaid_tokens.user_registration.Repository;

import national.exam.java.electricity_generating_prepaid_tokens.user_registration.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
}
