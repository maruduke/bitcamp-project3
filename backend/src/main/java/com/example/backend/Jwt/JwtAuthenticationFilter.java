package com.example.backend.Jwt;

import com.example.backend.entity.maria.User;
import com.example.backend.security.CustomUserDetailService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Log4j2
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailService customUserDetailService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, CustomUserDetailService customUserDetailService) {
        this.jwtUtil = jwtUtil;
        this.customUserDetailService = customUserDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("JWT Authentication Filter");
        String authorization = request.getHeader("Authorization");
        log.info("authorization: " + authorization);
        if(authorization!=null && jwtUtil.validateToken(authorization)) {
            String atk = authorization.substring("Bearer ".length());
            log.info("atk: " + atk);
            try{
                Subject subject = jwtUtil.getSubject(atk);
                log.info("subject: " + subject);
                String requsetURI = request.getRequestURI();
                log.info("requsetURI: " + requsetURI);
                if(subject.getType().equals("RTK") && !requsetURI.equals("/login/reissue")){
                    throw new JwtException("토큰을 확인하세요.");
                }
                User user = customUserDetailService.loadUserByUsername(subject.getEmail());
                log.info("user: " + user);
                Authentication token = new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
                log.info("token: " + token);
                SecurityContextHolder.getContext().setAuthentication(token);
            }catch(JwtException e){
                log.info("jwt exception: " + e);
                request.setAttribute("exception", e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }
}
