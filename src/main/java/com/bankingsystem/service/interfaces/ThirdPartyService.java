package com.bankingsystem.service.interfaces;

import com.bankingsystem.classes.Money;
import com.bankingsystem.model.Account;

public interface ThirdPartyService {
    void transfer(Account payer, Account payee, Money amount);
}
