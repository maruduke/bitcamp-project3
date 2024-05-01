package com.example.backend.repository;

import com.example.backend.dto.Member.MemberDto;
import com.example.backend.entity.maria.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<User, Long> {

    // 이름, 부서, 직위, 이메일, 전화번호, 상사번호만을 조회하는 쿼리
    @Query("SELECT NEW com.example.backend.dto.Member.MemberDto(u.userId, u.name, u.dept, u.position, u.email, u.tel, u.birthDay, u.supervisor) FROM User u")
    List<MemberDto> findAllMembersWithInfo();
}
