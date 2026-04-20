package com.company.ticket_booking_backend.service;

import com.company.ticket_booking_backend.model.LoginResponse;
import com.company.ticket_booking_backend.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    User createUser(User user);

    User getUserByEmail(String email);
    User loginUser(String email, String password);
    User getUserById(String userId);
    void verifyEmail(String token);

    void deleteUser(String userId);

    User updateUser(String userId, User updatedUser, MultipartFile avatarFile);

    List<User> getAllUsers();
    void verifyOtp(String email, String otp);
    void resetPassword(String email, String newPassword);
    User forgotPassword(String email);
    void logoutUser(String userId);
    String createRefreshToken(User user);



    LoginResponse refreshToken(String refreshToken);

    void statusChange(String email, User.Status newStatus);
    void roleChange(String email, User.Role newRole);
}
