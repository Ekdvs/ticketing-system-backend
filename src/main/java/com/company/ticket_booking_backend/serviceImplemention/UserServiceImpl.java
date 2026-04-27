package com.company.ticket_booking_backend.serviceImplemention;

import com.company.ticket_booking_backend.EmailTemplates.EmailTemplates;
import com.company.ticket_booking_backend.model.LoginResponse;
import com.company.ticket_booking_backend.model.User;
import com.company.ticket_booking_backend.repository.UserRepository;
import com.company.ticket_booking_backend.security.JwtUtil;
import com.company.ticket_booking_backend.service.CloudinaryService;
import com.company.ticket_booking_backend.service.EmailService;
import com.company.ticket_booking_backend.service.UserService;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;


import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(16);
    private final EmailService emailService;
    private final CloudinaryService cloudinaryService;

    public UserServiceImpl(UserRepository userRepository,
                           JwtUtil jwtUtil,
                           EmailService emailService, CloudinaryService cloudinaryService) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.emailService = emailService;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public User createUser(User user) {

        userRepository.findByEmail(user.getEmail()).ifPresent(u -> {
            throw new RuntimeException("User already exists. Use another email.");
        });

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setVerifyEmail(false);
        user.setStatus(User.Status.ACTIVE);
        user.setRole(User.Role.USER);
        user.setEmailVerificationToken(UUID.randomUUID().toString());

        // generate token
        String token = UUID.randomUUID().toString();
        user.setEmailVerificationToken(token);
        User savedUser= userRepository.save(user);

        String link = "https://ticket-booking-backend-j5wu.onrender.com/api/auth/verify-email/" + token;
        emailService.sendEmail(
                savedUser.getEmail(),
                "Verify Your Email",
                EmailTemplates.welcomeEmail(savedUser.getFirstName(), link)
        );
        return savedUser;

    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User does not exist."));
    }

    @Override
    public User loginUser(String email, String password) {

        User user = getUserByEmail(email);

        if (!user.getVerifyEmail()) {
            throw new RuntimeException("Please verify your email before login.");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Incorrect password.");
        }

        if(user.getStatus()== User.Status.INACTIVE) {
            throw new RuntimeException("Your account is inactive. Please Contact Admin");
        }

        user.setLastLoginDate(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    public String createRefreshToken(User user) {
        String token = UUID.randomUUID().toString();
        user.setRefreshToken(token);
        userRepository.save(user);
        return token;
    }

    @Override
    public LoginResponse refreshToken(String refreshToken) {

        User user = userRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        // (optional security check)
        if (user.getRefreshToken() == null || user.getRefreshToken().isEmpty()) {
            throw new RuntimeException("Refresh token expired. Please login again.");
        }

        // CREATE NEW TOKENS
        String newAccessToken = jwtUtil.generateToken(user);

        String newRefreshToken = UUID.randomUUID().toString();
        user.setRefreshToken(newRefreshToken);

        userRepository.save(user);

        return new LoginResponse(
                newAccessToken,
                newRefreshToken,
                user.getEmail(),
                user.getRole().name()
        );
    }
    // ---- other methods unchanged ----

    @Override
    public User getUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User does not exist."));
    }

    @Override
    public void verifyEmail(String token) {
        User user = userRepository.findByEmailVerificationToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        user.setVerifyEmail(true);
        user.setEmailVerificationToken(null);
        userRepository.save(user);
    }

    @Override
    public void logoutUser(String userId) {
        User user = getUserById(userId);
        user.setRefreshToken(null);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User updateUser(String userId, User updatedUser, MultipartFile avatarFile) {
        User user = getUserById(userId);
        if(user == null) {
            throw new RuntimeException("User does not exist.");
        }

        if (updatedUser.getFirstName() != null) user.setFirstName(updatedUser.getFirstName());
        if (updatedUser.getLastName() != null) user.setLastName(updatedUser.getLastName());
        if (updatedUser.getMobile() != null) user.setMobile(updatedUser.getMobile());

        if (avatarFile != null && !avatarFile.isEmpty()) {

            // 🔴 Delete old avatar first
            if (user.getAvatar() != null && !user.getAvatar().isEmpty()) {
                cloudinaryService.deleteImage(user.getAvatar());
            }

            // 🟢 Upload new avatar
            String newImageUrl = cloudinaryService.uploadImage(avatarFile);
            user.setAvatar(newImageUrl);
        }

        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User forgotPassword(String email) {
        User user = getUserByEmail(email);
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);

        user.setForgotPasswordOtp(otp);
        user.setForgotPasswordExpiryDate(LocalDateTime.now().plusMinutes(5));

        emailService.sendEmail(
                user.getEmail(),
                "Password Reset OTP",
                EmailTemplates.otpEmail(user.getFirstName(), otp)
        );

        return userRepository.save(user);
    }

    @Override
    public void verifyOtp(String email, String otp) {
        User user = getUserByEmail(email);

        if (user.getForgotPasswordExpiryDate() == null ||
                user.getForgotPasswordExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired");
        }

        if (!otp.equals(user.getForgotPasswordOtp())) {
            throw new RuntimeException("Invalid OTP");
        }

        user.setForgotPasswordOtp(null);
        user.setForgotPasswordExpiryDate(null);
        userRepository.save(user);
    }

    @Override
    public void resetPassword(String email, String newPassword) {
        User user = getUserByEmail(email);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public void statusChange(String email, User.Status newStatus) {
        User user = getUserByEmail(email);
        if(user == null) {
            throw new RuntimeException("User does not exist.");
        }
        user.setStatus(newStatus);
        userRepository.save(user);
    }

    @Override
    public void roleChange(String email,User.Role newRole){
        User user = getUserByEmail(email);
        if(user == null) {
            throw new RuntimeException("User does not exist.");
        }

        user.setRole(newRole);
        userRepository.save(user);
    }

    @Override
    public User googleLogin(String accessToken) {

        RestTemplate restTemplate = new RestTemplate();

        String url = "https://www.googleapis.com/oauth2/v3/userinfo";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                Map.class
        );

        Map body = response.getBody();

        if (body == null || body.get("email") == null) {
            throw new RuntimeException("Invalid Google token");
        }

        String email = (String) body.get("email");
        String fullName = (String) body.get("name");

        return userRepository.findByEmail(email)
                .map(existing -> {
                    existing.setLastLoginDate(LocalDateTime.now());
                    return userRepository.save(existing);
                })
                .orElseGet(() -> {

                    // 🔥 FIX: parse name INSIDE lambda (no duplicate variables issue)
                    String firstName = "";
                    String lastName = "";

                    if (fullName != null) {
                        String[] parts = fullName.split(" ");
                        firstName = parts[0];
                        if (parts.length > 1) {
                            lastName = parts[1];
                        }
                    }

                    User newUser = User.builder()
                            .email(email)
                            .firstName(firstName)
                            .lastName(lastName)
                            .provider("GOOGLE")
                            .verifyEmail(true)
                            .avatar(body.get("picture") != null ? (String) body.get("picture") : "")
                            .role(User.Role.USER)
                            .lastLoginDate(LocalDateTime.now())
                            .build();

                    return userRepository.save(newUser);
                });
    }


}