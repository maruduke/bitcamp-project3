package com.example.backend.service;

import com.example.backend.dto.Member.MemberDto;
import com.example.backend.entity.maria.User;
import com.example.backend.repository.MemberRepository;
import com.example.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService{

    private final MemberRepository memberRepository;

    public List<MemberDto> getAllMembers() {

        return memberRepository.findAllMembersWithInfo();
    }

    public List<MemberDto> getDeptMembers(String dept) {

        return memberRepository.findDeptMembers(dept);
    }
}
