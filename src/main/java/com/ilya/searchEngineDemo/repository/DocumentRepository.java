package com.ilya.searchEngineDemo.repository;

import com.ilya.searchEngineDemo.model.Document;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class DocumentRepository {
    private Set<Document> documents = new HashSet<>();

    public void add(Document document) {
        documents.add(document);
    }

    public Set<Document> get() {
        return this.documents;
    }

}
