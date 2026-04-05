package com.example.FinanceDataBackend.security;

import com.example.FinanceDataBackend.service.RateLimiterService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class RateLimitingFilter extends OncePerRequestFilter {

    private final RateLimiterService rateLimiterService;

    public RateLimitingFilter(RateLimiterService rateLimiterService) {
        this.rateLimiterService = rateLimiterService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String key = (request.getUserPrincipal() != null)
                ? request.getUserPrincipal().getName() // logged in user
                : request.getRemoteAddr(); // use IP for login API

        String route = request.getRequestURI();

        if (!rateLimiterService.tryConsume(key, route)) {
            response.setStatus(429);
            response.setContentType("application/json");
            response.getWriter().write(
                "{\"error\":\"Too Many Requests\",\"message\":\"Try again after some time\"}"
            );
            return;
        }

        filterChain.doFilter(request, response);
    }
}