package com.codingdojo.Counter;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class CounterController {

    @GetMapping("/")
    public String home(HttpSession session) {
        Integer count = (Integer) session.getAttribute("count");
        if (count == null) {
            count = 0;
        }
        count++;
        session.setAttribute("count", count);
        return "home.jsp";
    }

    @GetMapping("/counter")
    public String counter(HttpSession session, Model model) {
        Integer count = (Integer) session.getAttribute("count");
        if (count == null) {
            count = 0;
        }
        model.addAttribute("count", count);
        return "counter.jsp";
    }

    @GetMapping("/increment-by-2")
    public String incrementBy2(HttpSession session) {
        Integer count = (Integer) session.getAttribute("count");
        if (count == null) {
            count = 0;
        }
        count += 2;
        session.setAttribute("count", count);
        return "redirect:/counter";
    }

    @GetMapping("/reset")
    public String reset(HttpSession session) {
        session.setAttribute("count", 0);
        return "redirect:/counter";
    }
}