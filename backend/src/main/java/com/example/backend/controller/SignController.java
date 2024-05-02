package com.example.backend.controller;


import com.example.backend.dto.template.TemplateDto;
import com.example.backend.entity.maria.User;
import com.example.backend.service.BoardService;
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


    private final BoardService boardService;

    @PostMapping("/create")
    public ResponseEntity createDocument(@RequestBody TemplateDto templateDto) {

        boardService.saveTemplate(templateDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/test")
    public String test() {
        return "good";
    }

    @GetMapping("/test2")
    public void test2(@AuthenticationPrincipal User user) {
        log.info(user.toString());

    }
}
