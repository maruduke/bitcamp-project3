package com.example.backend.service;

import com.example.backend.dto.board.MyListDto;
import com.example.backend.entity.maria.User;
import com.example.backend.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final DocumentRepository documentRepository;

    public Slice<MyListDto> getMyList(User user, Pageable pageable){

        return documentRepository.findByWriter(user, pageable);
    }

}
