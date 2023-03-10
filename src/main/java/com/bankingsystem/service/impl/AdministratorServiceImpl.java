package com.bankingsystem.service.impl;

import com.bankingsystem.classes.Money;
import com.bankingsystem.dto.BalanceDTO;
import com.bankingsystem.dto.ThirdPartyDTO;
import com.bankingsystem.model.*;
import com.bankingsystem.repository.*;
import com.bankingsystem.service.interfaces.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public BalanceDTO checkBalance(Long accountId) {
        BalanceDTO dto = new BalanceDTO(); // to be returned at the end of this function
        Optional<Savings> optionalSavings = savingsRepository.findById(accountId);
        Optional<Checking> optionalChecking = checkingRepository.findById(accountId);
        Optional<StudentChecking> optionalStudentChecking = studentCheckingRepository.findById(accountId);
        Optional<CreditCard> optionalCreditCard = creditCardRepository.findById(accountId);
        // find out the account type and set parameters to DTO
        if (optionalSavings.isPresent()){
            dto.setAccountId(optionalSavings.get().getId());
            dto.setAccountType("SAVINGS_ACCOUNT");
            dto.setAmount(optionalSavings.get().getBalance().getAmount());/***/
        } else if (optionalChecking.isPresent()) {
            dto.setAccountId(optionalChecking.get().getId());
            dto.setAccountType("CHECKING_ACCOUNT");
            dto.setAmount(optionalChecking.get().getBalance().getAmount());
        } else if (optionalStudentChecking.isPresent()) {
            dto.setAccountId(optionalStudentChecking.get().getId());
            dto.setAccountType("STUDENT_CHECKING_ACCOUNT");
            dto.setAmount(optionalStudentChecking.get().getBalance().getAmount());
        }else if (optionalCreditCard.isPresent()){
            dto.setAccountId(optionalCreditCard.get().getId());
            dto.setAccountType("CREDIT_CARD_ACCOUNT");
            dto.setAmount(optionalCreditCard.get().getBalance().getAmount());
        }
        return dto;
    }

    @Override
    public void updateBalance(Long accountId, BalanceDTO balanceDTO) {
        Optional<Savings> optionalSavings = savingsRepository.findById(accountId);
        Optional<Checking> optionalChecking = checkingRepository.findById(accountId);
        Optional<StudentChecking> optionalStudentChecking = studentCheckingRepository.findById(accountId);
        Optional<CreditCard> optionalCreditCard = creditCardRepository.findById(accountId);
        // find out the account type and set balance new value, then persist it
        if (optionalSavings.isPresent()){
            optionalSavings.get().setBalance(new Money(balanceDTO.getAmount()));
            savingsRepository.save(optionalSavings.get());
        } else if (optionalChecking.isPresent()) {
            optionalChecking.get().setBalance(new Money(balanceDTO.getAmount()));
            checkingRepository.save(optionalChecking.get());
        } else if (optionalStudentChecking.isPresent()) {
            optionalStudentChecking.get().setBalance(new Money(balanceDTO.getAmount()));
            studentCheckingRepository.save(optionalStudentChecking.get());
        }else if (optionalCreditCard.isPresent()){
            optionalCreditCard.get().setBalance(new Money(balanceDTO.getAmount()));
            creditCardRepository.save(optionalCreditCard.get());
        }
    }

    @Override
    public ThirdParty addThirdParty(ThirdPartyDTO thirdPartyDTO) throws NoSuchAlgorithmException {
        // create a new ThirdParty and encode the key attribute, then persist it
        ThirdParty newThirdParty = new ThirdParty(passwordEncoder.encode(thirdPartyDTO.getKey()), thirdPartyDTO.getName());
        thirdPartyRepository.save(newThirdParty);
        return newThirdParty;
    }
    @Override
    public void deleteThirdParty(Long id){
        thirdPartyRepository.deleteById(id);
    }

//    @Override
//    public Map<Long, AccountDTO> findAllAccounts() {
//        Map<Long, AccountDTO> dtoMap = new HashMap<>();
//        List<AccountDTO> accountDTOS = new ArrayList<>();
//        List<Savings> savings = savingsRepository.findAll();
//        List<Checking> checkings = checkingRepository.findAll();
//        List<StudentChecking> studentCheckings = studentCheckingRepository.findAll();
//        List<CreditCard> creditCards = creditCardRepository.findAll();
//
//        for (Savings item: savings) {
//            AccountDTO dto = new AccountDTO();
//            dto.setAccountType("SAVINGS_ACCOUNT");
//            dto.setId(item.getId());/***/
//            dto.setBalance(item.getBalance());/***/
//            dto.setPrimaryOwner(item.getPrimaryOwner());/***/
//            dto.setSecondaryOwner(item.getSecondaryOwner());/***/
//            dto.setPenaltyFee(item.getPenaltyFee());/***/
//            dto.setMinimumBalance(item.getMinimumBalance());//
//            dto.setInterestRate(item.getInterestRate());//
//            dto.setMonthlyMaintenanceFee(null);
//            dto.setCreditLimit(null);
//            dto.setCreationDate(item.getCreationDate());//
//            dto.setLastInterestDate(item.getLastInterestDate());//
//            dto.setStatus(null);
//            accountDTOS.add(dto);
//        }
//        for (Checking item: checkings) {
//            AccountDTO dto = new AccountDTO();
//            dto.setAccountType("CHECKING_ACCOUNT");
//            dto.setId(item.getId());
//            dto.setBalance(item.getBalance());
//            dto.setPrimaryOwner(item.getPrimaryOwner());
//            dto.setSecondaryOwner(item.getSecondaryOwner());
//            dto.setPenaltyFee(item.getPenaltyFee());
//            dto.setMinimumBalance(item.getMinimumBalance());
//            dto.setMonthlyMaintenanceFee(item.getMonthlyMaintenanceFee());
//            dto.setCreationDate(item.getCreationDate());
//            dto.setStatus(item.getStatus());
//            accountDTOS.add(dto);
//        }
//        for (StudentChecking item: studentCheckings) {
//            AccountDTO dto = new AccountDTO();
//            dto.setAccountType("STUDENT_CHECKING_ACCOUNT");
//            dto.setId(item.getId());
//            dto.setBalance(item.getBalance());
//            dto.setPrimaryOwner(item.getPrimaryOwner());
//            dto.setSecondaryOwner(item.getSecondaryOwner());
//            dto.setPenaltyFee(item.getPenaltyFee());
//            dto.setCreationDate(item.getCreationDate());
//            dto.setStatus(item.getStatus());
//            accountDTOS.add(dto);
//        }
//        for (CreditCard item: creditCards) {
//            AccountDTO dto = new AccountDTO();
//            dto.setAccountType("CREDIT_CARD");
//            dto.setId(item.getId());
//            dto.setBalance(item.getBalance());
//            dto.setPrimaryOwner(item.getPrimaryOwner());
//            dto.setSecondaryOwner(item.getSecondaryOwner());
//            dto.setPenaltyFee(item.getPenaltyFee());
////            dto.setMinimumBalance(item.getMinimumBalance());
//            dto.setInterestRate(item.getInterestRate());
////            dto.setMonthlyMaintenanceFee(item.getMonthlyMaintenanceFee());
//            dto.setCreditLimit(item.getCreditLimit());
////            dto.setCreationDate(item.getCreationDate());
////            dto.setLastInterestDate(item.getLastInterestDate());
////            dto.setStatus(item.getStatus());
//            accountDTOS.add(dto);
//        }
//        for (AccountDTO dto: accountDTOS) {
//            dtoMap.put(dto.getId(), dto);
//        }
//        return dtoMap;
//    }
}
