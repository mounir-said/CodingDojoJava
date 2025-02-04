package com.iset.gestionDocPat.controller;

import com.iset.gestionDocPat.model.Appointment;
import com.iset.gestionDocPat.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/add")
    public String showAddAppointmentForm(Model model) {
        model.addAttribute("appointment", new Appointment());
        return "appointments/addAppointment";
    }

    @PostMapping("/save")
    public String saveAppointment(@ModelAttribute Appointment appointment) {
        // Save the appointment using the service
        appointmentService.saveAppointment(appointment);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteAppointment(@PathVariable Long id) {
        // Delete the appointment using the service
        appointmentService.deleteAppointment(id);
        return "redirect:/";
    }
}