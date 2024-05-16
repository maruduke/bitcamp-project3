package com.example.backend.dto.Member;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

    @NotEmpty
    private String name;

    private String dept;

    private String position;

    @NotEmpty
    private String email;

    @NotEmpty
    private String tel;


}
