package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.UserInfo;
import com.example.demo.form.SignupForm;
import com.example.demo.repo.UserInfoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {

    private final UserInfoRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserInfoRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public String authenticateUser(String email, String password) {
        Optional<UserInfo> userInfoOptional = repository.findByEmail(email);
        if (userInfoOptional.isPresent()) {
            UserInfo userInfo = userInfoOptional.get();
            if (passwordEncoder.matches(password, userInfo.getPassword())) {
                return "Authenticated User";
            } else {
                return "Incorrect password";
            }
        } else {
            return "User not found";
        }
    }

    public String addUser(SignupForm form) {
        Optional<UserInfo> userInfoExistedOpt = repository.findByEmail(form.getEmail());
        if (userInfoExistedOpt.isPresent()) {
            return "User already exists";
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setLastName(form.getLastName());
        userInfo.setFirstName(form.getFirstName());
        userInfo.setEmail(form.getEmail());
        userInfo.setPassword(passwordEncoder.encode(form.getPassword()));

        repository.save(userInfo);
        return "User added successfully";
    }
}
