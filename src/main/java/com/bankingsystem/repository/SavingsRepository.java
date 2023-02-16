package com.bankingsystem.repository;

import com.bankingsystem.model.Savings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavingsRepository extends JpaRepository<Savings,Long> {


    @Query("SELECT s FROM Savings s WHERE s.primaryOwner.id = :id OR s.secondaryOwner.id = :id" )
    List<Savings> findByAccountHolderId(@Param("id") Long id);
}
