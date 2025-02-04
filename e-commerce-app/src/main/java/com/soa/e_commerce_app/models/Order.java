package com.soa.e_commerce_app.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.util.Date;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Order date is required")
    @PastOrPresent(message = "Order date must be in the past or present")
    private Date orderDate;

    @NotNull(message = "Total amount is required")
    @Min(value = 0, message = "Total amount must be greater than or equal to 0")
    private double totalAmount;

    @NotNull(message = "Customer is required")
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    private String status; // Add this field

    // Default constructor
    public Order() {
    }

    // Constructor with all fields except ID
    public Order(Date orderDate, double totalAmount, Customer customer) {
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.customer = customer;
        this.status = "Pending"; // Default status
    }

    // Constructor with all fields including ID
    public Order(Long id, Date orderDate, double totalAmount, Customer customer) {
        this.id = id;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.customer = customer;
        this.status = "Pending"; // Default status
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Override toString() for debugging
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", totalAmount=" + totalAmount +
                ", customer=" + customer +
                ", status='" + status + '\'' +
                '}';
    }
}