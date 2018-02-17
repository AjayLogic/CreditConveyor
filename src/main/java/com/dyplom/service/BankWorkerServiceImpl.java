package com.dyplom.service;

import com.dyplom.entity.BankWorker;
import com.dyplom.entity.Role;
import com.dyplom.repository.BankWorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service("bankWorkerService")
public class BankWorkerServiceImpl implements BankWorkerService {

    @Autowired
    private BankWorkerRepository bankWorkerRepository;
    @Autowired
    private BankService bankService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public BankWorker findBankWorkerByEmail(String email) {
        return bankWorkerRepository.findByEmail(email);
    }

    @Override
    public void saveBankWorkerHowManager(BankWorker manager, BankWorker admin) {
        if(manager.getId() == 0)
            manager.setPassword(bCryptPasswordEncoder.encode(manager.getPassword()));
        else
            manager.setPassword(manager.getPassword());

        manager.setActive(1);
        manager.setBank(bankService.findBankByBankWorker(admin));
        Role role = roleService.findByRole("ROLE_MANAGER");
        manager.setRole(new HashSet<Role>(Arrays.asList(role)));
        bankWorkerRepository.save(manager);
    }

    @Override
    public void saveBankWorkerHowCreditor(BankWorker creditor, BankWorker manager) {
        if(creditor.getId() == 0)
            creditor.setPassword(bCryptPasswordEncoder.encode(creditor.getPassword()));
        else
            creditor.setPassword(creditor.getPassword());

        creditor.setActive(1);
        creditor.setBank(bankService.findBankByBankWorker(manager));
        Role role = roleService.findByRole("ROLE_CREDITOR");
        creditor.setRole(new HashSet<Role>(Arrays.asList(role)));
        bankWorkerRepository.save(creditor);
    }

    @Override
    public List<BankWorker> findAllByRole(Role role) {
        return bankWorkerRepository.findAllByRole(role);
    }

    @Override
    public void delete(Long id) {
        bankWorkerRepository.delete(id);
    }

    @Override
    public BankWorker findOne(long id) {
        return bankWorkerRepository.findOne(id);
    }
}
