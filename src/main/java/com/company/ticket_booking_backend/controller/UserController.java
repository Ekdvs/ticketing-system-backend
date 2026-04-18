package com.company.ticket_booking_backend.controller;

import com.company.ticket_booking_backend.model.ApiResponse;
import com.company.ticket_booking_backend.model.User;
import com.company.ticket_booking_backend.service.UserService;
import com.company.ticket_booking_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    // GET CURRENT USER
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<User>> getMe(Authentication authentication) {

        try {
            String userId = authentication.getName();

            User user = userService.getUserById(userId);

            return ResponseEntity.ok(
                    new ApiResponse<>("User fetched successfully", false, true, user)
            );

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(e.getMessage(), true, false, null));
        }
    }

    // LOGOUT
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(Authentication authentication) {

        try {
            String email = authentication.getName();

            User user = userService.getUserByEmail(email);
            userService.logoutUser(user.getId());

            return ResponseEntity.ok(
                    new ApiResponse<>("Logged out successfully", false, true, null)
            );

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(e.getMessage(), true, false, null));
        }
    }

    // DELETE ACCOUNT
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<String>> deleteUser(Authentication authentication) {

        try {
            String email = authentication.getName();

            User user = userService.getUserByEmail(email);
            userRepository.deleteById(user.getId());

            return ResponseEntity.ok(
                    new ApiResponse<>("User deleted successfully", false, true, null)
            );

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(e.getMessage(), true, false, null));
        }
    }

    // GET ALL USERS (ADMIN ONLY later you can secure it)
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllUsers() {

        return ResponseEntity.ok(
                new ApiResponse<>("Users fetched successfully",
                        false,
                        true,
                        userRepository.findAll())
        );
    }
}