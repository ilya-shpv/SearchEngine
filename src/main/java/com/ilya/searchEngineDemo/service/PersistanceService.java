package com.ilya.searchEngineDemo.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilya.searchEngineDemo.model.Document;
import com.ilya.searchEngineDemo.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PersistanceService {

    @Autowired private DocumentRepository documentRepository;

    public void persist(String path) throws FileNotFoundException{
        try {
            Set<Document> documents = new HashSet<>();
            documents.addAll(readFromFile(path));
            documentRepository.addDocuments(documents);
        } catch (Exception e) {
            throw new FileNotFoundException("Initializing database thrown error: " + e.getMessage());
        }
    }

    private List<Document> readFromFile(String path) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(path);
        return mapper.readValue(file, new TypeReference<List<Document>>(){});
    }

}
