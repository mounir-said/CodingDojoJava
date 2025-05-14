package com.pfe.hypermax.service;

import com.pfe.hypermax.model.OnlineProduct;
import com.pfe.hypermax.repository.OnlineProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Page<OnlineProduct> findAll(Pageable pageable) {
        return onlineProductRepository.findAll(pageable);
    }

    public void saveProducts(List<OnlineProduct> products) {
        onlineProductRepository.saveAll(products); // Optimized with saveAll
    }

    public Optional<OnlineProduct> findById(Long id) {
        return onlineProductRepository.findById(id);
    }

    public List<OnlineProduct> findAllProducts() {
        return onlineProductRepository.findAllByOrderByIdDesc();
    }

    public Page<OnlineProduct> findAllProducts(Pageable pageable) {
        return onlineProductRepository.findAllByOrderByIdDesc(pageable);
    }

    public List<OnlineProduct> findByQuery(String query) {
        return onlineProductRepository.findByTitleContainingIgnoreCase(query);
    }

    public Page<OnlineProduct> findByTitleContainingIgnoreCase(String query, Pageable pageable) {
        return onlineProductRepository.findByTitleContainingIgnoreCase(query, pageable);
    }

    public OnlineProduct findByUrl(String url) {
        return onlineProductRepository.findByUrl(url);
    }

    public void deleteProduct(Long id) {
        onlineProductRepository.deleteById(id);
    }

    public long countAllProducts() {
        return onlineProductRepository.count();
    }

    public boolean existsByUrl(String url) {
        return onlineProductRepository.existsByUrl(url);
    }

    public List<String> findDistinctSources() {
        return onlineProductRepository.findDistinctSources();
    }

    public void deleteById(Long id) {
        onlineProductRepository.deleteById(id);
    }

    // Fixed implementations for missing methods
    public long count() {
        return onlineProductRepository.count();
    }

    public List<OnlineProduct> findTop5ByOrderByTimestampDesc() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("timestamp").descending());
        return onlineProductRepository.findAll(pageable).getContent();
    }
}