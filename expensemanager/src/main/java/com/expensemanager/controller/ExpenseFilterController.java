package com.expensemanager.controller;

import com.expensemanager.dto.ExpenseDTO;
import com.expensemanager.dto.ExpenseFilterDTO;
import com.expensemanager.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.text.ParseException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ExpenseFilterController {

    private final ExpenseService expenseService;

    @GetMapping("/filterExpenses")
    public String filterExpenses(@ModelAttribute("filter") ExpenseFilterDTO expenseFilterDTO, Model model) throws ParseException {
        List<ExpenseDTO> expenseDTOS = expenseService.getFilteredExpenses(expenseFilterDTO);
        model.addAttribute("expenses", expenseDTOS);
        String totalExpenses = expenseService.totalExpenses(expenseDTOS);
        model.addAttribute("totalExpenses", totalExpenses);
        return "expenses-list";
    }
}
