package com.bankingsystem.service.interfaces;

import com.bankingsystem.dto.BalanceDTO;
import com.bankingsystem.dto.ThirdPartyDTO;
import com.bankingsystem.model.ThirdParty;
import java.security.NoSuchAlgorithmException;

public interface AdministratorService {

//    Map<Long, AccountDTO> findAllAccounts();
    BalanceDTO checkBalance(Long accountId);
    void updateBalance(Long accountId, BalanceDTO balanceDTO);
    ThirdParty addThirdParty(ThirdPartyDTO thirdPartyDTO) throws NoSuchAlgorithmException;
    void deleteThirdParty(Long id);
}
