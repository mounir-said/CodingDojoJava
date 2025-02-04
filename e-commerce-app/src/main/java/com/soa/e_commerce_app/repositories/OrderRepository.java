package com.soa.e_commerce_app.repositories;



import com.soa.e_commerce_app.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
