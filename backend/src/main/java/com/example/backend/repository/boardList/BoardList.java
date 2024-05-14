package com.example.backend.repository.boardList;

import com.example.backend.dto.board.MyListDto;
import com.example.backend.dto.board.WaitDto;
import com.example.backend.entity.maria.User;
import com.example.backend.entity.maria.enumData.DocState;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface BoardList {
    Slice<MyListDto> findByWriter(User user, Pageable pageable);

    Slice<WaitDto> findByState(User user, Pageable pageable, List<DocState> stateList);

    Slice<WaitDto> findByStateIn(User user, Pageable pageable, List<DocState> stateList);
}
