package com.example.FinanceDataBackend.service;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimiterService {

    // Inner class to track attempts
    private static class Attempt {
        int count;
        long lastAttemptTime;
    }

    private final Map<String, Attempt> attempts = new ConcurrentHashMap<>();

    // Limits per route
    private static final int MAX_LOGIN_ATTEMPTS = 5;
    private static final long LOGIN_BLOCK_MS = 15 * 60_000; // 15 minutes

    private static final int MAX_RECORDS_ATTEMPTS = 25;
    private static final long RECORDS_BLOCK_MS = 60_000; // 1 minute

    private static final int MAX_DASHBOARD_ATTEMPTS = 25;
    private static final long DASHBOARD_BLOCK_MS = 60_000; // 1 minute

    public boolean tryConsume(String key, String route) {

        // ✅ Skip Swagger and related resources completely
        if (route.startsWith("/swagger-ui") ||
            route.startsWith("/v3/api-docs") ||
            route.startsWith("/swagger-resources") ||
            route.startsWith("/webjars") ||
            route.startsWith("/favicon.ico")) {
            return true; // never rate limit Swagger
        }

        Attempt attempt = attempts.getOrDefault(key, new Attempt());
        long now = Instant.now().toEpochMilli();

        int maxAttempts;
        long blockDuration;

        // Assign limits per route type
        if (route.startsWith("/records")) {
            maxAttempts = MAX_RECORDS_ATTEMPTS;
            blockDuration = RECORDS_BLOCK_MS;
        } else if (route.startsWith("/dashboard")) {
            maxAttempts = MAX_DASHBOARD_ATTEMPTS;
            blockDuration = DASHBOARD_BLOCK_MS;
        } else { // default (login, other)
            maxAttempts = MAX_LOGIN_ATTEMPTS;
            blockDuration = LOGIN_BLOCK_MS;
        }

        // Block if attempts exceeded and within block duration
        if (attempt.count >= maxAttempts && now - attempt.lastAttemptTime < blockDuration) {
            return false;
        }

        // Reset count if block duration passed
        if (now - attempt.lastAttemptTime >= blockDuration) {
            attempt.count = 0;
        }

        attempt.count++;
        attempt.lastAttemptTime = now;
        attempts.put(key, attempt);

        return true;
    }
}