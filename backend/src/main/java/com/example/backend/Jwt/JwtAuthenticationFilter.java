package com.example.backend.Jwt;

import com.example.backend.entity.maria.User;
import com.example.backend.security.CustomUserDetailService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailService customUserDetailService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, CustomUserDetailService customUserDetailService) {
        this.jwtUtil = jwtUtil;
        this.customUserDetailService = customUserDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authoriztion = request.getHeader("Authorization");
        if(authoriztion!=null){
            String atk = authoriztion.substring("Bearer ".length());
            try{
                Subject subject = jwtUtil.getSubject(atk);
                User user = customUserDetailService.loadUserByUsername(subject.getEmail());
                Authentication token = new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(token);
            }catch(JwtException e){
                request.setAttribute("exception", e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }
}
