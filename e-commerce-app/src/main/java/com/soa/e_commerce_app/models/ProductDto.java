package com.soa.e_commerce_app.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

/**
 * Data Transfer Object for Product.
 * Used to transfer product data, including an image file, between layers.
 */
public class ProductDto {

    @NotEmpty(message = "Name is required.")
    @Size(max = 255, message = "Name must not exceed 255 characters.")
    private String name;

    @NotEmpty(message = "Brand is required.")
    @Size(max = 255, message = "Brand must not exceed 255 characters.")
    private String brand;

    @NotEmpty(message = "Category is required.")
    @Size(max = 255, message = "Category must not exceed 255 characters.")
    private String category;
    @Min(0)
    private double price;

    @NotEmpty(message = "Description is required.")
    private String description;

    private MultipartFile imageFile;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", imageFile=" + (imageFile != null ? imageFile.getOriginalFilename() : "null") +
                '}';
    }
}