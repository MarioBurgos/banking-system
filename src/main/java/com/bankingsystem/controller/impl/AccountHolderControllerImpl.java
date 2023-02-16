package com.bankingsystem.controller.impl;

import com.bankingsystem.classes.Money;
import com.bankingsystem.controller.interfaces.AccountHolderController;
import com.bankingsystem.model.Account;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountHolderControllerImpl implements AccountHolderController {
//    @GetMapping("/accounts/{id}/balance")
//    public Money checkBalance() {
//        return null;
//    }

    @Override
    public void transfer(Account account, Money amount, String beneficiaryName, Long beneficiaryId) {

    }
}
