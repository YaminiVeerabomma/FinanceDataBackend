package com.example.FinanceDataBackend.service;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LoginAttemptService {

    private final int MAX_ATTEMPTS = 5;
    private final long BLOCK_DURATION_MS = 15 * 60 * 1000; // 15 mins

    private static class Attempt {
        int count;
        long lastAttemptTime;

        Attempt() { this.count = 0; this.lastAttemptTime = Instant.now().toEpochMilli(); }
    }

    private final Map<String, Attempt> attemptsCache = new ConcurrentHashMap<>();

    public boolean isBlocked(String key) {
        Attempt attempt = attemptsCache.get(key);
        if (attempt == null) return false;

        if (attempt.count >= MAX_ATTEMPTS) {
            long elapsed = Instant.now().toEpochMilli() - attempt.lastAttemptTime;
            if (elapsed < BLOCK_DURATION_MS) return true;
            else {
                attemptsCache.remove(key);
                return false;
            }
        }
        return false;
    }

    public void loginSucceeded(String key) {
        attemptsCache.remove(key);
    }

    public void loginFailed(String key) {
        attemptsCache.compute(key, (k, attempt) -> {
            if (attempt == null) attempt = new Attempt();
            attempt.count++;
            attempt.lastAttemptTime = Instant.now().toEpochMilli();
            return attempt;
        });
    }
}