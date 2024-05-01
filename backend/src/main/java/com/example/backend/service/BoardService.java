package com.example.backend.service;

import com.example.backend.dto.template.AccountExpenseDto;
import com.example.backend.dto.template.TemplateDto;
import com.example.backend.entity.maria.Document;
import com.example.backend.entity.mongo.Template;
import com.example.backend.entity.mongo.TypeData;
import com.example.backend.repository.DocumentRepository;
import com.example.backend.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final DocumentRepository documentRepository;
    private final TemplateRepository templateRepository;



    public String saveTemplate(TemplateDto templateDto) {

        LocalDate createDate = LocalDate.now();

        Template<? extends TypeData> template = templateDto.toTemplateEntity();
        // mongodb 저장
        templateRepository.save(template);

        Document document = templateDto.toDocumentDto(template.getId(), createDate);
        documentRepository.save(document);

        return template.getId();
    }
}
