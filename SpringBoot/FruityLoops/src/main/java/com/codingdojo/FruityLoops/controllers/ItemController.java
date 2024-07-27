package com.codingdojo.FruityLoops.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codingdojo.FruityLoops.models.Item;

import java.util.ArrayList;

@Controller
public class ItemController {

    @RequestMapping("/")
    public String index(Model model) {
        ArrayList<Item> fruits = new ArrayList<Item>();
        fruits.add(new Item("Kiwi", 1.5));
        fruits.add(new Item("Mango", 2.0));
        fruits.add(new Item("Goji Berries", 4.0));
        fruits.add(new Item("Guava", 0.75));

        // Add fruits to your view model here
        model.addAttribute("fruits", fruits);

        return "index"; // Ensure this path is correct
    }
}
