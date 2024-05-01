package com.example.backend.controller;

import com.example.backend.dto.user.LoginDto;
import com.example.backend.entity.maria.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LogInController {

    private final UserService userService;

    @GetMapping()
    public String loginGET(LoginDto loginDto){
        log.info("loginGET....");
        log.info("test");
        return "redirect:/login";
    }

    @PostMapping()
    public LoginDto loginPOST(@RequestBody LoginDto loginDto){
        log.info("loginPOST....");
        return userService.login(loginDto);

    }


}
