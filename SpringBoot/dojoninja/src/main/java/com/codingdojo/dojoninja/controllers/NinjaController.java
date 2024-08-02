package com.codingdojo.dojoninja.controllers;

import com.codingdojo.dojoninja.models.Ninja;
import com.codingdojo.dojoninja.services.DojoService;
import com.codingdojo.dojoninja.services.NinjaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NinjaController {
    @Autowired
    private NinjaService ninjaService;

    @Autowired
    private DojoService dojoService;

    @GetMapping("/ninjas/new")
    public String newNinja(@ModelAttribute("ninja") Ninja ninja, Model model) {
        model.addAttribute("dojos", dojoService.allDojos());
        return "newNinja.jsp";
    }

    @PostMapping("/ninjas")
    public String createNinja(@ModelAttribute("ninja") Ninja ninja) {
        ninjaService.createNinja(ninja);
        return "redirect:/dojos";
    }
}
