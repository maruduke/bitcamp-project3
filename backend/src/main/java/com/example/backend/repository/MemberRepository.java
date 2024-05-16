package com.example.backend.repository;

import com.example.backend.dto.Member.MemberDto;
import com.example.backend.entity.maria.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<User, Long> {

    // 이름, 부서, 직위, 이메일, 전화번호, 상사번호만을 조회하는 쿼리
    @Query("SELECT NEW com.example.backend.dto.Member.MemberDto(u.name, u.dept, u.position, u.email, u.tel) FROM User u")
    List<MemberDto> findAllMembersWithInfo();

    @Query("SELECT NEW com.example.backend.dto.Member.MemberDto(u.name, u.dept, u.position, u.email, u.tel) " +
            "FROM User u WHERE u.dept = :dept")
    List<MemberDto> findDeptMembers(String dept);
}
