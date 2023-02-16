package com.bankingsystem.model;

import com.bankingsystem.classes.Money;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class CreditCard extends Account {
    @DecimalMin(value = "100")
    @DecimalMax(value = "100000")
    @Column(columnDefinition = "DECIMAL(19,4)")
    @Positive
    @NotNull
    private BigDecimal creditLimit;
    @DecimalMin(value = "0.1")
    @DecimalMax(value = "0.2")
    @Column(columnDefinition = "DECIMAL(19,4)")
    @Positive
    @NotNull
    private BigDecimal interestRate;

    public CreditCard() {
        super();
        creditLimit = new BigDecimal("100");
    }

    public CreditCard(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal interestRate) {
        super(balance, primaryOwner, secondaryOwner);
        this.interestRate = interestRate;
        creditLimit = new BigDecimal("100");
    }

    public void setBalance(Money balance) {
        super.setBalance(balance);
    }

    public Money getBalance() {
        return super.getBalance();
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
