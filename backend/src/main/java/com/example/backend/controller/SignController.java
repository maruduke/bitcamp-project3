package com.example.backend.controller;


import com.example.backend.dto.sign.ApproveDto;
import com.example.backend.dto.sign.UserDto;
import com.example.backend.dto.template.TemplateDto;
import com.example.backend.dto.template.TemplateResponseDto;
import com.example.backend.dto.template.TemporaryResponseDto;
import com.example.backend.entity.maria.User;
import com.example.backend.entity.mongo.Template;
import com.example.backend.entity.mongo.TypeData;
import com.example.backend.service.SignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sign")
@CrossOrigin(origins = "*")
@Log4j2
@RequiredArgsConstructor
public class SignController {


    private final SignService signService;

    /**
     * 결재 문서 저장
     * @param user  로그인한 사용자 정보
     * @param templateDto 저장할 템플릿 정보
     * @return  상태 정보 200
     */
    @PostMapping("/create")
    public ResponseEntity<String> createDocument(@AuthenticationPrincipal User user ,@RequestBody TemplateDto templateDto) {
        templateDto.setWriter(user.getUserId());
        log.info(templateDto);
        String templateId = signService.saveTemplate(templateDto);
        return new ResponseEntity(templateId,HttpStatus.OK);
    }

    /**
     * 결재 승인자가 문서 결재, 반려
     * @param user 로그인한 사용자 정보
     * @param approveDto 문서Id, 승인 여부
     * @return 상태 정보 200
     */
    @PostMapping("/approve")
    public ResponseEntity signDocument(@AuthenticationPrincipal User user, @RequestBody ApproveDto approveDto) {
        signService.approve(user, approveDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 파일 임시 저장
     * @param user
     * @param templateDto
     * @return
     */
    @PostMapping("/temporaryStorage")
    public ResponseEntity temporaryStorage(@AuthenticationPrincipal User user, @RequestBody TemplateDto templateDto) {
        signService.temporaryStorage(user, templateDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 임시 저장된 파일 가져오기
     * @param user
     * @return
     */
    @GetMapping("/getTemporary")
    public ResponseEntity<TemporaryResponseDto> getTemporary(@AuthenticationPrincipal User user) {

        log.info(user);
        Template<? extends TypeData> template = signService.getTemporaryStorage(user);

        TemporaryResponseDto temporaryResponseDto = TemporaryResponseDto.builder().type(template.getType()).typeData(template.getTypeData()).build();

        // dto 반환 해야 함
        return ResponseEntity.ok(temporaryResponseDto);
    }

    @GetMapping("/getUserList")
    public ResponseEntity<List<UserDto>> getUserList() {

        List<UserDto> users = signService.getUserList();

        return ResponseEntity.ok(users);
    }





}
