package com.codingdojo.OmikujiForm;


import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OmikujiController {

    @GetMapping("/omikuji")
    public String omikujiForm() {
        return "omikujiForm";
    }

    @PostMapping("/omikuji")
    public String processForm(@RequestParam String name, @RequestParam String message, HttpSession session) {
        session.setAttribute("name", name);
        session.setAttribute("message", message);
        return "redirect:/omikuji/show";
    }

    @GetMapping("/omikuji/show")
    public String showFortune(HttpSession session, Model model) {
        String name = (String) session.getAttribute("name");
        String message = (String) session.getAttribute("message");
        model.addAttribute("name", name);
        model.addAttribute("message", message);
        return "omikujiShow";
    }
}
