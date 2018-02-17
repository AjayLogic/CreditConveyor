package com.dyplom.service;

import com.dyplom.entity.Client;
import com.dyplom.entity.TestResult;
import com.dyplom.repository.TestResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("testResultServiceImpl")
public class TestResultServiceImpl implements TestResultService{
    @Autowired
    TestResultRepository testResultRepository;

    @Override
    public void save(TestResult testResult) {
        testResultRepository.save(testResult);
    }

    @Override
    public List<TestResult> getByClient(Client client) {
        return testResultRepository.getByClient(client);
    }
}
