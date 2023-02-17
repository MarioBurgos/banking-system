package com.bankingsystem.controller.interfaces;

import com.bankingsystem.dto.UserDTO;
import com.bankingsystem.model.User;

import java.util.List;

public interface UserController {

    List<UserDTO> getUsers();
    void saveUser(User user);

}
