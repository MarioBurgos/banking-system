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

import java.math.BigDecimal;
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
        List<Savings> optionalSavings = savingsRepository.findByPrimaryOwner(accountHolderId);
        List<Checking> optionalChecking = checkingRepository.findByPrimaryOwner(accountHolderId);
        List<StudentChecking> optionalStudentChecking = studentCheckingRepository.findByPrimaryOwner(accountHolderId);
        List<CreditCard> optionalCreditCard = creditCardRepository.findByPrimaryOwner(accountHolderId);
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
    public void transfer(Long accountHolderId, Long accountId, String amount, Optional<String> beneficiaryName, Optional<Long> beneficiaryAccountId) {
        BigDecimal amountBigDecimal = new BigDecimal(amount);
        Optional<Savings> optionalSavingsBeneficiary;
        Optional<Checking> optionalCheckingBeneficiary;
        Optional<StudentChecking> optionalStudentCheckingBeneficiary;
        Optional<CreditCard> optionalCreditCardBeneficiary;
        List<Savings> savingsBeneficiaryList;
        List<Checking> checkingBeneficiaryList;
        List<StudentChecking> studentCheckingBeneficiaryList;
        List<CreditCard> creditCardBeneficiaryList;
        // find out which type of Account owns the sender
        Optional<Savings> optionalSavingsSender = savingsRepository.findById(accountId);
        Optional<Checking> optionalCheckingSender = checkingRepository.findById(accountId);
        Optional<StudentChecking> optionalStudentCheckingSender = studentCheckingRepository.findById(accountId);
        Optional<CreditCard> optionalCreditCardSender = creditCardRepository.findById(accountId);
        //if it has balance > amount to be sent, subtract amount from sender balance
        if (optionalSavingsSender.isPresent() && optionalSavingsSender.get().getBalance().getAmount().compareTo(amountBigDecimal) > 0){
            optionalSavingsSender.get().setBalance(new Money(optionalSavingsSender.get().getBalance().getAmount().subtract(amountBigDecimal)));
            savingsRepository.save(optionalSavingsSender.get());
        }
        else if (optionalCheckingSender.isPresent() && optionalCheckingSender.get().getBalance().getAmount().compareTo(amountBigDecimal) > 0){
            optionalCheckingSender.get().setBalance(new Money(optionalCheckingSender.get().getBalance().getAmount().subtract(amountBigDecimal)));
            checkingRepository.save(optionalCheckingSender.get());
        }
        else if (optionalStudentCheckingSender.isPresent() && optionalStudentCheckingSender.get().getBalance().getAmount().compareTo(amountBigDecimal) > 0){
            optionalStudentCheckingSender.get().setBalance(new Money(optionalStudentCheckingSender.get().getBalance().getAmount().subtract(amountBigDecimal)));
            studentCheckingRepository.save(optionalStudentCheckingSender.get());
        }
        else if (optionalCreditCardSender.isPresent() && optionalCreditCardSender.get().getBalance().getAmount().compareTo(amountBigDecimal) > 0){
            optionalCreditCardSender.get().setBalance(new Money(optionalCreditCardSender.get().getBalance().getAmount().subtract(amountBigDecimal)));
            creditCardRepository.save(optionalCreditCardSender.get());
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sender's account not found");
        }

        if (beneficiaryAccountId.isPresent()){ // find out beneficiary's account type by accountId
            optionalSavingsBeneficiary = savingsRepository.findById(beneficiaryAccountId.get());
            optionalCheckingBeneficiary = checkingRepository.findById(beneficiaryAccountId.get());
            optionalStudentCheckingBeneficiary = studentCheckingRepository.findById(beneficiaryAccountId.get());
            optionalCreditCardBeneficiary = creditCardRepository.findById(beneficiaryAccountId.get());
            // then add the amount to the account
            if (optionalSavingsBeneficiary.isPresent()){
                optionalSavingsBeneficiary.get().setBalance(new Money(optionalSavingsBeneficiary.get().getBalance().getAmount().add(amountBigDecimal)));
                savingsRepository.save(optionalSavingsBeneficiary.get());
            } else if (optionalCheckingBeneficiary.isPresent()) {
                optionalCheckingBeneficiary.get().setBalance(new Money(optionalCheckingBeneficiary.get().getBalance().getAmount().add(amountBigDecimal)));
                checkingRepository.save(optionalCheckingBeneficiary.get());
            } else if (optionalStudentCheckingBeneficiary.isPresent()) {
                optionalStudentCheckingBeneficiary.get().setBalance(new Money(optionalStudentCheckingBeneficiary.get().getBalance().getAmount().add(amountBigDecimal)));
                studentCheckingRepository.save(optionalStudentCheckingBeneficiary.get());
            } else if (optionalCreditCardBeneficiary.isPresent()) {
                optionalCreditCardBeneficiary.get().setBalance(new Money(optionalCreditCardBeneficiary.get().getBalance().getAmount().add(amountBigDecimal)));
                creditCardRepository.save(optionalCreditCardBeneficiary.get());
            }
        }  else if (beneficiaryName.isPresent()){ // find out beneficiary's account type by accountHolder name
            savingsBeneficiaryList = savingsRepository.findByPrimaryOwnerName(beneficiaryName.get());
            checkingBeneficiaryList = checkingRepository.findByPrimaryOwnerName(beneficiaryName.get());
            studentCheckingBeneficiaryList = studentCheckingRepository.findByPrimaryOwnerName(beneficiaryName.get());
            creditCardBeneficiaryList = creditCardRepository.findByPrimaryOwnerName(beneficiaryName.get());
            // then add the amount to the first account
            if (savingsBeneficiaryList.size() > 0){
                savingsBeneficiaryList.get(0).setBalance(new Money(savingsBeneficiaryList.get(0).getBalance().getAmount().add(amountBigDecimal)));
                savingsRepository.save(savingsBeneficiaryList.get(0));
            } else if (checkingBeneficiaryList.size() > 0) {
                checkingBeneficiaryList.get(0).setBalance(new Money(checkingBeneficiaryList.get(0).getBalance().getAmount().add(amountBigDecimal)));
                checkingRepository.save(checkingBeneficiaryList.get(0));
            } else if (studentCheckingBeneficiaryList.size() > 0) {
                studentCheckingBeneficiaryList.get(0).setBalance(new Money(studentCheckingBeneficiaryList.get(0).getBalance().getAmount().add(amountBigDecimal)));
                studentCheckingRepository.save(studentCheckingBeneficiaryList.get(0));
            } else if (creditCardBeneficiaryList.size() > 0) {
                creditCardBeneficiaryList.get(0).setBalance(new Money(creditCardBeneficiaryList.get(0).getBalance().getAmount().add(amountBigDecimal)));
                creditCardRepository.save(creditCardBeneficiaryList.get(0));
            }
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Beneficiary's account not found");
        }
    }
}
