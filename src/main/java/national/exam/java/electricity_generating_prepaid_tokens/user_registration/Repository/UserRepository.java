package national.exam.java.electricity_generating_prepaid_tokens.user_registration.Repository;

import national.exam.java.electricity_generating_prepaid_tokens.user_registration.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Checks if a user exists by email or phone number
    boolean existsByEmailOrPhoneNumber(String email, String phoneNumber);

    // Finds user by email
    Optional<User> findByEmail(String email);

    @Override
    Optional<User> findById(Long userId);
}