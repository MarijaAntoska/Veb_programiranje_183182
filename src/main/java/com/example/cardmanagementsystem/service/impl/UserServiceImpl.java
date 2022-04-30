package com.example.cardmanagementsystem.service.impl;

import com.example.cardmanagementsystem.model.User;
import com.example.cardmanagementsystem.model.exceptions.InvalidUserIdException;
import com.example.cardmanagementsystem.model.exceptions.UsernameAlreadyExistsException;
import com.example.cardmanagementsystem.model.exceptions.UsernameNotFoundException;
import com.example.cardmanagementsystem.repository.UserRepository;
import com.example.cardmanagementsystem.service.UserService;

import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
/*
import org.springframework.security.core.userdetails.UsernameNotFoundException;
*/
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(InvalidUserIdException::new);
    }

    @Override
    public Optional<User> findByUsername(String username)
    {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public User findByName(String name) {
        return userRepository.findByUsername(name).orElseThrow();
    }

    @Override
    public List<User> listAll() {
        return userRepository.findAll();
    }

    @Override
    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User edit(User user)
    {
        return userRepository.save(user);
    }

    @Override
    public User register(User user) throws InvalidCredentialsException {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if(user.getUsername() == null || user.getUsername().isEmpty() ||
                user.getPassword()==null || user.getPassword().isEmpty())
        {
            throw new InvalidCredentialsException("Invalid credentials!");
        }
        if(this.userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new UsernameAlreadyExistsException();
        }

        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(UsernameNotFoundException::new);
    }
}
