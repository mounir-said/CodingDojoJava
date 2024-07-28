package com.codingdojo.BurgerTracker_1.services;


import com.codingdojo.BurgerTracker_1.models.Burger;
import com.codingdojo.BurgerTracker_1.repositories.BurgerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BurgerService {
 private final BurgerRepository burgerRepository;

 public BurgerService(BurgerRepository burgerRepository) {
     this.burgerRepository = burgerRepository;
 }

 public List<Burger> allBurgers() {
     return burgerRepository.findAll();
 }

 public Burger createBurger(Burger burger) {
     return burgerRepository.save(burger);
 }

 public Burger findBurger(Long id) {
     Optional<Burger> optionalBurger = burgerRepository.findById(id);
     return optionalBurger.orElse(null);
 }
}

