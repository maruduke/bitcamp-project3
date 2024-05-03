package com.example.backend.service;

import com.example.backend.dto.template.TemplateDto;
import com.example.backend.entity.maria.Document;
import com.example.backend.entity.maria.TaskProgress;
import com.example.backend.entity.maria.enumData.DocState;
import com.example.backend.entity.mongo.Template;
import com.example.backend.entity.mongo.TypeData;
import com.example.backend.repository.DocumentRepository;
import com.example.backend.repository.TaskProgressRepository;
import com.example.backend.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
@RequiredArgsConstructor
@Log4j2
public class SignService {

    private final DocumentRepository documentRepository;
    private final TemplateRepository templateRepository;
    private final TaskProgressRepository taskProgressRepository;


    // MongoDB template ID값 반환
    public String saveTemplate(TemplateDto templateDto) {

        LocalDate createDate = LocalDate.now();

        // mongodb 템플릿 저장
        Template<? extends TypeData> template = templateDto.toTemplateEntity();
        templateRepository.save(template);

        log.info(template);

        // Document Table 템플릿 메타 정보 저장
        Document document = templateDto.toDocumentEntity(template.getId(), createDate);
        documentRepository.save(document);

        // TaskProgress Table 결재 진행 상황 저장
        TaskProgress taskProgressApprover = TaskProgress.builder()
                .documentId(template.getId())
                .state(DocState.PROCESS_1)
                .ref_user_id(templateDto.getApproverList().get(0))
                .build();

        taskProgressRepository.save(taskProgressApprover);

        return template.getId();
    }



}