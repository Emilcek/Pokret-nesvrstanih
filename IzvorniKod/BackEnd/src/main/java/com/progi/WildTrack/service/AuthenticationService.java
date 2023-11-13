package com.progi.WildTrack.service;


import com.progi.WildTrack.dto.RegisterDto;
import com.progi.WildTrack.dto.AuthenticationResponseDto;
import com.progi.WildTrack.dto.LoginDto;
import com.progi.WildTrack.models.Client;
import com.progi.WildTrack.models.Explorer;
import com.progi.WildTrack.models.Token;
import com.progi.WildTrack.models.TokenType;
import com.progi.WildTrack.repository.ExplorerRepository;
import com.progi.WildTrack.repository.TokenRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.progi.WildTrack.repository.ClientRepository;
import com.progi.WildTrack.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final ClientRepository repository;
  private final ExplorerRepository explorerRepository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponseDto register(RegisterDto request) {

    System.out.println("AuthenticationService.register");
//    System.out.println(repository.existsByClient_name(request.getClient_name()));
    if (repository.existsByEmail(request.getEmail())) {
        System.out.println("AuthenticationService.register: client already exists");
        return null;
    }
    var client = Client.builder()
        .client_name(request.getClient_name())
        .firstname(request.getFirstname())
        .lastname(request.getLastname())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(request.getRole())
        .build();
    var savedClient = repository.save(client);
    var jwtToken = jwtService.generateToken(client);
    var refreshToken = jwtService.generateRefreshToken(client);
    saveClientToken(savedClient, jwtToken);
    if (request.getRole().equals("EXPLORER")) {
      var explorer = Explorer.builder()
              .client(savedClient)
              .build();
      explorerRepository.save(explorer);
      savedClient.setExplorer(explorer);
    }
    return AuthenticationResponseDto.builder()
        .accessToken(jwtToken)
            .refreshToken(refreshToken)
        .build();
  }

  public AuthenticationResponseDto authenticate(LoginDto request) {
    System.out.println("AuthenticationService.authenticate");
    System.out.println("AuthenticationService.authenticate: request = " + request.getClient_name());
    var client = repository.findByUsername(request.getClient_name())
            .orElseThrow(() -> new RuntimeException("Client not found"));
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getClient_name(),
            request.getPassword()
        )
    );
    var jwtToken = jwtService.generateToken(client);
    var refreshToken = jwtService.generateRefreshToken(client);
    revokeAllClientTokens(client);
    saveClientToken(client, jwtToken);
    return AuthenticationResponseDto.builder()
        .accessToken(jwtToken)
            .refreshToken(refreshToken)
        .build();
  }

  private void saveClientToken(Client client, String jwtToken) {
    var token = Token.builder()
        .client(client)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
//    System.out.println(tokenRepository.findByToken(jwtToken)
//            .orElseThrow());
  }

  private void revokeAllClientTokens(Client client) {
    var validClientTokens = tokenRepository.findAllValidTokenByClient(client.getClient_name());
    if (validClientTokens.isEmpty())
      return;
    validClientTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validClientTokens);
  }

  public void refreshToken(
          HttpServletRequest request,
          HttpServletResponse response
  ) throws IOException {
    System.out.println("AuthenticationService.refreshToken");
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String clientName;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    clientName = jwtService.extractUsername(refreshToken);
    if (clientName != null) {
      var client = this.repository.findByUsername(clientName)
              .orElseThrow();
      if (jwtService.isTokenValid(refreshToken, client)) {
        var accessToken = jwtService.generateToken(client);
        revokeAllClientTokens(client);
        saveClientToken(client, accessToken);
        var authResponse = AuthenticationResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }
}
