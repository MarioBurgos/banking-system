package com.bankingsystem.controller.impl;

import com.bankingsystem.controller.interfaces.AdministratorController;
import com.bankingsystem.dto.BalanceDTO;
import com.bankingsystem.dto.ThirdPartyDTO;
import com.bankingsystem.model.ThirdParty;
import com.bankingsystem.service.interfaces.AdministratorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
public class AdministratorControllerImpl implements AdministratorController {
    @Autowired
    private AdministratorService administratorService;

    @GetMapping("/accounts/{id}/balance")
    public BalanceDTO checkBalance(@PathVariable(name = "id") Long accountId) {
        return administratorService.checkBalance(accountId);
    }

    @PatchMapping("/accounts/{id}/balance")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBalance(@PathVariable(name = "id") Long accountId, @RequestBody BalanceDTO balanceDTO) {
        administratorService.updateBalance(accountId, balanceDTO);
    }

    @PostMapping("/thirdparty")
    @ResponseStatus(HttpStatus.CREATED)
    public ThirdParty addThirdParty(@RequestBody @Valid ThirdPartyDTO thirdPartyDTO) throws NoSuchAlgorithmException {
        return administratorService.addThirdParty(thirdPartyDTO);
    }

    @DeleteMapping("/thirdparty/{id}")
    public void deleteThirdParty(@PathVariable Long id) {
        administratorService.deleteThirdParty( id);
    }

//    @GetMapping("/accounts")
//    public Map<Long, AccountDTO> findAllAccounts() {
//        return administratorService.findAllAccounts();
//    }
}
