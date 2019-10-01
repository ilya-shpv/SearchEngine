package com.ilya.searchEngineDemo.model;

import java.util.*;

public class InvertedIndex {

    private final String value;
    private final Set<Long> documentsContaining;
    private Map<Long, Double> tfIdfMap = new HashMap<>();

    public InvertedIndex(String value) {
        this.value = value;
        this.documentsContaining = new HashSet<>();
    }

    public String getValue() {
        return value;
    }

    public Set<Long> getDocumentsContaining() {
        return documentsContaining;
    }

    public Map<Long, Double> getTfIdfMap() {
        return tfIdfMap;
    }

    public void setTfIdfMap(Map<Long, Double> tfIdfMap) {
        this.tfIdfMap = tfIdfMap;
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

    @Override
    public String toString() {
        return "InvertedIndex{" +
                "value='" + value + '\'' +
                ", documentsContaining=" + documentsContaining +
                ", tfIdfMap=" + tfIdfMap +
                '}';
    }

}
