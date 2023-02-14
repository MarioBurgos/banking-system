package com.bankingsystem.model;

import com.bankingsystem.classes.Money;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SavingsTest {

    private Savings savingsAccount;
    BigDecimal initialBalance, interestRate, penaltyFee, balanceLessThanMinimum;
    @BeforeEach
    void setUp() {
        initialBalance = new BigDecimal("2000");
        balanceLessThanMinimum = new BigDecimal("500");
        savingsAccount = new Savings(new Money(initialBalance), null, null, null, null);
    }

    @Test
    void getBalance_AYearHasPassed_BalancePlusInterests(){
        long yearAndAHalf = 31556952000L + (31556952000L/2);
        // check initial balance
        assertEquals(initialBalance, savingsAccount.getBalance().getAmount());
        // simulate creationDate -1 year
        savingsAccount.setCreationDate(new Date(savingsAccount.getCreationDate().getTime() - yearAndAHalf));
        // set last interest date to -1 year too
        savingsAccount.setLastInterestDate(new Date(savingsAccount.getCreationDate().getTime()));
        BigDecimal balancePlusInterests = initialBalance.add(initialBalance.multiply(savingsAccount.getInterestRate()));
        assertEquals(balancePlusInterests, savingsAccount.getBalance().getAmount());
    }
    @Test
    void getBalance_AYearHasNotPassedYet_BalanceWithoutInterests() {
        // check initial balance
        assertEquals(initialBalance, savingsAccount.getBalance().getAmount());
    }
    @Test
    void setBalance_BalanceIsLessThanMinimumBalance_PenaltyFeeIsApplied() {
        savingsAccount.setBalance(new Money(balanceLessThanMinimum));
        assertEquals(savingsAccount.getBalance().getAmount(), balanceLessThanMinimum.subtract(savingsAccount.getPenaltyFee()));
    }
}