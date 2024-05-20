package com.example.alwaysSpring.config;

import com.example.alwaysSpring.domain.tokens.RefreshToken;
import com.example.alwaysSpring.domain.tokens.RefreshTokenRepository;
import com.example.alwaysSpring.jwt.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class Oauth2SuccessHandler implements AuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String name = oAuth2User.getAttributes().get("name").toString();
        String tokenValue = jwtUtil.createToken(name, JwtUtil.REFRESH_TOKEN);
        RefreshToken refreshToken = saveOrUpdate(name, tokenValue);
        response.sendRedirect("http://localhost:3000/login/" + refreshToken.getTokenValue());
    }

    private RefreshToken saveOrUpdate(String name, String tokenValue) {
        RefreshToken refreshToken = refreshTokenRepository.findByUsername(name)
                .map(entity -> entity.updateToken(tokenValue))
                .orElse(new RefreshToken(tokenValue, name));
        return refreshTokenRepository.save(refreshToken);
    }
}

