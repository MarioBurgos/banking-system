package com.bankingsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.validation.constraints.NotBlank;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class ThirdParty extends BankingUser {
    @NotBlank
    private String hashedKey;
    @NotBlank
    private String hashedName;

    public ThirdParty() {
    }

    public ThirdParty(String hashedKey, String hashedName) {
        this.hashedKey = hashedKey;
        this.hashedName = hashedName;
    }

    public String getHashedKey() {
        return hashedKey;
    }

    public void setHashedKey(String hashedKey) {
        this.hashedKey = hashedKey;
    }

    public String getHashedName() {
        return hashedName;
    }

    public void setHashedName(String hashedName) {
        this.hashedName = hashedName;
    }
}
