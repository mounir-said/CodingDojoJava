package com.codingdojo.SaveTravels.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.SaveTravels.models.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}

