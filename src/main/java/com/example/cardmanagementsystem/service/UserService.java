package com.example.cardmanagementsystem.service;

import com.example.cardmanagementsystem.model.User;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    User findById(Long id);

    List<User> listAll();

    public User create(User user);

    public User edit(User user);
    public User register(User user) throws InvalidCredentialsException;
    public Optional<User> findByUsername(String username);
    public User findByName(String name);
}
