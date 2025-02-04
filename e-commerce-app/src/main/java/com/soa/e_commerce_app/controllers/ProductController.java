package com.soa.e_commerce_app.controllers;

import com.soa.e_commerce_app.models.Product;
import com.soa.e_commerce_app.models.ProductDto;
import com.soa.e_commerce_app.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductsRepository productsRepository;

    @GetMapping
    public String productList(Model model) {
        List<Product> products = productsRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("products", products);
        return "products/list";
    }

    @GetMapping("/create")
    public String createProduct(Model model) {
        model.addAttribute("productDto", new ProductDto());
        return "products/add";
    }

    @PostMapping("/create")
    public String saveProduct(@Valid @ModelAttribute ProductDto productDto, BindingResult bindingResult, Model model) {
        if (productDto.getImageFile().isEmpty()) {
            bindingResult.addError(new FieldError("productDto", "imageFile", "Image file is required."));
        }

        if (bindingResult.hasErrors()) {
            return "products/add";
        }

        MultipartFile image = productDto.getImageFile();
        String storedFileName = new Date().getTime() + "_" + image.getOriginalFilename();
        String uploadDir = "public/images/"; // Updated path

        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadDir + storedFileName), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "products/add";
        }

        Product product = new Product();
        product.setName(productDto.getName());
        product.setBrand(productDto.getBrand());
        product.setCategory(productDto.getCategory());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setImage_filename(storedFileName);
        product.setCreated_at(new Date());
        productsRepository.save(product);

        return "redirect:/products";
    }

    @GetMapping("/edit")
    public String editPage(@RequestParam int id, Model model) {
        Optional<Product> optionalProduct = productsRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            ProductDto productDto = new ProductDto();
            productDto.setName(product.getName());
            productDto.setBrand(product.getBrand());
            productDto.setCategory(product.getCategory());
            productDto.setPrice(product.getPrice());
            productDto.setDescription(product.getDescription());

            model.addAttribute("product", product);
            model.addAttribute("productDto", productDto);
            return "products/edit";
        }
        return "redirect:/products";
    }

    @PostMapping("/edit")
    public String updateProduct(@RequestParam int id, @Valid @ModelAttribute ProductDto productDto,
                                BindingResult bindingResult, Model model) {
        Optional<Product> optionalProduct = productsRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            return "redirect:/products";
        }

        Product product = optionalProduct.get();

        if (bindingResult.hasErrors()) {
            model.addAttribute("product", product);
            return "products/edit";
        }

        if (!productDto.getImageFile().isEmpty()) {
            String uploadDir = "src/main/resources/static/images/"; // Updated path
            String storedFileName = new Date().getTime() + "_" + productDto.getImageFile().getOriginalFilename();

            try {
                Path oldImagePath = Paths.get(uploadDir + product.getImage_filename());
                Files.deleteIfExists(oldImagePath);

                try (InputStream inputStream = productDto.getImageFile().getInputStream()) {
                    Files.copy(inputStream, Paths.get(uploadDir + storedFileName), StandardCopyOption.REPLACE_EXISTING);
                }
                product.setImage_filename(storedFileName);
            } catch (Exception e) {
                e.printStackTrace();
                model.addAttribute("product", product);
                return "products/edit";
            }
        }

        product.setName(productDto.getName());
        product.setBrand(productDto.getBrand());
        product.setCategory(productDto.getCategory());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        productsRepository.save(product);

        return "redirect:/products";
    }
    @GetMapping("/view")
    public String viewProduct(@RequestParam int id, Model model) {
        Optional<Product> optionalProduct = productsRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            model.addAttribute("product", product);
            return "products/view"; // Return the view product template
        }
        return "redirect:/products"; // Redirect if product not found
    }

    @GetMapping("/delete")
    public String deleteProduct(@RequestParam int id) {
        productsRepository.deleteById(id);
        return "redirect:/products";
    }
}