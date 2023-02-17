package com.bankingsystem.controller.impl;

import com.bankingsystem.model.Role;
import com.bankingsystem.model.User;
import com.bankingsystem.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

class AccountHolderControllerImplTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private MvcResult mvcResult;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private User admin, user;
    private Role adminRole, userRole;
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        adminRole = new Role("ADMIN");
        userRole = new Role("USER");
        admin = new User("Administrator", "admin", passwordEncoder.encode("password"));
        user = new User("Regular user", "user", passwordEncoder.encode("password"));
        admin.setRoles(List.of(adminRole, userRole));
        user.setRoles(List.of(userRole));
        userRepository.saveAll(List.of(admin, user));
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void checkBalance() {
    }

    @Test
    void transfer() {
    }
}