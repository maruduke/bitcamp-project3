package com.example.backend.controller;

import com.example.backend.dto.MemberDto;
import com.example.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/member")
    public List<MemberDto> getMembers() {
        return memberRepository.findAllMembersWithInfo();
    }

}
