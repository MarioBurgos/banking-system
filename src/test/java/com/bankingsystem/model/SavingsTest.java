package com.bankingsystem.model;

import com.bankingsystem.classes.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class SavingsTest {

    private Savings savingsAccount;
    BigDecimal initialBalance, balanceLessThanMinimum;
    @BeforeEach
    void setUp() {
        initialBalance = new BigDecimal("2000");
        balanceLessThanMinimum = new BigDecimal("600");
        savingsAccount = new Savings(new Money(initialBalance), null, null, null, null);
    }

    @Test
    void getBalance_AYearHasPassed_BalancePlusInterests(){
        // set creationDate -1 year
        LocalDate oneYearBefore = savingsAccount.getCreationDate().toLocalDate().minusYears(1);
        savingsAccount.setCreationDate(Date.valueOf(oneYearBefore));
        savingsAccount.setLastInterestDate(Date.valueOf(oneYearBefore));
        BigDecimal balancePlusInterests = initialBalance.add(initialBalance.multiply(savingsAccount.getInterestRate()));
        assertEquals(balancePlusInterests, savingsAccount.getBalance().getAmount());
    }
    @Test
    void getBalance_ThreeYearsHavePassed_BalancePlusInterests(){
        // set creationDate -2 years
        LocalDate threeYearsBefore = savingsAccount.getCreationDate().toLocalDate().minusYears(3);
        savingsAccount.setCreationDate(Date.valueOf(threeYearsBefore));
        // set lastInterestDate -2 years
        savingsAccount.setLastInterestDate(Date.valueOf(threeYearsBefore));

        for (int i = 0; i < 3; i++) {
            BigDecimal balancePlusInterests = initialBalance.add(initialBalance.multiply(savingsAccount.getInterestRate()));
            initialBalance = balancePlusInterests;
        }
        assertEquals(initialBalance.setScale(4, RoundingMode.HALF_EVEN), savingsAccount.getBalance().getAmount());
        assertTrue(savingsAccount.getCreationDate().before(new Date(new java.util.Date().getTime())));
    }
    @Test
    void setBalance_BalanceIsLessThanMinimumBalance_PenaltyFeeIsApplied() {
        savingsAccount.setBalance(new Money(balanceLessThanMinimum));
        assertEquals(savingsAccount.getBalance().getAmount(), balanceLessThanMinimum.subtract(savingsAccount.getPenaltyFee()));
    }
}