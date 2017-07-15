package com.dyplom.service;


import com.dyplom.entity.Bank;
import com.dyplom.entity.BankWorker;

public interface BankService {
    Bank findBankByBankWorker(BankWorker bankWorker);
}
