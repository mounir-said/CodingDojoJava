package com.soa.e_commerce_app.services;

import com.soa.e_commerce_app.models.Order;
import com.soa.e_commerce_app.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    // Get all orders
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Get an order by ID
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    // Create a new order
    public Order createOrder(Order order) {
        if (order.getOrderDate() == null || order.getTotalAmount() < 0 || order.getCustomer() == null) {
            throw new IllegalArgumentException("Invalid order data");
        }
        return orderRepository.save(order);
    }

    // Update an existing order
    public Order updateOrder(Long id, Order order) {
        Optional<Order> existingOrder = orderRepository.findById(id);
        if (existingOrder.isPresent()) {
            Order updatedOrder = existingOrder.get();
            updatedOrder.setOrderDate(order.getOrderDate());
            updatedOrder.setTotalAmount(order.getTotalAmount());
            updatedOrder.setCustomer(order.getCustomer());
            updatedOrder.setStatus(order.getStatus()); // Update status
            return orderRepository.save(updatedOrder);
        } else {
            throw new RuntimeException("Order not found with id: " + id);
        }
    }

    // Delete an order by ID
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Order not found with id: " + id);
        }
        orderRepository.deleteById(id);
    }

    // Simulate payment for an order
    public void payOrder(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            Order existingOrder = order.get();
            // Update order status to "Paid"
            existingOrder.setStatus("Paid");
            orderRepository.save(existingOrder);
        } else {
            throw new RuntimeException("Order not found with id: " + id);
        }
    }
}