package com.bankingsystem.repository;

import com.bankingsystem.classes.Address;
import com.bankingsystem.classes.Money;
import com.bankingsystem.enums.Status;
import com.bankingsystem.model.AccountHolder;
import com.bankingsystem.model.Savings;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class SavingsRepositoryTest {

    @Autowired
    private SavingsRepository savingsRepository;
    @Autowired
    private AccountHolderRepository accountHolderRepository;
    private Savings savingsAccount;
    private AccountHolder primaryHolder, secondaryHolder;
    private Address address, mailingAddress;
    @BeforeEach
    void setUp() {
        address = new Address("street", "city", "postalCode", "country");
        mailingAddress = new Address("mailingStreet", "mailingCity", "mailingPostalCode", "mailingCountry");
        primaryHolder = new AccountHolder("primaryHolder", new Date(1990, 1,1), address, mailingAddress, List.of());
        secondaryHolder = new AccountHolder("secondaryHolder", new Date(1980, 2,2), address, mailingAddress, List.of());
        accountHolderRepository.saveAll(List.of(primaryHolder, secondaryHolder));

        savingsAccount = new Savings();
        savingsAccount.setBalance(new Money(new BigDecimal("1000")));
        savingsAccount.setSecretKey("secretKey");
        savingsAccount.setCreationDate(new Date(2021,2,14));
        savingsAccount.setInterestRate(new BigDecimal("0.25"));
        savingsAccount.setPrimaryOwner(primaryHolder);
        savingsAccount.setSecondaryOwner(secondaryHolder);

        savingsRepository.save(savingsAccount);
        primaryHolder.setAccounts(List.of(savingsAccount));
        secondaryHolder.setAccounts(List.of(savingsAccount));
        accountHolderRepository.saveAll(List.of(primaryHolder, secondaryHolder));

    }

    @AfterEach
    void tearDown() {
        savingsAccount.setPrimaryOwner(null);
        savingsAccount.setSecondaryOwner(null);
        savingsRepository.save(savingsAccount);
        primaryHolder.setAccounts(List.of());
        secondaryHolder.setAccounts(List.of());
        accountHolderRepository.saveAll(List.of(primaryHolder,secondaryHolder));
        savingsRepository.delete(savingsAccount);
        accountHolderRepository.deleteAll(List.of(primaryHolder, secondaryHolder));
    }

    @Test
    void jpaMethods_TheyWorkAsExpected(){
        //findAll
        List<Savings> savings = savingsRepository.findAll();
        assertEquals(1 , savingsRepository.findAll().size());
        //findById
        Optional<Savings> optionalSavings = savingsRepository.findById(savingsAccount.getId());
        assertTrue(optionalSavings.isPresent());
        assertEquals(savingsAccount.getSecondaryOwner().getName(), optionalSavings.get().getSecondaryOwner().getName());
    }

}