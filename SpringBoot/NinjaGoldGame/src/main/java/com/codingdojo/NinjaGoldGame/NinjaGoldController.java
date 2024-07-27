package com.codingdojo.NinjaGoldGame;


import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class NinjaGoldController {

    private final Random random = new Random();

    @RequestMapping("/")
    public String index(HttpSession session, Model model) {
        Integer gold = (Integer) session.getAttribute("gold");
        if (gold == null) {
            gold = 0;
            session.setAttribute("gold", gold);
        }

        List<String> activities = (List<String>) session.getAttribute("activities");
        if (activities == null) {
            activities = new ArrayList<>();
            session.setAttribute("activities", activities);
        }

        model.addAttribute("gold", gold);
        model.addAttribute("activities", activities);
        return "index";
    }

    @PostMapping("/process")
    public String process(HttpSession session, RedirectAttributes redirectAttributes, String place) {
        Integer gold = (Integer) session.getAttribute("gold");
        List<String> activities = (List<String>) session.getAttribute("activities");

        int earnedGold = 0;
        String message = "";

        switch (place) {
            case "farm":
                earnedGold = random.nextInt(11) + 10; // 10-20
                message = "You entered a farm and earned " + earnedGold + " gold!";
                break;
            case "cave":
                earnedGold = random.nextInt(6) + 5; // 5-10
                message = "You explored a cave and earned " + earnedGold + " gold!";
                break;
            case "house":
                earnedGold = random.nextInt(4) + 2; // 2-5
                message = "You visited a house and earned " + earnedGold + " gold!";
                break;
            case "quest":
                earnedGold = random.nextInt(101) - 50; // -50 to 50
                message = "You went on a quest and earned " + earnedGold + " gold!";
                break;
            case "spa":
                earnedGold = -(random.nextInt(16) + 5); // -5 to -20
                message = "You visited a spa and lost " + (-earnedGold) + " gold!";
                break;
        }

        gold += earnedGold;
        session.setAttribute("gold", gold);

        if (gold < 0) {
            return "redirect:/debtors-prison";
        }

        LocalDateTime now = LocalDateTime.now();
        activities.add(now + ": " + message);
        session.setAttribute("activities", activities);

        return "redirect:/";
    }

    @PostMapping("/reset")
    public String reset(HttpSession session) {
        session.setAttribute("gold", 0);
        session.setAttribute("activities", new ArrayList<>());
        return "redirect:/";
    }

    @RequestMapping("/debtors-prison")
    public String debtorsPrison() {
        return "debtorsPrison";
    }
}
