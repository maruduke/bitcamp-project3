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
        String requestURI = request.getRequestURI();

        // 로그인 요청인 경우에는 토큰 검증 없이 통과
        if (requestURI.equals("/login/post")) {
            filterChain.doFilter(request, response);
            return;
        }

        // authorization이 null이거나 빈 문자열인 경우 처리
        if(authorization == null || authorization.isEmpty()) {
            // 여기서 로그인 요청이 아닌데 토큰이 없으면 권한 없음 상태로 처리할 수 있음
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // authorization이 null이 아니고 빈 문자열이 아닌 경우에만 토큰 검증
        if(jwtUtil.validateToken(authorization)) {
            String atk = authorization.substring("Bearer ".length());
            log.info("atk: " + atk);
            try{
                Subject subject = jwtUtil.getSubject(atk);
                log.info("subject: " + subject);

                log.info("requsetURI: " + requestURI);
                if(subject.getType().equals("RTK") && !requestURI.equals("/login/reissue")){
                    throw new JwtException("토큰을 확인하세요.");
                }
                User user = customUserDetailService.loadUserByUsername(subject.getEmail());
                log.info("user: " + user);
                Authentication token = new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
                log.info("token: " + token);
                SecurityContextHolder.getContext().setAuthentication(token);
            }catch(JwtException e){
                log.info("jwt exception: " + e);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 상태 코드 설정
                response.getWriter().write(e.getMessage()); // 오류 메시지 응답으로 전송
                return; // 필터 체인을 종료하여 더 이상의 처리를 하지 않음
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        filterChain.doFilter(request, response);
    }


}
