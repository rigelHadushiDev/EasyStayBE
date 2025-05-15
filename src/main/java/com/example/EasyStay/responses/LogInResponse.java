package com.example.EasyStay.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogInResponse {
    private String accessToken;
    private String refreshToken;
    private long expiresIn;
    private String role;
    private boolean passwordChanged;
}
