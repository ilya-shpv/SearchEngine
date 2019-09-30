package com.ilya.searchEngineDemo.model;

import java.util.Map;
import java.util.Objects;

public class Document {
    private final long id;
    private final String body;
    private final Map<String, Integer> indexes;

    public Document(final long id, final String body, final Map<String, Integer> indexes) {
        this.body = body;
        this.id = id;
        this.indexes = indexes;
    }

    public long getId() {
        return id;
    }

    public Map<String, Integer> getIndexes() {
        return indexes;
    }

    public String getBody() {
        return body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Document doc = (Document) o;
        return Objects.equals(id, doc.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", body='" + body + '\'' +
                ", indexes=" + indexes +
                '}';
    }
}
