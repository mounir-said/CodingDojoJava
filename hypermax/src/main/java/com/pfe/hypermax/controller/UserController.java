package com.pfe.hypermax.controller;

import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import com.pfe.hypermax.dto.BlogPostDto;
import com.pfe.hypermax.model.*;
import com.pfe.hypermax.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.pfe.hypermax.repository.UserRepository;
import com.pfe.hypermax.util.CommonUtil;
import com.pfe.hypermax.util.OrderStatus;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private CartService cartService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private BlogPostService blogPostService;

	// In UserController.java
	private static final String PROFILE_UPLOAD_DIR;

	static {
		String uploadPath = Paths.get("uploads/profile_img").toAbsolutePath().toString();
		try {
			Files.createDirectories(Paths.get(uploadPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		PROFILE_UPLOAD_DIR = uploadPath;
	}

	@GetMapping("/")
	public String home() {
		return "user/home";
	}

	@ModelAttribute
	public void getUserDetails(Principal p, Model m) {
		if (p != null) {
			String email = p.getName();
			UserDtls userDtls = userService.getUserByEmail(email);
			m.addAttribute("user", userDtls);
			Integer countCart = cartService.getCountCart(userDtls.getId());
			m.addAttribute("countCart", countCart);
		}

		List<Category> allActiveCategory = categoryService.getAllActiveCategory();
		m.addAttribute("categorys", allActiveCategory);
	}

	@GetMapping("/addCart")
	public String addToCart(@RequestParam Integer pid, @RequestParam Integer uid, HttpSession session) {
		Cart saveCart = cartService.saveCart(pid, uid);

		if (ObjectUtils.isEmpty(saveCart)) {
			session.setAttribute("errorMsg", "Product add to cart failed");
		} else {
			session.setAttribute("succMsg", "Product added to cart");
		}
		return "redirect:/product/" + pid;
	}

	@GetMapping("/cart")
	public String loadCartPage(Principal p, Model m) {

		UserDtls user = getLoggedInUserDetails(p);
		List<Cart> carts = cartService.getCartsByUser(user.getId());
		m.addAttribute("carts", carts);
		if (carts.size() > 0) {
			Double totalOrderPrice = carts.get(carts.size() - 1).getTotalOrderPrice();
			m.addAttribute("totalOrderPrice", totalOrderPrice);
		}
		return "/user/cart";
	}

	@GetMapping("/cartQuantityUpdate")
	public String updateCartQuantity(@RequestParam String sy, @RequestParam Integer cid) {
		cartService.updateQuantity(sy, cid);
		return "redirect:/user/cart";
	}

	private UserDtls getLoggedInUserDetails(Principal p) {
		String email = p.getName();
		UserDtls userDtls = userService.getUserByEmail(email);
		return userDtls;
	}

	@GetMapping("/orders")
	public String orderPage(Principal p, Model m) {
		UserDtls user = getLoggedInUserDetails(p);
		List<Cart> carts = cartService.getCartsByUser(user.getId());
		m.addAttribute("carts", carts);
		if (carts.size() > 0) {
			Double orderPrice = carts.get(carts.size() - 1).getTotalOrderPrice();
			Double totalOrderPrice = carts.get(carts.size() - 1).getTotalOrderPrice() + 250 + 100;
			m.addAttribute("orderPrice", orderPrice);
			m.addAttribute("totalOrderPrice", totalOrderPrice);
		}
		return "/user/order";
	}

	@PostMapping("/save-order")
	public String saveOrder(@ModelAttribute OrderRequest request, Principal p) throws Exception {
		// System.out.println(request);
		UserDtls user = getLoggedInUserDetails(p);
		orderService.saveOrder(user.getId(), request);

		return "redirect:/user/success";
	}

	@GetMapping("/success")
	public String loadSuccess() {
		return "/user/success";
	}

	@GetMapping("/user-orders")
	public String myOrder(Model m, Principal p) {
		UserDtls loginUser = getLoggedInUserDetails(p);
		List<ProductOrder> orders = orderService.getOrdersByUser(loginUser.getId());
		m.addAttribute("orders", orders);
		return "/user/my_orders";
	}

	@GetMapping("/update-status")
	public String updateOrderStatus(@RequestParam Integer id, @RequestParam Integer st, HttpSession session) {

		OrderStatus[] values = OrderStatus.values();
		String status = null;

		for (OrderStatus orderSt : values) {
			if (orderSt.getId().equals(st)) {
				status = orderSt.getName();
			}
		}

		ProductOrder updateOrder = orderService.updateOrderStatus(id, status);
		
		try {
			commonUtil.sendMailForProductOrder(updateOrder, status);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!ObjectUtils.isEmpty(updateOrder)) {
			session.setAttribute("succMsg", "Status Updated");
		} else {
			session.setAttribute("errorMsg", "status not updated");
		}
		return "redirect:/user/user-orders";
	}

	@GetMapping("/profile")
	public String profile() {
		return "/user/profile";
	}

	private String sanitizeFileName(String originalName) {
		return originalName.replaceAll("[^a-zA-Z0-9.-]", "_")
				.replace(" ", "_")
				.toLowerCase();
	}

	@PostMapping("/update-profile")
	public String updateProfile(
			@ModelAttribute UserDtls user,
			@RequestParam MultipartFile img,
			Principal principal,
			HttpSession session) {
		try {
			// Get existing user
			UserDtls existingUser = userService.getUserByEmail(principal.getName());

			// Handle file upload
			if (!img.isEmpty()) {
				// 1. Delete old image if exists
				if (existingUser.getProfileImage() != null &&
						!existingUser.getProfileImage().equals("default.jpg")) {
					Path oldPath = Paths.get(PROFILE_UPLOAD_DIR, existingUser.getProfileImage());
					Files.deleteIfExists(oldPath);
				}

				// 2. Save new image
				String fileName = sanitizeFileName(img.getOriginalFilename());
				Path filePath = Paths.get(PROFILE_UPLOAD_DIR, fileName);
				Files.copy(img.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

				// 3. Update database record
				existingUser.setProfileImage(fileName);
			}

			// Update other fields
			existingUser.setName(user.getName());
			existingUser.setMobileNumber(user.getMobileNumber());
			existingUser.setAddress(user.getAddress());
			existingUser.setCity(user.getCity());
			existingUser.setState(user.getState());
			existingUser.setPincode(user.getPincode());

			userService.updateUserProfile(existingUser);
			session.setAttribute("succMsg", "Profile updated successfully");

		} catch (Exception e) {
			session.setAttribute("errorMsg", "Error: " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/user/profile";
	}

	@PostMapping("/change-password")
	public String changePassword(@RequestParam String newPassword, @RequestParam String currentPassword, Principal p,
			HttpSession session) {
		UserDtls loggedInUserDetails = getLoggedInUserDetails(p);

		boolean matches = passwordEncoder.matches(currentPassword, loggedInUserDetails.getPassword());

		if (matches) {
			String encodePassword = passwordEncoder.encode(newPassword);
			loggedInUserDetails.setPassword(encodePassword);
			UserDtls updateUser = userService.updateUser(loggedInUserDetails);
			if (ObjectUtils.isEmpty(updateUser)) {
				session.setAttribute("errorMsg", "Password not updated !! Error in server");
			} else {
				session.setAttribute("succMsg", "Password Updated sucessfully");
			}
		} else {
			session.setAttribute("errorMsg", "Current Password incorrect");
		}

		return "redirect:/user/profile";
	}

	@GetMapping("/suggestions")
	public String showSuggestionForm(Model model) {
		model.addAttribute("blogPostDto", new BlogPostDto());
		return "user/suggestion-form";
	}


	@PostMapping("/suggestions")
	public String submitSuggestion(
			@Valid @ModelAttribute BlogPostDto blogPostDto,
			BindingResult result,
			@AuthenticationPrincipal UserDetails userDetails,
			RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			return "user/suggestion-form";
		}


		blogPostService.createPost(blogPostDto, userDetails.getUsername());
		redirectAttributes.addFlashAttribute("success", "Suggestion submitted!");
		return "redirect:/user/suggestions";
	}

	@GetMapping("/my-suggestions")
	public String userSuggestions(
			@AuthenticationPrincipal UserDetails userDetails,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			Model model) {

		// Service now throws exception directly if user not found
		UserDtls user = userService.getUserByEmail(userDetails.getUsername());

		Page<BlogPost> postsPage = blogPostService.getPostsByUser(user, PageRequest.of(page, size));
		model.addAttribute("postsPage", postsPage);
		return "user/my-suggestions";
	}

}
