package com.ilya.searchEngineDemo.repository;

import com.ilya.searchEngineDemo.model.InvertedIndex;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

/**
 * Emulates the inverted index repository.
 *
 */
@Repository
public class IndexRepository {

    /**
     * Simple set that emulates index storage
     *
     */
    private Set<InvertedIndex> indexes = new HashSet<>();

    /**
     * Adds index to storage
     * @param index the object to be added
     */
    public synchronized void add(final InvertedIndex index) {
        indexes.add(index);
    }

    /**
     * Get all the documents that stored in repository
     * @return {@code indexes} set that contains all added indexes
     */
    public Set<InvertedIndex> get() {
        return indexes;
    }
}
