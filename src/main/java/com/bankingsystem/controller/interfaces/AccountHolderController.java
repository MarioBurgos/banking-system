package com.bankingsystem.controller.interfaces;

import com.bankingsystem.classes.Money;
import com.bankingsystem.model.Account;

public interface AccountHolderController {
    Money checkBalance();
    void transfer(Account account, Money amount, String beneficiaryName, Long beneficiaryId);
}
