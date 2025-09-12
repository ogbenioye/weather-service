package com.ogbenioye.weatherservice.security;

import com.ogbenioye.weatherservice.service.ApiKeyService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class ApiKeyFilter extends OncePerRequestFilter {

    private static final String HEADER_NAME = "X-API-KEY";
    private final ApiKeyService apiKeyService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        var key = request.getHeader(HEADER_NAME);

        if (request.getRequestURI().startsWith("/account/") || request.getRequestURI().startsWith("/key/")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (key == null || !apiKeyService.isValid(key)) {
           response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
           response.getWriter().write("Invalid API Key");
           return;
        }

        filterChain.doFilter(request, response);
    }
}
