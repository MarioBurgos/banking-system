package com.bankingsystem.model;

import com.bankingsystem.classes.Money;
import com.bankingsystem.enums.Status;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Date;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Checking extends Account {
    private String secretKey;
    private final BigDecimal minimumBalance;
    private final BigDecimal monthlyMaintenanceFee;
    private Date creationDate;

    public Checking() {
        creationDate = new Date(System.currentTimeMillis());
        minimumBalance = new BigDecimal("250");
        monthlyMaintenanceFee = new BigDecimal("12");
    }

    public Checking(AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey) {
        super(primaryOwner, secondaryOwner);
        this.secretKey = secretKey;
        creationDate = new Date(System.currentTimeMillis());
        minimumBalance = new BigDecimal("250");
        monthlyMaintenanceFee = new BigDecimal("12");
    }

    public void setBalance(Money balance){
        if (super.getBalance().getAmount().compareTo(minimumBalance) == -1){
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

    public BigDecimal getMinimumBalance() {
        return minimumBalance;
    }

    public BigDecimal getMonthlyMaintenanceFee() {
        return monthlyMaintenanceFee;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
