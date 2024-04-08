package com.udea.sitas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udea.sitas.model.Person;
import com.udea.sitas.repository.AccountRepository;

@Service
public class UpdateInfoService {
    @Autowired
    private AccountRepository accountRepository;

    public Person updateAccount(Person account) {

        Person dbaccount = accountRepository.findById(account.getId()).orElseThrow();
        dbaccount.setFirstName(account.getFirstName());
        dbaccount.setLastName(account.getLastName());
        dbaccount.setEmail(account.getEmail());
        dbaccount.setPassword(account.getPassword());
        dbaccount.setRoles(account.getRoles());
        return accountRepository.save(account);
    }
}
