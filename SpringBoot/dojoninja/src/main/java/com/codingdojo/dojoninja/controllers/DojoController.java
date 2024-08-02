package com.codingdojo.dojoninja.controllers;

import com.codingdojo.dojoninja.models.Dojo;
import com.codingdojo.dojoninja.services.DojoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DojoController {
    @Autowired
    private DojoService dojoService;

    @GetMapping("/dojos/new")
    public String newDojo(@ModelAttribute("dojo") Dojo dojo) {
        return "newDojo.jsp";
    }

    @PostMapping("/dojos")
    public String createDojo(@ModelAttribute("dojo") Dojo dojo) {
        dojoService.createDojo(dojo);
        return "redirect:/dojos";
    }

    @GetMapping("/dojos/{id}")
    public String showDojo(@PathVariable("id") Long id, Model model) {
        Dojo dojo = dojoService.findDojo(id);
        model.addAttribute("dojo", dojo);
        return "showDojo.jsp";
    }

    @GetMapping("/dojos")
    public String allDojos(Model model) {
        model.addAttribute("dojos", dojoService.allDojos());
        return "dojos.jsp";
    }
}

