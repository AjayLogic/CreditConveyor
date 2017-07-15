package com.dyplom.service;

import com.dyplom.entity.Bank;
import com.dyplom.entity.BankWorker;
import com.dyplom.entity.CreditProduct;
import com.dyplom.repository.CreditProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("creditProductService")
public class CreditProductServiceImpl implements CreditProductService {

    @Autowired
    CreditProductRepository creditProductRepository;
    @Autowired
    BankService bankService;

    @Override
    public void save(CreditProduct creditProduct, BankWorker creator) {
        creditProduct.setBank(bankService.findBankByBankWorker(creator));
        creditProductRepository.save(creditProduct);
    }

    @Override
    public List<CreditProduct> findAll() {
        return creditProductRepository.findAll();
    }

    @Override
    public CreditProduct findOne(Long id) {
        return creditProductRepository.findOne(id);
    }

    @Override
    public void delete(Long id) {
        creditProductRepository.delete(id);
    }

    @Override
    public CreditProduct findByName(String name) {
        return creditProductRepository.findByName(name);
    }
}
