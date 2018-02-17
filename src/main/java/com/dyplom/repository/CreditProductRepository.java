package com.dyplom.repository;

import com.dyplom.entity.CreditProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("creditProductRepository")
public interface CreditProductRepository extends JpaRepository<CreditProduct, Long> {
    CreditProduct findByName(String name);
}
