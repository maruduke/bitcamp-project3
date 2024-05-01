package com.example.backend.repository;

import com.example.backend.entity.mongo.Report;
import com.example.backend.entity.mongo.Template;
import com.example.backend.entity.mongo.TypeData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

    @Test
    public void find() {
        Optional<Template> template = templateRepository.findById("66308ea6ee73234e6da70c17");

        Template findTemplate = template.orElse(null);

        if (findTemplate == null)
            System.out.println("null");
        else
            System.out.println(findTemplate.toString());
            System.out.println(findTemplate.getTypeData().toString());
    }

}