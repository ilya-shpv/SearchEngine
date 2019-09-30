package com.ilya.searchEngineDemo.model;

import java.util.*;

public class InvertedIndex {

    private final String value;
    private final Set<Long> documentsContaining;

    public InvertedIndex(String value) {
        this.documentsContaining = new HashSet<>();
        this.value = value;
    }

    public void addDocument(Long id) {
        if (documentsContaining.contains(id)) {
            throw new IllegalArgumentException("Index already contains this document id");
        }
        documentsContaining.add(id);
    }

    public String getValue() {
        return value;
    }

    public Set<Long> getDocumentsContaining() {
        return documentsContaining;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InvertedIndex index = (InvertedIndex) o;
        return Objects.equals(value, index.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
