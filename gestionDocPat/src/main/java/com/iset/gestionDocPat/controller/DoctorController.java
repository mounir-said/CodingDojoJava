package com.iset.gestionDocPat.controller;

import com.iset.gestionDocPat.model.Doctor;
import com.iset.gestionDocPat.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/doctors")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    @GetMapping("/add")
    public String showAddDoctorForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "doctors/addDoctor";
    }

    @PostMapping("/save")
    public String saveDoctor(@ModelAttribute Doctor doctor) {
        doctorService.saveDoctor(doctor);
        return "redirect:/";
    }

    // Add this method if you need to find a doctor by email
    @GetMapping("/email/{email}")
    public String getDoctorByEmail(@PathVariable String email, Model model) {
        Doctor doctor = doctorService.getDoctorByEmail(email);
        model.addAttribute("doctor", doctor);
        return "doctors/doctorDetails";
    }

    // Delete a doctor
    @GetMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return "redirect:/";
    }
}