package com.bankingsystem.model;

import com.bankingsystem.classes.Money;
import com.bankingsystem.enums.Status;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Date;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Savings extends Account {

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
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "interest_rate"))
    })
    private Money interestRate;

    public Savings() {
    }

    public Savings(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Money penaltyFee, Status status, String secretKey, Money minimumBalance, Money monthlyMaintenanceFee, Date creationDate, Money interestRate) {
        super(balance, primaryOwner, secondaryOwner, penaltyFee, status);
        this.secretKey = secretKey;
        this.minimumBalance = minimumBalance;
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
        this.creationDate = creationDate;
        this.interestRate = interestRate;
    }

//    public void setDefaultInterestRate(){
//        this.interestRate = new BigDecimal("0.0025");
//    }
//    public void setDefaultMinimumBalance(){
//        this.minimumBalance = new BigDecimal("1000");
//    }

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

    public Money getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Money interestRate) {
        BigDecimal maximumInterestRate = new BigDecimal("0.5");
        if (interestRate.getAmount().compareTo(maximumInterestRate) > 0){
            this.interestRate = new Money(maximumInterestRate);
        }
    }
}
