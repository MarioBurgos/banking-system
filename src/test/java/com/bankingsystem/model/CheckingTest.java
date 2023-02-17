package com.bankingsystem.model;

import com.bankingsystem.classes.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CheckingTest {
    private  Checking checkingAccount;
    BigDecimal initialBalance, balanceLessThanMinimum;
    @BeforeEach
    void setUp() {
        initialBalance = new BigDecimal("500");
        balanceLessThanMinimum = new BigDecimal("100");
        checkingAccount = new Checking(new Money(initialBalance), null, null, null );
    }

    @Test
    void setBalance_BalanceIsLessThanMinimumBalance_PenaltyFeeIsApplied() {
        checkingAccount.setBalance(new Money(balanceLessThanMinimum));
        assertEquals(checkingAccount.getBalance().getAmount(), balanceLessThanMinimum.subtract(checkingAccount.getPenaltyFee()));
    }
}