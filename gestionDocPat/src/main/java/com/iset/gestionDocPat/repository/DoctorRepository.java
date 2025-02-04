package com.iset.gestionDocPat.repository;

import com.iset.gestionDocPat.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    // Add this method if you need to find a doctor by email
    Doctor findByEmail(String email);

    Doctor findByName(String doctorName);
}