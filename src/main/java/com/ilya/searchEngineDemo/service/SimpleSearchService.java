package com.ilya.searchEngineDemo.service;

import com.ilya.searchEngineDemo.model.InvertedIndex;
import com.ilya.searchEngineDemo.repository.DocumentRepository;
import com.ilya.searchEngineDemo.repository.IndexRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

public class SimpleSearchService implements SearchService {

    @Autowired private IndexRepository indexRepository;
    @Autowired private DocumentRepository documentRepository;
    @Autowired private TfIdfService tfIdfService;

    public Map<Long, Double> sort(String indexId) {
        InvertedIndex index = find(indexId);
        double idf = tfIdfService.calculateIdf(documentRepository.getDocuments().size(), index.getDocumentsContaining().size());
        Set<Long> documentsContainingIndex = index.getDocumentsContaining();
        Map<Long, Integer> wordOccurrenceInDocuments = findIndexOccurrenceInDocuments(indexId, documentsContainingIndex);
        Map<Long, Double> tfCalculatedForDocuments = calculateTfForDocuments(wordOccurrenceInDocuments);
        Map<Long, Double> tfIdfCalculatedForDocuments = calculateTfIdfForDocuments(tfCalculatedForDocuments, idf);
        return sortByTfIdf(tfIdfCalculatedForDocuments);
    }

    private InvertedIndex find(String indexId) {
        return indexRepository.findIndexById(indexId).orElseThrow(() -> new IllegalArgumentException("Index doesn't exist in any document."));
    }

    private Map<Long, Integer> findIndexOccurrenceInDocuments(String indexId, Set<Long> docs) {
        return docs.stream()
                .collect(Collectors.toMap(Long::longValue, i -> documentRepository
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
                                tfIdfService.calculateTf(value, documentRepository.findDocumentById(key).get().getIndexes().size())
                        )
        );
        return tfCalculatedForDocuments;
    }

    private Map<Long, Double> calculateTfIdfForDocuments(Map<Long, Double> tfCalculatedForDocuments,
                                                         double idf) {
        return tfCalculatedForDocuments.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        value -> tfIdfService.calculateTfIdf(value.getValue(), idf)
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

}
