package com.example.backend.controller;

import com.example.backend.dto.template.AccountDto;
import com.example.backend.service.BoardService;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Log4j2
@RestController
@RequestMapping("/board")
public class BoardController {

    @GetMapping("/accountRegister")
    public void accountRegisterGet(){

    }

//    @PostMapping("/accountRegister")
//    public String accountRegisterPost(AccountDto accountDto, BindingResult bindingResult, RedirectAttributes redirectAttributes){
//        if(bindingResult.hasErrors()){
//
//        }
//    }
}
