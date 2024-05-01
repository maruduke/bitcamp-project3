package com.example.backend.controller;

import com.example.backend.Jwt.JwtUtil;
import com.example.backend.dto.user.JoinDto;
import com.example.backend.dto.user.LoginDto;
import com.example.backend.dto.user.TokenDto;
import com.example.backend.dto.user.UserResponse;
import com.example.backend.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class LogInController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/join")
    public UserResponse joinPost(@RequestBody JoinDto joinDto) {
        log.info("joinPost.......");

        return userService.Join(joinDto);
    }

    @GetMapping("/login/get")
    public String loginGET(LoginDto loginDto){
        log.info("loginGET....");
        log.info("test");
        return "redirect:/login";
    }

    @PostMapping("/login/post")
    public TokenDto loginPOST(@RequestBody LoginDto loginDto) throws BadRequestException, JsonProcessingException {
        log.info("loginPOST....");
        UserResponse userResponse = userService.login(loginDto);
        return jwtUtil.createTokensByLogin(userResponse);

    }

    @GetMapping("/test")
    public String test(){
        return "good!";
    }


}
