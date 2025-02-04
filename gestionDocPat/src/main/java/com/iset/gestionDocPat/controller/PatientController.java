package com.iset.gestionDocPat.controller;

import com.iset.gestionDocPat.model.Patient;
import com.iset.gestionDocPat.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/add")
    public String showAddPatientForm(Model model) {
        // Pass an empty Patient object to the form
        model.addAttribute("patient", new Patient());
        return "patients/addPatient";
    }

    @PostMapping("/save")
    public String savePatient(@ModelAttribute Patient patient) {
        // Save the patient using the service
        patientService.savePatient(patient);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return "redirect:/";
    }
}