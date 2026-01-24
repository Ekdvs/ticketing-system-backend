package com.company.ticket_booking_backend.service;

import com.company.ticket_booking_backend.model.User;

public interface UserService {
    User createUser(User user);
<<<<<<< HEAD
    User getUserByEmail(String email);
    User loginUser(String email, String password);
    User getUserById(String userId);
    void verifyEmail(String token);
=======
>>>>>>> ff24bb8d6cb62f16db7ee8e55bf235edb510e59e
}
