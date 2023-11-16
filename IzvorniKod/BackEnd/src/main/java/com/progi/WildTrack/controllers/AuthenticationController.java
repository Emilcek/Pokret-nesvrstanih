package com.progi.WildTrack.controllers;

import com.progi.WildTrack.dto.AuthenticationResponseDto;
import com.progi.WildTrack.dto.LoginDto;
import com.progi.WildTrack.dto.RegisterDto;
import com.progi.WildTrack.service.AuthenticationService;
import com.progi.WildTrack.service.impl.EmailSenderServisImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "${FRONTEND_API_URL}")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final EmailSenderServisImpl emailSender;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto>register(
            @RequestBody RegisterDto request
    ) {
        System.out.println("controller " + request);
        emailSender.sendEmail(request.getEmail(), request.getFirstName(), "https://www.youtube.com/");
        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> authenticate(
            @RequestBody LoginDto request
    ) {
        System.out.println("controller " + request);
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }


}
