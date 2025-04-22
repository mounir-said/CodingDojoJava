package com.pfe.hypermax.controller;


import com.pfe.hypermax.model.Product;
import com.pfe.hypermax.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class ProductController {
    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/products")
    public String getAllProducts(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/products/search")
    public String searchProducts(
            @RequestParam String query,
            Model model) {
        List<Product> products = productRepository.findByTitleContainingIgnoreCase(query);
        model.addAttribute("products", products);
        return "products";
    }
}