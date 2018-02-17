package com.dyplom.repository;

import com.dyplom.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("contractRepository")
public interface ContractRepository extends JpaRepository<Contract, Long> {
    List<Contract> findByStartDateBetween(Date startDate, Date endDate);
}
