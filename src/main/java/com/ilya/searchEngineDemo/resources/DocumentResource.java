package com.ilya.searchEngineDemo.resources;

import com.ilya.searchEngineDemo.model.Document;
import com.ilya.searchEngineDemo.model.InvertedIndex;
import com.ilya.searchEngineDemo.service.DocumentService;
import com.ilya.searchEngineDemo.service.SimpleSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/document")
public class DocumentResource {

    @Autowired SimpleSearchService searchService;
    @Autowired DocumentService documentService;

    @GetMapping(value = "/id")
    public ResponseEntity<Document> getDocument(@PathVariable("id") Long id) {
        return ResponseEntity.ok(documentService.findDocumentById(id).get());
    }

    @PostMapping(value = "/tokens")
    public ResponseEntity<Set<InvertedIndex>> searchDocuments(String tokens) {
        return ResponseEntity.ok(searchService.search(tokens));
    }

    @PostMapping(value = "/id")
    public ResponseEntity<Document> addDocument(@RequestBody String body) {
        return new ResponseEntity<>(documentService.addDocument(body), HttpStatus.CREATED);
    }

}
