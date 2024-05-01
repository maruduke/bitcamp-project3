package com.example.backend.controller;

import com.example.backend.dto.template.AccountDto;
import com.example.backend.dto.template.TemplateDto;
import com.example.backend.entity.mongo.Report;
import com.example.backend.entity.mongo.Template;
import com.example.backend.entity.mongo.TypeData;
import com.example.backend.repository.TemplateRepository;
import com.example.backend.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        System.out.println(test);
        System.out.println(test.getType());


        return null;
    }



}
