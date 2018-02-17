package com.dyplom.repository;

import com.dyplom.entity.Client;
import com.dyplom.entity.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("testResultRepository")
public interface TestResultRepository extends JpaRepository<TestResult, Long> {

    List<TestResult> getByClient(Client client);
}
