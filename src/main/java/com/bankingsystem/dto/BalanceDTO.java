package com.bankingsystem.dto;

import com.bankingsystem.classes.Money;

import java.math.BigDecimal;

public class BalanceDTO {
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
