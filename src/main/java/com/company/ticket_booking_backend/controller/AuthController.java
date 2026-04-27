package com.company.ticket_booking_backend.controller;

import com.company.ticket_booking_backend.model.*;
import com.company.ticket_booking_backend.security.JwtUtil;
import com.company.ticket_booking_backend.service.UserService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;


    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    // ================= REGISTER =================
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(@RequestBody User user) {

        User created = userService.createUser(user);

        UserResponse res = map(created);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("User registered successfully", false, true, res));
    }

    // ================= LOGIN =================
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest req) {

        User user = userService.loginUser(req.getEmail(), req.getPassword());

        String accessToken = jwtUtil.generateToken(user);
        String refreshToken = userService.createRefreshToken(user);

        LoginResponse res = new LoginResponse(
                accessToken,
                refreshToken,
                user.getEmail(),
                user.getRole().name()
        );

        return ResponseEntity.ok(
                new ApiResponse<>("Login successful", false, true, res)
        );
    }

    // ================= REFRESH TOKEN =================
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<LoginResponse>> refresh(@RequestParam String refreshToken) {

        LoginResponse res = userService.refreshToken(refreshToken);

        return ResponseEntity.ok(
                new ApiResponse<>("Token refreshed", false, true, res)
        );
    }

    // ================= EMAIL VERIFY =================
    @GetMapping("/verify-email/{token}")
    public ResponseEntity<ApiResponse<String>> verifyEmail(@PathVariable String token) {

        userService.verifyEmail(token);

        return ResponseEntity.ok(
                new ApiResponse<>("Email verified", false, true, null)
        );
    }

    // ================= OTP send =================
    @PostMapping("/forgot-password/{email}")
    public ResponseEntity<ApiResponse> forgotPassword(@PathVariable String email) {

            User user = userService.forgotPassword(email);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("User not found", true, false, null));
            }
            return ResponseEntity.ok(new ApiResponse<>(
                    "Password reset link sent to email (simulated)",
                    false,
                    true,
                    null));

    }

    // ================= OTP Verify =================
    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse<String>> verifyOtp(@RequestParam String email, @RequestParam String otp) {
            User user =userService.getUserByEmail(email); // Check if user exists, else throw
        if(user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("User not found", true, false, null));
        }
            userService.verifyOtp(email, otp);
            return ResponseEntity.ok(new ApiResponse<>(
                    "OTP verified successfully",
                    false,
                    true,
                    null));


    }

    @PutMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String email, @RequestParam String newPassword) {

        if(email == null || newPassword == null) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("Email and new password are required", true, false, null));
        }

        User user = userService.forgotPassword(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("User not found", true, false, null));
        }
            userService.resetPassword(email, newPassword);
            return ResponseEntity.ok(new ApiResponse<>(
                    "Password reset successfully",
                    false,
                    true,
                    null));


    }

    // ================= GOOGLE LOGIN =================
    @PostMapping("/google-login")
    public ResponseEntity<ApiResponse<LoginResponse>>googleLogin(@RequestBody GoogleLoginRequest request) {

        User user = userService.googleLogin(request.getAccessToken());

        String accessToken = jwtUtil.generateToken(user);
        String refreshToken = userService.createRefreshToken(user);

        LoginResponse res = new LoginResponse(
                accessToken,
                refreshToken,
                user.getEmail(),
                user.getRole().name()
        );
        return ResponseEntity.ok(
                new ApiResponse<>("Google login successful", false, true, res)
        );

    }

    private UserResponse map(User u) {
        UserResponse r = new UserResponse();
        r.setId(u.getId());
        r.setFirstName(u.getFirstName());
        r.setLastName(u.getLastName());
        r.setEmail(u.getEmail());
        r.setAvatar(u.getAvatar());
        r.setMobile(u.getMobile());
        r.setRole(u.getRole().name());
        return r;
    }


}