package com.pfe.hypermax.controller;

import com.pfe.hypermax.model.OnlineProduct;
import com.pfe.hypermax.model.SearchRequest;
import com.pfe.hypermax.model.SearchResult;
import com.pfe.hypermax.service.McpService;
import com.pfe.hypermax.service.OnlineProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/search-engine")
public class SearchController {
    private final McpService mcpService;
    private final OnlineProductService onlineProductService;

    public SearchController(McpService mcpService, OnlineProductService onlineProductService) {
        this.mcpService = mcpService;
        this.onlineProductService = onlineProductService;
    }

    @GetMapping("/")
    public String showSearchForm(Model model) throws IOException {
        model.addAttribute("searchRequest", new SearchRequest());
        model.addAttribute("savedResults", mcpService.getSavedResults());
        return "search";
    }

    @PostMapping("/perform")
    public String search(
            @Valid @ModelAttribute("searchRequest") SearchRequest searchRequest,
            BindingResult bindingResult,
            Model model) throws IOException {

        if (bindingResult.hasErrors()) {
            model.addAttribute("savedResults", mcpService.getSavedResults());
            return "search";
        }

        List<SearchResult> results = mcpService.searchAndStore(searchRequest.getQuery());

        results.forEach(result -> {
            OnlineProduct onlineProduct = new OnlineProduct();
            onlineProduct.setTitle(result.getTitle());
            onlineProduct.setUrl(result.getUrl());
            onlineProduct.setDescription(result.getDescription());
            onlineProduct.setImageUrl(result.getImage());
            onlineProduct.setSource(result.getSource());

            try {
                String cleanPrice = result.getPrice().replaceAll("[^0-9.]", "");
                onlineProduct.setPrice(new BigDecimal(cleanPrice));
            } catch (Exception e) {
                onlineProduct.setPrice(BigDecimal.ZERO);
            }

            onlineProductService.saveProduct(onlineProduct);
        });

        model.addAttribute("results", results);
        model.addAttribute("savedResults", mcpService.getSavedResults());
        return "search";
    }
}