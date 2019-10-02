package com.ilya.searchEngineDemo.repository;

import com.ilya.searchEngineDemo.model.Document;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

/**
 * Emulates the document repository.
 *
 */
@Repository
public class DocumentRepository {

    /**
     * Simple set that emulates document storage
     *
     */
    private final Set<Document> documents = new HashSet<>();

    /**
     * Adds document to storage
     * @param document the object to be added
     */
    public synchronized void add(Document document) {
        documents.add(document);
    }

    /**
     * Get all the documents that stored in repository
     * @return {@code documents} set that contains all added documents
     */
    public Set<Document> get() {
        return documents;
    }

}
