package com.company.ticket_booking_backend.serviceImplemention;

import com.company.ticket_booking_backend.model.User;
import com.company.ticket_booking_backend.repository.UserRepository;
import com.company.ticket_booking_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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





}