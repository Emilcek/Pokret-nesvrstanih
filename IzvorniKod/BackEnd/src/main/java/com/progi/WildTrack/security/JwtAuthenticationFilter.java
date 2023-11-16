package com.progi.WildTrack.security;

import com.progi.WildTrack.dao.TokenRepository;
import com.progi.WildTrack.domain.Client;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            System.out.println("authHeader: " + authHeader);
            filterChain.doFilter(request, response);
            return;
        }
        System.out.println("authHeader proso: " + authHeader);
        jwt = authHeader.substring(7);
        username = jwtService.extractUsername(jwt);
        System.out.println("username: " + username);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            System.out.println("userDetails: " + userDetails.getAuthorities());
            if (jwtService.isTokenValid(jwt, userDetails)) {
                System.out.println("token is valid");
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
                Client client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                System.out.println(client.getClientName());
            }
            else {
                System.out.println("token is not valid");
            }
        }
        filterChain.doFilter(request, response);
    }
}