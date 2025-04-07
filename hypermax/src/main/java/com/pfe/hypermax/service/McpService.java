package com.pfe.hypermax.service;

import com.pfe.hypermax.model.SearchResult;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
public class McpService {
    private final BraveSearchService braveSearchService;
    private final FileStorageService fileStorageService;

    public McpService(BraveSearchService braveSearchService, FileStorageService fileStorageService) {
        this.braveSearchService = braveSearchService;
        this.fileStorageService = fileStorageService;
    }

    public List<SearchResult> searchAndStore(String query) throws IOException {
        // Fetch results from Brave Search API
        List<SearchResult> results = braveSearchService.search(query).block();
        if (results == null || results.isEmpty()) {
            return Collections.emptyList(); // Handle empty results gracefully
        }

        // Save results to storage
        fileStorageService.saveSearchResults(query, results);
        return results;
    }

    public String getSavedResults() throws IOException {
        return fileStorageService.getSavedResults();
    }
}