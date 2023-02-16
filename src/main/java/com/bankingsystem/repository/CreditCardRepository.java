package com.bankingsystem.repository;

import com.bankingsystem.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    @Query("SELECT c FROM CreditCard c WHERE c.primaryOwner.id = :id OR c.secondaryOwner.id = :id" )
    List<CreditCard> findByAccountHolderId(@Param("id") Long id);
}
