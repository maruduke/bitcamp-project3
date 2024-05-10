package com.example.backend.controller;

import com.example.backend.dto.board.MyListDto;
import com.example.backend.dto.board.WaitDto;
import com.example.backend.entity.maria.User;
import com.example.backend.entity.maria.enumData.DocState;
import com.example.backend.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @CrossOrigin
    @GetMapping("/myList")
    public Slice<MyListDto> getMyList(@AuthenticationPrincipal User user, @RequestParam("pageNumber") int pageNumber) {

        Pageable pageable = PageRequest.of(pageNumber, 10);
        return boardService.getMyList(user, pageable);
    }

    @CrossOrigin
    @GetMapping("/refList")
    public Slice<WaitDto> getRefList(@AuthenticationPrincipal User user, @RequestParam("pageNumber") int pageNumber, @RequestParam("DocState") DocState docState) {

        Pageable pageable = PageRequest.of(pageNumber, 10);

        return boardService.getRefList(user, pageable, docState);
    }

    @CrossOrigin
    @GetMapping("/approveList")
    public Slice<WaitDto> getApproveList(@AuthenticationPrincipal User user, @RequestParam("pageNumber") int pageNumber, @RequestParam("DocState") DocState docState) {

        Pageable pageable = PageRequest.of(pageNumber, 10);

        return boardService.getApproveList(user, pageable, docState);
    }
}