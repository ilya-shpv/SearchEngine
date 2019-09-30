package com.ilya.searchEngineDemo.service;

import com.ilya.searchEngineDemo.model.InvertedIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SimpleSearchService {

    @Autowired private InvertedIndexService indexService;
    @Autowired private DocumentService documentService;

    public Set<InvertedIndex> search(String tokens) {
        Set<InvertedIndex> indexes = new HashSet<>();
        Arrays.stream(tokens.split("\\s")).forEach(i -> indexes.add(searchByIndex(i)));
        return indexes;
    }

    private InvertedIndex searchByIndex(String index) {
        Map<Long, Double> tfIdfMap = sort(index);
        InvertedIndex curIndex = indexService.findIndexById(index).get();
        curIndex.setTfIdfMap(tfIdfMap);
        return curIndex;
    }

    private Map<Long, Double> sort(String indexId) {
        InvertedIndex index = indexService.findIndexById(indexId).orElseThrow(() -> new IllegalArgumentException("Index doesn't exist in any document."));
        double idf = calculateIdf(documentService.getDocuments().size(), index.getDocumentsContaining().size());
        Set<Long> documentsContainingIndex = index.getDocumentsContaining();
        Map<Long, Integer> wordOccurrenceInDocuments = findIndexOccurrenceInDocuments(indexId, documentsContainingIndex);
        Map<Long, Double> tfCalculatedForDocuments = calculateTfForDocuments(wordOccurrenceInDocuments);
        Map<Long, Double> tfIdfCalculatedForDocuments = calculateTfIdfForDocuments(tfCalculatedForDocuments, idf);
        return sortByTfIdf(tfIdfCalculatedForDocuments);
    }

    private Map<Long, Integer> findIndexOccurrenceInDocuments(String indexId, Set<Long> docs) {
        return docs.stream()
                .collect(Collectors.toMap(Long::longValue, i -> documentService
                        .findDocumentById(i)
                        .get()
                        .getIndexes()
                        .get(indexId)));
    }

    private Map<Long, Double> calculateTfForDocuments(Map<Long, Integer> wordOccurrenceInDocuments) {
        Map<Long, Double> tfCalculatedForDocuments = new HashMap<>();
        wordOccurrenceInDocuments.forEach(
                (key, value) ->
                        tfCalculatedForDocuments.put(
                                key,
                                calculateTf(value, documentService.findDocumentById(key).get().getIndexes().size())
                        )
        );
        return tfCalculatedForDocuments;
    }

    private Map<Long, Double> calculateTfIdfForDocuments(Map<Long, Double> tfCalculatedForDocuments,
                                                         double idf) {
        return tfCalculatedForDocuments.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        value -> calculateTfIdf(value.getValue(), idf)
                ));
    }

    private Map<Long, Double> sortByTfIdf(Map<Long, Double> tfIdfCalculatedForDocuments) {
        return tfIdfCalculatedForDocuments.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e2,
                        LinkedHashMap::new
                ));
    }

    private double calculateTf(int wordOccurrence, int allWords) {
        return (double) wordOccurrence / (double) allWords;
    }

    private double calculateIdf(int allDocuments, int docsContainingWord) {
        return Math.log10((double) allDocuments / (double) docsContainingWord);
    }

    private double calculateTfIdf(double tf, double idf) {
        return tf * idf;
    }

}
