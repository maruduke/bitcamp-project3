package com.example.backend.controller;

import com.example.backend.dto.board.MyListDto;
import com.example.backend.dto.board.WaitDto;
import com.example.backend.dto.template.TemplateResponseDto;
import com.example.backend.entity.maria.User;
import com.example.backend.entity.maria.enumData.DocState;
import com.example.backend.entity.mongo.Template;
import com.example.backend.entity.mongo.TypeData;
import com.example.backend.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/myList")
    public Slice<MyListDto> getMyList(@AuthenticationPrincipal User user, @RequestParam("pageNumber") int pageNumber) {

        Pageable pageable = PageRequest.of(pageNumber, 10);
        return boardService.getMyList(user, pageable);
    }

    @GetMapping("/refList")
    public Slice<WaitDto> getRefList(@AuthenticationPrincipal User user, @RequestParam("pageNumber") int pageNumber, @RequestParam("DocState") DocState docState) {

        Pageable pageable = PageRequest.of(pageNumber, 10);

        return boardService.getRefList(user, pageable, docState);
    }

    @GetMapping("/approveList")
    public Slice<WaitDto> getApproveList(@AuthenticationPrincipal User user, @RequestParam("pageNumber") int pageNumber, @RequestParam("DocState") DocState docState) {

        Pageable pageable = PageRequest.of(pageNumber, 10);

        return boardService.getApproveList(user, pageable, docState);
    }

    @GetMapping("/read")
    public ResponseEntity<TemplateResponseDto<? extends TypeData>> read(@RequestParam String documentId) {


        TemplateResponseDto<? extends TypeData> template = boardService.getTemplateResponseDto(documentId);

        log.info(template);

        return ResponseEntity.ok(template);
    }
}