package com.soa.e_commerce_app.repositories;

import com.soa.e_commerce_app.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Product, Integer> {

}
