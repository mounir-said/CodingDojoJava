package com.pfe.GreenPlanet.controller;

import com.pfe.GreenPlanet.dto.UserDto;
import com.pfe.GreenPlanet.model.User;
import com.pfe.GreenPlanet.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.Optional;

@Controller
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @ModelAttribute
    public void addCommonAttributes(Model model, Principal principal) {
        if (principal != null) {
            String email = principal.getName();
            Optional<User> userOptional = userService.findByEmail(email);
            userOptional.ifPresent(user -> model.addAttribute("currentUser", user));
        }
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "GreenPlanet - Home");
        return "layout";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "GreenPlanet - Login");
        model.addAttribute("userDto", new UserDto());
        return "login";
    }

    @GetMapping("/register")
    public String getRegistrationPage(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "register";
    }

    @PostMapping("/register")
    public String saveUser(@Valid @ModelAttribute("userDto") UserDto userDto,
                           BindingResult result,
                           Model model) {

        if (result.hasErrors()) {
            return "register";
        }

        if (userService.findByEmail(userDto.getEmail()).isPresent()) {
            model.addAttribute("error", "Email already exists");
            return "register";
        }

        userService.save(userDto);
        model.addAttribute("message", "Registered Successfully!");
        return "redirect:/login?registered";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        model.addAttribute("title", "GreenPlanet - Dashboard");

        if (principal != null) {
            String email = principal.getName();
            User user = userService.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            model.addAttribute("user", user);
        }

        return "dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout";
    }
}