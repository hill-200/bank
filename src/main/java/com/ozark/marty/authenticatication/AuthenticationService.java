package com.ozark.marty.authenticatication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ozark.marty.entities.Logout;
import com.ozark.marty.entities.Role;
import com.ozark.marty.entities.TokenType;
import com.ozark.marty.entities.Users;
import com.ozark.marty.repository.LogoutRepository;
import com.ozark.marty.repository.UserRepository;
import com.ozark.marty.security.JwtService;
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
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final LogoutRepository logoutRepository;


    public Response authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                       request.getUserEmail(),
                        request.getPassword()
                ));
        var user = userRepository.findByEmail(request.getUserEmail());
        var token = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllValidUserTokens(user);
        saveUserToken(user, token);
        return Response.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .build();
    }

    public Response register(RegisterRequest request) {
        var user = Users.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        if(request.getRole() == null){
            user.setRole(Role.USER);
        }

        var savedUser = userRepository.save(user);
        var token = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, token);
        return Response.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(Users user, String jwtToken){
        var users = Logout.builder()
                .user(user)
                .token(jwtToken)
                .expired(false)
                .revoked(false)
                .tokenType(TokenType.BEARER)
                .build();
        logoutRepository.save(users);
    }

    private void revokeAllValidUserTokens(Users users){
        var jwtToken = logoutRepository.findAllValidTokensByUserID(users.getId());
        if(jwtToken.isEmpty())
            return;
        jwtToken.forEach(t ->{
            t.setRevoked(true);
            t.setExpired(true);
        });
        logoutRepository.saveAll(jwtToken);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String jwtToken;
        final String userEmail;

        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")){
            return;
        }
        jwtToken = authorizationHeader.substring(7);
        userEmail = jwtService.extractUsername(jwtToken);

        if(userEmail != null){
            var user = this.userRepository.findByEmail(userEmail);

            if(jwtService.isTokenValid(jwtToken, user)){
                var accessToken = jwtService.generateToken(user);
                revokeAllValidUserTokens(user);
                saveUserToken(user, accessToken);

                var authResponse = Response.builder()
                        .accessToken(accessToken)
                        .refreshToken(jwtToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
