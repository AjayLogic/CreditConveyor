package com.dyplom.service;

import com.dyplom.entity.Bank;
import com.dyplom.entity.BankWorker;
import com.dyplom.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("bankService")
public class BankServiceImpl implements BankService {

    @Autowired
    private BankRepository bankRepository;

    @Override
    public Bank findBankByBankWorker(BankWorker bankWorker) {
        return bankRepository.findOne(bankWorker.getBank().getId());
    }
}
