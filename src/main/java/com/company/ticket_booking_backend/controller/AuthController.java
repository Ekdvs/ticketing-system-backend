package com.company.ticket_booking_backend.controller;

import com.company.ticket_booking_backend.model.*;
import com.company.ticket_booking_backend.repository.UserRepository;
import com.company.ticket_booking_backend.security.JwtUtil;
import com.company.ticket_booking_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    // REGISTER
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(@RequestBody User user) {

        if (user.getFirstName() == null ||
                user.getLastName() == null ||
                user.getEmail() == null ||
                user.getPassword() == null) {

            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("All fields are required", true, false, null));
        }

        try {
            User createdUser = userService.createUser(user);

            UserResponse response = new UserResponse();
            response.setId(createdUser.getId());
            response.setFirstName(createdUser.getFirstName());
            response.setLastName(createdUser.getLastName());
            response.setEmail(createdUser.getEmail());
            response.setAvatar(createdUser.getAvatar());
            response.setMobile(createdUser.getMobile());
            response.setRole(createdUser.getRole().name());

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>("User registered successfully", false, true, response));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Internal server error", true, false, null));
        }
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest req) {

        try {
            User user = userService.loginUser(req.getEmail(), req.getPassword());

            String accessToken = jwtUtil.generateToken(user);
            String refreshToken = java.util.UUID.randomUUID().toString();

            user.setRefreshToken(refreshToken);
            userRepository.save(user);

            LoginResponse res = new LoginResponse(
                    accessToken,
                    refreshToken,
                    user.getEmail(),
                    user.getRole().name()
            );

            return ResponseEntity.ok(new ApiResponse<>("Login successful", false, true, res));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(e.getMessage(), true, false, null));
        }
    }

    // VERIFY EMAIL
    @GetMapping("/verify-email/{token}")
    public ResponseEntity<ApiResponse<String>> verifyEmail(@PathVariable String token) {

        try {
            userService.verifyEmail(token);

            return ResponseEntity.ok(
                    new ApiResponse<>("Email verified successfully", false, true, null)
            );

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(e.getMessage(), true, false, null));
        }
    }

    // FORGOT PASSWORD
    @PostMapping("/forgot-password/{email}")
    public ResponseEntity<ApiResponse> forgotPassword(@PathVariable String email) {

        try {
            userService.forgotPassword(email);

            return ResponseEntity.ok(
                    new ApiResponse<>("Password reset link sent", false, true, null)
            );

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(e.getMessage(), true, false, null));
        }
    }

    // VERIFY OTP
    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse<String>> verifyOtp(@RequestParam String email,
                                                         @RequestParam String otp) {

        try {
            userService.verifyOtp(email, otp);

            return ResponseEntity.ok(
                    new ApiResponse<>("OTP verified", false, true, null)
            );

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(e.getMessage(), true, false, null));
        }
    }

    // RESET PASSWORD
    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(@RequestParam String email,
                                                     @RequestParam String newPassword) {

        try {
            userService.resetPassword(email, newPassword);

            return ResponseEntity.ok(
                    new ApiResponse<>("Password reset successful", false, true, null)
            );

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(e.getMessage(), true, false, null));
        }
    }
}