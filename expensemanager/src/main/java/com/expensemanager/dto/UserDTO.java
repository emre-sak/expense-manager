package com.expensemanager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @NotBlank(message = "İsim boş olamaz")
    private String name;
    @Email(message = "Geçersiz email adresi")
    private String email;
    @NotBlank(message = "Şifre boş olamaz")
    @Size(min = 5, message = "Şifre {min} karakterden az olamaz")
    private String password;
    private String confirmPassword;
}
