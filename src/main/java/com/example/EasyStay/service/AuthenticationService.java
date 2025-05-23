package com.example.EasyStay.service;

import com.example.EasyStay.dtos.LoginUserDto;
import com.example.EasyStay.entities.UserEntity;
import com.example.EasyStay.responses.LogInResponse;

public interface AuthenticationService {

    UserEntity signup(UserEntity input);

    LogInResponse authenticate(LoginUserDto input);

    void resendAutoGeneratedPassword(String email);

    String generateTemporaryPassword();

    void sendVerificationEmail(UserEntity user, String hashedTemporaryPassword);

    LogInResponse refreshAccessToken(String refreshToken);
}