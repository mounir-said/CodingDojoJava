package com.iset.gestionDocPat.service;

import com.iset.gestionDocPat.model.Patient;
import com.iset.gestionDocPat.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public Patient getPatientByEmail(String email) {
        return patientRepository.findByEmail(email);
    }

    public Patient getPatientByName(String patientName) {
        return patientRepository.findByName(patientName);
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }
}