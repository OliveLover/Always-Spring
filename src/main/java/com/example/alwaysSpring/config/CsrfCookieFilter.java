package com.example.alwaysSpring.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class CsrfCookieFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if (csrfToken.getHeaderName() != null) {
            response.setHeader(csrfToken.getHeaderName(), csrfToken.getToken());
            System.out.println(">>>>>>>>>>>>>>>>> :" + csrfToken.getHeaderName());
            System.out.println(">>>>>>>>>>>>>>>>> :" + csrfToken.getToken());
            System.out.println(">>>>>>>>>>>>>>>>> :" + response.getHeaderNames());
            System.out.println(">>>>>>>>>>>>>>>>> :" + response.getHeader("X-XSRF-TOKEN"));
        }
        filterChain.doFilter(request, response);
    }
}