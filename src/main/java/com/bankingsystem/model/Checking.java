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
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "minimum_balance"))
    })
    private Money minimumBalance;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "monthly_maintenance_fee"))
    })
    private Money monthlyMaintenanceFee;
    private Date creationDate;

    public Checking() {
    }

    public Checking(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Money penaltyFee, Status status, String secretKey, Money minimumBalance, Money monthlyMaintenanceFee, Date creationDate) {
        super(balance, primaryOwner, secondaryOwner, penaltyFee, status);
        this.secretKey = secretKey;
        this.minimumBalance = minimumBalance;
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
        this.creationDate = creationDate;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Money getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(Money minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public Money getMonthlyMaintenanceFee() {
        return monthlyMaintenanceFee;
    }

    public void setMonthlyMaintenanceFee(Money monthlyMaintenanceFee) {
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
