package com.pfe.hypermax.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SearchRequest {
    @NotBlank(message = "Search query cannot be empty")
    private String query;
}