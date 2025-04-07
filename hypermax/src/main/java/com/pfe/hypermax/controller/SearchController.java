package com.pfe.hypermax.controller;
;

import com.pfe.hypermax.model.SearchRequest;
import com.pfe.hypermax.model.SearchResult;
import com.pfe.hypermax.service.McpService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.io.IOException;
import java.util.List;

@Controller
public class SearchController {
    private final McpService mcpService;

    public SearchController(McpService mcpService) {
        this.mcpService = mcpService;
    }

    @GetMapping("/")
    public String showSearchForm(Model model) throws IOException {
        model.addAttribute("searchRequest", new SearchRequest());
        model.addAttribute("savedResults", mcpService.getSavedResults());
        return "search";
    }

    @PostMapping("/search")
    public String search(@Valid @ModelAttribute SearchRequest searchRequest,
                         BindingResult bindingResult,
                         Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("savedResults", mcpService.getSavedResults());
            return "search";
        }

        List<SearchResult> results = mcpService.searchAndStore(searchRequest.getQuery());
        model.addAttribute("results", results);
        model.addAttribute("savedResults", mcpService.getSavedResults());
        return "search";
    }
}