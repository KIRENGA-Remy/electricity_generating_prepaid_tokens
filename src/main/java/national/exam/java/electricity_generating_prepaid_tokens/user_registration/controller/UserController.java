package national.exam.java.electricity_generating_prepaid_tokens.user_registration.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
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
@Tag(name = "EUCL Electricity Generating Prepaid Tokens", description = "User authentication, password reset, and management operations")
public class UserController {
    private final AuthService authService;

    public UserController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/test")
    public String test() {
        return "Public route working!";
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user", responses = {
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error or email already taken")
    })
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }
    @PostMapping("/login")
    @Operation(summary = "Authenticate a user", responses = {
            @ApiResponse(responseCode = "200", description = "User logged in successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<String> loginUser(@RequestBody @Valid LoginRequest loginRequest) {
        return  authService.login(loginRequest.getEmail(), loginRequest.getPassword());
    }
    @GetMapping("/users/{id}")
    @Operation(summary = "Get user by ID", responses = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<?> findUserById(@PathVariable("id") Long userId) {
        return authService.getUserById(userId);
    }

    @PutMapping("/users/{id}")
    @Operation(summary = "Update an existing user", responses = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Email already taken")
    })
    public ResponseEntity<User> updateUser(
            @Valid @RequestBody UpdateUserRequest updateUserRequest,
            @Parameter(description = "ID of the user to update", required = true)
            @PathVariable("id") Long userId) {
        return authService.editUser(updateUserRequest, userId);
    }

    @DeleteMapping("/users/{id}")
    @Operation(summary = "Delete a user by ID", responses = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long userId) {
        return authService.deleteById(userId);
    }

    @PostMapping("/users/forgot-password")
    @Operation(summary = "Send a password reset token to the user's email", responses = {
            @ApiResponse(responseCode = "200", description = "Token generated and sent to email"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request or missing fields")
    })
    public ResponseEntity<String> forgotPassword(@RequestBody @Valid ForgotPasswordRequest request) {
        return authService.forgotPassword(request);
    }

    @PostMapping("/users/reset-password/{token}")
    @Operation(summary = "Reset password using the token sent to the user", responses = {
            @ApiResponse(responseCode = "200", description = "Password reset successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid or expired token"),
            @ApiResponse(responseCode = "400", description = "New password does not meet requirements")
    })
    public ResponseEntity<String> resetPassword(
            @PathVariable("token")
            @Schema(description = "The password reset token sent via email", example = "abc123xyz789") String token,
            @RequestBody @Valid ResetPasswordRequest request) {
        return authService.resetPassword(token, request);
    }
}
