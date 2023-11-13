package com.progi.WildTrack.service;

import com.progi.WildTrack.dto.AuthenticationResponseDto;
import com.progi.WildTrack.dto.LoginDto;
import com.progi.WildTrack.dto.RegisterDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {

    Void register(RegisterDto request);

    AuthenticationResponseDto authenticate(LoginDto request);

    void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException;
}
