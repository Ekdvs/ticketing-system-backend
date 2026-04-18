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