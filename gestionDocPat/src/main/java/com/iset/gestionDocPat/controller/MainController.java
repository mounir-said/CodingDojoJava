package com.iset.gestionDocPat.controller;

import com.iset.gestionDocPat.model.Appointment;
import com.iset.gestionDocPat.model.Doctor;
import com.iset.gestionDocPat.model.Patient;
import com.iset.gestionDocPat.service.AppointmentService;
import com.iset.gestionDocPat.service.DoctorService;
import com.iset.gestionDocPat.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/")
    public String home(Model model) {
        // Fetch all doctors, patients, and appointments
        model.addAttribute("doctors", doctorService.getAllDoctors());
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("appointments", appointmentService.getAllAppointments());

        return "home";
    }
}