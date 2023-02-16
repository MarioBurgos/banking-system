package com.bankingsystem.repository;

import com.bankingsystem.model.Checking;
import com.bankingsystem.model.CreditCard;
import com.bankingsystem.model.StudentChecking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    List<CreditCard> findByPrimaryOwner(Long id);

    List<CreditCard> findByPrimaryOwnerName(String name);

}
