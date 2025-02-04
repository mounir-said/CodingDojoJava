package com.iset.gestionDocPat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Permit access to specific URLs without authentication
                        .requestMatchers("/", "/doctors/add", "/patients/add", "/appointments/add").permitAll()
                        .requestMatchers("/doctors/email/**", "/patients/email/**").permitAll() // Example: Allow email-based lookups
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll() // Static resources
                        .anyRequest().permitAll() // All other requests require authentication
                )
                .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity (not recommended for production)
                .httpBasic(httpBasic -> httpBasic.disable()); // Disable basic HTTP authentication

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web -> web.ignoring().requestMatchers(
                "/h2-console/**", // Ignore H2 Console
                "/favicon.ico"    // Ignore favicon requests
        ));
    }

    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        DefaultHttpFirewall firewall = new DefaultHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true);
        return firewall;
    }
}