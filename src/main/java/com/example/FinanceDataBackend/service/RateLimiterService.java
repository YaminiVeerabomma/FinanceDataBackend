package com.example.FinanceDataBackend.service;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimiterService {

    private static class Attempt {
        int count;
        long lastAttemptTime;
    }

    private final Map<String, Attempt> attempts = new ConcurrentHashMap<>();

    // Limits
    private final int MAX_LOGIN_ATTEMPTS = 5;
    private final long LOGIN_BLOCK_MS = 15 * 60_000; // 15 min

    private final int MAX_RECORDS_ATTEMPTS = 25;
    private final long RECORDS_BLOCK_MS = 60_000; // 1 min

    private final int MAX_DASHBOARD_ATTEMPTS = 25;
    private final long DASHBOARD_BLOCK_MS = 60_000; // 1 min

    public boolean tryConsume(String key, String route) {
        Attempt attempt = attempts.getOrDefault(key, new Attempt());
        long now = Instant.now().toEpochMilli();

        int maxAttempts;
        long blockDuration;

        if (route.startsWith("/records")) {
            maxAttempts = MAX_RECORDS_ATTEMPTS;
            blockDuration = RECORDS_BLOCK_MS;
        } else if (route.startsWith("/dashboard")) {
            maxAttempts = MAX_DASHBOARD_ATTEMPTS;
            blockDuration = DASHBOARD_BLOCK_MS;
        } else {
            maxAttempts = MAX_LOGIN_ATTEMPTS;
            blockDuration = LOGIN_BLOCK_MS;
        }

        if (attempt.count >= maxAttempts && now - attempt.lastAttemptTime < blockDuration) {
            return false; // blocked
        }

        if (now - attempt.lastAttemptTime >= blockDuration) {
            attempt.count = 0; // reset after block duration
        }

        attempt.count++;
        attempt.lastAttemptTime = now;
        attempts.put(key, attempt);
        return true;
    }
}