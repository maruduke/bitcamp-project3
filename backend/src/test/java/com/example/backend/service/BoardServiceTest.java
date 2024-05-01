package com.example.backend.service;

import com.example.backend.dto.template.BussinessTripDto;
import com.example.backend.dto.template.ReportDto;
import com.example.backend.dto.template.TemplateDto;
import com.example.backend.entity.maria.Document;
import com.example.backend.entity.maria.enumData.DocType;
import com.example.backend.entity.mongo.Report;
import com.example.backend.entity.mongo.Template;
import com.example.backend.repository.DocumentRepository;
import com.example.backend.repository.TemplateRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class BoardServiceTest {
    private static final Logger log = LoggerFactory.getLogger(BoardServiceTest.class);
    @Autowired
    private BoardService boardService;

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private DocumentRepository documentRepository;


    @Test
    public void templateSaveTest() {
        // given
        ReportDto dto = new ReportDto();
        dto.setWriter(2L);
        dto.setType(DocType.REPORT);
        dto.setRefList(new ArrayList<Long>());
        dto.setDetail("test");


        // when
        String id = boardService.saveTemplate(dto);

        // then
        Template report = templateRepository.findById(id).orElse(null);
        Document document = documentRepository.findById(id).orElse(null);

        log.info(report.toString());
        log.info(document.toString());

        Assertions.assertEquals(id, report.getId());
        Assertions.assertEquals(id, document.getDocumentId());

    }
}
