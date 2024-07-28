package com.codingdojo.BurgerTracker_1.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name="burgers")
public class Burger {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 @NotNull
 @Size(min = 1, message = "Burger name must not be empty")
 private String name;

 @NotNull
 @Size(min = 1, message = "Restaurant name must not be empty")
 private String restaurant;

 @NotNull
 @Min(value = 1, message = "Rating must be greater than 0")
 @Max(value = 5, message = "Rating must be less than or equal to 5")
 private Integer rating;

 @Size(max = 255, message = "Notes must be less than 255 characters")
 private String notes;

 @Column(updatable = false)
 private Date createdAt;
 private Date updatedAt;

 public Burger() {}

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

public String getRestaurant() {
	return restaurant;
}

public void setRestaurant(String restaurant) {
	this.restaurant = restaurant;
}

public Integer getRating() {
	return rating;
}

public void setRating(Integer rating) {
	this.rating = rating;
}

public String getNotes() {
	return notes;
}

public void setNotes(String notes) {
	this.notes = notes;
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
	return "Burger [id=" + id + ", name=" + name + ", restaurant=" + restaurant + ", rating=" + rating + ", notes="
			+ notes + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
}

 
 
}

