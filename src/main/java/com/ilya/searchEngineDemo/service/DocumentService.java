package com.ilya.searchEngineDemo.service;

import com.ilya.searchEngineDemo.model.Document;
import com.ilya.searchEngineDemo.model.InvertedIndex;
import com.ilya.searchEngineDemo.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class DocumentService {

    private static AtomicLong idCounter = new AtomicLong(0); //TODO add proper id generator

    @Autowired private DocumentRepository documentRepository;
    @Autowired private InvertedIndexService indexService;


    public Document addDocument(String body) {
        Document document = new Document(generateId(), body, createIndexMap(body));
        documentRepository.add(document);
        indexy();
        return document;
    }


    public Optional<Document> findDocumentById(Long id) {
        return documentRepository.get().stream()
                .filter(i -> i.getId() == id)
                .findAny();
    }

    public Set<Document> getDocuments() {
        return documentRepository.get();
    }

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

    private static long generateId() {
        return idCounter.incrementAndGet();
    }

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
