package com.company.ticket_booking_backend.serviceImplemention;

import com.company.ticket_booking_backend.model.User;
import com.company.ticket_booking_backend.repository.UserRepository;
import com.company.ticket_booking_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

        return userRepository.save(user);
    }
}
