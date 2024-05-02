package com.example.backend.Jwt;

import com.example.backend.dto.user.TokenDto;
import com.example.backend.dto.user.UserResponse;
import com.example.backend.service.RedisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Log4j2
public class JwtUtil {
    private final RedisService redisService;
    private final ObjectMapper objectMapper;

    @Value("${spring.jwt.key}")
    private String key;

    @Value("${spring.jwt.live.atk}")
    private Long atkLive;

    @Value("${spring.jwt.live.rtk}")
    private Long rtkLive;

    @PostConstruct
    protected void init() {
        key = Base64.getEncoder().encodeToString(key.getBytes());
    }

    public TokenDto createTokensByLogin(UserResponse userResponse) throws JsonProcessingException {
        Subject atkSubject = Subject.atk(
                userResponse.getEmail(),
                userResponse.getAuthority());
        Subject rtkSubject = Subject.rtk(
                userResponse.getEmail(),
                userResponse.getAuthority());

        String atk = createToken(atkSubject, atkLive);
        String rtk = createToken(rtkSubject, rtkLive);

        redisService.setValues(userResponse.getEmail(), rtk, Duration.ofMillis(rtkLive));

        return new TokenDto(atk, rtk);
    }

    private String createToken(Subject subject, Long atkLive) throws JsonProcessingException {
        String subjectStr = objectMapper.writeValueAsString(subject);
        Claims claims = Jwts.claims()
                .setSubject(subjectStr);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + atkLive))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public Subject getSubject(String atk) throws JsonProcessingException {
        String subjectStr = Jwts.parser().setSigningKey(key).parseClaimsJws(atk).getBody().getSubject();
        return objectMapper.readValue(subjectStr, Subject.class);
    }

    public TokenDto reissueAtk(UserResponse userResponse) throws JsonProcessingException {
        String rtkInRedis = redisService.getValues(userResponse.getEmail());
        if (rtkInRedis == null) {
            System.out.println("인증정보가 만료되었습니다.");
        }
        Subject atkSubject = Subject.atk(
                userResponse.getEmail(),
                userResponse.getAuthority());
        String atk = createToken(atkSubject, atkLive);
        return new TokenDto(atk, null);
    }

    public boolean validateToken(String atk){
        try{
            Jwts.parser().setSigningKey(key).parseClaimsJws(atk);
            if(redisService.hasBlackList(atk)){
                throw new RuntimeException("로그아웃 되었습니다.");

            }
            return true;
        }catch (SecurityException | MalformedJwtException e){
            log.info("잘못된 JWT 서명입니다.");
        }catch(ExpiredJwtException e){
            log.info("만료된 토큰입니다.");
        }catch(UnsupportedJwtException e){
            log.info("지원되지 않는 JWT 토큰입니다.");
        }catch(IllegalArgumentException e){
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}
