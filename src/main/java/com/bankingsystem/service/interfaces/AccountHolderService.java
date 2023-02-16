package com.bankingsystem.service.interfaces;

import com.bankingsystem.classes.Money;
import com.bankingsystem.dto.BalanceDTO;
import com.bankingsystem.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountHolderService {
    List<BalanceDTO> checkBalance(Long accountHolderId);
    void transfer(Long accountHolderId, Long accountId, String amount, Optional<String> beneficiaryName, Optional<Long> beneficiaryAccountId);

}
