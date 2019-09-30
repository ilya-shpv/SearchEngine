package com.ilya.searchEngineDemo.service;

import org.springframework.stereotype.Service;

@Service
public class TfIdfService {
    public double calculateTf(int wordOccurrence, int allWords) {
        return (double) wordOccurrence / (double) allWords;
    }

    public double calculateIdf(int allDocuments, int docsContainingWord) {
        return Math.log10((double) allDocuments / (double) docsContainingWord);
    }

    public double calculateTfIdf(double tf, double idf) {
        return tf * idf;
    }
}
