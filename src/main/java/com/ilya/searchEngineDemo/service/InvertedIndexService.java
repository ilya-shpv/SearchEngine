package com.ilya.searchEngineDemo.service;

import com.ilya.searchEngineDemo.model.InvertedIndex;
import com.ilya.searchEngineDemo.repository.IndexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class InvertedIndexService {

    @Autowired private IndexRepository indexRepository;

    public void addIndex(final InvertedIndex index) {
        if (indexRepository.get().contains(index)) {
            throw new IllegalArgumentException("Index already exists");
        }
        indexRepository.add(index);
    }

    public void addDocument(InvertedIndex index, Long id) {
        Set<Long> documentsContaining = index.getDocumentsContaining();
        if (documentsContaining.contains(id)) {
            throw new IllegalArgumentException("Index already contains this document id");
        }
        documentsContaining.add(id);
    }

    public Optional<InvertedIndex> findIndexById(String id) {
        return indexRepository.get().stream()
                .filter(i -> i.equals(new InvertedIndex(id)))
                .findAny();
    }

}
