package com.progi.WildTrack.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;


import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(req ->
                        req
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/explorer/**").hasAuthority("tragac")
                                .requestMatchers("/stationLead/**").hasAuthority("voditeljPostaje")
                                .requestMatchers("/researcher/**").hasAuthority("istrazivac")
                                .requestMatchers("/admin/**").hasAuthority("admin")
                                .requestMatchers(antMatcher("/animal/**")).hasAnyAuthority("tragac","istrazivac", "admin")
                                .requestMatchers(antMatcher("/action/**")).hasAnyAuthority("tragac","istrazivac", "admin")
                                .requestMatchers(antMatcher("/animalcomment/**")).hasAnyAuthority("tragac","istrazivac", "admin")
                                .requestMatchers(antMatcher("/explorerlocation/**")).hasAnyAuthority("tragac","istrazivac", "admin")
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.getContext())
                        //prebaci SecurityContextHolder.getContext() u SecurityContextHolder.clearContext()
                )
        ;

        return http.build();
    }
}
