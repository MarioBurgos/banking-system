package com.bankingsystem.service.interfaces;

import com.bankingsystem.model.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);
    List<User> getUsers();

}
