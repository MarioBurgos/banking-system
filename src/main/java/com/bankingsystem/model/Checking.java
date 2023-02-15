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
    @Column(columnDefinition="DECIMAL(19,4)")
    private final BigDecimal minimumBalance;
    @Column(columnDefinition="DECIMAL(19,4)")
    private final BigDecimal monthlyMaintenanceFee;
    private Date creationDate;
    @Enumerated(EnumType.STRING)
    private Status status;

    public Checking() {
        super();
        creationDate = new Date(System.currentTimeMillis());
        minimumBalance = new BigDecimal("250");
        monthlyMaintenanceFee = new BigDecimal("12");
        status = Status.ACTIVE;
    }

    public Checking(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey) {
        super(balance, primaryOwner, secondaryOwner);
        this.secretKey = secretKey;
        creationDate = new Date(System.currentTimeMillis());
        minimumBalance = new BigDecimal("250");
        monthlyMaintenanceFee = new BigDecimal("12");
        status = Status.ACTIVE;
    }

    public void setBalance(Money balance){
        super.setBalance(balance);
        if (super.getBalance().getAmount().compareTo(minimumBalance) == -1){
            BigDecimal balanceMinusPenaltyFee = getBalance().getAmount().subtract(super.getPenaltyFee());
            super.setBalance(new Money(balanceMinusPenaltyFee));
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
