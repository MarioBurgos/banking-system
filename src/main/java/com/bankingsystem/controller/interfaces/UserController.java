package com.bankingsystem.controller.interfaces;

import com.bankingsystem.model.User;

import java.util.List;

public interface UserController {

    List<User> getUsers();
    void saveUser(User user);

}
