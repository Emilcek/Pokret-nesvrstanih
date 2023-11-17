package com.progi.WildTrack.service;

import com.progi.WildTrack.dto.AuthenticationResponseDto;
import com.progi.WildTrack.dto.ClientDetailsDTO;
import com.progi.WildTrack.dto.LoginDto;
import com.progi.WildTrack.dto.RegisterDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;

public interface AuthenticationService {

    AuthenticationResponseDto register(RegisterDto request);

    AuthenticationResponseDto authenticate(LoginDto request);

    void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException;

    ResponseEntity<ClientDetailsDTO> verify(String url);
}
