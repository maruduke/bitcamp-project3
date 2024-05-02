package com.example.backend.controller;

import com.example.backend.Jwt.JwtUtil;
import com.example.backend.dto.user.JoinDto;
import com.example.backend.dto.user.LoginDto;
import com.example.backend.dto.user.TokenDto;
import com.example.backend.dto.user.UserResponse;
import com.example.backend.entity.maria.User;
import com.example.backend.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LogInController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

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

//    @DeleteMapping("/logout")
//    public UserResponse logout(@AuthenticationPrincipal User user, @RequestBody TokenDto tokenDto) throws JsonProcessingException {
//        return UserResponse.of(userService.logout(tokenDto.getAtk(), user.getEmail()));
//    }

    @GetMapping("/test")
    public String test(Authentication authentication) throws JsonProcessingException {
        String email = authentication.getName();
        return email;
    }
//
//    @GetMapping("")
//    public String ok() {
//        return "test";
//    }

}
