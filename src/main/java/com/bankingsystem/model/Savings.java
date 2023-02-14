package com.bankingsystem.model;

import com.bankingsystem.classes.Money;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Savings extends Account {

    private String secretKey;

    private final BigDecimal minimumBalance;
    private BigDecimal monthlyMaintenanceFee;
    private Date creationDate;
    @DecimalMin(value = "0")
    @DecimalMax(value = "0.5")
    private BigDecimal interestRate;

    public Savings() {
        this.minimumBalance = new BigDecimal("1000");
        this.interestRate = new BigDecimal("0.0025").setScale(2, RoundingMode.HALF_EVEN);
    }

    public Savings(AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey, BigDecimal monthlyMaintenanceFee) {
        super(primaryOwner, secondaryOwner);
        this.secretKey = secretKey;
        this.minimumBalance = new BigDecimal("1000");
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
        creationDate = new Date(System.currentTimeMillis());
        interestRate = new BigDecimal("0.0025").setScale(2, RoundingMode.HALF_EVEN);
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

    public void setMonthlyMaintenanceFee(BigDecimal monthlyMaintenanceFee) {
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }
}
