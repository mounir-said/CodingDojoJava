package com.pfe.hypermax.controller;

import com.pfe.hypermax.model.Product;
import com.pfe.hypermax.model.SearchRequest;
import com.pfe.hypermax.model.SearchResult;
import com.pfe.hypermax.service.McpService;
import com.pfe.hypermax.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class SearchController {
    private final McpService mcpService;
    private final ProductService productService;

    public SearchController(McpService mcpService, ProductService productService) {
        this.mcpService = mcpService;
        this.productService = productService;
    }

    @GetMapping("/")
    public String showSearchForm(Model model) throws IOException {
        model.addAttribute("searchRequest", new SearchRequest());
        model.addAttribute("savedResults", mcpService.getSavedResults());
        return "search";
    }

    @PostMapping("/search")
    public String search(
            @Valid @ModelAttribute("searchRequest") SearchRequest searchRequest,
            BindingResult bindingResult,
            Model model) throws IOException {

        if (bindingResult.hasErrors()) {
            model.addAttribute("savedResults", mcpService.getSavedResults());
            return "search";
        }

        // 1. Get results from Brave API
        List<SearchResult> results = mcpService.searchAndStore(searchRequest.getQuery());

        // 2. Save to MySQL
        results.forEach(result -> {
            Product product = new Product();
            product.setTitle(result.getTitle());
            product.setUrl(result.getUrl());
            product.setDescription(result.getDescription());
            product.setImageUrl(result.getImage());
            product.setSource(result.getSource());

            // Convert String price to BigDecimal
            try {
                String cleanPrice = result.getPrice().replaceAll("[^0-9.]", "");
                product.setPrice(new BigDecimal(cleanPrice));
            } catch (Exception e) {
                product.setPrice(BigDecimal.ZERO);
            }

            productService.saveProduct(product);
        });

        model.addAttribute("results", results);
        model.addAttribute("savedResults", mcpService.getSavedResults());
        return "search";
    }
}