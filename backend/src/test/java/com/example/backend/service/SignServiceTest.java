package com.example.backend.service;

import com.example.backend.dto.template.ReportDto;
import com.example.backend.entity.maria.Document;
import com.example.backend.entity.maria.TaskProgress;
import com.example.backend.entity.maria.enumData.DocType;
import com.example.backend.entity.mongo.Template;
import com.example.backend.repository.DocumentRepository;
import com.example.backend.repository.TaskProgressRepository;
import com.example.backend.repository.TemplateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SignServiceTest {
    private static final Logger log = LoggerFactory.getLogger(BoardServiceTest.class);

    @Autowired
    private SignService signService;

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private TaskProgressRepository taskProgressRepository;


    @Test
    @WithUserDetails(value="igj2222@naver.com")
    public void templateSaveTest() {
        // given
        ReportDto dto = new ReportDto();
        dto.setWriter(2L);
        dto.setType(DocType.REPORT);
        dto.setRefList(new ArrayList<Long>());
        dto.setApproverList(Arrays.asList(1L,2L,3L) );
        dto.setDetail("test");


        // when
        String id = signService.saveTemplate(dto);

        // then
        Template report = templateRepository.findById(id).orElse(null);
        Document document = documentRepository.findById(id).orElse(null);
        TaskProgress taskProgress = taskProgressRepository.findByDocumentId(id).orElse(null);

        log.info(report.toString());
        log.info(document.toString());
        log.info(taskProgress.toString());

        Assertions.assertEquals(id, report.getId());
        Assertions.assertEquals(id, document.getDocumentId());
        Assertions.assertEquals(id, taskProgress.getDocumentId());

    }
}