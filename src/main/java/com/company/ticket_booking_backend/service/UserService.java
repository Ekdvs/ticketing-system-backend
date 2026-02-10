package com.company.ticket_booking_backend.service;

import com.company.ticket_booking_backend.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user);

    User getUserByEmail(String email);
    User loginUser(String email, String password);
    User getUserById(String userId);
    void verifyEmail(String token);

    void deleteUser(String userId);

    List<User> getAllUsers();
    void verifyOtp(String email, String otp);
    void resetPassword(String email, String newPassword);
    User forgotPassword(String email);
    void logoutUser(String userId);
}
