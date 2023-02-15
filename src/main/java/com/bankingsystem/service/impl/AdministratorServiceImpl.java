package com.bankingsystem.service.impl;

import com.auth0.jwt.algorithms.Algorithm;
import com.bankingsystem.classes.Money;
import com.bankingsystem.dto.AccountDTO;
import com.bankingsystem.dto.BalanceDTO;
import com.bankingsystem.dto.ThirdPartyDTO;
import com.bankingsystem.model.*;
import com.bankingsystem.repository.*;
import com.bankingsystem.service.interfaces.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

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
    @Autowired
    private ThirdPartyRepository thirdPartyRepository;
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
    public void updateBalance(Long accountId, BalanceDTO balanceDTO) {
        AccountDTO dto = new AccountDTO();
        Map<Long, AccountDTO> dtoMap = findAllAccounts();
        dto = dtoMap.get(accountId);
        switch (dto.getAccountType()){
            case "SAVINGS_ACCOUNT"-> {
                Optional<Savings> account = savingsRepository.findById(accountId);
                if(!account.isPresent()){throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found.");}
                account.get().setBalance(new Money(balanceDTO.getAmount()));
                savingsRepository.save(account.get());
            }
            case "CHECKING_ACCOUNT"-> {
                Optional<Checking> account = checkingRepository.findById(accountId);
                if(!account.isPresent()){throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found.");}
                account.get().setBalance(new Money(balanceDTO.getAmount()));
                checkingRepository.save(account.get());
            }
            case "STUDENT_CHECKING_ACCOUNT"-> {
                Optional<StudentChecking> account = studentCheckingRepository.findById(accountId);
                if(!account.isPresent()){throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found.");}
                account.get().setBalance(new Money(balanceDTO.getAmount()));
                studentCheckingRepository.save(account.get());
            }
            case "CREDIT_CARD"-> {
                Optional<CreditCard> account = creditCardRepository.findById(accountId);
                if(!account.isPresent()){throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found.");}
                account.get().setBalance(new Money(balanceDTO.getAmount()));
                creditCardRepository.save(account.get());
            }
        }
    }

    @Override
    public ThirdParty addThirdParty(ThirdPartyDTO thirdPartyDTO) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashedName = digest.digest(thirdPartyDTO.getName().getBytes(StandardCharsets.UTF_8));
        byte[] hashedKey = digest.digest(thirdPartyDTO.getKey().getBytes(StandardCharsets.UTF_8));
        ThirdParty newThirdParty = new ThirdParty(hashedName.toString(), hashedKey.toString());
        thirdPartyRepository.save(newThirdParty);
        return newThirdParty;
    }

}
