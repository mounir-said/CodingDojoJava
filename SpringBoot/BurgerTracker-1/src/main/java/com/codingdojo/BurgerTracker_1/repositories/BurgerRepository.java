package com.codingdojo.BurgerTracker_1.repositories;


import com.codingdojo.BurgerTracker_1.models.Burger;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BurgerRepository extends CrudRepository<Burger, Long> {
 List<Burger> findAll();
}

