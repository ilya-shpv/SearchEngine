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
       index.getDocumentsContaining().add(id);
    }

    public Optional<InvertedIndex> findIndexById(String indexId) {
//        return indexRepository.get().stream()
//                .filter(i -> i.getValue().equals(indexId))
//                .findFirst();
        Optional<InvertedIndex> invertedIndex = Optional.empty();
        for(InvertedIndex index : indexRepository.get()) {
            if(index.getValue().equals(indexId)) {
                invertedIndex = Optional.of(index);
            }
        }
        return invertedIndex;
    }


}
