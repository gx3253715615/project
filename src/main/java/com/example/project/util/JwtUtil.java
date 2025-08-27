package com.example.project.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

/**
 * JWT工具类
 *
 * @author gaoxinyu
 * @date 2025/8/27 17:19
 */
@Component
public class JwtUtil {

    private static Key key;
    private static long expireTime;

    /**
     * 从配置文件注入签名密钥和过期时间
     */
    @Value("${jwt.key}")
    public void setKeyStr(String keyStr) {
        this.key = Keys.hmacShaKeyFor(keyStr.getBytes(StandardCharsets.UTF_8));
    }

    @Value("${jwt.expire-time}")
    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    /**
     * 生成 JWT Token
     */
    public static String createToken(Long userId) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 解析 JWT Token
     */
    public static Long parseToken(String token) throws JwtException {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return Long.parseLong(claims.getSubject());
        } catch (JwtException e) {
            throw new JwtException("无效的JWT token或解析失败", e);
        }
    }

    /**
     * 判断 Token 是否已过期
     */
    public static boolean isTokenExpired(String token) {
        try {
            Date expiration = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();
            return expiration.before(new Date());
        } catch (JwtException e) {
            return true;
        }
    }

    /**
     * 检查 Token 是否有效
     */
    public static boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

}
