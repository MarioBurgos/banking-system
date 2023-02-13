package com.bankingsystem.model;

import com.bankingsystem.classes.Money;
import com.bankingsystem.enums.Status;
import jakarta.persistence.*;

import java.sql.Date;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class StudentChecking extends Account {
    private String secretKey;
    private Date creationDate;

    public StudentChecking(Long id, Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Money penaltyFee, Status status, String secretKey, Date creationDate) {
        super(id, balance, primaryOwner, secondaryOwner, penaltyFee, status);
        this.secretKey = secretKey;
        this.creationDate = creationDate;
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
