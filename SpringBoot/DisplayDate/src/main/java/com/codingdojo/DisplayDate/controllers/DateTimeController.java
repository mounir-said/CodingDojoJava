package com.codingdojo.DisplayDate.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class DateTimeController {

    @GetMapping("/date")
    public String date(Model model) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, 'the' dd 'of' MMMM, yyyy");
        String currentDate = dateFormat.format(new Date());
        model.addAttribute("currentDate", currentDate);
        return "date";
    }

    @GetMapping("/time")
    public String time(Model model) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        String currentTime = timeFormat.format(new Date());
        model.addAttribute("currentTime", currentTime);
        return "time";
    }

    @GetMapping("/")
    public String dashboard() {
        return "dashboard";
    }
}
