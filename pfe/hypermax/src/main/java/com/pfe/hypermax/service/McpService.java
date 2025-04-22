package com.pfe.hypermax.service;

import com.pfe.hypermax.model.Product;
import com.pfe.hypermax.model.SearchResult;
import com.pfe.hypermax.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Service
public class McpService {
    private final BraveSearchService braveSearchService;
    private final FileStorageService fileStorageService;
    private final ProductRepository productRepository;

    public McpService(BraveSearchService braveSearchService,
                      FileStorageService fileStorageService,
                      ProductRepository productRepository) {
        this.braveSearchService = braveSearchService;
        this.fileStorageService = fileStorageService;
        this.productRepository = productRepository;
    }

    public List<SearchResult> searchAndStore(String query) throws IOException {
        List<SearchResult> results = braveSearchService.search(query).block();
        if (results == null || results.isEmpty()) {
            return Collections.emptyList();
        }

        // Process and save results
        results.forEach(result -> {
            // Clean and truncate data
            String cleanDescription = cleanText(result.getDescription(), 2000);
            String cleanImageUrl = cleanUrl(result.getImage(), 1000);
            BigDecimal cleanPrice = parsePrice(result.getPrice());

            // Save to database
            Product product = new Product();
            product.setTitle(cleanText(result.getTitle(), 255));
            product.setPrice(cleanPrice);
            product.setSource(cleanText(result.getSource(), 100));
            product.setUrl(cleanUrl(result.getUrl(), 1000));
            product.setImageUrl(cleanImageUrl);
            product.setDescription(cleanDescription);

            productRepository.save(product);
        });

        fileStorageService.saveSearchResults(query, results);
        return results;
    }

    public String getSavedResults() throws IOException {
        return fileStorageService.getSavedResults();
    }

    // Helper methods
    private String cleanText(String text, int maxLength) {
        if (text == null) return "";
        text = text.replaceAll("<[^>]*>", ""); // Remove HTML tags
        return text.length() > maxLength ? text.substring(0, maxLength) : text;
    }

    private String cleanUrl(String url, int maxLength) {
        if (url == null) return "";
        return url.length() > maxLength ? url.substring(0, maxLength) : url;
    }

    private BigDecimal parsePrice(String priceStr) {
        if (priceStr == null) return BigDecimal.ZERO;
        try {
            // Extract numeric values (handles $1,299.99 or €1.199,99)
            String clean = priceStr.replaceAll("[^0-9.,]", "")
                    .replace(",", ".");
            return new BigDecimal(clean);
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }
}