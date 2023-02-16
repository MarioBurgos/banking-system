package com.bankingsystem.model;

import com.bankingsystem.classes.Address;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.sql.Date;
import java.util.List;
@Entity
@PrimaryKeyJoinColumn(name = "id")
public class AccountHolder extends BankingUser {
    @NotBlank
    private String name;
    private Date dateOfBirth;
    @Embedded
    @NotNull
    private Address primaryAddress;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "mailing_street")),
            @AttributeOverride(name = "city", column = @Column(name = "mailing_city")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "mailing_postal_code")),
            @AttributeOverride(name = "country", column = @Column(name = "mailing_country"))
    })
    private Address mailingAddress;
    @OneToMany(mappedBy = "primaryOwner")
    @JsonIgnore
    private List<Account> accounts;

    public AccountHolder() {
    }

    public AccountHolder(String name, Date dateOfBirth, Address primaryAddress, Address mailingAddress, List<Account> accounts) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.primaryAddress = primaryAddress;
        this.mailingAddress = mailingAddress;
        this.accounts = accounts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Address getPrimaryAddress() {
        return primaryAddress;
    }

    public void setPrimaryAddress(Address primaryAddress) {
        this.primaryAddress = primaryAddress;
    }

    public Address getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(Address mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
