package com.pfe.GreenPlanet.service;

import com.pfe.GreenPlanet.model.Plant;
import com.pfe.GreenPlanet.dto.PlantDto;
import com.pfe.GreenPlanet.repository.PlantRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PlantService {

    private final PlantRepository plantRepository;

    @Autowired
    public PlantService(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }

    public Plant savePlant(Plant plant) {
        if (plant.getCreated_at() == null) {
            plant.setCreated_at(LocalDateTime.now());
        }
        plant.setUpdated_at(LocalDateTime.now());
        return plantRepository.save(plant);
    }

    public Page<Plant> getAllPlantsPagination(int page, int size) {
        return plantRepository.findAll(PageRequest.of(page, size));
    }

    @Transactional
    public void deletePlant(Long id) {
        plantRepository.deleteById(id);
    }

    public long countAllPlants() {
        return plantRepository.count();
    }

    public Optional<Plant> getPlantById(Long id) {
        return plantRepository.findById(id);
    }

    public List<Plant> findRecentPlants(int limit) {
        return plantRepository.findRecentPlants(PageRequest.of(0, limit)).getContent();
    }

    @Transactional
    public Plant updatePlant(Plant plant) {
        plant.setUpdated_at(LocalDateTime.now());
        return plantRepository.save(plant);
    }


    public Page<Plant> getAllAvailablePlantsPagination(int page, int size) {
        page = Math.max(page, 0);
        size = size <= 0 ? 10 : size;
        return plantRepository.findByQuantityGreaterThan(0, PageRequest.of(page, size));
    }

    public Page<Plant> searchPlantsPagination(int page, int size, String search) {
        page = Math.max(page, 0);
        size = size <= 0 ? 10 : size;
        Pageable pageable = PageRequest.of(page, size);

        if (search == null || search.trim().isEmpty()) {
            return getAllAvailablePlantsPagination(page, size);
        }

        return plantRepository.findByQuantityGreaterThanAndNameContainingIgnoreCaseOrQuantityGreaterThanAndCategoryContainingIgnoreCase(
                0, search, 0, search, pageable);
    }

    public List<Plant> getAllAvailablePlants() {
        return plantRepository.findByQuantityGreaterThan(0);
    }


}