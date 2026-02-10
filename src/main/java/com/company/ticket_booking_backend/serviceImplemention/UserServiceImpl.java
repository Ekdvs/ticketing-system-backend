package com.company.ticket_booking_backend.serviceImplemention;

import com.company.ticket_booking_backend.model.User;
import com.company.ticket_booking_backend.repository.UserRepository;
import com.company.ticket_booking_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(16);
    @Override
    public User createUser(User user) {

        //Email uniquenss check
        userRepository.findByEmail(user.getEmail()).ifPresent(u->{
            throw new RuntimeException("User already exists. Use another email.");
        });

        //hash password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Defaults
        user.setVerifyEmail(false);
        user.setStatus(User.Status.ACTIVE);
        user.setRole(User.Role.USER);
        String token = java.util.UUID.randomUUID().toString();
        user.setEmailVerificationToken(token);

        return userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User does not exist."));
    }


    @Override
    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User does not exist."));

        // Check if email is verified
        if (user.getVerifyEmail() == false) {
            throw new RuntimeException("Please verify your email before login.");
        }

        // Check password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Incorrect password.");
        }

        // Update last login date
        user.setLastLoginDate(new java.util.Date());
        userRepository.save(user);

        return user;
    }

    @Override
    public User getUserById(String userId) {
        Optional<User> user = userRepository.findById(userId);
        if(!user.isPresent()) {
            throw new RuntimeException("User does not exist.");
        }
        return user.get();
    }

    @Override
    public void verifyEmail(String token) {
        User user = userRepository.findByEmailVerificationToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid verification token"));

        if (Boolean.TRUE.equals(user.getVerifyEmail())) {
            throw new RuntimeException("Email already verified");
        }

        user.setVerifyEmail(true);
        user.setEmailVerificationToken(null); // clear token after verification
        userRepository.save(user);
    }

    @Override
    public void logoutUser(String userId) {
        User user = getUserById(userId);
        user.setRefreshToken(null); // Clear refresh token on logout
        userRepository.save(user);
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User updateUser(String userId, User updatedUser) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User does not exist."));

        if (updatedUser.getFirstName() != null) {
            existingUser.setFirstName(updatedUser.getFirstName());
        }
        if (updatedUser.getLastName() != null) {
            existingUser.setLastName(updatedUser.getLastName());
        }
        if (updatedUser.getMobile() != null) {
            existingUser.setMobile(updatedUser.getMobile());
        }
        if (updatedUser.getAvatar() != null) {
            existingUser.setAvatar(updatedUser.getAvatar());
        }

        return userRepository.save(existingUser);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User forgotPassword(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User does not exist."));

        String otp = String.valueOf(new Random().nextInt(900000)+100000);
        user.setForgotPasswordOtp(otp);
        user.setForgotPasswordExpiryDate(LocalDateTime.now().plusMinutes(5));
        userRepository.save(user);
        //mailSevice.sendOtpEmail(user, otp);
        return user;
    }

    @Override
    public void verifyOtp(String email ,String otp){
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User does not exist."));

        if (user.getForgotPasswordExpiryDate() == null ||
                user.getForgotPasswordExpiryDate().isBefore(LocalDateTime.now())) {

            throw new RuntimeException("OTP expired");
        }

        if(!user.getForgotPasswordOtp().equals(otp)){
            throw new RuntimeException("Invalid OTP.");
        }
        user.setForgotPasswordOtp(null);
        user.setForgotPasswordExpiryDate(null);
        userRepository.save(user);


    }

    @Override
    public void resetPassword(String email, String newPassword){
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User does not exist."));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }






}