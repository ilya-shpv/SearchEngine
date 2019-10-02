package com.ilya.searchEngineDemo.service;

import com.ilya.searchEngineDemo.model.Document;
import com.ilya.searchEngineDemo.model.InvertedIndex;
import com.ilya.searchEngineDemo.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Service that implements document operations.
 *
 */
@Service
public class DocumentService {

    private static AtomicLong idCounter = new AtomicLong(0); //TODO add proper id generator

    @Autowired private DocumentRepository documentRepository;
    @Autowired private InvertedIndexService indexService;

    /**
     * Adds document to the repository. Generates auto incremented document id
     * and creates inverted index map
     * @param body the document content to be added
     */
    public Document addDocument(String body) {
        Document document = new Document(generateId(), body, createIndexMap(body));
        documentRepository.add(document);
        indexy();
        return document;
    }


    /**
     * Returns document that has corresponding id
     * @param id the document id to search by
     */
    public Optional<Document> findDocumentById(Long id) {
        return documentRepository.get().stream()
                .filter(i -> i.getId() == id)
                .findAny();
    }

    /**
     * Get all the documents that stored in repository
     */
    public Set<Document> getDocuments() {
        return documentRepository.get();
    }

    /**
     * Rebuild inverted indexes according to new documents arrival
     */
    private void indexy() {
        documentRepository.get().forEach(doc -> {
            Set<String> indexes = new HashSet<>(doc.getIndexes().keySet());
            for (String curIndex : indexes) {
                Optional<InvertedIndex> wrappedIndex = indexService.findIndexById(curIndex);
                if (wrappedIndex.isPresent()) {
                    InvertedIndex index = wrappedIndex.get();
                    indexService.addDocument(index, doc.getId());
                } else {
                    InvertedIndex index = new InvertedIndex(curIndex);
                    indexService.addDocument(index, doc.getId());
                    indexService.addIndex(index);
                }
            }
        });
    }

    /**
     * Simple document id generation
     */
    private static long generateId() {
        return idCounter.incrementAndGet();
    }

    /**
     * Creates index map for newly arrived document
     * @param body the document content to be indexed
     */
    private Map<String, Integer> createIndexMap(String body) {
        Map<String, Integer> indexes = new HashMap<>();
        Arrays.stream(body.split("\\s"))
                .forEach(i -> {
                            if(indexes.containsKey(i)) {
                                Integer keyValue = indexes.get(i);
                                indexes.replace(i, keyValue, keyValue + 1);
                            } else {
                                indexes.put(i, 1);
                            }
                        }
                );
        return indexes;
    }

}
