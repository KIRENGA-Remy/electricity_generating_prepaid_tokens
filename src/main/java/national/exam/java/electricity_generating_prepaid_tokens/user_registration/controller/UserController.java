package national.exam.java.electricity_generating_prepaid_tokens.user_registration.controller;

import jakarta.validation.Valid;
import national.exam.java.electricity_generating_prepaid_tokens.user_registration.Repository.UserRepository;
import national.exam.java.electricity_generating_prepaid_tokens.user_registration.Requests.*;
import national.exam.java.electricity_generating_prepaid_tokens.user_registration.User;
import national.exam.java.electricity_generating_prepaid_tokens.user_registration.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    private final AuthService authService;

    public UserController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody @Valid LoginRequest loginRequest) {
        return  authService.login(loginRequest.getEmail(), loginRequest.getPassword());
    }
    @GetMapping("/users/{id}")
    public ResponseEntity<?> findUserById(@PathVariable("id") Long userId) {
        return authService.getUserById(userId);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@Valid @RequestBody UpdateUserRequest updateUserRequest, @PathVariable("id") Long userId) {
        return authService.editUser(updateUserRequest, userId);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long userId) {
        return authService.deleteById(userId);
    }

    @PostMapping("/users/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody @Valid ForgotPasswordRequest request) {
        return authService.forgotPassword(request);
    }

    @PostMapping("/users/reset-password/{token}")
    public ResponseEntity<String> resetPassword(
            @PathVariable("token") String token,
            @RequestBody @Valid ResetPasswordRequest request) {
        return authService.resetPassword(token, request);
    }
}
