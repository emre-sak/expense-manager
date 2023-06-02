package com.expensemanager.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExpenseNotFoundException.class)
    public String handleExpenseNotFoundException(
            HttpServletRequest request,
            ExpenseNotFoundException exception,
            Model model) {

        model.addAttribute("notFound", true);
        model.addAttribute("message", exception.getMessage());

        return "response";
    }

    @ExceptionHandler(Exception.class)
    public String handleGlobalException(
            HttpServletRequest request,
            Exception exception,
            Model model) {

        model.addAttribute("serverError", true);
        model.addAttribute("message", exception.getMessage());
        model.addAttribute("stackTrace", exception.getStackTrace());

        return "response";
    }

}
