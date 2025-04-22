package com.pfe.hypermax.service;


import com.pfe.hypermax.model.Product;
import com.pfe.hypermax.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public List<Product> findByQuery(String query) {
        return productRepository.findByTitleContainingIgnoreCase(query);
    }
}