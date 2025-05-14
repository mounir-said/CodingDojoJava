package com.pfe.GreenPlanet.controller;

import com.pfe.GreenPlanet.dto.ReservationDto;
import com.pfe.GreenPlanet.model.Plant;
import com.pfe.GreenPlanet.model.Reservation;
import com.pfe.GreenPlanet.model.ReservationStatus;
import com.pfe.GreenPlanet.model.User;
import com.pfe.GreenPlanet.repository.PlantRepository;
import com.pfe.GreenPlanet.service.ReservationService;
import com.pfe.GreenPlanet.service.UserService;
import com.pfe.GreenPlanet.service.PlantService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasRole('USER')")
public class UserController {

    private final UserService userService;
    private final PlantService plantService;
    private final ReservationService reservationService;
    private final PlantRepository plantRepository;


    public UserController(UserService userService,
                          PlantService plantService,
                          ReservationService reservationService,
                          PlantRepository plantRepository) {
        this.userService = userService;
        this.plantService = plantService;
        this.reservationService = reservationService;
        this.plantRepository = plantRepository;
    }

    // Helper method to get authenticated user
    private User getAuthenticatedUser(UserDetails userDetails) {
        return userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found with username: " + userDetails.getUsername()));
    }

    @GetMapping("/dashboard")
    public String userDashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String username = userDetails.getUsername();
        User user = userService.findByEmail(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with username: " + username));

        List<Plant> featuredPlants = plantRepository.findRandomAvailablePlants(6);

        model.addAttribute("currentUser", user);
        model.addAttribute("plants", featuredPlants);
        model.addAttribute("reservationDto", new ReservationDto()); // Add this line

        return "user/dashboard";
    }

    // Update this method to show the reservation form
    @GetMapping("/reserve")
    public String showReservationForm(@AuthenticationPrincipal UserDetails userDetails,
                                      Model model) {
        User user = getAuthenticatedUser(userDetails);
        List<Plant> availablePlants = plantService.getAllAvailablePlants();

        model.addAttribute("currentUser", user);
        model.addAttribute("reservationDto", new ReservationDto());
        model.addAttribute("plants", availablePlants);

        return "user/reservation-form"; // Changed from "user/dashboard"
    }

    @PostMapping("/reserve")
    @Transactional
    public String processReservation(
            @Valid @ModelAttribute("reservationDto") ReservationDto reservationDto,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {

        if (bindingResult.hasErrors()) {
            User user = getAuthenticatedUser(userDetails);
            model.addAttribute("currentUser", user);
            model.addAttribute("plants", plantService.getAllAvailablePlants());
            return "user/reservation-form"; // Changed from "user/reserve"
        }

        try {
            Reservation savedReservation = reservationService.saveReservation(
                    reservationDto,
                    userDetails.getUsername()
            );

            return "redirect:/user/reservations?success";
        } catch (Exception e) {
            User user = getAuthenticatedUser(userDetails);
            model.addAttribute("currentUser", user);
            model.addAttribute("errorMessage", "Error saving reservation: " + e.getMessage());
            model.addAttribute("plants", plantService.getAllAvailablePlants());
            return "user/reservation-form"; // Changed from "user/reserve"
        }
    }

    @GetMapping("/plants")
    public String getAllPlants(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size,
            @RequestParam(required = false) String search,
            @AuthenticationPrincipal UserDetails userDetails,  // Better than Principal
            Model model) {

        Page<Plant> plantsPage = plantService.searchPlantsPagination(page, size, search);

        model.addAttribute("plants", plantsPage.getContent());
        model.addAttribute("currentPage", plantsPage.getNumber());
        model.addAttribute("totalPages", plantsPage.getTotalPages());
        model.addAttribute("search", search);

        // Add current user if authenticated - using UserService
        if (userDetails != null) {
            User currentUser = userService.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "User not found with username: " + userDetails.getUsername()));
            model.addAttribute("currentUser", currentUser);
        }

        return "user/plants";
    }


    @GetMapping("/reservations")
    public String getUserReservations(
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {

        User user = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found"));

        List<Reservation> reservations = reservationService.findByUserId(user.getId());
        model.addAttribute("reservations", reservations);
        model.addAttribute("currentUser", user);

        return "user/reservations";
    }

    @GetMapping("/view-plant/{id}")
    public String viewPlant(@PathVariable Long id,
                            @AuthenticationPrincipal UserDetails userDetails,
                            Model model) {
        // Get plant by ID
        Plant plant = plantService.getPlantById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Plant not found with id: " + id));

        // Get current user
        User user = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found with username: " + userDetails.getUsername()));

        // Add attributes to model
        model.addAttribute("plant", plant);
        model.addAttribute("currentUser", user);
        model.addAttribute("reservationDto", new ReservationDto());

        return "user/plant-detail";
    }
}