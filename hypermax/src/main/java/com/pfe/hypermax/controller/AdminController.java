package com.pfe.hypermax.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import com.pfe.hypermax.model.*;
import com.pfe.hypermax.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.pfe.hypermax.util.CommonUtil;
import com.pfe.hypermax.util.OrderStatus;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

	// Services
	@Autowired private CategoryService categoryService;
	@Autowired private ProductService productService;
	@Autowired private OnlineProductService onlineProductService;
	@Autowired private UserService userService;
	@Autowired private CartService cartService;
	@Autowired private OrderService orderService;
	@Autowired private CommonUtil commonUtil;
	@Autowired private PasswordEncoder passwordEncoder;
	@Autowired private McpService mcpService;
	@Autowired private BlogPostService blogPostService;

	// Path constants
	private static final String SEARCH_RESULTS_PATH = "mcp-storage/search_results.txt";
	// Update path constants to point to static directory
	private static final String CATEGORY_UPLOAD_DIR = Paths.get("uploads/category_img").toAbsolutePath().toString();
	private static final String PRODUCT_UPLOAD_DIR = Paths.get("uploads/product_img").toAbsolutePath().toString();
	private static final String PROFILE_UPLOAD_DIR = Paths.get("uploads/profile_img").toAbsolutePath().toString();

	// Add static initializer to create directories
	static {
		try {
			Files.createDirectories(Paths.get(CATEGORY_UPLOAD_DIR));
			Files.createDirectories(Paths.get(PRODUCT_UPLOAD_DIR));
			Files.createDirectories(Paths.get(PROFILE_UPLOAD_DIR));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@ModelAttribute
	public void getUserDetails(Principal p, Model m) {
		if (p != null) {
			UserDtls user = userService.getUserByEmail(p.getName());
			m.addAttribute("user", user);
			m.addAttribute("countCart", cartService.getCountCart(user.getId()));
		}
		m.addAttribute("categorys", categoryService.getAllActiveCategory());
	}

	//=== Core Controller Methods ===//

	@GetMapping("/")
	public String adminDashboard() {
		return "admin/index";
	}

	//=== Category Management ===//
	@GetMapping("/category")
	public String viewCategories(Model m,
								 @RequestParam(defaultValue = "0") int page,
								 @RequestParam(defaultValue = "10") int size) {
		Page<Category> categoryPage = categoryService.getAllCategorPagination(page, size);
		m.addAttribute("categorys", categoryPage.getContent());
		addPaginationAttributes(m, categoryPage);
		return "admin/category";
	}


	@PostMapping("/saveCategory")
	public String saveCategory(@ModelAttribute Category category,
							   @RequestParam("file") MultipartFile file,
							   HttpSession session) {
		try {
			// File handling with proper directory creation
			String filename = sanitizeFileName(file.getOriginalFilename());
			Path uploadPath = Paths.get(CATEGORY_UPLOAD_DIR);

			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}

			Files.copy(file.getInputStream(),
					uploadPath.resolve(filename),
					StandardCopyOption.REPLACE_EXISTING);

			// Save category with image name
			category.setImageName(filename);
			Category savedCategory = categoryService.saveCategory(category);

			session.setAttribute("succMsg", "Category saved successfully");
		} catch (Exception e) {
			session.setAttribute("errorMsg", "Error saving category: " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/admin/category";
	}

	@PostMapping("/updateCategory")
	public String updateCategory(@ModelAttribute Category category,
								 @RequestParam("file") MultipartFile file,
								 HttpSession session) {
		try {
			Category existing = categoryService.getCategoryById(category.getId());
			String newFilename = existing.getImageName();

			if (!file.isEmpty()) {
				// Delete old image if it's not default
				if (existing.getImageName() != null && !existing.getImageName().equals("default.jpg")) {
					Path oldPath = Paths.get(CATEGORY_UPLOAD_DIR, existing.getImageName());
					Files.deleteIfExists(oldPath);
				}

				// Save new image
				newFilename = sanitizeFileName(file.getOriginalFilename());
				Path uploadPath = Paths.get(CATEGORY_UPLOAD_DIR);
				Files.copy(file.getInputStream(),
						uploadPath.resolve(newFilename),
						StandardCopyOption.REPLACE_EXISTING);
			}

			// Update category
			existing.setName(category.getName());
			existing.setIsActive(category.getIsActive());
			existing.setImageName(newFilename);

			categoryService.saveCategory(existing);
			session.setAttribute("succMsg", "Category updated successfully");
		} catch (Exception e) {
			session.setAttribute("errorMsg", "Error updating category: " + e.getMessage());
		}
		return "redirect:/admin/loadEditCategory/" + category.getId();
	}

	//=== Product Management ===//
	@GetMapping("/loadAddProduct")
	public String showAddProductForm(Model m) {
		m.addAttribute("categories", categoryService.getAllCategory());
		return "admin/add_product";
	}

	@PostMapping("/saveProduct")
	public String saveProduct(@ModelAttribute Product product,
							  @RequestParam("file") MultipartFile image,
							  HttpSession session) {
		try {
			// File handling
			String imageName = image.isEmpty() ? "default.jpg" : sanitizeFileName(image.getOriginalFilename());
			if (!image.isEmpty()) {
				Path uploadPath = Paths.get(PRODUCT_UPLOAD_DIR);
				if (!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}
				Files.copy(image.getInputStream(),
						uploadPath.resolve(imageName),
						StandardCopyOption.REPLACE_EXISTING);
			}

			// Rest of the method remains the same
			product.setImage(imageName);
			product.setDiscount(0);
			product.setDiscountPrice(product.getPrice());

			Product savedProduct = productService.saveProduct(product);
			if(savedProduct == null) {
				deleteFile(PRODUCT_UPLOAD_DIR, imageName);
				throw new IOException("Failed to save product to database");
			}

			session.setAttribute("succMsg", "Product saved successfully");
		} catch (Exception e) {
			session.setAttribute("errorMsg", "Error saving product: " + e.getMessage());
		}
		return "redirect:/admin/loadAddProduct";
	}

	@PostMapping("/updateProduct")
	public String updateProduct(@ModelAttribute Product product,
								@RequestParam("file") MultipartFile image,
								HttpSession session) {
		try {
			Product existing = productService.getProductById(product.getId());
			String newImageName = existing.getImage();

			// Handle image update
			if (!image.isEmpty()) {
				newImageName = sanitizeFileName(image.getOriginalFilename());
				Path uploadPath = Paths.get("uploads/product_img");
				Files.createDirectories(uploadPath);
				Files.copy(image.getInputStream(),
						uploadPath.resolve(newImageName),
						StandardCopyOption.REPLACE_EXISTING);

				// Delete old image if exists
				if (!"default.jpg".equals(existing.getImage())) {
					Files.deleteIfExists(uploadPath.resolve(existing.getImage()));
				}
			}

			// Update product with new image name
			product.setImage(newImageName);

			// Validate discount
			if (product.getDiscount() < 0 || product.getDiscount() > 100) {
				throw new IllegalArgumentException("Discount must be 0-100%");
			}

			Product updated = productService.updateProduct(product);
			session.setAttribute("succMsg", "Product updated successfully");
		} catch (Exception e) {
			session.setAttribute("errorMsg", "Error: " + e.getMessage());
		}
		return "redirect:/admin/editProduct/" + product.getId();
	}

	//=== User Management ===//
	@PostMapping("/save-admin")
	public String createAdmin(@ModelAttribute UserDtls user,
							  @RequestParam("img") MultipartFile file,
							  HttpSession session) {
		try {
			// File handling
			String imageName = "default.jpg";
			if (!file.isEmpty()) {
				imageName = sanitizeFileName(file.getOriginalFilename());
				saveUploadedFile(file, PROFILE_UPLOAD_DIR, imageName);
			}

			// Save user
			user.setProfileImage(imageName);
			UserDtls savedUser = userService.saveAdmin(user);

			if(savedUser == null) {
				deleteFile(PROFILE_UPLOAD_DIR, imageName);
				throw new IOException("Failed to save admin to database");
			}

			session.setAttribute("succMsg", "Admin created successfully");
		} catch (Exception e) {
			session.setAttribute("errorMsg", "Error creating admin: " + e.getMessage());
		}
		return "redirect:/admin/add-admin";
	}

	// Add to AdminController.java
	@GetMapping("/profile")
	public String adminProfile() {
		return "admin/profile"; // Should match your Thymeleaf template name
	}

	@PostMapping("/update-profile")
	public String updateProfile(@ModelAttribute UserDtls user,
								@RequestParam MultipartFile img,
								HttpSession session) {
		try {
			UserDtls existing = userService.getUserByEmail(user.getEmail());
			String newImageName = existing.getProfileImage();

			// Handle image update
			if (!img.isEmpty()) {
				newImageName = sanitizeFileName(img.getOriginalFilename());
				saveUploadedFile(img, PROFILE_UPLOAD_DIR, newImageName);
				deleteFile(PROFILE_UPLOAD_DIR, existing.getProfileImage());
			}

			// Update user
			user.setProfileImage(newImageName);
			userService.updateUserProfile(user, img);

			session.setAttribute("succMsg", "Profile updated successfully");
		} catch (Exception e) {
			session.setAttribute("errorMsg", "Error updating profile: " + e.getMessage());
		}
		return "redirect:/admin/profile";
	}

	//=== Helper Methods ===//
	private String sanitizeFileName(String name) {
		return name.replaceAll("[^a-zA-Z0-9.-]", "_")
				.replace(" ", "_")
				.toLowerCase();
	}

	private void saveUploadedFile(MultipartFile file, String directory, String filename) throws IOException {
		Path uploadPath = Paths.get(directory);
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		Files.copy(file.getInputStream(), uploadPath.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
	}

	private void deleteFile(String directory, String filename) throws IOException {
		Path filePath = Paths.get(directory, filename);
		if (Files.exists(filePath)) Files.delete(filePath);
	}

	private void validateImageFile(MultipartFile file) {
		String contentType = file.getContentType();
		if (contentType == null ||
				(!contentType.equals("image/jpeg") && !contentType.equals("image/png"))) {
			throw new IllegalArgumentException("Invalid image format. Only JPEG/PNG allowed.");
		}
	}

	private void updateProductPrice(Product product) {
		// Convert values to BigDecimal
		BigDecimal originalPrice = BigDecimal.valueOf(product.getPrice());
		BigDecimal discount = BigDecimal.valueOf(product.getDiscount());

		// Calculate discount amount
		BigDecimal discountAmount = originalPrice
				.multiply(discount)
				.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

		// Update discount price
		product.setDiscountPrice(originalPrice.subtract(discountAmount).doubleValue());

		productService.saveProduct(product);
	}

	private void addPaginationAttributes(Model m, Page<?> page) {
		m.addAttribute("pageNo", page.getNumber());
		m.addAttribute("pageSize", page.getSize());
		m.addAttribute("totalElements", page.getTotalElements());
		m.addAttribute("totalPages", page.getTotalPages());
		m.addAttribute("isFirst", page.isFirst());
		m.addAttribute("isLast", page.isLast());
	}





	@PostMapping("/change-password")
	public String changePassword(@RequestParam String newPassword, @RequestParam String currentPassword, Principal p,
			HttpSession session) {
		UserDtls loggedInUserDetails = commonUtil.getLoggedInUserDetails(p);

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

		return "redirect:/admin/profile";
	}

	@GetMapping("/search-history")
	public String showSearchHistory(Model model) {
		List<String> searchEntries = parseSearchHistory();
		model.addAttribute("searchEntries", searchEntries);
		return "admin/searchhistory";
	}

	private List<String> parseSearchHistory() {
		List<String> entries = new ArrayList<>();
		Path path = Paths.get(SEARCH_RESULTS_PATH);

		try {
			if (Files.exists(path)) {
				String content = Files.readString(path);
				// Split by the === delimiter
				String[] rawEntries = content.split("=== ");

				for (String entry : rawEntries) {
					if (!entry.trim().isEmpty()) {
						entries.add(entry.trim());
					}
				}
			} else {
				entries.add("Search history file not found at: " + SEARCH_RESULTS_PATH);
			}
		} catch (IOException e) {
			e.printStackTrace();
			entries.add("Error reading search history: " + e.getMessage());
		}

		if (entries.isEmpty()) {
			entries.add("No search history found");
		}

		return entries;
	}

	@GetMapping("/online-products")
	public String listOnlineProducts(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "timestamp,desc") String[] sort,
			Model model) {

		Sort.Direction direction = sort[1].equalsIgnoreCase("desc") ?
				Sort.Direction.DESC : Sort.Direction.ASC;
		PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sort[0]));

		Page<OnlineProduct> productPage = onlineProductService.findAll(pageRequest);

		model.addAttribute("products", productPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", productPage.getTotalPages());
		model.addAttribute("totalItems", productPage.getTotalElements());
		model.addAttribute("sortField", sort[0]);
		model.addAttribute("sortDirection", sort[1]);

		return "admin/online-products";
	}

	@GetMapping("/online-products/search")
	public String searchProducts(
			@RequestParam String query,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			Model model,
			HttpSession session) {

		try {
			mcpService.searchAndStore(query);

			PageRequest pageRequest = PageRequest.of(page, size);
			Page<OnlineProduct> productPage = onlineProductService.findByTitleContainingIgnoreCase(query, pageRequest);

			model.addAttribute("products", productPage.getContent());
			model.addAttribute("currentPage", page);
			model.addAttribute("totalPages", productPage.getTotalPages());
			model.addAttribute("totalItems", productPage.getTotalElements());
			model.addAttribute("searchQuery", query);

		} catch (Exception e) {
			session.setAttribute("errorMsg", "Error searching products: " + e.getMessage());
		}

		return "admin/online-products";
	}

	@GetMapping("/online-products/analytics")
	public String showAnalytics(Model model) {
		model.addAttribute("totalProducts", onlineProductService.count());
		model.addAttribute("distinctSources", onlineProductService.findDistinctSources());
		model.addAttribute("latestProducts", onlineProductService.findTop5ByOrderByTimestampDesc());
		return "admin/analytics";
	}

	@GetMapping("/online-products/delete/{id}")
	public String deleteOnlineProduct(@PathVariable Long id, HttpSession session) {
		try {
			onlineProductService.deleteById(id);
			session.setAttribute("succMsg", "Product deleted successfully");
		} catch (Exception e) {
			session.setAttribute("errorMsg", "Error deleting product");
		}
		return "redirect:/admin/online-products";
	}


	@GetMapping("/search-engine/")
	public String showSearchForm(Model model) throws IOException {
		model.addAttribute("searchRequest", new SearchRequest());
		model.addAttribute("savedResults", mcpService.getSavedResults());
		return "admin/search";
	}

	@PostMapping("/search-engine/perform")
	public String search(
			@Valid @ModelAttribute("searchRequest") SearchRequest searchRequest,
			BindingResult bindingResult,
			Model model) throws IOException {

		if (bindingResult.hasErrors()) {
			model.addAttribute("savedResults", mcpService.getSavedResults());
			return "admin/search";
		}

		List<SearchResult> results = mcpService.searchAndStore(searchRequest.getQuery());

		List<OnlineProduct> productsToSave = new ArrayList<>();

		for (SearchResult result : results) {
			if (!onlineProductService.existsByUrl(result.getUrl())) {  // 🛡️ Check before saving
				OnlineProduct onlineProduct = new OnlineProduct();
				onlineProduct.setTitle(result.getTitle());
				onlineProduct.setUrl(result.getUrl());
				onlineProduct.setDescription(result.getDescription());
				onlineProduct.setImageUrl(result.getImage());
				onlineProduct.setSource(result.getSource());

				try {
					String cleanPrice = result.getPrice().replaceAll("[^0-9.]", "");
					onlineProduct.setPrice(new BigDecimal(cleanPrice));
				} catch (Exception e) {
					onlineProduct.setPrice(BigDecimal.ZERO);
				}

				productsToSave.add(onlineProduct);
			}
			// else: duplicate URL, skip
		}

		onlineProductService.saveProducts(productsToSave);

		model.addAttribute("results", results);
		model.addAttribute("savedResults", mcpService.getSavedResults());
		return "admin/search";
	}

	@GetMapping("/suggestions")
	public String viewSuggestions(
			Model model,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(required = false) PostStatus status) {

		Page<BlogPost> postsPage;
		if (status != null) {
			postsPage = blogPostService.getPostsByStatus(status, PageRequest.of(page, size));
		} else {
			postsPage = blogPostService.getAllPosts(PageRequest.of(page, size));
		}

		model.addAttribute("postsPage", postsPage);
		return "admin/suggestions-list";
	}

	@GetMapping("/suggestions/{id}/approve")
	public String approveSuggestion(@PathVariable Long id) {
		blogPostService.updatePostStatus(id, PostStatus.APPROVED);
		return "redirect:/admin/suggestions";
	}

	@GetMapping("/suggestions/{id}/reject")
	public String rejectSuggestion(@PathVariable Long id) {
		blogPostService.updatePostStatus(id, PostStatus.REJECTED);
		return "redirect:/admin/suggestions";
	}

	@GetMapping("/orders")
	public String getAllOrders(Model m, @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
							   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
//		List<ProductOrder> allOrders = orderService.getAllOrders();
//		m.addAttribute("orders", allOrders);
//		m.addAttribute("srch", false);

		Page<ProductOrder> page = orderService.getAllOrdersPagination(pageNo, pageSize);
		m.addAttribute("orders", page.getContent());
		m.addAttribute("srch", false);

		m.addAttribute("pageNo", page.getNumber());
		m.addAttribute("pageSize", pageSize);
		m.addAttribute("totalElements", page.getTotalElements());
		m.addAttribute("totalPages", page.getTotalPages());
		m.addAttribute("isFirst", page.isFirst());
		m.addAttribute("isLast", page.isLast());

		return "/admin/orders";
	}

	@PostMapping("/update-order-status")
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
		return "redirect:/admin/orders";
	}

	@GetMapping("/search-order")
	public String searchProduct(@RequestParam String orderId, Model m, HttpSession session,
								@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
								@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

		if (orderId != null && orderId.length() > 0) {

			ProductOrder order = orderService.getOrdersByOrderId(orderId.trim());

			if (ObjectUtils.isEmpty(order)) {
				session.setAttribute("errorMsg", "Incorrect orderId");
				m.addAttribute("orderDtls", null);
			} else {
				m.addAttribute("orderDtls", order);
			}

			m.addAttribute("srch", true);
		} else {
//			List<ProductOrder> allOrders = orderService.getAllOrders();
//			m.addAttribute("orders", allOrders);
//			m.addAttribute("srch", false);

			Page<ProductOrder> page = orderService.getAllOrdersPagination(pageNo, pageSize);
			m.addAttribute("orders", page);
			m.addAttribute("srch", false);

			m.addAttribute("pageNo", page.getNumber());
			m.addAttribute("pageSize", pageSize);
			m.addAttribute("totalElements", page.getTotalElements());
			m.addAttribute("totalPages", page.getTotalPages());
			m.addAttribute("isFirst", page.isFirst());
			m.addAttribute("isLast", page.isLast());

		}
		return "/admin/orders";

	}
}
