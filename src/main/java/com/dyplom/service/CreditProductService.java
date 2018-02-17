package com.dyplom.service;

import com.dyplom.entity.Bank;
import com.dyplom.entity.BankWorker;
import com.dyplom.entity.CreditProduct;

import java.util.List;

public interface CreditProductService {
    void save(CreditProduct creditProduct, BankWorker creator);
    List<CreditProduct> findAll();
    CreditProduct findOne(Long id);
    void delete(Long id);
    CreditProduct findByName(String name);
}
