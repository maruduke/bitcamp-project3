package com.example.backend.repository;

import com.example.backend.entity.mongo.Report;
import com.example.backend.entity.mongo.Template;
import com.example.backend.entity.mongo.TypeData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
class TemplateRepositoryTest {

    @Autowired
    private TemplateRepository templateRepository;

    @Test
    public void save() {
        TypeData report = Report.builder()
                .title("test")
                .detail("report test")
                .build();


        Template reporttemplate = Template.builder().typeData(report).build();
        templateRepository.save(reporttemplate);

        Template find = templateRepository.findById(reporttemplate.getId()).get();

        Assertions.assertEquals(reporttemplate.getId(), find.getId());
    }


}