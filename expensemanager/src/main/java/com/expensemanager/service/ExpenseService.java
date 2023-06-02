package com.expensemanager.service;

import com.expensemanager.dto.ExpenseDTO;
import com.expensemanager.dto.ExpenseFilterDTO;

import java.text.ParseException;
import java.util.List;

public interface ExpenseService {
    List<ExpenseDTO> getAllExpenses();
    ExpenseDTO saveExpenseDetails(ExpenseDTO expenseDTO) throws ParseException;
    void deleteExpense(String id);
    ExpenseDTO getExpenseById(String id);
    List<ExpenseDTO> getFilteredExpenses(ExpenseFilterDTO expenseFilterDTO) throws ParseException;
    String totalExpenses(List<ExpenseDTO> expenses);

}
