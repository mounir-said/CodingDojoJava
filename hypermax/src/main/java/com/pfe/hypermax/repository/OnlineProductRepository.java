package com.pfe.hypermax.repository;

import com.pfe.hypermax.model.OnlineProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OnlineProductRepository extends JpaRepository<OnlineProduct, Long> {

    // Find methods with sorting
    List<OnlineProduct> findAllByOrderByIdDesc();
    Page<OnlineProduct> findAllByOrderByIdDesc(Pageable pageable);

    // Search methods
    List<OnlineProduct> findByTitleContainingIgnoreCase(String query);
    Page<OnlineProduct> findByTitleContainingIgnoreCase(String query, Pageable pageable);

    // URL-based methods
    OnlineProduct findByUrl(String url);
    boolean existsByUrl(String url);

    // Distinct sources
    @Query("SELECT DISTINCT o.source FROM OnlineProduct o")
    List<String> findDistinctSources();
}