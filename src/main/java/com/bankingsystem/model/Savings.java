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

    @Column(columnDefinition = "DECIMAL(19,4)")
    private final BigDecimal minimumBalance;
    @Column(columnDefinition = "DECIMAL(19,4)")
    private BigDecimal monthlyMaintenanceFee;
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

    public Savings(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey, BigDecimal monthlyMaintenanceFee) {
        super(balance, primaryOwner, secondaryOwner);
        this.secretKey = secretKey;
        this.minimumBalance = new BigDecimal("1000");
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
        creationDate = new Date(System.currentTimeMillis());
        lastInterestDate = creationDate;
        interestRate = new BigDecimal("0.0025").setScale(4, RoundingMode.HALF_EVEN);
        if (super.getBalance().getAmount().compareTo(minimumBalance) == -1) {
            throw new IllegalArgumentException("Balance in Savings account must be equals or greater than 1000");
        }
    }

    /**
     * Checks the difference of currentDate and lastInterestDate.  If the difference is greater than a year, returns true
     */
    private boolean isYearHasPassed() {
        // a year in millis = 31556952000L
        Date currentDate = new Date(new java.util.Date().getTime());
        if (currentDate.getTime() - lastInterestDate.getTime() > 31556952000L) {
            lastInterestDate = currentDate;
            return true;
        }
        return false;
    }

    /** Checks whether the current balance is less than the minimum balance or not.
    In affirmative case -> subtracts the penalty fee*/
    @Override
    public void setBalance(Money balance) {
        // todo check this function because it's not subtracting penalty fee

        BigDecimal currentBalance = super.getBalance().getAmount();
        if (currentBalance.compareTo(minimumBalance) < 0) {
            BigDecimal balanceMinusPenaltyFee = super.getBalance().getAmount().subtract(super.getPenaltyFee());
            super.setBalance(new Money(balanceMinusPenaltyFee));
        } else {
            super.setBalance(balance);
        }
    }

    /** Gets the balance, checking if it needs to add interests */
    @Override
    public Money getBalance() {
        BigDecimal balanceInterests = super.getBalance().getAmount().multiply(interestRate).setScale(4, RoundingMode.HALF_EVEN);
        if (isYearHasPassed()) {
            setBalance(new Money(super.getBalance().getAmount().add(balanceInterests)));
        }
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
