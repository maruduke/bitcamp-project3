package com.example.backend.service;

import com.example.backend.Jwt.JwtUtil;
import com.example.backend.dto.user.*;
import com.example.backend.entity.maria.User;
import com.example.backend.entity.maria.enumData.Authority;
import com.example.backend.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.BadRequestException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;
    private final JwtUtil jwtUtil;
    private final RedisTemplate redisTemplate;

    public void join(JoinDto joinDto) throws BadRequestException {
        boolean isExist = userRepository.existsByEmail(joinDto.getEmail());

        if(isExist) {
            throw new BadRequestException("중복된 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(joinDto.getPassword());

        if(joinDto.getDept().contains("인사")|| joinDto.getPosition().equals("CEO")){
            joinDto.setAuthority(Authority.ADMIN);
        }else{
            joinDto.setAuthority(Authority.USER);
        }

        User user = new User(
                joinDto.getName(),
                joinDto.getEmail(),
                encodedPassword,
                joinDto.getDept(),
                joinDto.getPosition(),
                joinDto.getAuthority(),
                joinDto.getTel());

        userRepository.save(user);

    }

    public UserResponse login(LoginDto loginDto) throws BadRequestException {
        User user = userRepository
                .findByEmail(loginDto.getEmail())
                .orElseThrow(()-> new BadRequestException("아이디가 존재하지않습니다."));

        boolean matches = passwordEncoder.matches(loginDto.getPassword(), user.getPassword());

        if(!matches) {
             throw new BadRequestException("비밀번호가 다릅니다.");
        }

        return UserResponse.of(user);
    }

    public void logout(String token) {

        if(jwtUtil.validateToken(token)){
            redisService.setBlackList("logout", token);

            log.info("토큰을 블랙리스트에 추가했습니다.");
        }
        String atk = token.substring(7);
        log.info("atk : " + atk);
        redisService.deleteValues(atk);
        log.info(atk);
    }

    public User getUser(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }

    @Transactional
    public int modifyInfo(String email, InfoDto infoDto){

        String tel = infoDto.getTel();

        return userRepository.updateUserByEmail(tel, email);

    }

    public HeaderDto getName(String email){
        HeaderDto headerDto = new HeaderDto();
        headerDto.setName(userRepository.findNameByEmail(email));

        return headerDto;
    }



}
