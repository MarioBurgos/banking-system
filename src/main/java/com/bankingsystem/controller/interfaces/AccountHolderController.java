package com.bankingsystem.controller.interfaces;

import com.bankingsystem.classes.Money;
import com.bankingsystem.dto.BalanceDTO;

import java.util.List;
import java.util.Optional;

public interface AccountHolderController {
    List<BalanceDTO> checkBalance(Long accountHolderId);
    void transfer(Long accountHolderId, Long accountId, String amount, Optional<String> beneficiaryName, Optional<Long> beneficiaryAccountId);
}
