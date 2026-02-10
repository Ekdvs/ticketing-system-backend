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
import java.util.Date;

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
    @Size(min = 6 , message = "Password must be at least 6 characters" )
    private String password;

    private String avatar = "";

    private String mobile = "";

    private String refreshToken = "";

    private Boolean verifyEmail = false;

    private Date lastLoginDate;

    private Status status = Status.ACTIVE;

    private String forgotPasswordOtp = "";

    private Date forgotPasswordExpiryDate = null;

    private Role role = Role.USER;

    private String emailVerificationToken;


    @CreatedDate
    private Date createdDate;

    @LastModifiedDate
    private Date updateAt;

    //Enums
    public enum Status {
        ACTIVE,
        INACTIVE
    }

    public enum Role {
        USER,
        ADMIN,
        ORGANIZER

    }

    //getter and setter


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public @NotBlank(message = "Provide First Name") String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotBlank(message = "Provide First Name") String firstName) {
        this.firstName = firstName;
    }

    public @NotBlank(message = "Provide Last Name") String getLastName() {
        return lastName;
    }

    public void setLastName(@NotBlank(message = "Provide Last Name") String lastName) {
        this.lastName = lastName;
    }

    public @NotBlank(message = "Provide Email") @Email(message = "Invalid email format") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Provide Email") @Email(message = "Invalid email format") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Provide Password") @Size(min = 6, message = "Password must be at least 6 characters") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Provide Password") @Size(min = 6 , message = "Password must be at least 6 characters" ) String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Boolean getVerifyEmail() {
        return verifyEmail;
    }

    public void setVerifyEmail(Boolean verifyEmail) {
        this.verifyEmail = verifyEmail;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getForgotPasswordOtp() {
        return forgotPasswordOtp;
    }

    public void setForgotPasswordOtp(String forgotPasswordOtp) {
        this.forgotPasswordOtp = forgotPasswordOtp;
    }

    public Date getForgotPasswordExpiryDate() {
        return forgotPasswordExpiryDate;
    }

    public void setForgotPasswordExpiryDate(LocalDateTime forgotPasswordExpiryDate) {
        this.forgotPasswordExpiryDate = forgotPasswordExpiryDate;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getEmailVerificationToken() {
        return emailVerificationToken;
    }
    public void setEmailVerificationToken(String emailVerificationToken) {
        this.emailVerificationToken = emailVerificationToken;
    }
}
