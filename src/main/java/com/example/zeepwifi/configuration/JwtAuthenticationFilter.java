package com.example.zeepwifi.configuration;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.zeepwifi.models.JwtClaim;
import com.example.zeepwifi.utils.JwtUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String refreshToken = httpServletRequest.getHeader("refreshToken");

        ObjectMapper map = new ObjectMapper();
        map.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            JwtClaim claim = map.convertValue(jwtUtil.extractAllClaims(token).get("data"), JwtClaim.class);
            String accountUsername = claim.getAccountUsername();

            if (accountUsername != null && jwtUtil.verifyToken(token)) {
                Set<GrantedAuthority> authority = new HashSet<>();
                // Add authority if roles are available
                // authority.add(new SimpleGrantedAuthority(role));

                // BCryptPasswordEncoder bEncoder = new BCryptPasswordEncoder();

                SecurityContextHolder.getContext()
                        .setAuthentication(
                                new UsernamePasswordAuthenticationToken(accountUsername, null, authority));
            }
        }

        chain.doFilter(request, response);
    }
}
