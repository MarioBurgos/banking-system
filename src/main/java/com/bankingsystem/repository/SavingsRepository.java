package com.bankingsystem.repository;

import com.bankingsystem.model.Savings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavingsRepository extends JpaRepository<Savings, Long> {

    List<Savings> findByPrimaryOwner(Long id);

    List<Savings> findByPrimaryOwnerName(String name);


}
