package com.example.backend.service;

import com.example.backend.dto.template.AccountExpenseDto;
import com.example.backend.dto.template.TemplateDto;
import com.example.backend.entity.maria.Document;
import com.example.backend.repository.DocumentRepository;
import com.example.backend.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final DocumentRepository documentRepository;
    private final TemplateRepository templateRepository;



    public void saveTemplate(TemplateDto templateDto) {



    }
}
