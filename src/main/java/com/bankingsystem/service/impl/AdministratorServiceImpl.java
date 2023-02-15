package com.bankingsystem.service.impl;

import com.bankingsystem.classes.Money;
import com.bankingsystem.dto.AccountDTO;
import com.bankingsystem.model.*;
import com.bankingsystem.repository.*;
import com.bankingsystem.service.interfaces.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdministratorServiceImpl implements AdministratorService {
    @Autowired
    private SavingsRepository savingsRepository;
    @Autowired
    private CheckingRepository checkingRepository;
    @Autowired
    private StudentCheckingRepository studentCheckingRepository;
    @Autowired
    private CreditCardRepository creditCardRepository;
    @Override
    public Map<Long, AccountDTO> findAllAccounts() {
        Map<Long, AccountDTO> dtoMap = new HashMap<>();
        List<AccountDTO> accountDTOS = new ArrayList<>();
        List<Savings> savings = savingsRepository.findAll();
        List<Checking> checkings = checkingRepository.findAll();
        List<StudentChecking> studentCheckings = studentCheckingRepository.findAll();
        List<CreditCard> creditCards = creditCardRepository.findAll();

        for (Savings item: savings) {
            AccountDTO dto = new AccountDTO();
            dto.setAccountType("SAVINGS_ACCOUNT");
            dto.setId(item.getId());/***/
            dto.setBalance(item.getBalance());/***/
            dto.setPrimaryOwner(item.getPrimaryOwner());/***/
            dto.setSecondaryOwner(item.getSecondaryOwner());/***/
            dto.setPenaltyFee(item.getPenaltyFee());/***/
            dto.setMinimumBalance(item.getMinimumBalance());//
            dto.setInterestRate(item.getInterestRate());//
            dto.setMonthlyMaintenanceFee(null);
            dto.setCreditLimit(null);
            dto.setCreationDate(item.getCreationDate());//
            dto.setLastInterestDate(item.getLastInterestDate());//
            dto.setStatus(null);
            accountDTOS.add(dto);
        }
        for (Checking item: checkings) {
            AccountDTO dto = new AccountDTO();
            dto.setAccountType("CHECKING_ACCOUNT");
            dto.setId(item.getId());
            dto.setBalance(item.getBalance());
            dto.setPrimaryOwner(item.getPrimaryOwner());
            dto.setSecondaryOwner(item.getSecondaryOwner());
            dto.setPenaltyFee(item.getPenaltyFee());
            dto.setMinimumBalance(item.getMinimumBalance());
            dto.setMonthlyMaintenanceFee(item.getMonthlyMaintenanceFee());
            dto.setCreationDate(item.getCreationDate());
            dto.setStatus(item.getStatus());
            accountDTOS.add(dto);
        }
        for (StudentChecking item: studentCheckings) {
            AccountDTO dto = new AccountDTO();
            dto.setAccountType("STUDENT_CHECKING_ACCOUNT");
            dto.setId(item.getId());
            dto.setBalance(item.getBalance());
            dto.setPrimaryOwner(item.getPrimaryOwner());
            dto.setSecondaryOwner(item.getSecondaryOwner());
            dto.setPenaltyFee(item.getPenaltyFee());
            dto.setCreationDate(item.getCreationDate());
            dto.setStatus(item.getStatus());
            accountDTOS.add(dto);
        }
        for (CreditCard item: creditCards) {
            AccountDTO dto = new AccountDTO();
            dto.setAccountType("CREDIT_CARD");
            dto.setId(item.getId());
            dto.setBalance(item.getBalance());
            dto.setPrimaryOwner(item.getPrimaryOwner());
            dto.setSecondaryOwner(item.getSecondaryOwner());
            dto.setPenaltyFee(item.getPenaltyFee());
//            dto.setMinimumBalance(item.getMinimumBalance());
            dto.setInterestRate(item.getInterestRate());
//            dto.setMonthlyMaintenanceFee(item.getMonthlyMaintenanceFee());
            dto.setCreditLimit(item.getCreditLimit());
//            dto.setCreationDate(item.getCreationDate());
//            dto.setLastInterestDate(item.getLastInterestDate());
//            dto.setStatus(item.getStatus());
            accountDTOS.add(dto);
        }
        for (AccountDTO dto: accountDTOS) {
            dtoMap.put(dto.getId(), dto);
        }
        return dtoMap;
    }

    @Override
    public Money checkBalance(Long accountId) {
        AccountDTO dto = new AccountDTO();
        Map<Long, AccountDTO> accountDTOS = findAllAccounts();
        dto = accountDTOS.get(accountId);
        return dto.getBalance();
    }

    @Override
    public void updateBalance(Long accountId, Money amount) {
        AccountDTO dto = new AccountDTO();
        Map<Long, AccountDTO> accountDTOS = findAllAccounts();
        dto = accountDTOS.get(accountId);
    }

//    @Override
//    public User createAccount() {
//        return null;
//    }
//    @Override
//    public Money checkBalance(Account account) {
//        return null;
//    }
//
//    @Override
//    public void updateBalance(Account account, Money amount) {
//
//    }
}
