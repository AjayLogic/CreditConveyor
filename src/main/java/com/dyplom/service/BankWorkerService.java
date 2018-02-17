package com.dyplom.service;

import com.dyplom.entity.BankWorker;
import com.dyplom.entity.Role;

import java.util.List;

public interface BankWorkerService {
    BankWorker findBankWorkerByEmail(String email);
    void saveBankWorkerHowManager(BankWorker bankWorker, BankWorker boss);
    void saveBankWorkerHowCreditor(BankWorker bankWorker, BankWorker boss);
    List<BankWorker> findAllByRole(Role role);
    void delete(Long id);
    BankWorker findOne(long id);
}
