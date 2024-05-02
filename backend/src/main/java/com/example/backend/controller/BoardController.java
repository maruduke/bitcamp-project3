package com.example.backend.controller;

import com.example.backend.dto.template.TemplateDto;
import com.example.backend.repository.TemplateRepository;
import com.example.backend.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

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



}
