package com.bankingsystem.model;

import com.bankingsystem.classes.Money;
import com.bankingsystem.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class CreditCard extends Account {
    @DecimalMin(value = "100")
    @DecimalMax(value = "100000")
    private BigDecimal creditLimit;
    @DecimalMin(value = "0.1")
    @DecimalMax(value = "0.2")
    private BigDecimal interestRate;

    public CreditCard() {
        creditLimit = new BigDecimal("100");
    }

    public CreditCard(AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal interestRate) {
        super(primaryOwner, secondaryOwner);
        this.interestRate = interestRate;
        creditLimit = new BigDecimal("100");
    }
    public void setBalance(Money balance){
        if (super.getBalance().getAmount().compareTo(new BigDecimal("0")) == -1){
            super.setBalance(new Money(super.getBalance().getAmount().subtract(super.getPenaltyFee())));
        }else {
            super.setBalance(balance);
        }
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }
}
