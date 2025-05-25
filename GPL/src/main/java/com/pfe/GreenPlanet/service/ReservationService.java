package com.pfe.GreenPlanet.service;

import com.pfe.GreenPlanet.dto.ReservationDto;
import com.pfe.GreenPlanet.model.*;
import com.pfe.GreenPlanet.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserService userService;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository,
                              UserService userService) {
        this.reservationRepository = reservationRepository;
        this.userService = userService;
    }


    public Reservation saveReservation(ReservationDto reservationDto, String username) {
        // Get the managed user entity

        User user = userService.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Convert DTO to entity
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setPlantDescription(reservationDto.getPlantDescription());
        reservation.setQuantity(reservationDto.getQuantity());
        reservation.setFullName(reservationDto.getFullName());
        reservation.setAddress(reservationDto.getAddress());
        reservation.setPhoneNumber(reservationDto.getPhoneNumber());
        reservation.setStatus(ReservationStatus.PENDING);

        return reservationRepository.save(reservation);
    }

    public List<Reservation> findByUserId(Long userId) {
        return reservationRepository.findByUserId(userId);
    }

    public long countPendingReservations() {
        return reservationRepository.countByStatus(ReservationStatus.PENDING);
    }


    public List<Reservation> findRecentReservations(int limit) {
        return reservationRepository.findTop5ByOrderByCreatedAtDesc();
    }
    public Page<Reservation> getAllReservations(Pageable pageable) {
        return reservationRepository.findAll(pageable);
    }

    public Page<Reservation> getReservationsByStatus(ReservationStatus status, Pageable pageable) {
        return reservationRepository.findByStatus(status, pageable);
    }

    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }

    public void updateReservationStatus(Long id, ReservationStatus status) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
        reservation.setStatus(status);
        reservationRepository.save(reservation);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}