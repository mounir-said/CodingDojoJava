package com.pfe.GreenPlanet.dto;

import jakarta.validation.constraints.*;

public class PlantDto {

        @NotBlank(message = "Name is required")
        @Size(max = 255, message = "Name must not exceed 255 characters")
        private String name;

        @NotBlank(message = "Category is required")
        @Size(max = 255, message = "Category must not exceed 255 characters")
        private String category;

        @NotBlank(message = "Description is required")
        private String description;

        @Positive(message = "Price must be positive")
        private double price;

        @Min(value = 0, message = "Quantity cannot be negative")
        private int quantity;

        // Constructors
        public PlantDto() {
        }

        public PlantDto(String name, String category, String description,
                        double price, int quantity) {
                this.name = name;
                this.category = category;
                this.description = description;
                this.price = price;
                this.quantity = quantity;
        }



        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getCategory() {
                return category;
        }

        public void setCategory(String category) {
                this.category = category;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public double getPrice() {
                return price;
        }

        public void setPrice(double price) {
                this.price = price;
        }

        public int getQuantity() {
                return quantity;
        }

        public void setQuantity(int quantity) {
                this.quantity = quantity;
        }

        @Override
        public String toString() {
                return "PlantDto{" +
                        ", name='" + name + '\'' +
                        ", category='" + category + '\'' +
                        ", description='" + description + '\'' +
                        ", price=" + price +
                        ", quantity=" + quantity +
                        '}';
        }
}