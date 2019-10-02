package com.ilya.searchEngineDemo.service;

import com.ilya.searchEngineDemo.model.InvertedIndex;
import com.ilya.searchEngineDemo.repository.IndexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

/**
 * Service that implements inverted index operations.
 *
 */
@Service
public class InvertedIndexService {

    @Autowired private IndexRepository indexRepository;

    /**
     * Adds index to repository
     * @param index the object to be added
     * @throws IllegalArgumentException if the repository contains given {@code index}
     */
    public void addIndex(final InvertedIndex index) {
        if (indexRepository.get().contains(index)) {
            throw new IllegalArgumentException("Index already exists");
        }
        indexRepository.add(index);
    }

    /**
     * Adds document id to the index
     * @param index the object to which will be added document id
     * @param id the document id which will be added to the index
     */
    public void addDocument(InvertedIndex index, Long id) {
       index.getDocumentsContaining().add(id);
    }

    /**
     * Returns index that has corresponding id
     * @param indexId the index id to search by
     */
    public Optional<InvertedIndex> findIndexById(String indexId) {
        return indexRepository.get().stream()
                .filter(i -> i.getValue().equals(indexId))
                .findFirst();
    }


}
