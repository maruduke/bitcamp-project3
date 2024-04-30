package com.example.backend.service;

import com.example.backend.dto.MemberDto;
import com.example.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService{

    @Autowired
    private final MemberRepository memberRepository;

    public List<MemberDto> getAllMembers() {
        return memberRepository.findAllMembersWithInfo();
    }

}
