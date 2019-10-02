package com.ilya.searchEngineDemo.service;

import com.ilya.searchEngineDemo.model.InvertedIndex;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleSearchServiceTest {

    @Autowired private SimpleSearchService searchService;
    @Autowired private DocumentService documentService;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void search() {
        String body1 = "This is a simple search engine";
        documentService.addDocument(body1);
        String body2 = "";
        documentService.addDocument(body2);
        String body3 = "This is a simple search engine";
        documentService.addDocument(body3);
        String body4 = "Hello world";
        documentService.addDocument(body4);

        Set<InvertedIndex> result1 = searchService.search("This");
        assertEquals(result1.toString(), "[InvertedIndex{value='This', documentsContaining=[1, 3], tfIdfMap={1=0.050171665943996864, 3=0.050171665943996864}}]");
        Set<InvertedIndex> result2 = searchService.search("Hello");
        assertNotEquals(result2.toString(), "[InvertedIndex{value='This', documentsContaining=[1, 3], tfIdfMap={1=0.050171665943996864, 3=0.050171665943996864}}]");
        exceptionRule.expect(IllegalArgumentException.class);
        searchService.search("this");
        Set<InvertedIndex> result4 = searchService.search("This world");
        assertNotEquals(result4.toString(), "[InvertedIndex{value='world', documentsContaining=[4], tfIdfMap={4=0.3010299956639812}}, InvertedIndex{value='This', documentsContaining=[1, 3], tfIdfMap={1=0.050171665943996864, 3=0.050171665943996864}}]");

    }
}