package com.pfe.GreenPlanet.dto;

import jakarta.validation.constraints.*;

public class ReservationDto {

    @NotBlank(message = "Plant description is required")
    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String plantDescription;

    @Min(value = 1, message = "Quantity must be at least 1")
    @Max(value = 100, message = "Quantity cannot exceed 100")
    private int quantity;

    @NotBlank(message = "Full name is required")
    @Size(max = 100, message = "Full name must not exceed 100 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Full name should contain only letters and spaces")
    private String fullName;

    @NotBlank(message = "Address is required")
    @Size(max = 255, message = "Address must not exceed 255 characters")
    private String address;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s./0-9]*$",
            message = "Invalid phone number format")
    private String phoneNumber;

    // Additional fields for better UX
    private Long plantId;  // If reserving a specific plant
    private String specialInstructions;

    // Constructors
    public ReservationDto() {
    }

    public ReservationDto(String plantDescription, int quantity, String fullName,
                          String address, String phoneNumber) {
        this.plantDescription = plantDescription;
        this.quantity = quantity;
        this.fullName = fullName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    // Getters and Setters
    public String getPlantDescription() {
        return plantDescription;
    }

    public void setPlantDescription(String plantDescription) {
        this.plantDescription = plantDescription;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getPlantId() {
        return plantId;
    }

    public void setPlantId(Long plantId) {
        this.plantId = plantId;
    }

    public String getSpecialInstructions() {
        return specialInstructions;
    }

    public void setSpecialInstructions(String specialInstructions) {
        this.specialInstructions = specialInstructions;
    }

    @Override
    public String toString() {
        return "ReservationDto{" +
                "plantDescription='" + plantDescription + '\'' +
                ", quantity=" + quantity +
                ", fullName='" + fullName + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", plantId=" + plantId +
                ", specialInstructions='" + specialInstructions + '\'' +
                '}';
    }

    // Builder pattern for fluent creation
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String plantDescription;
        private int quantity;
        private String fullName;
        private String address;
        private String phoneNumber;
        private Long plantId;
        private String specialInstructions;

        public Builder plantDescription(String plantDescription) {
            this.plantDescription = plantDescription;
            return this;
        }

        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder plantId(Long plantId) {
            this.plantId = plantId;
            return this;
        }

        public Builder specialInstructions(String specialInstructions) {
            this.specialInstructions = specialInstructions;
            return this;
        }

        public ReservationDto build() {
            ReservationDto dto = new ReservationDto();
            dto.setPlantDescription(plantDescription);
            dto.setQuantity(quantity);
            dto.setFullName(fullName);
            dto.setAddress(address);
            dto.setPhoneNumber(phoneNumber);
            dto.setPlantId(plantId);
            dto.setSpecialInstructions(specialInstructions);
            return dto;
        }
    }
}