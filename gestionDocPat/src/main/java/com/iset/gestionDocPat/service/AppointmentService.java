package com.iset.gestionDocPat.service;

import com.iset.gestionDocPat.model.Appointment;
import com.iset.gestionDocPat.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment saveAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }
}