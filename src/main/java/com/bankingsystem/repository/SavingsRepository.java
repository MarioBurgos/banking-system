package com.bankingsystem.repository;

import com.bankingsystem.model.Savings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingsRepository extends JpaRepository<Savings,Long> {
}
