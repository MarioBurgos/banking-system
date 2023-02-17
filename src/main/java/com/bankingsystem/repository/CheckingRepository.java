package com.bankingsystem.repository;

import com.bankingsystem.model.Checking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckingRepository extends JpaRepository<Checking, Long> {
    List<Checking> findByPrimaryOwner(Long id);

    List<Checking> findByPrimaryOwnerName(String name);

}
