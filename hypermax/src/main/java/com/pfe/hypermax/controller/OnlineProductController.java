package com.pfe.hypermax.controller;

import com.pfe.hypermax.model.OnlineProduct;
import com.pfe.hypermax.repository.OnlineProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/online-products")
public class OnlineProductController {
    private final OnlineProductRepository onlineProductRepository;

    public OnlineProductController(OnlineProductRepository onlineProductRepository) {
        this.onlineProductRepository = onlineProductRepository;
    }

    @GetMapping("/")
    public String getAllProducts(Model model) {
        model.addAttribute("products", onlineProductRepository.findAll());
        return "onlineProducts"; // Make sure this template exists
    }

    @GetMapping("/search")
    public String searchProducts(
            @RequestParam String query,
            Model model) {
        model.addAttribute("products",
                onlineProductRepository.findByTitleContainingIgnoreCase(query));
        return "onlineProducts";
    }
}