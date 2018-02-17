package com.dyplom.repository;

import com.dyplom.entity.Bank;
import com.dyplom.entity.BankWorker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("bankRepository")
public interface BankRepository extends JpaRepository<Bank, Long> {
}
