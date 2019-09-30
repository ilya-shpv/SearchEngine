package com.ilya.searchEngineDemo.service;

import com.ilya.searchEngineDemo.model.Document;
import com.ilya.searchEngineDemo.model.InvertedIndex;
import com.ilya.searchEngineDemo.repository.DocumentRepository;
import com.ilya.searchEngineDemo.repository.IndexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class IndexingService {

    @Autowired private DocumentRepository documentRepository;
    @Autowired private IndexRepository indexRepository;

    public void indexy() {
        Set<Document> documents = documentRepository.getDocuments();
        documents.forEach(doc -> {
            Set<String> indexes = new HashSet<>(doc.getIndexes().keySet());
            for (String curIndex : indexes) {
                Optional<InvertedIndex> wrappedIndex = indexRepository.findIndexById(curIndex);
                if (wrappedIndex.isPresent()) {
                    InvertedIndex index = wrappedIndex.get();
                    index.addDocument(doc.getId());
                } else {
                    InvertedIndex index = new InvertedIndex(curIndex);
                    index.addDocument(doc.getId());
                    indexRepository.addIndex(index);
                }
            }
        });
    }
}
