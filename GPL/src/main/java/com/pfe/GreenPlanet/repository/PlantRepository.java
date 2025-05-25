package com.pfe.GreenPlanet.repository;


import com.pfe.GreenPlanet.model.Plant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlantRepository extends JpaRepository<Plant, Long> {
    Page<Plant> findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(String search, String search1, Pageable pageable);

    List<Plant> findByCategory(String category);

    List<Plant> findByQuantityGreaterThan(int i);

    Page<Plant> findAllByOrderByQuantityDesc(PageRequest of);

    @Query("SELECT p FROM Plant p ORDER BY p.created_at DESC")
    Page<Plant> findRecentPlants(Pageable pageable);

    @Modifying
    @Query("DELETE FROM Plant p WHERE p.id = :id")
    void deletePlantById(@Param("id") Long id);

    Page<Plant> findByQuantityGreaterThan(int quantity, Pageable pageable);

    // Correct query derivation method
    Page<Plant> findByQuantityGreaterThanAndNameContainingIgnoreCaseOrQuantityGreaterThanAndCategoryContainingIgnoreCase(
            int quantity, String name, int quantity2, String category, Pageable pageable);

    @Query(value = "SELECT * FROM plants WHERE quantity > 0 ORDER BY RAND() LIMIT :count", nativeQuery = true)
    List<Plant> findRandomAvailablePlants(@Param("count") int count);
}