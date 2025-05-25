package com.pfe.GreenPlanet.service;

import com.pfe.GreenPlanet.dto.UserDto;
import com.pfe.GreenPlanet.model.User;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {


    User save(UserDto registrationDto);

    Optional<User> findByEmail(String email);

    Page<User> findAllUsers(Pageable pageable);

    void deleteUser(Long id);

    long countAllUsers();
}