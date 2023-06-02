package com.expensemanager.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseDTO {
    private Long id;
    private String expenseId;
    @NotBlank(message = "Gider adı boş olamaz")
    @Size(min = 3, message = "Gider adı en az 3 karakter olmalıdır")
    private String name;
    private String description;
    @NotNull(message = "Tutar boş olamaz")
    @Min(value = 1, message = "Girilen tutar 1'den küçük olamaz")
    private BigDecimal amount;
    private String formattedAmount;
    private Date date;
    private String dateString;

}
