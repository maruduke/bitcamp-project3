package com.example.backend.dto.board;

import com.example.backend.entity.maria.enumData.DocState;
import com.example.backend.entity.maria.enumData.DocType;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString(callSuper = true)
@AllArgsConstructor
public class MyListDto {

    // document 테이블
    private String title;
    private DocType type;
    private DocState state;
    private LocalDate createDate;

//    public Document fromEntity(){
//        return Document.builder()
//                .title(this.getTitle())
//                .type(this.getType())
//                .state(this.getState())
//                .createDate(this.getCreateDate())
//                .build();
//    }

}
