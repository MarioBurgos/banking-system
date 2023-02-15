package com.bankingsystem.repository;

import com.bankingsystem.classes.Address;
import com.bankingsystem.classes.Money;
import com.bankingsystem.enums.Status;
import com.bankingsystem.model.AccountHolder;
import com.bankingsystem.model.Checking;
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
class CheckingRepositoryTest {

    @Autowired
    private CheckingRepository checkingRepository;
    @Autowired
    private AccountHolderRepository accountHolderRepository;
    private Checking checkingAccount;
    private AccountHolder primaryHolder, secondaryHolder;
    private Address address, mailingAddress;
    @BeforeEach
    void setUp() {
        address = new Address("street", "city", "postalCode", "country");
        mailingAddress = new Address("mailingStreet", "mailingCity", "mailingPostalCode", "mailingCountry");
        primaryHolder = new AccountHolder("primaryHolder", new Date(1990, 1,1), address, mailingAddress, List.of());
        secondaryHolder = new AccountHolder("secondaryHolder", new Date(1980, 2,2), address, mailingAddress, List.of());
        accountHolderRepository.saveAll(List.of(primaryHolder, secondaryHolder));

        checkingAccount = new Checking();
        checkingAccount.setBalance(new Money(new BigDecimal("1000")));
        checkingAccount.setCreationDate(new Date(2021,2,14));
        checkingAccount.setSecretKey("secretKey");
        checkingAccount.setPrimaryOwner(primaryHolder);
        checkingAccount.setSecondaryOwner(secondaryHolder);

        checkingRepository.save(checkingAccount);
        primaryHolder.setAccounts(List.of(checkingAccount));
        secondaryHolder.setAccounts(List.of(checkingAccount));
        accountHolderRepository.saveAll(List.of(primaryHolder, secondaryHolder));
    }

    @AfterEach
    void tearDown() {
        checkingAccount.setPrimaryOwner(null);
        checkingAccount.setSecondaryOwner(null);
        checkingRepository.save(checkingAccount);
        primaryHolder.setAccounts(List.of());
        secondaryHolder.setAccounts(List.of());
        accountHolderRepository.saveAll(List.of(primaryHolder,secondaryHolder));
        checkingRepository.delete(checkingAccount);
        accountHolderRepository.deleteAll(List.of(primaryHolder, secondaryHolder));
    }

    @Test
    void jpaMethods_TheyWorkAsExpected(){
        Optional<Checking> optionalChecking = checkingRepository.findById(checkingAccount.getId());
        assertTrue(optionalChecking.isPresent());
        assertEquals(checkingAccount.getPrimaryOwner().getName(), optionalChecking.get().getPrimaryOwner().getName());
    }
}