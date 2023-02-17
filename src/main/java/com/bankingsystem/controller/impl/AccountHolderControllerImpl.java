package com.bankingsystem.controller.impl;

import com.bankingsystem.controller.interfaces.AccountHolderController;
import com.bankingsystem.dto.BalanceDTO;
import com.bankingsystem.service.interfaces.AccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AccountHolderControllerImpl implements AccountHolderController {
    @Autowired
    private AccountHolderService accountHolderService;
    @GetMapping("/account-holder/{account-holder-id}/balance")
    public List<BalanceDTO> checkBalance(@PathVariable(name = "account-holder-id") Long accountHolderId) {
        return accountHolderService.checkBalance(accountHolderId);
    }

    @PatchMapping("account-holder/{account-holder-id}/accounts/{account-id}/transfer")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void transfer(@PathVariable(name = "account-holder-id") Long accountHolderId, @PathVariable(name = "account-id") Long accountId, @RequestParam String amount, @RequestParam(name = "beneficiary-name", required = false) Optional<String> beneficiaryName, @RequestParam(name = "beneficiary-account-id", required = false) Optional<Long> beneficiaryAccountId) {
        accountHolderService.transfer(accountHolderId, accountId, amount, beneficiaryName, beneficiaryAccountId);
    }
}
