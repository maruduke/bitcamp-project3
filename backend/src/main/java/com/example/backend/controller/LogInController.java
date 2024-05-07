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
    public UserResponse joinPost(@RequestBody JoinDto joinDto) {
        log.info("joinPost.......");

        return userService.Join(joinDto);
    }

    @GetMapping("/get")
    public String loginGET(LoginDto loginDto){
        log.info("loginGET....");
        log.info("test");
        return "login";
    }

    @PostMapping("/post")
    public TokenDto loginPOST(@RequestBody LoginDto loginDto) throws BadRequestException, JsonProcessingException {
        log.info("loginPOST....");
        UserResponse userResponse = userService.login(loginDto);
        return jwtUtil.createTokensByLogin(userResponse);

    }

    @GetMapping("/reissue")
    public TokenDto reissue(@AuthenticationPrincipal User user) throws JsonProcessingException {
        UserResponse userResponse = new UserResponse(
                user.getName(),
                user.getEmail(),
                user.getAuthority());
        return jwtUtil.reissueAtk(userResponse);
    }


    @PostMapping("/logout")
    public void logout()  {
        String auth = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info(auth);
        if (auth != null) {
            userService.logout(auth);
            log.info("로그아웃되었습니다.");
        }
    }

    @GetMapping("/mypage")
    public ResponseEntity<User> myPage(@AuthenticationPrincipal User user) throws JsonProcessingException {
        String email = user.getEmail();
        return ResponseEntity.ok(userService.getUser(email));

    }

    @PostMapping("/mypage/modify")
    public ResponseEntity<Integer> modify(@AuthenticationPrincipal User user, @RequestBody InfoDto infoDto) throws JsonProcessingException {
        String name = user.getName();
        return ResponseEntity.ok(userService.modifyInfo(name, infoDto));

    }

}
