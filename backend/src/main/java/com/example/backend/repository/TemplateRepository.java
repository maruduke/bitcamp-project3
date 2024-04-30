package com.example.backend.repository;

import com.example.backend.entity.mongo.Report;
import com.example.backend.entity.mongo.Template;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateRepository extends MongoRepository<Template, String> {



}
