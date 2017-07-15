package com.dyplom.repository;

import com.dyplom.entity.BankWorker;
import com.dyplom.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("bankWorkerRepository")
public interface BankWorkerRepository extends JpaRepository<BankWorker, Long> {
    BankWorker findByEmail(String email);
    List<BankWorker> findAllByRole(Role role);
}
