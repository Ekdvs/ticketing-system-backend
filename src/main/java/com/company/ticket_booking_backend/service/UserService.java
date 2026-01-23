package com.company.ticket_booking_backend.service;

import com.company.ticket_booking_backend.model.User;

public interface UserService {
    User createUser(User user);
    User getUserByEmail(String email);
    User loginUser(String email, String password);
    User getUserById(String userId);
}
