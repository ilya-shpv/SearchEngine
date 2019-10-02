package com.ilya.searchEngineDemo.service;

import com.ilya.searchEngineDemo.model.Document;
import com.ilya.searchEngineDemo.repository.DocumentRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DocumentServiceTest {

    @Autowired private DocumentService service;

    @Test
    public void addDocument() {

        String body1 = "This is a simple search engine";
        service.addDocument(body1);
        assertEquals(1, service.getDocuments().size());

        String body2 = "";
        service.addDocument(body2);
        assertEquals(2, service.getDocuments().size());

        String body3 = "This is a simple search engine";
        service.addDocument(body2);
        assertEquals(3, service.getDocuments().size());

    }

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void findDocumentById() {
        String body1 = "This is a simple search engine";
        Document doc1 = service.addDocument(body1);

        Optional<Document> d1 = service.findDocumentById(1l);
        assertEquals(d1.get(), doc1);

        exceptionRule.expect(NoSuchElementException.class);
        Document d2 = service.findDocumentById(2l).get();
    }

}