package com.ilya.searchEngineDemo.resources;

import com.ilya.searchEngineDemo.model.Document;
import com.ilya.searchEngineDemo.model.InvertedIndex;
import com.ilya.searchEngineDemo.service.DocumentService;
import com.ilya.searchEngineDemo.service.SimpleSearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/document")
@Api(tags = "documents", description = "Simple rest api for search engine")
public class DocumentResource {

    @Autowired SimpleSearchService searchService;
    @Autowired DocumentService documentService;

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Get document", notes = "Get document by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Document found"),
            @ApiResponse(code = 404, message = "Document not found")
    })
    public ResponseEntity<Document> getDocument(@PathVariable("id") Long id) {
        return ResponseEntity.ok(documentService.findDocumentById(id).get());
    }

    @PostMapping(value= "/search")
    @ApiOperation(value = "Search documents", notes = "Search documents that contains specific string tokens")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Found documents"),
            @ApiResponse(code = 400, message = "Invalid request")
    })
    public ResponseEntity<Set<InvertedIndex>> searchDocuments(@RequestBody String tokens) {
        return ResponseEntity.ok(searchService.search(tokens));
    }

    @PostMapping(value= "/add")
    @ApiOperation(value = "Add document", notes = "Add new document")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Document added successfully"),
            @ApiResponse(code = 400, message = "Invalid request")
    })
    public ResponseEntity<Document> addDocument(@RequestBody String body) {
        return new ResponseEntity<>(documentService.addDocument(body), HttpStatus.CREATED);
    }

}
