package com.pfe.GreenPlanet.service;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        var authorities = authentication.getAuthorities();
        var roles = authorities.stream()
                .map(r -> r.getAuthority())
                .collect(Collectors.toList());

        if (roles.contains("ROLE_ADMIN")) {
            response.sendRedirect("/admin/dashboard");
        }
        else if (roles.contains("ROLE_USER")) {
            response.sendRedirect("/user/dashboard");
        }
        else {
            response.sendRedirect("/");
        }
    }
}