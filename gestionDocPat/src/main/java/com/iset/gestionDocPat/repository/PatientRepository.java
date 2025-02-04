package com.iset.gestionDocPat.repository;

import com.iset.gestionDocPat.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findByEmail(String email);

    Patient findByName(String patientName);

}