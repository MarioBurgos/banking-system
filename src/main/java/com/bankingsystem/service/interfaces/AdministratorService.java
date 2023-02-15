package com.bankingsystem.service.interfaces;

import com.bankingsystem.classes.Money;
import com.bankingsystem.dto.AccountDTO;
import com.bankingsystem.model.Account;

import java.util.List;
import java.util.Map;

public interface AdministratorService {

    Map<Long, AccountDTO> findAllAccounts();
    Money checkBalance(Long accountId);
    void updateBalance(Long accountId, Money amount);
}
