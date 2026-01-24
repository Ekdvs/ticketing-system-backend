package com.company.ticket_booking_backend.controller;

import com.company.ticket_booking_backend.model.ApiResponse;

import com.company.ticket_booking_backend.model.LoginRequest;
import com.company.ticket_booking_backend.model.LoginResponse;
import com.company.ticket_booking_backend.model.User;
import com.company.ticket_booking_backend.repository.UserRepository;
import com.company.ticket_booking_backend.security.JwtUtil;
import com.company.ticket_booking_backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import com.company.ticket_booking_backend.model.User;
import com.company.ticket_booking_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> registerUser(@RequestBody User user) {
        //field validation
        if(user.getFirstName()==null || user.getLastName()==null || user.getEmail()==null || user.getPassword()==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(
                    "All field are request",
                    true,
                    false,
                    null
            ));
        }
        try {
            User createdUser = userService.createUser(user);

            String verificationLink = "http://localhost:8080/api/user/verify-email/" + createdUser.getEmailVerificationToken();
            System.out.println("Verification link: " + verificationLink);


            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(
                    "User registered successfully",
                    false,
                    true,
                    createdUser
            ));

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(
                            "Internal server error",
                            true,
                            false,
                            null
                    ));
        }
    }


    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            User user = userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());

            // Generate tokens
            String accessToken = jwtUtil.generateToken(user);
            String refreshToken = java.util.UUID.randomUUID().toString(); // simple refresh token
            user.setRefreshToken(refreshToken);

            // Save refresh token
            userRepository.save(user); // save updated user

            LoginResponse loginResponse = new LoginResponse(
                    accessToken,
                    refreshToken,
                    user.getEmail(),
                    user.getRole().name()
            );

            return ResponseEntity.ok(new ApiResponse<>("Login successful", false, true, loginResponse));

        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(e.getMessage(), true, false, null));
        }
    }


    @GetMapping("/me")
    public ResponseEntity<ApiResponse<User>> getUserData(HttpServletRequest request) {
        try {
            // Extract JWT from Authorization header
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>("Unauthorized: No token provided", true, false, null));
            }

            String token = authHeader.substring(7);
            //System.out.println("token: " + token);

            // Validate token
            if (!jwtUtil.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>("Unauthorized: Invalid token", true, false, null));
            }

            // Get userId from token
            String userId = jwtUtil.getUserIdFromToken(token);
            if (userId == null || userId.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>("Unauthorized", true, false, null));
            }

            // Call service to fetch user
            User user = userService.getUserById(userId);

            return ResponseEntity.ok(
                    new ApiResponse<>("User data fetched successfully", false, true, user)
            );

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(e.getMessage(), true, false, null));
        }
    }

    //verfiy email
    @GetMapping("/verify-email/{token}")
    public ResponseEntity<ApiResponse<String>> verifyEmail(@PathVariable String token) {
        try {
            System.out.println("Verifying email for token: " + token);
            userService.verifyEmail(token);
            return ResponseEntity.ok(new ApiResponse<>(
                "Email verified successfully", 
                false, 
                true, 
                null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(e.getMessage(), true, false, null));
        }
    }

}