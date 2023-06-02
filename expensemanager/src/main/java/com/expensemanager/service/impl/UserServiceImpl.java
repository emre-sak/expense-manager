package com.expensemanager.service.impl;

import com.expensemanager.dto.UserDTO;
import com.expensemanager.entity.User;
import com.expensemanager.repository.UserRepository;
import com.expensemanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void save(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setUserId(UUID.randomUUID().toString());

        userRepository.save(user);
    }

    @Override
    public User getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUserEmail = auth.getName();

        return userRepository.findByEmail(loggedInUserEmail).orElseThrow(
                () -> new UsernameNotFoundException("User not found for the email: " + loggedInUserEmail)
        );
    }

}
