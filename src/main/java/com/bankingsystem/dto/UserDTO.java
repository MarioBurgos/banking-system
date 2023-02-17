package com.bankingsystem.dto;

import com.bankingsystem.model.Role;

import java.util.List;

public class UserDTO {

    private Long id;
    private String name;
    private String username;
    private List<Role> roles;

    public UserDTO() {
    }

    public UserDTO(Long id, String name, String username, List<Role> roles) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
