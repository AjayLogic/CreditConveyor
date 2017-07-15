package com.dyplom.service;


import com.dyplom.entity.Client;
import com.dyplom.entity.TestResult;

import java.util.List;

public interface TestResultService {
    void save(TestResult testResult);
    List<TestResult> getByClient(Client client);
}
