package com.pfe.hypermax.controller;

import com.pfe.hypermax.model.*;
import com.pfe.hypermax.service.*;
import com.pfe.hypermax.util.CommonUtil;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class HomeController {

	private final CategoryService categoryService;
	private final ProductService productService;
	private final OnlineProductService onlineProductService;
	private final UserService userService;
	private final CommonUtil commonUtil;
	private final BCryptPasswordEncoder passwordEncoder;
	private final CartService cartService;

	@Autowired
	public HomeController(CategoryService categoryService,
						  ProductService productService,
						  OnlineProductService onlineProductService,
						  UserService userService,
						  CommonUtil commonUtil,
						  BCryptPasswordEncoder passwordEncoder,
						  CartService cartService) {
		this.categoryService = categoryService;
		this.productService = productService;
		this.onlineProductService = onlineProductService;
		this.userService = userService;
		this.commonUtil = commonUtil;
		this.passwordEncoder = passwordEncoder;
		this.cartService = cartService;
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
		m.addAttribute("categorys", categoryService.getAllActiveCategory());
	}

	@GetMapping("/")
	public String index(Model m) {
		List<Category> featuredCategories = categoryService.getAllActiveCategory().stream()
				.sorted((c1, c2) -> c2.getId().compareTo(c1.getId()))
				.limit(6)
				.toList();

		List<Product> featuredProducts = productService.getAllActiveProducts("").stream()
				.sorted((p1, p2) -> p2.getId().compareTo(p1.getId()))
				.limit(8)
				.toList();

		m.addAttribute("category", featuredCategories);
		m.addAttribute("products", featuredProducts);
		return "index";
	}

	@GetMapping("/products")
	public String products(Model m,
						   @RequestParam(value = "category", defaultValue = "") String category,
						   @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
						   @RequestParam(name = "pageSize", defaultValue = "12") Integer pageSize,
						   @RequestParam(defaultValue = "") String ch) {

		// Add categories for sidebar
		m.addAttribute("paramValue", category);
		m.addAttribute("categories", categoryService.getAllActiveCategory());
		m.addAttribute("searchRequest", new SearchRequest());

		// Get paginated products
		Page<Product> page = StringUtils.isEmpty(ch)
				? productService.getAllActiveProductPagination(pageNo, pageSize, category)
				: productService.searchActiveProductPagination(pageNo, pageSize, category, ch);

		List<Product> products = page.getContent();
		m.addAttribute("products", products);
		m.addAttribute("productsSize", products.size());

		// Add saved online products (limit to 8 for display)
		List<OnlineProduct> savedOnlineProducts = onlineProductService.findAllProducts()
				.stream()
				.limit(8)
				.collect(Collectors.toList());
		m.addAttribute("savedOnlineProducts", savedOnlineProducts);

		// Pagination attributes
		addPaginationAttributes(m, page, pageSize);

		return "product";
	}

	@GetMapping("/product/{id}")
	public String product(@PathVariable int id, Model m) {
		m.addAttribute("product", productService.getProductById(id));
		return "view_product";
	}

	@GetMapping("/search")
	public String searchProduct(@RequestParam String ch, Model m) {
		m.addAttribute("products", productService.searchProduct(ch));
		m.addAttribute("categories", categoryService.getAllActiveCategory());
		return "product";
	}

	// Authentication related methods
	@GetMapping("/signin")
	public String login() {
		return "login";
	}

	@GetMapping("/register")
	public String register() {
		return "register";
	}

	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute UserDtls user,
						   @RequestParam("img") MultipartFile file,
						   HttpSession session) throws IOException {

		if (userService.existsEmail(user.getEmail())) {
			session.setAttribute("errorMsg", "Email already exists");
			return "redirect:/register";
		}

		String imageName = file.isEmpty() ? "default.jpg" : file.getOriginalFilename();
		user.setProfileImage(imageName);
		UserDtls savedUser = userService.saveUser(user);

		if (!ObjectUtils.isEmpty(savedUser) && !file.isEmpty()) {
			saveProfileImage(file);
		}

		session.setAttribute(savedUser != null ? "succMsg" : "errorMsg",
				savedUser != null ? "Register successfully" : "Something went wrong");
		return "redirect:/register";
	}

	// Password reset methods
	@GetMapping("/forgot-password")
	public String showForgotPassword() {
		return "forgot_password";
	}

	@PostMapping("/forgot-password")
	public String processForgotPassword(@RequestParam String email,
										HttpSession session,
										HttpServletRequest request)
			throws UnsupportedEncodingException, MessagingException {

		UserDtls user = userService.getUserByEmail(email);
		if (ObjectUtils.isEmpty(user)) {
			session.setAttribute("errorMsg", "Invalid email");
			return "redirect:/forgot-password";
		}

		String resetToken = UUID.randomUUID().toString();
		userService.updateUserResetToken(email, resetToken);

		String resetUrl = CommonUtil.generateUrl(request) + "/reset-password?token=" + resetToken;
		boolean emailSent = commonUtil.sendMail(resetUrl, email);

		session.setAttribute(emailSent ? "succMsg" : "errorMsg",
				emailSent ? "Please check your email" : "Email not sent");
		return "redirect:/forgot-password";
	}

	@GetMapping("/reset-password")
	public String showResetPassword(@RequestParam String token, Model m) {
		UserDtls user = userService.getUserByToken(token);
		if (user == null) {
			m.addAttribute("msg", "Invalid or expired link");
			return "message";
		}
		m.addAttribute("token", token);
		return "reset_password";
	}

	@PostMapping("/reset-password")
	public String resetPassword(@RequestParam String token,
								@RequestParam String password,
								Model m) {
		UserDtls user = userService.getUserByToken(token);
		if (user == null) {
			m.addAttribute("errorMsg", "Invalid or expired link");
			return "message";
		}

		user.setPassword(passwordEncoder.encode(password));
		user.setResetToken(null);
		userService.updateUser(user);
		m.addAttribute("msg", "Password changed successfully");
		return "message";
	}

	// Helper methods
	private void addPaginationAttributes(Model m, Page<?> page, int pageSize) {
		m.addAttribute("pageNo", page.getNumber());
		m.addAttribute("pageSize", pageSize);
		m.addAttribute("totalElements", page.getTotalElements());
		m.addAttribute("totalPages", page.getTotalPages());
		m.addAttribute("isFirst", page.isFirst());
		m.addAttribute("isLast", page.isLast());
	}

	private void saveProfileImage(MultipartFile file) throws IOException {
		File saveFile = new ClassPathResource("static/img").getFile();
		Path path = Paths.get(saveFile.getAbsolutePath() + File.separator +
				"profile_img" + File.separator + file.getOriginalFilename());
		Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
	}
}