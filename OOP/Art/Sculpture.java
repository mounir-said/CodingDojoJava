package com.Art;


public class Sculpture extends Art {
 private String material;
 
 public Sculpture(String title, String author, String description, String material) {
     super(title, author, description);
     this.material = material;
 }

 @Override
 public void viewArt() {
     System.out.println("Title: " + title);
     System.out.println("Author: " + author);
     System.out.println("Description: " + description);
     System.out.println("Material: " + material);
 }
}
