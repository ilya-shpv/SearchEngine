package com.ilya.searchEngineDemo.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface SearchService {

    Map<Long, Double> sort(String indexId);

}
