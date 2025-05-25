package com.pfe.hypermax.service;

import com.pfe.hypermax.dto.BraveSearchResponse;
import com.pfe.hypermax.model.SearchResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BraveSearchService {
    private final WebClient webClient;
    private final String apiKey;
    private final int resultLimit;

    public BraveSearchService(
            WebClient.Builder webClientBuilder,
            @Value("${brave.api.url}") String apiUrl,
            @Value("${brave.api.key}") String apiKey,
            @Value("${brave.api.result-limit}") int resultLimit) {
        this.webClient = webClientBuilder.baseUrl(apiUrl)
                .defaultHeader("Accept", "application/json")
                .build();
        this.apiKey = apiKey;
        this.resultLimit = resultLimit;
    }

    public Mono<List<SearchResult>> search(String query) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/web/search")
                        .queryParam("q", query)
                        .queryParam("count", resultLimit)
                        .build())
                .header("X-Subscription-Token", apiKey)
                .retrieve()
                .onStatus(status -> status.isError(), response -> {
                    return Mono.error(new RuntimeException("API Error: " + response.statusCode()));
                })
                .bodyToMono(BraveSearchResponse.class)
                .map(response -> {
                    if (response == null || response.getWeb() == null || response.getWeb().getResults() == null) {
                        return Collections.emptyList(); // Handle null response gracefully
                    }
                    return response.getWeb().getResults().stream()
                            .map(webResult -> {
                                SearchResult result = new SearchResult();
                                result.setTitle(webResult.getTitle());
                                result.setUrl(webResult.getUrl());

                                // Clean up the description
                                result.setDescription(cleanDescription(webResult.getDescription()));

                                // Extract image URL from the Thumbnail object
                                result.setImage(webResult.getThumbnail() != null ? webResult.getThumbnail().getSrc() : null);

                                // Infer source from URL
                                result.setSource(extractSourceFromUrl(webResult.getUrl()));

                                // Extract price (example logic - replace with actual API field if available)
                                result.setPrice(extractPriceFromDescription(webResult.getDescription()));

                                return result;
                            })
                            .collect(Collectors.toList());
                });
    }

    /**
     * Helper method to extract price from the description.
     */
    private String extractPriceFromDescription(String description) {
        if (description == null) {
            return "Price not available";
        }
        // Simple regex to find prices like $100, €50, etc.
        String pricePattern = "\\$\\d+(\\.\\d{2})?|€\\d+(\\.\\d{2})?";
        return description.replaceAll(".*?(" + pricePattern + ").*", "$1");
    }

    /**
     * Helper method to infer the source from the URL.
     */
    private String extractSourceFromUrl(String url) {
        if (url == null) {
            return "Unknown Source";
        }
        if (url.contains("bestbuy.com")) {
            return "Best Buy";
        } else if (url.contains("amazon.com")) {
            return "Amazon";
        } else if (url.contains("walmart.com")) {
            return "Walmart";
        } else if (url.contains("apple.com")) {
            return "Apple";
        } else {
            return "Unknown Source";
        }
    }

    /**
     * Helper method to clean up the description.
     */
    private String cleanDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            return "Description not available";
        }

        // Remove all HTML tags using regex
        String plainText = description.replaceAll("<[^>]*>", "");

        // Optionally truncate the description to a reasonable length (e.g., 200 characters)
        int maxLength = 200;
        if (plainText.length() > maxLength) {
            plainText = plainText.substring(0, maxLength).trim() + "...";
        }

        return plainText;
    }
}