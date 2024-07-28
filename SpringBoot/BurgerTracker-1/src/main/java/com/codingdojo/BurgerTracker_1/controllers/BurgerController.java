package com.codingdojo.BurgerTracker_1.controllers;


import com.codingdojo.BurgerTracker_1.models.Burger;
import com.codingdojo.BurgerTracker_1.services.BurgerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BurgerController {
 @Autowired
 private BurgerService burgerService;

 @GetMapping("/burgers")
 public String index(Model model, @ModelAttribute("burger") Burger burger) {
     model.addAttribute("burgers", burgerService.allBurgers());
     return "index";
 }

 @PostMapping("/burgers")
 public String create(@Valid @ModelAttribute("burger") Burger burger, BindingResult result, Model model) {
     if (result.hasErrors()) {
         model.addAttribute("burgers", burgerService.allBurgers());
         return "index.jsp";
     } else {
         burgerService.createBurger(burger);
         return "redirect:/burgers";
     }
 }
}

