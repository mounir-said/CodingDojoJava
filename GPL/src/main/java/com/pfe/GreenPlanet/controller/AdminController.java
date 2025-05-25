package com.pfe.GreenPlanet.controller;

import com.pfe.GreenPlanet.dto.PlantDto;
import com.pfe.GreenPlanet.model.Plant;
import com.pfe.GreenPlanet.model.Reservation;
import com.pfe.GreenPlanet.model.ReservationStatus;
import com.pfe.GreenPlanet.model.User;
import com.pfe.GreenPlanet.repository.PlantRepository;
import com.pfe.GreenPlanet.repository.UserRepository;
import com.pfe.GreenPlanet.service.PlantService;
import com.pfe.GreenPlanet.service.ReservationService;
import com.pfe.GreenPlanet.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import com.pfe.GreenPlanet.dto.UserDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    public static final String UPLOAD_DIR = "src/main/resources/static/img/plant_img/";

    private final UserService userService;

    private final UserRepository userRepository;
    private final PlantService plantService;
    private final PlantRepository plantRepository;
    private final PasswordEncoder passwordEncoder;
    private final ReservationService reservationService;

    @Autowired
    public AdminController(UserService userService,
                           UserRepository userRepository,
                           PlantService plantService,
                           PlantRepository plantRepository,
                           PasswordEncoder passwordEncoder,
                           ReservationService reservationService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.plantService = plantService;
        this.plantRepository = plantRepository;
        this.passwordEncoder = passwordEncoder;
        this.reservationService = reservationService;
    }

    @ModelAttribute
    public void addCommonAttributes(Principal p, Model m) {
        if (p != null) {
            String email = p.getName();
            Optional<User> userOptional = userService.findByEmail(email);
            if (userOptional.isPresent()) {
                m.addAttribute("currentUser", userOptional.get());
            } else {
                logger.error("User not found with email: " + email);
            }
        }
    }

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("totalPlants", plantService.countAllPlants());
        model.addAttribute("totalUsers", userService.countAllUsers());
        model.addAttribute("pendingReservations", reservationService.countPendingReservations());
        model.addAttribute("recentReservations", reservationService.findRecentReservations(5));
        model.addAttribute("recentPlantsList", plantService.findRecentPlants(5));
        return "admin/dashboard";
    }

    @GetMapping("/plants/add")
    public String showAddPlantForm(Model model) {
        model.addAttribute("plantDto", new PlantDto());
        return "admin/add-plant";
    }

    @PostMapping("/plants/add")
    public String addPlant(
            @Valid @ModelAttribute("plantDto") PlantDto plantDto,
            BindingResult result,
            @RequestParam("image") MultipartFile image,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (result.hasErrors()) {
            return "admin/add-plant";
        }

        if (image.isEmpty()) {
            model.addAttribute("errorMessage", "Please select an image");
            return "admin/add-plant";
        }

        try {
            // Handle image upload with sync
            String imageFilename = handleImageUpload(image);

            // Convert DTO to Entity
            Plant plant = new Plant();
            plant.setName(plantDto.getName());
            plant.setCategory(plantDto.getCategory());
            plant.setDescription(plantDto.getDescription());
            plant.setPrice(plantDto.getPrice());
            plant.setQuantity(plantDto.getQuantity());
            plant.setImage_filename(imageFilename);
            plant.setCreated_at(LocalDateTime.now());
            plant.setUpdated_at(LocalDateTime.now());

            // Save plant
            plantService.savePlant(plant);

            // Force redirect to bypass cache
            redirectAttributes.addFlashAttribute("successMessage", "Plant added successfully!");
            return "redirect:/admin/plants?nocache=" + System.currentTimeMillis();

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error adding plant: " + e.getMessage());
            return "admin/add-plant";
        }
    }

    private String handleImageUpload(MultipartFile image) throws IOException {
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFilename = StringUtils.cleanPath(image.getOriginalFilename());
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uniqueFilename = UUID.randomUUID().toString() + fileExtension;

        Path filePath = uploadPath.resolve(uniqueFilename);
        try (InputStream inputStream = image.getInputStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            // Force sync
            Files.write(filePath, Files.readAllBytes(filePath), StandardOpenOption.SYNC);
        }

        return uniqueFilename;
    }

    @GetMapping("/plants/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Plant plant = plantService.getPlantById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid plant Id:" + id));

        // Convert Plant to PlantDto for the form
        PlantDto plantDto = new PlantDto();
        plantDto.setName(plant.getName());
        plantDto.setCategory(plant.getCategory());
        plantDto.setDescription(plant.getDescription());
        plantDto.setPrice(plant.getPrice());
        plantDto.setQuantity(plant.getQuantity());

        // Add both plant and plantDto to the model
        model.addAttribute("plant", plant);  // This was missing
        model.addAttribute("plantDto", plantDto);
        model.addAttribute("plantId", id);
        model.addAttribute("currentImage", plant.getImage_filename());

        return "admin/edit-plant";
    }

    @PostMapping("/plants/update/{id}")
    public String updatePlant(
            @PathVariable Long id,
            @Valid @ModelAttribute("plantDto") PlantDto plantDto,
            BindingResult result,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam("currentImage") String currentImage,
            RedirectAttributes redirectAttributes,
            Model model) {

        // Add plantId back to model in case of errors
        model.addAttribute("plantId", id);
        model.addAttribute("currentImage", currentImage);

        if (result.hasErrors()) {
            // Need to re-add the plant object for the template
            Plant plant = plantService.getPlantById(id).orElse(null);
            if (plant != null) {
                model.addAttribute("plant", plant);
            }
            return "admin/edit-plant";
        }

        try {
            String imageFilename = currentImage;

            // Handle new image upload if provided
            if (image != null && !image.isEmpty()) {
                // Delete old image file if it exists
                if (currentImage != null && !currentImage.isEmpty()) {
                    Path oldImagePath = Paths.get(UPLOAD_DIR, currentImage);
                    try {
                        Files.deleteIfExists(oldImagePath);
                    } catch (IOException e) {
                        logger.warn("Could not delete old image file: " + currentImage, e);
                    }
                }
                // Upload new image
                imageFilename = handleImageUpload(image);
            }

            // Get existing plant
            Plant plant = plantService.getPlantById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Plant not found with id: " + id));

            // Update plant properties
            plant.setName(plantDto.getName());
            plant.setCategory(plantDto.getCategory());
            plant.setDescription(plantDto.getDescription());
            plant.setPrice(plantDto.getPrice());
            plant.setQuantity(plantDto.getQuantity());
            plant.setImage_filename(imageFilename);
            plant.setUpdated_at(LocalDateTime.now());

            // Save updated plant
            plantService.updatePlant(plant);

            redirectAttributes.addFlashAttribute("successMessage", "Plant updated successfully!");
            return "redirect:/admin/plants";

        } catch (Exception e) {
            logger.error("Error updating plant with ID {}: {}", id, e.getMessage(), e);
            model.addAttribute("errorMessage", "Error updating plant: " + e.getMessage());

            // Re-add plant data for the form
            Plant plant = plantService.getPlantById(id).orElse(null);
            if (plant != null) {
                model.addAttribute("plant", plant);
            }

            return "admin/edit-plant";
        }
    }

    @GetMapping("/plants/delete/{id}")
    public String deletePlant(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {
        try {
            // Get the plant to delete (to access its image filename)
            Optional<Plant> plantOptional = plantService.getPlantById(id);
            if (plantOptional.isPresent()) {
                Plant plant = plantOptional.get();

                // Delete the associated image file
                if (plant.getImage_filename() != null && !plant.getImage_filename().isEmpty()) {
                    Path imagePath = Paths.get(UPLOAD_DIR + plant.getImage_filename());
                    try {
                        Files.deleteIfExists(imagePath);
                    } catch (IOException e) {
                        logger.error("Failed to delete image file: " + plant.getImage_filename(), e);
                    }
                }

                // Delete the plant from database
                plantService.deletePlant(id);
                redirectAttributes.addFlashAttribute("successMessage", "Plant deleted successfully!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Plant not found!");
            }
        } catch (Exception e) {
            logger.error("Error deleting plant: " + e.getMessage(), e);
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting plant: " + e.getMessage());
        }
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/plants/view/{id}")
    public String viewPlant(@PathVariable Long id, Model model) {
        Optional<Plant> plantOptional = plantService.getPlantById(id);

        if (plantOptional.isPresent()) {
            Plant plant = plantOptional.get();
            model.addAttribute("plant", plant);
            return "admin/view-plant";
        } else {
            return "redirect:/admin/plants?error=Plant+not+found";
        }
    }

    @GetMapping("/admin/reservations/details/{id}")
    public String showReservationDetails(@PathVariable Long id, Model model) {
        Optional<Reservation> reservation = reservationService.getReservationById(id);
        if (reservation == null) {
            return "redirect:/admin/reservations?error=Reservation not found";
        }

        model.addAttribute("reservation", reservation);
        return "admin/reservation-details";
    }

    @PostMapping("/delete/{id}")
    public String deleteReservation(@PathVariable Long id, HttpSession session) {
        try {
            reservationService.deleteReservation(id);
            session.setAttribute("succMsg", "Reservation deleted successfully!");
        } catch (Exception e) {
            session.setAttribute("errorMsg", "Error deleting reservation: " + e.getMessage());
        }
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/users")
    public String listUsers(
            Model model,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<User> usersPage = userService.findAllUsers(pageable);
        model.addAttribute("usersPage", usersPage);
        model.addAttribute("userDto", new UserDto());
        return "admin/users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        try {
            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting user: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/plants")
    public String listPlants(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search) {

        try {
            Page<Plant> plantsPage;

            if (search != null && !search.trim().isEmpty()) {
                plantsPage = plantService.searchPlantsPagination(page, size, search);
                model.addAttribute("search", search);
            } else {
                plantsPage = plantService.getAllPlantsPagination(page, size);
            }

            model.addAttribute("plantsPage", plantsPage);
            model.addAttribute("currentPage", page);
            model.addAttribute("pageSize", size);

            return "admin/plants-list";
        } catch (Exception e) {
            logger.error("Error fetching plants list: " + e.getMessage(), e);
            model.addAttribute("errorMessage", "Error fetching plants list: " + e.getMessage());
            return "admin/plants-list";
        }
    }

    @GetMapping("/reservations")
    public String listReservations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String status,
            Model model) {

        Page<Reservation> reservationsPage;

        if (status != null && !status.isEmpty()) {
            ReservationStatus reservationStatus = ReservationStatus.valueOf(status.toUpperCase());
            reservationsPage = reservationService.getReservationsByStatus(reservationStatus, PageRequest.of(page, size));
        } else {
            reservationsPage = reservationService.getAllReservations(PageRequest.of(page, size));
        }

        model.addAttribute("reservationsPage", reservationsPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("statusFilter", status);
        model.addAttribute("statuses", ReservationStatus.values());

        return "admin/reservations-list";
    }

    @GetMapping("/reservations/view/{id}")
    public String viewReservation(@PathVariable Long id, Model model) {
        Reservation reservation = reservationService.getReservationById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid reservation Id:" + id));

        model.addAttribute("reservation", reservation);
        return "admin/view-reservation";
    }

    @PostMapping("/reservations/update-status/{id}")
    public String updateReservationStatus(
            @PathVariable Long id,
            @RequestParam ReservationStatus status,
            RedirectAttributes redirectAttributes) {

        reservationService.updateReservationStatus(id, status);
        redirectAttributes.addFlashAttribute("successMessage", "Reservation status updated successfully!");
        return "redirect:/admin/reservations";
    }

    @GetMapping("/reservations/delete/{id}")
    public String deleteReservation(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        reservationService.deleteReservation(id);
        redirectAttributes.addFlashAttribute("successMessage", "Reservation deleted successfully!");
        return "redirect:/admin/reservations";
    }
}