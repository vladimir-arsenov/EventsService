package org.example.security.auth;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.repository.UserRepository;
import org.example.security.dto.AuthRequestDto;
import org.example.security.dto.AuthResponseDto;
import org.example.security.dto.RegisterRequestDto;
import org.example.security.jwt.JwtService;
import org.example.model.enums.Role;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthResponseDto register(RegisterRequestDto request) {
        log.info("Registering new user with username: {}", request.getUsername());

        var user = org.example.model.User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        log.debug("User {} saved successfully", user.getUsername());

        var jwtToken = jwtService.generateToken(user.getUsername());

        log.debug("Generated JWT token for user: {}", user.getUsername());

        return new AuthResponseDto(jwtToken);
    }

    public AuthResponseDto authenticate(AuthRequestDto request) {
        log.info("Authenticating user with username: {}", request.getUsername());

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        log.debug("Authentication successful for username: {}", request.getUsername());

        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("User with username %s not found".formatted(request.getUsername())));
        var jwtToken = jwtService.generateToken(user.getUsername());

        log.debug("JWT token generated for user: {}", user.getUsername());

        return new AuthResponseDto(jwtToken);
    }
}