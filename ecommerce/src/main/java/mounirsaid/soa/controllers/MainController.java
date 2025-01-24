package mounirsaid.soa.controllers;

import java.security.Principal;
import java.util.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import mounirsaid.soa.models.User;
import mounirsaid.soa.services.UserService;
import mounirsaid.soa.validator.UserValidator;

@Controller
@RequestMapping("/")
public class MainController {

    private final UserService userService;
    private final UserValidator userValidator;

    public MainController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping("/register")
    public String showRegistrationPage(@ModelAttribute("user") User user) {
        return "loginPage.jsp";
    }

    @PostMapping("/register")
    public String registerUser(
            @Valid @ModelAttribute("user") User user,
            BindingResult result,
            HttpServletRequest request) {
        userValidator.validate(user, result);
        String plainPassword = user.getPassword();

        if (result.hasErrors()) {
            return "loginPage.jsp";
        }

        if (userService.allUsers().isEmpty()) {
            userService.newUser(user, "ROLE_SUPER_ADMIN");
        } else {
            userService.newUser(user, "ROLE_USER");
        }

        authenticateUser(request, user.getEmail(), plainPassword);
        return "redirect:/";
    }

    private void authenticateUser(HttpServletRequest request, String email, String password) {
        try {
            request.login(email, password);
        } catch (ServletException e) {
            System.err.println("Error during auto-login: " + e.getMessage());
        }
    }

    @GetMapping("/login")
    public String showLoginPage(
            @ModelAttribute("user") User user,
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model) {

        if (error != null) {
            model.addAttribute("errorMessage", "Invalid Credentials, Please try again.");
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", "Logout Successful!");
        }

        return "loginPage.jsp";
    }

    @RequestMapping(value={"/", "/home"})
    public String home(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }

        String email = principal.getName();
        User user = userService.findByEmail(email);

        if (user != null) {
            user.setLastLogin(new Date());
            userService.updateUser(user);
            model.addAttribute("user", user);

            if (user.getRoles().stream().anyMatch(role -> role.getName().startsWith("ROLE_ADMIN"))) {
                model.addAttribute("users", userService.allUsers());
                return "adminPage.jsp";
            }
        }

        return "home.jsp";
    }

    @GetMapping("/admin/{id}")
    public String makeAdmin(@PathVariable("id") Long id) {
        User user = userService.findById(id);
        if (user != null) {
            userService.upgradeUser(user);
        }
        return "redirect:/home";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        User user = userService.findById(id);
        if (user != null) {
            userService.deleteUser(user);
        }
        return "redirect:/home";
    }
}
