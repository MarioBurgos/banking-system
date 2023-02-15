package com.bankingsystem.model;

import com.bankingsystem.classes.Money;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Savings extends Account {

    private String secretKey;

    @Column(columnDefinition = "DECIMAL(19,4)")
    private final BigDecimal minimumBalance;
//    @Column(columnDefinition = "DECIMAL(19,4)")
//    private BigDecimal monthlyMaintenanceFee;
    private Date creationDate;
    @DecimalMin(value = "0")
    @DecimalMax(value = "0.5")
    @Column(columnDefinition = "DECIMAL(19,4)")
    private BigDecimal interestRate;
    private Date lastInterestDate;

    public Savings() {
        super();
        creationDate = new Date(System.currentTimeMillis());
        lastInterestDate = creationDate;
        this.minimumBalance = new BigDecimal("1000");
        this.interestRate = new BigDecimal("0.0025").setScale(4, RoundingMode.HALF_EVEN);

    }

    public Savings(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey) {
        super(balance, primaryOwner, secondaryOwner);
        this.secretKey = secretKey;
        this.minimumBalance = new BigDecimal("1000");
        creationDate = new Date(System.currentTimeMillis());
        lastInterestDate = creationDate;
        interestRate = new BigDecimal("0.0025").setScale(4, RoundingMode.HALF_EVEN);
        if (super.getBalance().getAmount().compareTo(minimumBalance) == -1) {
            throw new IllegalArgumentException("Balance in Savings account must be equals or greater than 1000");
        }
    }

    /**
     * Checks whether the current balance is less than the minimum balance or not.
     * In affirmative case -> subtracts the penalty fee
     */

    public void setBalance(Money balance) {
        super.setBalance(balance);
        // todo check this function because it's not subtracting penalty fee
        if (super.getBalance().getAmount().compareTo(minimumBalance) < 0) {
            BigDecimal balanceMinusPenaltyFee = getBalance().getAmount().subtract(super.getPenaltyFee());
            super.setBalance(new Money(balanceMinusPenaltyFee));
        } else {
            super.setBalance(balance);
        }
    }

    /**
     * Gets the balance, checking if it needs to add interests
     */
    @Override
    public Money getBalance() {

        // Check the difference in years between now and lastInterestDate
        Period diff = Period.between(lastInterestDate.toLocalDate(), LocalDate.now());
        Integer yearsBetween = diff.getYears();
        // calculate interests of each year
        for (int i = 0; i < yearsBetween; i++) {
            BigDecimal balanceInterestsForOneYear = super.getBalance().getAmount().multiply(interestRate).setScale(4, RoundingMode.HALF_EVEN);
            super.setBalance(new Money(super.getBalance().getAmount().add(balanceInterestsForOneYear)));
        }
        // set add years to lastInterestDate and set the value
        LocalDate localLastInterestDate = lastInterestDate.toLocalDate().plusYears(yearsBetween);
        setLastInterestDate(Date.valueOf(localLastInterestDate));

        // and obviously, return the new balance plus interests
        return super.getBalance();
    }

    public Date getLastInterestDate() {
        return lastInterestDate;
    }

    public void setLastInterestDate(Date lastInterestDate) {
        this.lastInterestDate = lastInterestDate;
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
