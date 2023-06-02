package com.expensemanager.service;

import com.expensemanager.dto.UserDTO;
import com.expensemanager.entity.User;

public interface UserService {
    void save(UserDTO userDTO);
    User getLoggedInUser();

}
