package com.codingdojo.GreetingHuman;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class GreetingController {
	
	@GetMapping("/")
	public String greeting(
			@RequestParam(value = "first_name", defaultValue = "human") String firstName,
            @RequestParam(value = "last_name", defaultValue = "") String lastName,
            @RequestParam(value = "times", defaultValue = "1") int times) {
            	
            	String greeting = "Hello " + firstName + " " + lastName;
                StringBuilder response = new StringBuilder();
                
                for (int i = 0; i < times; i++) {
                    response.append(greeting).append(" ");
                }

                return response.toString().trim();
		
	}
	

}
