package com.bankingsystem.controller.interfaces;

import com.bankingsystem.classes.Money;
import com.bankingsystem.dto.BalanceDTO;
import com.bankingsystem.model.Account;

import java.util.List;

public interface AccountHolderController {
    List<BalanceDTO> checkBalance(Long accountHolderId);
    void transfer(Account account, Money amount, String beneficiaryName, Long beneficiaryId);
}
