package com.Art;

import java.util.ArrayList;
import java.util.Collections;

public class Museum {
 public static void main(String[] args) {
     // Create instances of Painting
     Painting painting1 = new Painting("Starry Night", "Vincent van Gogh", "A beautiful night sky.", "Oil");
     Painting painting2 = new Painting("The Persistence of Memory", "Salvador Dali", "A surreal landscape.", "Oil");
     Painting painting3 = new Painting("Mona Lisa", "Leonardo da Vinci", "A portrait of a woman.", "Oil");

     // Create instances of Sculpture
     Sculpture sculpture1 = new Sculpture("David", "Michelangelo", "A marble statue of David.", "Marble");
     Sculpture sculpture2 = new Sculpture("The Thinker", "Auguste Rodin", "A bronze statue of a man thinking.", "Bronze");

     // Store them in an ArrayList called museum
     ArrayList<Art> museum = new ArrayList<>();
     museum.add(painting1);
     museum.add(painting2);
     museum.add(painting3);
     museum.add(sculpture1);
     museum.add(sculpture2);

     // Shuffle the museum collection
     Collections.shuffle(museum);

     // Print information for each artwork
     for (Art art : museum) {
         art.viewArt();
         System.out.println();
     }
 }
}
