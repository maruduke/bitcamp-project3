package com.example.backend.controller;

import com.example.backend.Jwt.JwtUtil;
import com.example.backend.dto.user.*;
import com.example.backend.entity.maria.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.RedisService;
import com.example.backend.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.BadRequestException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LogInController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final RedisService redisService;
    private final RedisTemplate redisTemplate;
    private final UserRepository userRepository;

    @PostMapping("/join")
    public ResponseEntity<String> joinPost(@RequestBody JoinDto joinDto) {
        log.info("joinPost.......");
        userService.Join(joinDto);
        return ResponseEntity.ok("사원이 등록되었습니다.");
    }

    @GetMapping("/get")
    public String loginGET(){
        log.info("loginGET....");
        log.info("test");
        return "login";
    }

    @PostMapping("/post")
    public ResponseEntity<TokenDto> loginPOST(@RequestBody LoginDto loginDto) throws BadRequestException, JsonProcessingException {
        log.info("loginPOST....");
        UserResponse userResponse = userService.login(loginDto);
        return ResponseEntity.ok(jwtUtil.createTokensByLogin(userResponse));
    }

    @GetMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@AuthenticationPrincipal User user) throws JsonProcessingException {
        UserResponse userResponse = new UserResponse(
                user.getName(),
                user.getEmail(),
                user.getAuthority());
        return ResponseEntity.ok(jwtUtil.reissueAtk(userResponse));
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token)  {
        log.info("logout....");
        log.info("token: " + token);
        if (token != null) {
            userService.logout(token);
        }
        return ResponseEntity.ok("로그아웃 되었습니다.");
    }

    @GetMapping("/mypage")
    public ResponseEntity<User> myPage(@AuthenticationPrincipal User user) throws JsonProcessingException {
        String email = user.getEmail();
        return ResponseEntity.ok(userService.getUser(email));
    }

    @PutMapping("/mypage/modify")
    public ResponseEntity<String> modify(@AuthenticationPrincipal User user, @RequestBody InfoDto infoDto, @RequestHeader("Authorization") String token) throws JsonProcessingException {
        String name = user.getName();
        userService.modifyInfo(name, infoDto);
        userService.logout(token);
        return ResponseEntity.ok("회원정보가 변경되었습니다. 다시 로그인해주세요.");
    }

}
