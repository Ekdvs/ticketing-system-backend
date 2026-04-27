package com.company.ticket_booking_backend.model;

import lombok.Data;

@Data
public class GoogleLoginRequest {
    private String access_token;

    public String getAccessToken() {
        return access_token;
    }

    public void setAccessToken(String access_token) {
        this.access_token = access_token;
    }
}
