package com.dyplom.service;

import com.dyplom.entity.Contract;
import com.dyplom.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("contractService")
public class ContractServiceImpl implements ContractService{
    @Autowired
    ContractRepository contractRepository;

    @Override
    public void save(Contract contract) {
        if (contract.getId() != 0){
            contract.setChecked(true);
        }
        contractRepository.save(contract);
    }

    @Override
    public List<Contract> findAll() {
        return contractRepository.findAll();
    }

    @Override
    public Contract findById(Long id) {
        return contractRepository.findOne(id);
    }

    @Override
    public List<Contract> findByDatesBetween(Date startDate, Date endDate) {
        return contractRepository.findByStartDateBetween(startDate, endDate);
    }
}
