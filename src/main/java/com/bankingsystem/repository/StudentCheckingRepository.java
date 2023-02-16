package com.bankingsystem.repository;

import com.bankingsystem.model.Savings;
import com.bankingsystem.model.StudentChecking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentCheckingRepository extends JpaRepository<StudentChecking, Long> {
    @Query("SELECT s FROM StudentChecking s WHERE s.primaryOwner.id = :id OR s.secondaryOwner.id = :id" )
    List<StudentChecking> findByAccountHolderId(@Param("id") Long id);
    @Query("SELECT s FROM StudentChecking s WHERE s.primaryOwner.name = :name OR s.secondaryOwner.name = :name" )
    List<StudentChecking> findByAccountHolderName(@Param("name") String name);
}
