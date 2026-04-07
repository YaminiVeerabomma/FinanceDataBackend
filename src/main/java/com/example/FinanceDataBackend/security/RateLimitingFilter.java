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

        String path = request.getRequestURI();

        // ✅ Skip Swagger and related resources completely
        if (path.startsWith("/swagger-ui") ||
            path.startsWith("/v3/api-docs") ||
            path.startsWith("/swagger-resources") ||
            path.startsWith("/webjars") ||
            path.startsWith("/favicon.ico")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Determine key: logged-in user or IP address
        String key = (request.getUserPrincipal() != null)
                ? request.getUserPrincipal().getName()
                : request.getRemoteAddr();

        // Apply rate limiting for API routes only
        if (!rateLimiterService.tryConsume(key, path)) {
            response.setStatus(429); // Too Many Requests
            response.setContentType("application/json");
            response.getWriter().write(
                "{\"error\":\"Too Many Requests\",\"message\":\"Try again after some time\"}"
            );
            return;
        }

        filterChain.doFilter(request, response);
    }
}