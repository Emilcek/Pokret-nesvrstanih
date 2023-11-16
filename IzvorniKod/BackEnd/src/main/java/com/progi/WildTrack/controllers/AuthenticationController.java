package com.progi.WildTrack.controllers;

import com.progi.WildTrack.domain.Client;
import com.progi.WildTrack.dto.AuthenticationResponseDto;
import com.progi.WildTrack.dto.LoginDto;
import com.progi.WildTrack.dto.RegisterDto;
import com.progi.WildTrack.service.AuthenticationService;
import com.progi.WildTrack.service.ClientService;
import com.progi.WildTrack.service.impl.EmailSenderServisImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "${FRONTEND_API_URL}")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final EmailSenderServisImpl emailSender;
    private final ClientService clientService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto>register(
            @RequestBody RegisterDto request
    ) {
        System.out.println("controller " + request);
        emailSender.sendEmail(request.getEmail(), request.getFirstName(), "https://localhost:8080/api/auth/verified");
        return ResponseEntity.ok(service.register(request));
    }
    @GetMapping("/verified")
    public RedirectView verified() {
        Client client = clientService.getClient();
        //System.out.println("KLIJENT: " + client);
        client.setVerified(true);
        clientService.save(client);
        return new RedirectView("https://localhost:8080/api/auth/login");
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
