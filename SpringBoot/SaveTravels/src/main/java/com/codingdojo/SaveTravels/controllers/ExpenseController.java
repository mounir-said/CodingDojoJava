package com.codingdojo.SaveTravels.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.codingdojo.SaveTravels.models.Expense;
import com.codingdojo.SaveTravels.services.ExpenseService;

import jakarta.validation.Valid;

@Controller
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/expenses")
    public String index(Model model, @ModelAttribute("expense") Expense expense) {
        model.addAttribute("expenses", expenseService.allExpenses());
        return "index";
    }

    @PostMapping("/expenses")
    public String create(@Valid @ModelAttribute("expense") Expense expense, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("expenses", expenseService.allExpenses());
            return "index";
        } else {
            expenseService.createExpense(expense);
            return "redirect:/expenses";
        }
    }

    @GetMapping("/expenses/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Expense expense = expenseService.findExpense(id);
        if (expense != null) {
            model.addAttribute("expense", expense);
            return "edit";
        }
        return "redirect:/expenses";
    }

    @PostMapping("/expenses/edit")
    public String editExpense(@Valid @ModelAttribute("expense") Expense expense, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // Re-populate the form with current expense data in case of errors
            model.addAttribute("expense", expense);
            return "edit";
        }
        expenseService.updateExpense(expense);
        return "redirect:/expenses";
    }

    @GetMapping("/expenses/delete/{id}")
    public String deleteExpense(@PathVariable("id") Long id) {
        expenseService.deleteExpense(id);
        return "redirect:/expenses";
    }

    @GetMapping("/expenses/details/{id}")
    public String viewExpenseDetails(@PathVariable("id") Long id, Model model) {
        Expense expense = expenseService.findExpense(id);
        if (expense != null) {
            model.addAttribute("expense", expense);
            return "details";
        }
        return "redirect:/expenses";
    }
}
