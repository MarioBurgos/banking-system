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

    List<StudentChecking> findByPrimaryOwner(Long id);
    List<StudentChecking> findByPrimaryOwnerName(String name);
}
