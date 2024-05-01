package com.example.backend.service;

import com.example.backend.dto.template.AccountDto;
import com.example.backend.dto.template.TemplateDto;
import com.example.backend.entity.maria.Document;
import com.example.backend.entity.maria.enumData.DocType;
import com.example.backend.entity.mongo.Template;
import com.example.backend.repository.DocumentRepository;
import com.example.backend.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final DocumentRepository documentRepository;
    private final TemplateRepository templateRepository;

    public String register(AccountDto accountDto){
        Document entity = Document.dtoToEntity(accountDto);

        Document documentId = documentRepository.save(entity);

        AccountDto dto = AccountDto.entityToDto(documentId);

        return dto.getDocumentId();
    }

    public void saveTemplate(TemplateDto templateDto) {



    }
}
