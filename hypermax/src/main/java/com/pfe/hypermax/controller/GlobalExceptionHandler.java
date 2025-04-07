package com.pfe.hypermax.controller;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IOException.class)
    public String handleIOException(IOException e, Model model) {
        model.addAttribute("errorMessage", "File storage error: " + e.getMessage());
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception e, Model model) {
        model.addAttribute("errorMessage", "An error occurred: " + e.getMessage());
        return "error";
    }
}