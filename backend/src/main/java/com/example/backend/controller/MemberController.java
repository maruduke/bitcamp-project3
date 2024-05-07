package com.example.backend.controller;

import com.example.backend.dto.Member.MemberDto;
import com.example.backend.repository.MemberRepository;
import com.example.backend.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @CrossOrigin
    @GetMapping("/member")
    public List<MemberDto> getMembers() {

        return memberService.getAllMembers();
    }

}
