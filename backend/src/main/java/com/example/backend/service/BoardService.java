package com.example.backend.service;

import com.example.backend.dto.board.MyListDto;
import com.example.backend.dto.board.WaitDto;
import com.example.backend.entity.maria.User;
import com.example.backend.entity.maria.enumData.DocState;
import com.example.backend.repository.DocumentRepository;
import com.example.backend.repository.RefRepository;
import com.example.backend.repository.TaskProgressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final DocumentRepository documentRepository;

    public Slice<MyListDto> getMyList(User user, Pageable pageable){

        return documentRepository.findByWriter(user, pageable);
    }

    public Slice<WaitDto> getRefList(User user, Pageable pageable, DocState state){

        return documentRepository.findByState(user, pageable, state);
    }

    public Slice<WaitDto> getApproveList(User user, Pageable pageable, DocState state){

        List<DocState> stateList = Arrays.asList(DocState.PROCESS_1, DocState.PROCESS_2, DocState.PROCESS_3);

        return documentRepository.findByStateIn(user, pageable, stateList);
    }
}
