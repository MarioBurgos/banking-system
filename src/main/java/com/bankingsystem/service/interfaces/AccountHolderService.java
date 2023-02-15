package com.bankingsystem.service.interfaces;

import com.bankingsystem.classes.Money;
import com.bankingsystem.model.Account;

public interface AccountHolderService {
    Money checkBalance();
    void transfer(Account account, Money amount, String beneficiaryName, Long beneficiaryId);

}
