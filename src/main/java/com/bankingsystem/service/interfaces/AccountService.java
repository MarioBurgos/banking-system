package com.bankingsystem.service.interfaces;

import com.bankingsystem.classes.Money;
import com.bankingsystem.model.Account;

public interface AccountService {
    Money checkBalance(Account account);
}
