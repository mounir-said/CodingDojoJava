package com.soa.e_commerce_app.controllers;

import com.soa.e_commerce_app.models.Order;
import com.soa.e_commerce_app.services.CustomerService;
import com.soa.e_commerce_app.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.WebDataBinder;
import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    // Get all orders
    @GetMapping
    public String getAllOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "orders/list";
    }

    // Show add order form
    // Add a custom date binder
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                try {
                    if (text != null && !text.isEmpty()) {
                        setValue(new SimpleDateFormat("yyyy-MM-dd").parse(text));
                    } else {
                        setValue(null); // Handle null or empty input
                    }
                } catch (ParseException e) {
                    throw new IllegalArgumentException("Invalid date format. Please use yyyy-MM-dd");
                }
            }

            @Override
            public String getAsText() {
                Date date = (Date) getValue();
                if (date != null) {
                    return new SimpleDateFormat("yyyy-MM-dd").format(date);
                } else {
                    return ""; // Return empty string for null dates
                }
            }
        });
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("order", new Order());
        model.addAttribute("customers", customerService.getAllCustomers());
        return "orders/add";
    }

    @PostMapping
    public String addOrder(@ModelAttribute Order order) {
        orderService.createOrder(order);
        return "redirect:/orders";
    }

    // Show edit order form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderById(id);
        if (order == null) {
            throw new RuntimeException("Order not found with id: " + id);
        }
        model.addAttribute("order", order);
        model.addAttribute("customers", customerService.getAllCustomers());
        return "orders/edit";
    }

    // Update an existing order
    @PutMapping("/{id}")
    public String updateOrder(@PathVariable Long id, @ModelAttribute Order order) {
        orderService.updateOrder(id, order);
        return "redirect:/orders";
    }

    // Delete an order
    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "redirect:/orders";
    }

    // Pay for an order
    @GetMapping("/{id}/pay")
    public String showPaymentForm(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderById(id);
        if (order == null) {
            throw new RuntimeException("Order not found with id: " + id);
        }
        model.addAttribute("order", order);
        return "orders/payment";
    }

    // Process payment
    @PostMapping("/{id}/process-payment")
    public String processPayment(@PathVariable Long id,
                                 @RequestParam String cardNumber,
                                 @RequestParam String expirationDate,
                                 @RequestParam String cvv) {
        // Simulate payment processing
        System.out.println("Processing payment for order: " + id);
        System.out.println("Card Number: " + cardNumber);
        System.out.println("Expiration Date: " + expirationDate);
        System.out.println("CVV: " + cvv);

        // Update order status or perform other payment-related logic
        orderService.payOrder(id);

        return "redirect:/orders";
    }

    // View order details
    @GetMapping("/view/{id}")
    public String viewOrder(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderById(id);
        if (order == null) {
            throw new RuntimeException("Order not found with id: " + id);
        }
        model.addAttribute("order", order);
        return "orders/view";
    }
}