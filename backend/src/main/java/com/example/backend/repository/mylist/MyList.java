package com.example.backend.repository.mylist;

import com.example.backend.dto.board.MyListDto;
import com.example.backend.entity.maria.Document;
import com.example.backend.entity.maria.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyList {
    Slice<MyListDto> findSliceBy(User user, Pageable pageable);
}
