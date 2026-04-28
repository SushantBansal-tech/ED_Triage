package com.acme.encounter.config;

import com.acme.encounter.security.JwtAuthenticationFilter;
import com.acme.encounter.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
                          CustomUserDetailsService customUserDetailsService) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/auth/**").permitAll()
            .requestMatchers("/api/v1/encounters/my/**").hasRole("PATIENT")
            .requestMatchers("/api/v1/encounters/*/triage-summary").hasAnyRole("NURSE","DOCTOR","ADMIN")
            .requestMatchers("/api/v1/encounters/*/triage-assessments").hasAnyRole("NURSE","DOCTOR","ADMIN")
            .requestMatchers("/api/v1/triage-assessments/*/vitals").hasAnyRole("NURSE","DOCTOR","ADMIN")
            .requestMatchers("/api/v1/triage-assessments/*/symptom-notes").hasAnyRole("NURSE","DOCTOR","ADMIN")
            .requestMatchers("/api/v1/triage-assessments/*/score").hasAnyRole("NURSE","DOCTOR","ADMIN")
            .requestMatchers("/api/v1/triage-assessments/*/recommendation").hasAnyRole("NURSE","DOCTOR","ADMIN")
            .requestMatchers("/api/v1/triage-recommendations/*/override").hasAnyRole("DOCTOR","ADMIN")
            .requestMatchers("/api/v1/triage-assessments/*/finalize").hasAnyRole("DOCTOR","ADMIN")
            .anyRequest().authenticated()
        )
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider())
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
}

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}