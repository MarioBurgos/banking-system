package com.bankingsystem.service.impl;

import com.bankingsystem.dto.UserDTO;
import com.bankingsystem.model.Role;
import com.bankingsystem.model.User;
import com.bankingsystem.repository.UserRepository;
import com.bankingsystem.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        // Encode the user's password for security before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public List<UserDTO> getUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        UserDTO dto;
        List<Role> roles;
        for (User u: users) {
            roles = new ArrayList<>();
            for (Role r: u.getRoles()) {
                roles.add(r);
            }
            dto = new UserDTO(u.getId(), u.getName(), u.getUsername(), roles);
            userDTOList.add(dto);
        }
        return userDTOList;
    }

}
