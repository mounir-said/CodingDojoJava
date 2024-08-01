package com.codingdojo.BurgerTracker_1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.codingdojo.BurgerTracker_1.models.Burger;
import com.codingdojo.BurgerTracker_1.services.BurgerService;

import jakarta.validation.Valid;

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
            return "index";
        } else {
            burgerService.createBurger(burger);
            return "redirect:/burgers";
        }
    }

    @GetMapping("/burgers/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Burger burger = burgerService.findById(id);
        if (burger != null) {
            model.addAttribute("burger", burger);
            return "update";
        }
        return "redirect:/burgers";
    }

    @PostMapping("/burgers/update")
    public String updateBurger(@Valid @ModelAttribute("burger") Burger burger, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("burger", burger);
            return "update";
        }
        burgerService.updateBurger(burger);
        return "redirect:/burgers";
    }
}

