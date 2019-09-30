package com.ilya.searchEngineDemo.repository;

import com.ilya.searchEngineDemo.model.InvertedIndex;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class IndexRepository {
    private Set<InvertedIndex> indexes = new HashSet<>();

    public void add(final InvertedIndex index) {
        indexes.add(index);
    }

    public Set<InvertedIndex> get() {
        return indexes;
    }
}
