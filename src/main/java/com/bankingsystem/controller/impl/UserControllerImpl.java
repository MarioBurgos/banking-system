package com.bankingsystem.controller.impl;

import com.bankingsystem.controller.interfaces.UserController;
import com.bankingsystem.dto.UserDTO;
import com.bankingsystem.model.User;
import com.bankingsystem.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserControllerImpl implements UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<UserDTO> getUsers() {
        return userService.getUsers();
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveUser(@RequestBody User user) {
        userService.saveUser(user);
    }


}
