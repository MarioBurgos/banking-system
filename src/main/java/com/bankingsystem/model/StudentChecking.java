package com.bankingsystem.model;

import com.bankingsystem.classes.Money;
import com.bankingsystem.enums.Status;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Date;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class StudentChecking extends Account {
    private String secretKey;
    private Date creationDate;

    public StudentChecking() {
        this.creationDate = new Date(System.currentTimeMillis());
    }

    public StudentChecking(AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey) {
        super(primaryOwner, secondaryOwner);
        this.secretKey = secretKey;
        this.creationDate = new Date(System.currentTimeMillis());
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
