package com.bankingsystem.controller.impl;

import com.bankingsystem.classes.Money;
import com.bankingsystem.controller.interfaces.AdministratorController;
import com.bankingsystem.dto.AccountDTO;
import com.bankingsystem.model.Account;
import com.bankingsystem.service.interfaces.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class AdministratorControllerImpl implements AdministratorController {

    @Autowired
    private AdministratorService administratorService;

    @GetMapping("/accounts")
    public Map<Long, AccountDTO> findAllAccounts(){
        return administratorService.findAllAccounts();
    }

    @GetMapping("/accounts/{id}/balance")
    public Money checkBalance(@PathVariable(name = "id") Long accountId) {
        return administratorService.checkBalance(accountId);
    }

    @PatchMapping("/accounts/{id}/balance")
    public void updateBalance(Account account, Money amount) {

    }
}
