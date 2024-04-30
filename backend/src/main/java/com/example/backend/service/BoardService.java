package com.example.backend.service;

import com.example.backend.dto.template.AccountDto;
import com.example.backend.entity.maria.Document;
import com.example.backend.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final DocumentRepository documentRepository;

    public String register(AccountDto accountDto){
        Document entity = Document.dtoToEntity(accountDto);

        Document documentId = documentRepository.save(entity);

        AccountDto dto = AccountDto.entityToDto(documentId);

        return dto.getDocumentId();
    }
}
