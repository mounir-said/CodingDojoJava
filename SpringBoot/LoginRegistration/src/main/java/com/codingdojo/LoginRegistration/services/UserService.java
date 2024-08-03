package com.codingdojo.LoginRegistration.services;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.codingdojo.LoginRegistration.models.LoginUser;
import com.codingdojo.LoginRegistration.models.User;
import com.codingdojo.LoginRegistration.repositories.UserRepository;
    
@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepo;
    
    // This method will be called from the controller
    // whenever a user submits a registration form.
    
    public User register(User newUser, BindingResult result) {
    	
    	Optional<User> potentialUser = userRepo.findByEmail(newUser.getEmail());
    	
    	if(potentialUser.isPresent()) {
    		result.rejectValue("email", "Matches", "An account with that email already exists!");
    	}

    	if(!newUser.getPassword().equals(newUser.getConfirm())) {
    	    result.rejectValue("confirm", "Matches", "The Confirm Password must match Password!");
    	}

        if(result.hasErrors()) {
        	return null;
        }
    
        String hashed = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
        newUser.setPassword(hashed);
        return userRepo.save(newUser);
    }

    // This method will be called from the controller
    // whenever a user submits a login form.
    public User login(LoginUser newLoginUser, BindingResult result) {
        // TO-DO - Reject values:
        
    	Optional<User> potentialUser = userRepo.findByEmail(newLoginUser.getEmail());
    	
    	if(!potentialUser.isPresent()) {
    		result.rejectValue("email", "Matches", "User not found!");
    		return null;
    	}
    	
    	User user = potentialUser.get();
        
    	if(!BCrypt.checkpw(newLoginUser.getPassword(), user.getPassword())) {
    	    result.rejectValue("password", "Matches", "Invalid Password!");
    	}
    	
    	if(result.hasErrors()) {
        	return null;
        }

    	return user;
    }
    
    public User findById(Long id) {
    	Optional<User> potentialUser = userRepo.findById(id);
    	if(potentialUser.isPresent()) {
    		return potentialUser.get();
    	}
    	return null;
    }

}