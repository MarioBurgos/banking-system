package com.bankingsystem.model;

import com.bankingsystem.classes.Money;
import com.bankingsystem.enums.Status;
import jakarta.persistence.*;

import java.util.Optional;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class CreditCard extends Account {
    @Embedded
    @AttributeOverrides({  //se soluciona Overriding los nombres de las columnas en la BD
            @AttributeOverride(name = "amount", column = @Column(name = "credit_limit"))
    })
    private Money creditLimit;
    @Embedded
    @AttributeOverrides({  //se soluciona Overriding los nombres de las columnas en la BD
            @AttributeOverride(name = "amount", column = @Column(name = "interest_rate"))
    })
    private Money interestRate;

    public CreditCard() {
    }

    public CreditCard(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Money penaltyFee, Status status, Money creditLimit, Money interestRate) {
        super(balance, primaryOwner, secondaryOwner, penaltyFee, status);
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
    }

    public Money getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Money creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Money getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Money interestRate) {
        this.interestRate = interestRate;
    }
}
