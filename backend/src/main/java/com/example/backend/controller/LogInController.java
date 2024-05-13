package com.example.backend.controller;

import com.example.backend.Jwt.JwtUtil;
import com.example.backend.dto.user.*;
import com.example.backend.entity.maria.User;
import com.example.backend.entity.maria.enumData.Authority;
import com.example.backend.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LogInController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @GetMapping("/header")
    public ResponseEntity<HeaderDto> getHeader(@AuthenticationPrincipal User user, @RequestHeader("Authorization") String token) {
        if(!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(401).build();
        }
        log.info(userService.getName(user.getEmail()));
        return ResponseEntity.ok(userService.getName(user.getEmail()));
    }

    @GetMapping("/joinget")
    public ResponseEntity joinGET(@AuthenticationPrincipal User user) {
        Authority authority = user.getAuthority();
        if(authority == Authority.USER){
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/join")
    public ResponseEntity<String> joinPost(@RequestBody JoinDto joinDto) throws BadRequestException {
        log.info("joinPost.......");
        userService.join(joinDto);
        return ResponseEntity.ok("사원 등록이 완료되었습니다.");
    }

    @GetMapping("/get")
    public ResponseEntity loginGET(@RequestHeader("Authorization") String token) {
        if(!jwtUtil.validateToken(token)){
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.status(200).build();
    }

    @CrossOrigin("*")
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
    public ResponseEntity<User> myPage(@AuthenticationPrincipal User user, @RequestHeader("Authorization") String token) {
        if(!jwtUtil.validateToken(token)){
            return ResponseEntity.status(401).build();
        }
        String email = user.getEmail();
        log.info(email);
        return ResponseEntity.ok(userService.getUser(email));
    }

    @PutMapping("/mypage/modify")
    public ResponseEntity<String> modify(@AuthenticationPrincipal User user, @RequestBody InfoDto infoDto) {
        String email = user.getEmail();
        userService.modifyInfo(email, infoDto);
        return ResponseEntity.ok("회원정보가 변경되었습니다.");
    }

}
