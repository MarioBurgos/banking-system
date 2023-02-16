package com.bankingsystem.repository;

import com.bankingsystem.model.Checking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckingRepository extends JpaRepository<Checking, Long> {
    @Query("SELECT c FROM Checking c WHERE c.primaryOwner.id = :id OR c.secondaryOwner.id = :id" )
    List<Checking> findByAccountHolderId(@Param("id") Long id);
    @Query("SELECT c FROM Checking c WHERE c.primaryOwner.name = :name OR c.secondaryOwner.name = :name" )
    List<Checking> findByAccountHolderName(@Param("name") String name);
}
