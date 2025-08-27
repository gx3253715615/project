package com.example.project.util;

import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.Base64;

class JwtUtilTest {
    @Test
    void createKey() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[32];
        secureRandom.nextBytes(key);
        String base64Key = Base64.getEncoder().encodeToString(key);
        System.out.println(base64Key);
    }
}
