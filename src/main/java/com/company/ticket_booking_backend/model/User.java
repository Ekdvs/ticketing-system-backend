package com.company.ticket_booking_backend.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    private String id;

    @NotBlank(message = "Provide First Name")
    private String firstName;

    @NotBlank(message = "Provide Last Name")
    private String lastName;

    @NotBlank(message = "Provide Email")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Provide Password")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    private String avatar = "";
    private String mobile = "";
    private String refreshToken = "";
    private Boolean verifyEmail = false;

    private LocalDateTime lastLoginDate;

    private Status status = Status.ACTIVE;

    private String forgotPasswordOtp = "";

    private LocalDateTime forgotPasswordExpiryDate;

    private Role role = Role.USER;

    private String emailVerificationToken;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updateAt;

    // Enums
    public enum Status {
        ACTIVE,
        INACTIVE
    }

    public enum Role {
        USER,
        ADMIN,
        ORGANIZER
    }
}