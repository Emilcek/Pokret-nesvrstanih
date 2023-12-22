package com.progi.WildTrack.service.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.progi.WildTrack.dao.*;
import com.progi.WildTrack.domain.*;
import com.progi.WildTrack.dto.AuthenticationResponseDto;
import com.progi.WildTrack.dto.ClientDetailsDTO;
import com.progi.WildTrack.dto.LoginDto;
import com.progi.WildTrack.dto.RegisterDto;
import com.progi.WildTrack.security.JwtService;
import com.progi.WildTrack.service.AuthenticationService;
import com.progi.WildTrack.service.VehicleService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.List;
import java.util.SimpleTimeZone;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
  private final ClientRepository repository;
  private final ExplorerRepository explorerRepository;
  private final StationLeadRepository stationLeadRepository;
  private final VehicleRepository vehicleRepository;
  private final VehicleService vehicleService;
  private final StatusRepository statusRepository;
  private final ResearcherRepository researcherRepository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final JavaMailSender javaMailSender;

  @Value("${FRONTEND_API_URL}")
  private String frontendApiUrl;

  @Value("${MAIL_USER}")
  private String mailUser;

  public AuthenticationResponseDto register(RegisterDto request) {
    if (repository.existsByClientName(request.getClientName()) || repository.existsByEmail(request.getEmail())) {
        throw new RuntimeException("Client already exists");
    }
    Client client = Client.builder()
        .clientName(request.getClientName())
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .email(request.getEmail())
        .clientPassword(passwordEncoder.encode(request.getPassword()))
        .role(request.getRole())
        .clientPhotoURL(request.getClientPhotoURL())
        .build();
    System.out.println(client);
    Client savedClient = repository.save(client);
    Status status = (Status) statusRepository.findByDescription(Description.PENDING).orElseThrow();
      switch (request.getRole()) {
          case "voditeljPostaje" -> {
              StationLead stationLead = new StationLead(savedClient, status);
              stationLeadRepository.save(stationLead);
          }
          case "tragac" -> {
              Explorer explorer = new Explorer(savedClient);
              explorerRepository.save(explorer);
              for (String i : request.getEducatedFor()) {
                  Vehicle vehicle = (Vehicle) vehicleRepository.findByVehicleType(i).orElseThrow();
                  vehicleService.addExplorerToVehicle(vehicle, explorer);
              }
          }
          case "istrazivac" -> {
              Researcher researcher = new Researcher(savedClient, status);
              researcherRepository.save(researcher);
          }
      }
    String jwtToken = jwtService.generateToken(client);
    String refreshToken = jwtService.generateRefreshToken(client);
    try {
      sendEmail(request.getEmail(), request.getFirstName(), frontendApiUrl + "/verified?url=" + jwtToken);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
    revokeAllClientTokens(client);
    saveClientToken(client, jwtToken);
    return AuthenticationResponseDto.builder()
            .accessToken(jwtToken)
            .refreshToken(refreshToken)
            .build();
  }

  public AuthenticationResponseDto authenticate(LoginDto request) {
    System.out.println(request.getClientName());
    Client client = repository.findByClientName(request.getClientName())
            .orElseThrow(() -> new RuntimeException("Client not found"));
    if (!client.isVerified()) {
        throw new RuntimeException("Client not verified");
    }
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getClientName(),
            request.getPassword()
        )
    );
    String jwtToken = jwtService.generateToken(client);
    String refreshToken = jwtService.generateRefreshToken(client);
    revokeAllClientTokens(client);
    saveClientToken(client, jwtToken);
    return AuthenticationResponseDto.builder()
        .accessToken(jwtToken)
            .refreshToken(refreshToken)
        .build();
  }

  private void saveClientToken(Client client, String jwtToken) {
    Token token = Token.builder()
        .client(client)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
  }

  private void revokeAllClientTokens(Client client) {
    List<Token> validClientTokens = tokenRepository.findAllValidTokenByClient(client.getClientName());
    if (validClientTokens.isEmpty())
      return;
    tokenRepository.deleteAll(validClientTokens);
  }

  public void refreshToken(
          HttpServletRequest request,
          HttpServletResponse response
  ) throws IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String clientName;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    clientName = jwtService.extractUsername(refreshToken);
    if (clientName != null) {
      Client client = this.repository.findByClientName(clientName)
              .orElseThrow();
      if (jwtService.isTokenValid(refreshToken, client)) {
        String accessToken = jwtService.generateToken(client);
        revokeAllClientTokens(client);
        saveClientToken(client, accessToken);
        AuthenticationResponseDto authResponse = AuthenticationResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }

  @Override
  public ResponseEntity<ClientDetailsDTO> verify(String url) {
    String clientName = jwtService.extractUsername(url);
    Client client = repository.findByClientName(clientName).orElse(null);
    if (client == null) {
      // TODO error handling
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    client.setVerified(true);
    repository.save(client);
    System.out.println("verified " + frontendApiUrl);
    return ResponseEntity.ok(new ClientDetailsDTO(client));
  }

  private void sendEmail(String to, String firstname, String url) throws MessagingException {
    MimeMessage message = javaMailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true);
    helper.setFrom(mailUser);
    helper.setTo(to);
    helper.setSubject("WildTrack-Verifikacija");
    String htmlContent = "<p>Poštovani/a,</p>"
            + "<p>Hvala vam što ste se registrirali na našoj web stranici. Kako biste dovršili proces registracije i aktivirali svoj račun, molimo vas da kliknete na gumb ispod:</p>"
            + "<p><a href='" + url + "' style='"
            + "background-color: #4CAF50; color: white; padding: 10px 15px; text-decoration: none; display: inline-block; border-radius: 5px;'>"
            + "Verificirajte svoj račun</a></p>"
            + "<p>Ako niste vi registrirali ovaj račun, ignorirajte ovu poruku.</p>"
            + "<p>Hvala vam na povjerenju!</p>"
            + "<p>Srdačan pozdrav,<br/>Vaša WildTrack ekipa</p>";

    helper.setText(htmlContent, true);;

    javaMailSender.send(message);
    System.out.println("Email sent to " + to);
  }
}
