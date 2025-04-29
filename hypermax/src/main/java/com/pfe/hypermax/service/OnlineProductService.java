package com.pfe.hypermax.service;

import com.pfe.hypermax.model.OnlineProduct;
import com.pfe.hypermax.repository.OnlineProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OnlineProductService {
    private final OnlineProductRepository onlineProductRepository;

    public OnlineProductService(OnlineProductRepository onlineProductRepository) {
        this.onlineProductRepository = onlineProductRepository;
    }

    public void saveProducts(List<OnlineProduct> products) {
        for (OnlineProduct product : products) {
            onlineProductRepository.save(product); // Corrected: use the instance
        }
    }


    // Find by ID
    public Optional<OnlineProduct> findById(Long id) {
        return onlineProductRepository.findById(id);
    }

    // Find all products (sorted by newest first)
    public List<OnlineProduct> findAllProducts() {
        return onlineProductRepository.findAllByOrderByIdDesc();
    }

    // Find all products with pagination (sorted by newest first)
    public Page<OnlineProduct> findAllProducts(Pageable pageable) {
        return onlineProductRepository.findAllByOrderByIdDesc(pageable);
    }

    // Find by title containing query (case insensitive)
    public List<OnlineProduct> findByQuery(String query) {
        return onlineProductRepository.findByTitleContainingIgnoreCase(query);
    }

    // Find by title containing query with pagination
    public Page<OnlineProduct> findByQuery(String query, Pageable pageable) {
        return onlineProductRepository.findByTitleContainingIgnoreCase(query, pageable);
    }

    // Find by URL
    public OnlineProduct findByUrl(String url) {
        return onlineProductRepository.findByUrl(url);
    }

    // Delete a product by ID
    public void deleteProduct(Long id) {
        onlineProductRepository.deleteById(id);
    }

    // Count all products
    public long countAllProducts() {
        return onlineProductRepository.count();
    }

    // Check if product exists by URL
    public boolean existsByUrl(String url) {
        return onlineProductRepository.existsByUrl(url);
    }

    // Get distinct sources
    public List<String> findDistinctSources() {
        return onlineProductRepository.findDistinctSources();
    }
}