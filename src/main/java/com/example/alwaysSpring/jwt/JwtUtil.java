package com.example.alwaysSpring.jwt;

import com.example.alwaysSpring.domain.tokens.RefreshToken;
import com.example.alwaysSpring.domain.tokens.RefreshTokenRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SecurityException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
public class JwtUtil {
    private static final String BEARER_PREFIX = "Bearer_";
    private static final long ACCESS_TIME = 60 * 60 * 10000L;
    private static final long REFRESH_TIME = 7 * 24 * 60 * 60 * 10000L;
    public static final String ACCESS_TOKEN = "Access_Token";
    public static final String REFRESH_TOKEN = "Refresh_Token";
    private final SecretKey secretKey = Jwts.SIG.HS256.key().build();

    private final RefreshTokenRepository refreshTokenRepository;

    public JwtUtil(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public String getTokenFromHeader(HttpServletRequest request, String tokenType) {
        String token = tokenType.equals(ACCESS_TOKEN) ? ACCESS_TOKEN : REFRESH_TOKEN;
        String bearerToken = request.getHeader(token);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.split("_")[1].trim();
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
        log.info("isRefreshToken : " + refreshToken);
        if (!validateToken(refreshToken)) return false;
        Optional<RefreshToken> findRefreshToken = refreshTokenRepository.findByUsername(getUsernameFromToken(refreshToken));
        return findRefreshToken.isPresent() && refreshToken.equals(findRefreshToken.get().getTokenValue().split("_")[1].trim());
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

    @Transactional
    public String updateToken(String name, String tokenType) {
        RefreshToken findRefreshToken = refreshTokenRepository.findByUsername(name).orElseThrow(
                () -> new IllegalArgumentException("유효하지 않은 토큰입니다.")
        );
        String newRefreshToken = createToken(name, tokenType);
        findRefreshToken.updateToken(newRefreshToken);
        return newRefreshToken;
    }

    public String getUsernameFromToken(String tokenValue) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(tokenValue).getPayload().getSubject();
    }

    public void setHeaderAccessToken(HttpServletResponse response, String accessToken) {
        response.setHeader(ACCESS_TOKEN, accessToken);
    }

    public void setHeaderRefreshToken(HttpServletResponse response, String refreshToken) {
        response.setHeader(REFRESH_TOKEN, refreshToken);
    }
}
