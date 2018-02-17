package com.dyplom.service;


import com.dyplom.entity.Contract;
import org.apache.catalina.LifecycleState;

import java.util.Date;
import java.util.List;

public interface ContractService {
    void save(Contract contract);
    List<Contract> findAll();
    Contract findById(Long id);
    List<Contract> findByDatesBetween(Date startDate, Date endDate);
}
