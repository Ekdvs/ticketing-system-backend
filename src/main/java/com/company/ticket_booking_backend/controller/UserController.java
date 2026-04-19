package com.company.ticket_booking_backend.controller;

import com.company.ticket_booking_backend.model.ApiResponse;
import com.company.ticket_booking_backend.model.User;
import com.company.ticket_booking_backend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ================= CURRENT USER =================
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<User>> me(Authentication auth) {

        String email = auth.getName();

        User user = userService.getUserByEmail(email);

        return ResponseEntity.ok(
                new ApiResponse<>("User fetched", false, true, user)
        );
    }

    // ================= LOGOUT =================
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(Authentication auth) {

        String email = auth.getName();

        User user = userService.getUserByEmail(email);

        userService.logoutUser(user.getId());

        return ResponseEntity.ok(
                new ApiResponse<>("Logged out", false, true, null)
        );
    }

    // ================= DELETE ACCOUNT =================
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<String>> delete(Authentication auth) {

        String email = auth.getName();

        User user = userService.getUserByEmail(email);

        userService.deleteUser(user.getId());

        return ResponseEntity.ok(
                new ApiResponse<>("User deleted", false, true, null)
        );
    }

    // ================= ALL USERS =================
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<User>>> all() {

        return ResponseEntity.ok(
                new ApiResponse<>("All users", false, true, userService.getAllUsers())
        );
    }

    // ================= Update Profile =================
    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<User>> update(
            Authentication auth,
            @RequestPart(value = "user", required = false) String userJson,
            @RequestPart(value = "avatarFile", required = false) MultipartFile avatarFile
    ) throws Exception {

        String email = auth.getName();
        User user = userService.getUserByEmail(email);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("User not found", true, false, null));
        }

        User updatedUser = new User();

        if (userJson != null) {
            ObjectMapper mapper = new ObjectMapper();
            updatedUser = mapper.readValue(userJson, User.class);
        }

        User updated = userService.updateUser(user.getId(), updatedUser, avatarFile);

        return ResponseEntity.ok(
                new ApiResponse<>("User updated successfully", false, true, updated)
        );
    }
}