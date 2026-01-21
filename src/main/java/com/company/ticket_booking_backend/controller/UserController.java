package com.company.ticket_booking_backend.controller;

import com.company.ticket_booking_backend.model.ApiResponse;
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
}