package com.ozark.marty.security;

import com.ozark.marty.repository.LogoutRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final LogoutRepository logoutRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authorizationHeader = request.getHeader("Authorization");
        final String jwtToken;

        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")){
            return;
        }
        jwtToken = authorizationHeader.substring(7);
        var storedToken = logoutRepository.findByToken(jwtToken).orElse(null);
        if(storedToken != null){
            storedToken.setRevoked(true);
            storedToken.setExpired(true);
            logoutRepository.save(storedToken);
            SecurityContextHolder.clearContext();
        }
    }
}
