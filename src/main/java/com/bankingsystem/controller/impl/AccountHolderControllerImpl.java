package com.bankingsystem.controller.impl;

import com.bankingsystem.classes.Money;
import com.bankingsystem.controller.interfaces.AccountHolderController;
import com.bankingsystem.dto.BalanceDTO;
import com.bankingsystem.model.Account;
import com.bankingsystem.service.interfaces.AccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountHolderControllerImpl implements AccountHolderController {
    @Autowired
    private AccountHolderService accountHolderService;
    @GetMapping("/account-holder/{account-holder-id}/balance")
    public List<BalanceDTO> checkBalance(@PathVariable(name = "account-holder-id") Long accountHolderId) {
        return accountHolderService.checkBalance(accountHolderId);
    }

    @PatchMapping("account-holder/{account-holder-id}/accounts/{account-id}/transfer")
    public void transfer(Account account, Money amount, String beneficiaryName, Long beneficiaryId) {

    }
}
