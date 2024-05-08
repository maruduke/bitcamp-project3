package com.example.backend.service;

import com.example.backend.entity.maria.User;
import com.example.backend.repository.DocumentRepository;
import com.example.backend.repository.TaskProgressRepository;
import com.example.backend.repository.TemplateRepository;
import com.example.backend.security.CustomUserDetailService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithUserDetails;

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



//    @Test
//    @WithUserDetails(value="nevers1117@gmail.com")
//    public void templateSaveTest(@AuthenticationPrincipal User user) {
//
//        // given
//        ReportDto dto = new ReportDto();
//        dto.setWriter(2L);
//        dto.setType(DocType.REPORT);
//        dto.setRefList(new ArrayList<Long>());
//        dto.setApproverList(Arrays.asList(1L,2L,3L) );
//        dto.setDetail("test");
//
//
//        // when
//        String id = signService.saveTemplate(dto);
//
//        // then
//        Template report = templateRepository.findById(id).orElse(null);
//        Document document = documentRepository.findById(id).orElse(null);
//        TaskProgress taskProgress = taskProgressRepository.findByDocumentId(id).orElse(null);
//
//        log.info(report.toString());
//        log.info(document.toString());
//        log.info(taskProgress.toString());
//
//        Assertions.assertEquals(id, report.getId());
//        Assertions.assertEquals(id, document.getDocumentId());
//        Assertions.assertEquals(id, taskProgress.getDocumentId());
//
//    }


    @Test
    @WithUserDetails(value = "nevers1117@gmail.com")
    void saveTemplate(@AuthenticationPrincipal User user) throws Exception{
        String username = user.getUsername();
    }

    @Test
    void approve() {
    }

    @Test
    void documentDeny() {
    }

    @Test
    void documentApprove() {
    }

    @Test
    void temporaryStorage() {
    }

    @Test
    void getTemporaryStorage() {
    }
}