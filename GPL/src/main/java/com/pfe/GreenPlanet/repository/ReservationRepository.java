package com.pfe.GreenPlanet.repository;

import com.pfe.GreenPlanet.model.Reservation;
import com.pfe.GreenPlanet.model.ReservationStatus;
import com.pfe.GreenPlanet.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Page<Reservation> findByStatus(ReservationStatus status, Pageable pageable);
    long countByStatus(ReservationStatus status);
    List<Reservation> findByUserId(Long userId);

    List<Reservation> findTop5ByOrderByCreatedAtDesc();

}