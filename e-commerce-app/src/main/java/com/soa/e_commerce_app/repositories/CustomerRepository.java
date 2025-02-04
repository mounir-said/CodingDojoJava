package com.soa.e_commerce_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.soa.e_commerce_app.models.Customer;
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByEmail(String email);
}