package com.pfe.hypermax.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pfe.hypermax.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer> {

	public Cart findByProductIdAndUserId(Integer productId, Integer userId);

	public Integer countByUserId(Integer userId);

	public List<Cart> findByUserId(Integer userId);

}
