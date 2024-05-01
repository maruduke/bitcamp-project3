package com.example.backend.controller;

import com.example.backend.dto.template.TemplateDto;
import com.example.backend.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final TemplateRepository templateRepository;

    @GetMapping("/accountRegister")
    public void accountRegisterGet(){

    }

//    @PostMapping("/accountRegister")
//    public String accountRegisterPost(AccountDto accountDto, BindingResult bindingResult, RedirectAttributes redirectAttributes){
//        if(bindingResult.hasErrors()){
//
//        }
//    }

    @PostMapping("/test")
    public String test(@RequestBody TemplateDto test) {

        log.info(test);

        return null;
    }



}
