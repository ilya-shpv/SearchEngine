package com.ilya.searchEngineDemo.repository;

import com.ilya.searchEngineDemo.model.Document;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository
public class DocumentRepository {
    private Set<Document> documents = new HashSet<>();

    public void addDocuments(Set<Document> document) {
        documents.addAll(document);
    }

    public void addDocument(Document document) {
        documents.add(document);
    }

    public Set<Document> getDocuments() {
        return this.documents;
    }

    public Optional<Document> findDocumentById(Long id) {
        return documents.stream()
                .filter(i -> i.getId() == id)
                .findAny();
    }
}
