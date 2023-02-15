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
    @Enumerated(EnumType.STRING)
    private Status status;

    public StudentChecking() {
        super();
        this.creationDate = new Date(System.currentTimeMillis());
        status = Status.ACTIVE;
    }

    public StudentChecking(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey) {
        super(balance, primaryOwner, secondaryOwner);
        this.secretKey = secretKey;
        this.creationDate = new Date(System.currentTimeMillis());
        status = Status.ACTIVE;
    }
    public void setBalance(Money balance){
        if (super.getBalance().getAmount().compareTo(new BigDecimal("0")) == -1){
            super.setBalance(new Money(super.getBalance().getAmount().subtract(super.getPenaltyFee())));
        }else {
            super.setBalance(balance);
        }
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
