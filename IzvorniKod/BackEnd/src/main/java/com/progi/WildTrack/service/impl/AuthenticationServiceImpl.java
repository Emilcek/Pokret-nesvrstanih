package com.progi.WildTrack.service.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.progi.WildTrack.dao.ClientRepository;
import com.progi.WildTrack.dao.ExplorerRepository;
import com.progi.WildTrack.dao.TokenRepository;
import com.progi.WildTrack.domain.Client;
import com.progi.WildTrack.domain.Token;
import com.progi.WildTrack.domain.TokenType;
import com.progi.WildTrack.dto.AuthenticationResponseDto;
import com.progi.WildTrack.dto.LoginDto;
import com.progi.WildTrack.dto.RegisterDto;
import com.progi.WildTrack.security.JwtService;
import com.progi.WildTrack.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
  private final ClientRepository repository;
  private final ExplorerRepository explorerRepository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponseDto register(RegisterDto request) {
    if (repository.existsByClientName(request.getClientName())) {
        throw new RuntimeException("Client already exists");
    }
    var client = Client.builder()
        .clientName(request.getClientName())
        .firstName(request.getFirstname())
        .lastName(request.getLastname())
        .email(request.getEmail())
        .clientPassword(passwordEncoder.encode(request.getPassword()))
        .role(request.getRole())
        .clientPhotoURL(request.getClientPhotoURL())
        .build();
    var savedClient = repository.save(client);
    var jwtToken = jwtService.generateToken(client);
    var refreshToken = jwtService.generateRefreshToken(client);
    saveClientToken(savedClient, jwtToken);
//    if (request.getRole().equals("EXPLORER")) {
//      var explorer = Explorer.builder()
//              .client(savedClient)
//              .build();
//      explorerRepository.save(explorer);
//      savedClient.setExplorer(explorer);
//    }
    return AuthenticationResponseDto.builder()
        .accessToken(jwtToken)
            .refreshToken(refreshToken)
        .build();
  }

  public AuthenticationResponseDto authenticate(LoginDto request) {
    System.out.println(request.getClientName());
    var client = repository.findByClientName(request.getClientName())
            .orElseThrow(() -> new RuntimeException("Client not found"));
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getClientName(),
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
  }

  private void revokeAllClientTokens(Client client) {
    var validClientTokens = tokenRepository.findAllValidTokenByClient(client.getClientName());
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
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String clientName;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    clientName = jwtService.extractUsername(refreshToken);
    if (clientName != null) {
      var client = this.repository.findByClientName(clientName)
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
