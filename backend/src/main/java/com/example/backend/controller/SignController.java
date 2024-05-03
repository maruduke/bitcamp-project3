package com.example.backend.controller;


import com.example.backend.dto.template.TemplateDto;
import com.example.backend.entity.maria.User;
import com.example.backend.service.BoardService;
import com.example.backend.service.SignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sign")
@Log4j2
@RequiredArgsConstructor
public class SignController {


    private final SignService signService;

    @PostMapping("/create")
    public ResponseEntity createDocument(@AuthenticationPrincipal User user ,@RequestBody TemplateDto templateDto) {

        templateDto.setWriter(user.getUserId());
        log.info(templateDto);
        signService.saveTemplate(templateDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/arrove")
    public ResponseEntity signDocument(@AuthenticationPrincipal User user) {
        return null;
    }
}
