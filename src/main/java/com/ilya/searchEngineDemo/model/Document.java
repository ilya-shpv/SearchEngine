package com.ilya.searchEngineDemo.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Document {
    private static long idCounter = 0; //TODO add proper id generator
    private long id;
    private String body;
    private Map<String, Integer> indexes;

    public Document(final String body) {
        this.body = body;
        this.id = generateId();
        this.indexes = new HashMap<>();
        createIndexMap(body);
    }

    private static synchronized long generateId() {
        return idCounter++;
    }

    private void createIndexMap(String body) {
        Arrays.stream(body.split("\\s"))
                .forEach(i -> {
                            if(indexes.containsKey(i)) {
                                Integer keyValue = indexes.get(i);
                                indexes.replace(i, keyValue, keyValue + 1);
                            } else {
                                indexes.put(i, 1);
                            }
                        }
                );
    }

    public long getId() {
        return id;
    }

    public Map<String, Integer> getIndexes() {
        return indexes;
    }
}
