package com.iset.gestionDocPat.service;

import com.iset.gestionDocPat.model.Doctor;
import com.iset.gestionDocPat.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    // Add this method if you need to find a doctor by email
    public Doctor getDoctorByEmail(String email) {
        return doctorRepository.findByEmail(email);
    }

    public Doctor getDoctorByName(String doctorName) {
        return doctorRepository.findByName(doctorName);
    }

    public Optional<Doctor> getDoctorById(Long id) {
        return doctorRepository.findById(id);
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }
}