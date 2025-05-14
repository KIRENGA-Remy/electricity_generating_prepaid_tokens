package national.exam.java.electricity_generating_prepaid_tokens.user_registration.service;

import jakarta.validation.Valid;
import national.exam.java.electricity_generating_prepaid_tokens.user_registration.PasswordResetToken;
import national.exam.java.electricity_generating_prepaid_tokens.user_registration.Repository.PasswordResetTokenRepository;
import national.exam.java.electricity_generating_prepaid_tokens.user_registration.Repository.UserRepository;
import national.exam.java.electricity_generating_prepaid_tokens.user_registration.Requests.ForgotPasswordRequest;
import national.exam.java.electricity_generating_prepaid_tokens.user_registration.Requests.RegisterRequest;
import national.exam.java.electricity_generating_prepaid_tokens.user_registration.Requests.ResetPasswordRequest;
import national.exam.java.electricity_generating_prepaid_tokens.user_registration.Requests.UpdateUserRequest;
import national.exam.java.electricity_generating_prepaid_tokens.user_registration.Role;
import national.exam.java.electricity_generating_prepaid_tokens.user_registration.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private  final PasswordResetTokenRepository passwordResetTokenRepository;

    public AuthService(
            UserRepository userRepository,
            BCryptPasswordEncoder passwordEncoder,
            PasswordResetTokenRepository passwordResetTokenRepository){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    public ResponseEntity<String> login(String email, String password) {
        Optional<User> findByEmail = userRepository.findByEmail(email);

        if (findByEmail.isEmpty()) {
            return ResponseEntity.status(404).body("User not found");
        }

        User user = findByEmail.get();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        return ResponseEntity.ok("Successfully logged in");
    }

    public ResponseEntity<String> register(RegisterRequest registerRequest) {

        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            return ResponseEntity.status(400).body("User with this email already exists");
        }

        if (registerRequest.getEmail() == null || registerRequest.getPassword() == null ||
                registerRequest.getFirstName() == null || registerRequest.getLastName() == null) {
            return ResponseEntity.status(400).body("Missing required fields");
        }
            String hashedPassword = passwordEncoder.encode(registerRequest.getPassword());

            User newUser = new User();
            newUser.setEmail(registerRequest.getEmail());
            newUser.setPassword(hashedPassword);
            newUser.setFirstName(registerRequest.getFirstName());
            newUser.setLastName(registerRequest.getLastName());
            newUser.setAddress(registerRequest.getAddress());
            newUser.setPhoneNumber(registerRequest.getPhoneNumber());
        try {
            Role role = Role.valueOf(registerRequest.getRole().toUpperCase());
            newUser.setRole(role);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body("Invalid role: " + registerRequest.getRole());
        }

            userRepository.save(newUser);

            return ResponseEntity.status(201).body("User registered successfully");
        }

        public ResponseEntity<?> getUserById(Long id) {
            Optional<User> findById = userRepository.findById(id);
            if (findById.isEmpty()) {
                return ResponseEntity.status(404).body("User not found");
            }
            return ResponseEntity.ok(findById.get());
        }

        public ResponseEntity<Void> deleteById(Long id) {
            if(!userRepository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
        }

        public ResponseEntity<User> editUser(UpdateUserRequest updateUserRequest, Long userId) {
        Optional<User> findById = userRepository.findById(userId);
        if (findById.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User updateUser =  findById.get();
            if (updateUserRequest.getFirstName() != null) {
                updateUser.setFirstName(updateUserRequest.getFirstName());
            }
            if (updateUserRequest.getLastName() != null) {
                updateUser.setLastName(updateUserRequest.getLastName());
            }
            if (updateUserRequest.getEmail() != null) {
                if (userRepository.findByEmail(updateUserRequest.getEmail()).isPresent()) {
                    throw new RuntimeException("Email already taken");
                }
                updateUser.setEmail(updateUserRequest.getEmail());
            }
            if (updateUserRequest.getPhoneNumber() != null) {
                updateUser.setPhoneNumber(updateUserRequest.getPhoneNumber());
            }
            if (updateUserRequest.getAddress() != null) {
                updateUser.setAddress(updateUserRequest.getAddress());
            }

            User updatedUser = userRepository.save(updateUser);
            return ResponseEntity.ok(updatedUser);
        }
        public ResponseEntity<String> forgotPassword(@Valid ForgotPasswordRequest request) {
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
            if (optionalUser.isEmpty()) {
                return ResponseEntity.status(404).body("User not found");
            }
            String token = generateRandomToken();

            PasswordResetToken resetToken = new PasswordResetToken();
            resetToken.setToken(token);
            resetToken.setUser(optionalUser.get());
            resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(15)); // 15 min expiry

            passwordResetTokenRepository.save(resetToken);

            // In real app, send email with this token
            System.out.println("Reset Token: " + token); // Simulate sending via email

            return ResponseEntity.ok("Password reset token generated. Check your email.");
        }

    public ResponseEntity<String> resetPassword(String token, ResetPasswordRequest request) {
        Optional<PasswordResetToken> tokenOptional = passwordResetTokenRepository.findByToken(token);

        if (tokenOptional.isEmpty()) {
            return ResponseEntity.status(400).body("Invalid token");
        }

        PasswordResetToken resetToken = tokenOptional.get();

        if (LocalDateTime.now().isAfter(resetToken.getExpiryDate())) {
            return ResponseEntity.status(400).body("Token expired");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        passwordResetTokenRepository.delete(resetToken);
        return ResponseEntity.ok("Password reset successfully");
    }
    // ===== HELPER METHODS =====
    private String generateRandomToken() {
        return java.util.UUID.randomUUID().toString();
    }
    }
