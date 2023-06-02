package com.expensemanager.service.impl;

import com.expensemanager.dto.ExpenseDTO;
import com.expensemanager.dto.ExpenseFilterDTO;
import com.expensemanager.entity.Expense;
import com.expensemanager.entity.User;
import com.expensemanager.repository.ExpenseRepository;
import com.expensemanager.service.ExpenseService;
import com.expensemanager.service.UserService;
import com.expensemanager.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Override
    public List<ExpenseDTO> getAllExpenses() {
        User user = userService.getLoggedInUser();

        List<Expense> list = expenseRepository.findByDateBetweenAndUserId(
                        Date.valueOf(LocalDate.now().withDayOfMonth(1)),
                        Date.valueOf(LocalDate.now()),
                        user.getId());

        NumberFormat formatter = NumberFormat.getInstance(new Locale("tr", "TR"));
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);

        List<ExpenseDTO> expenseList = list.stream()
                .map(expense -> modelMapper.map(expense, ExpenseDTO.class))
                .peek(expenseDTO -> {
                    expenseDTO.setDateString(DateTimeUtil.convertDateToString(expenseDTO.getDate()));
                    String formattedAmount = formatter.format(expenseDTO.getAmount());
                    expenseDTO.setFormattedAmount(formattedAmount);
                })
                .collect(Collectors.toList());

        return expenseList;
    }

    @Override
    public ExpenseDTO saveExpenseDetails(ExpenseDTO expenseDTO) throws ParseException {

        Expense expense = modelMapper.map(expenseDTO, Expense.class);
        expense.setDate(DateTimeUtil.convertStringToDate(expenseDTO.getDateString()));
        expense.setUser(userService.getLoggedInUser());

        if(!expense.getDate().before(new java.util.Date())) {
            throw new RuntimeException("Gelecekteki tarihe izin verilemez");
        }

        if(expense.getId() == null) {
            expense.setExpenseId(UUID.randomUUID().toString());
        }

        Expense saveExpense = expenseRepository.save(expense);

        ExpenseDTO response = modelMapper.map(saveExpense, ExpenseDTO.class);

        return response;
    }

    @Override
    public void deleteExpense(String id) {
        Expense existingExpense = expenseRepository.findByExpenseId(id).orElseThrow(
                () -> new RuntimeException("Expense not found for the id : " + id)
        );

        expenseRepository.delete(existingExpense);
    }

    @Override
    public ExpenseDTO getExpenseById(String id) {
        Expense existingExpense = expenseRepository.findByExpenseId(id).orElseThrow(
                () -> new RuntimeException("Expense not found for the id : " + id)
        );

        ExpenseDTO response = modelMapper.map(existingExpense, ExpenseDTO.class);

        return response;
    }

    @Override
    public List<ExpenseDTO> getFilteredExpenses(ExpenseFilterDTO expenseFilterDTO) throws ParseException {

        String keyword = expenseFilterDTO.getKeyword();
        String sortBy = expenseFilterDTO.getSortBy();
        String startDateString = expenseFilterDTO.getStartDate();
        String endDateString = expenseFilterDTO.getEndDate();

        User user = userService.getLoggedInUser();

        Date startDate = !startDateString.isEmpty() ? DateTimeUtil.convertStringToDate(startDateString) : new Date(0);
        Date endDate = !endDateString.isEmpty() ?  DateTimeUtil.convertStringToDate(endDateString) : new Date(System.currentTimeMillis());

        List<Expense> expenses = expenseRepository
                .findByNameContainingAndDateBetweenAndUserId(keyword, startDate, endDate, user.getId());

        NumberFormat formatter = NumberFormat.getInstance(new Locale("tr", "TR"));
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);

        List<ExpenseDTO> expenseDTOS = expenses.stream()
                .map(expense -> modelMapper.map(expense, ExpenseDTO.class))
                .peek(expenseDTO -> {
                    expenseDTO.setDateString(DateTimeUtil.convertDateToString(expenseDTO.getDate()));
                    String formattedAmount = formatter.format(expenseDTO.getAmount());
                    expenseDTO.setFormattedAmount(formattedAmount);
                })
                .collect(Collectors.toList());

        if(sortBy.equals("date")) {
            expenseDTOS.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate())); // desc
        }else {
            expenseDTOS.sort((o1, o2) -> o2.getAmount().compareTo(o1.getAmount()));
        }

        return expenseDTOS;
    }

    @Override
    public String totalExpenses(List<ExpenseDTO> expenses) {
        BigDecimal sum = new BigDecimal(0);

        BigDecimal total = expenses.stream().map(amount -> amount.getAmount().add(sum))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        NumberFormat format = NumberFormat.getInstance(new Locale("tr", "TR"));
        format.setMinimumFractionDigits(2);
        format.setMaximumFractionDigits(2);

        return format.format(total);
    }
}
