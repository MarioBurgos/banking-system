package com.bankingsystem.service.impl;

import com.bankingsystem.classes.Money;
import com.bankingsystem.dto.BalanceDTO;
import com.bankingsystem.model.*;
import com.bankingsystem.repository.*;
import com.bankingsystem.service.interfaces.AccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountHolderServiceImpl implements AccountHolderService {
    @Autowired
    private SavingsRepository savingsRepository;
    @Autowired
    private CheckingRepository checkingRepository;
    @Autowired
    private StudentCheckingRepository studentCheckingRepository;
    @Autowired
    private CreditCardRepository creditCardRepository;
    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Override
    public List<BalanceDTO> checkBalance(Long accountHolderId) {
        BalanceDTO balanceDTO;
        List<BalanceDTO> dtos = new ArrayList<>();
        Optional<AccountHolder> optionalAccountHolder = accountHolderRepository.findById(accountHolderId);
        List<Savings> optionalSavings = savingsRepository.findByAccountHolderId(accountHolderId);
        List<Checking> optionalChecking = checkingRepository.findByAccountHolderId(accountHolderId);
        List<StudentChecking> optionalStudentChecking = studentCheckingRepository.findByAccountHolderId(accountHolderId);
        List<CreditCard> optionalCreditCard = creditCardRepository.findByAccountHolderId(accountHolderId);
        if (optionalSavings.size() > 0) {
            for (Savings s : optionalSavings) {
                balanceDTO = new BalanceDTO();
                balanceDTO.setAccountType("SAVINGS");
                balanceDTO.setAccountId(s.getId());
                balanceDTO.setAmount(s.getBalance().getAmount());
                dtos.add(balanceDTO);
            }
        }
        if (optionalChecking.size() > 0) {
            for (Checking c : optionalChecking) {
                balanceDTO = new BalanceDTO();
                balanceDTO.setAccountType("CHECKING");
                balanceDTO.setAccountId(c.getId());
                balanceDTO.setAmount(c.getBalance().getAmount());
                dtos.add(balanceDTO);
            }
        }
        if (optionalStudentChecking.size() > 0) {
            for (StudentChecking s : optionalStudentChecking) {
                balanceDTO = new BalanceDTO();
                balanceDTO.setAccountType("STUDENT_CHECKING");
                balanceDTO.setAccountId(s.getId());
                balanceDTO.setAmount(s.getBalance().getAmount());
                dtos.add(balanceDTO);
            }
        }
        if (optionalCreditCard.size() > 0) {
            for (CreditCard c : optionalCreditCard) {
                balanceDTO = new BalanceDTO();
                balanceDTO.setAccountType("CREDIT_CARD");
                balanceDTO.setAccountId(c.getId());
                balanceDTO.setAmount(c.getBalance().getAmount());
                dtos.add(balanceDTO);
            }
        }
        if (optionalSavings.size() == 0 &&
                optionalChecking.size() == 0 &&
                optionalStudentChecking.size() == 0 &&
                optionalCreditCard.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }
        return dtos;
    }

    @Override
    public void transfer(Account account, Money amount, String beneficiaryName, Long beneficiaryId) {

    }
}
