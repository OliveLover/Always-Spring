package com.example.alwaysSpring.config;

import com.example.alwaysSpring.jwt.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = jwtUtil.getTokenFromHeader(request, JwtUtil.ACCESS_TOKEN);
        String refreshToken = jwtUtil.getTokenFromHeader(request, JwtUtil.REFRESH_TOKEN);

        if (accessToken != null) {
            if (jwtUtil.validateToken(accessToken)) {
                setAuthentication(jwtUtil.getUsernameFromToken(accessToken));
            } else if (refreshToken != null && jwtUtil.isRefreshToken(refreshToken)) {
                String username = jwtUtil.getUsernameFromToken(refreshToken);
//                Users users = usersRepository.findByName(username).get();
                String newAccessToken = jwtUtil.createToken(username, JwtUtil.ACCESS_TOKEN);
                jwtUtil.setHeaderAccessToken(response, newAccessToken);
                setAuthentication(username);
            } else if (refreshToken == null) {
                String token = jwtUtil.createToken("ㅋㅋㅋ", JwtUtil.REFRESH_TOKEN);
//                handleTokenNotFound(response);
            } else {
                handleTokenNotFound(response);
            }
        }

        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String name) {
        Authentication authentication = createAuthentication(name);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private Authentication createAuthentication(String name) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(name);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    private void handleTokenNotFound(HttpServletResponse response) throws IOException {
        log.warn("Token not found");
//        response.sendError(HttpStatus.UNAUTHORIZED.value(), "Token not found");
    }
}
