package com.codingdojo.SaveTravels.models;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Expense name must not be empty")
    @Size(min = 1, message = "Expense name must not be empty")
    private String name;

    @NotNull(message = "Vendor name must not be empty")
    @Size(min = 1, message = "Vendor name must not be empty")
    private String vendor;

    @NotNull(message = "Amount must not be empty")
    @Positive(message = "Amount must be greater than 0")
    private Double amount;

    @Size(max = 255, message = "Description must be less than 255 characters")
    private String description;

    @Column(updatable = false)
    private Date createdAt;

    private Date updatedAt;

    public Expense() {}

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "Expense [id=" + id + ", name=" + name + ", vendor=" + vendor + ", amount=" + amount + ", description="
				+ description + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", getId()=" + getId()
				+ ", getName()=" + getName() + ", getVendor()=" + getVendor() + ", getAmount()=" + getAmount()
				+ ", getDescription()=" + getDescription() + ", getCreatedAt()=" + getCreatedAt() + ", getUpdatedAt()="
				+ getUpdatedAt() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

    
}


