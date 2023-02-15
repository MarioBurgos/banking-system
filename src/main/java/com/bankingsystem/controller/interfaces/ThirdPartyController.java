package com.bankingsystem.controller.interfaces;

import com.bankingsystem.classes.Money;
import com.bankingsystem.model.Account;

public interface ThirdPartyController {
    void transfer(Account payer, Account payee, Money amount);

}
