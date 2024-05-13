package com.example.alwaysSpring.jwt;

import com.example.alwaysSpring.domain.users.RefreshToken;
import com.example.alwaysSpring.domain.users.RefreshTokenRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SecurityException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
public class JwtUtil {
    private static final String BEARER_PREFIX = "Bearer ";
    private static final long ACCESS_TIME = 60 * 60 * 10000L;
    private static final long REFRESH_TIME = 7 * 24 * 60 * 60 * 10000L;
    public static final String ACCESS_TOKEN = "Access_Token";
    public static final String REFRESH_TOKEN = "Refresh_Token";
    private final SecretKey secretKey = Jwts.SIG.HS256.key().build();

    private RefreshTokenRepository refreshTokenRepository;

    public String getTokenFromHeader(HttpServletRequest request, String tokenType) {
        String token = tokenType.equals(ACCESS_TOKEN) ? ACCESS_TOKEN : REFRESH_TOKEN;
        String bearerToken = request.getHeader(token);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.split(" ")[1].trim();
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims is empty");
        }
        return false;
    }

    public boolean isRefreshToken(String refreshToken) {
        if(!validateToken(refreshToken)) return false;
        Optional<RefreshToken> findRefreshToken = refreshTokenRepository.findByUsername(getUserNameFromToken(refreshToken));
        return findRefreshToken.isPresent() && refreshToken.equals(findRefreshToken.get().getTokenValue().split(" ")[1].trim());
    }

    public String createToken(String name, String tokenType) {
        Date date = new Date();
        long time = tokenType.equals(ACCESS_TOKEN) ? ACCESS_TIME : REFRESH_TIME;
        return BEARER_PREFIX + Jwts.builder()
                .subject(name)
                .issuer("always-spring")
                .expiration(new Date(date.getTime() + time))
                .signWith(secretKey)
                .compact();
    }

    public String getUserNameFromToken(String token) {
        String tokenValue = token.split(" ")[1].trim();
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(tokenValue).getPayload().getSubject();
    }

    public void setHeaderAccessToken(HttpServletResponse response, String accessToken) {
        response.setHeader(ACCESS_TOKEN, accessToken);
    }

    public void setHeaderRefreshToken(HttpServletResponse response, String refreshToken) {
        response.setHeader(REFRESH_TOKEN, refreshToken);
    }
}
