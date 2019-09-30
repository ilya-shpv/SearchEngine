package com.ilya.searchEngineDemo.repository;

import com.ilya.searchEngineDemo.model.InvertedIndex;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository
public class IndexRepository {
    private Set<InvertedIndex> indexes = new HashSet<>();

    public void addIndex(final InvertedIndex index) {
        if (indexes.contains(index)) {
            throw new IllegalArgumentException("Index already exists");
        }
        indexes.add(index);
    }

    public Optional<InvertedIndex> findIndexById(String id) {
        return indexes.stream()
                .filter(i -> i.equals(new InvertedIndex(id)))
                .findAny();
    }
}
