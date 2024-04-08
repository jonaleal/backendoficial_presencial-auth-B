package com.udea.sitas.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udea.sitas.model.Person;
import com.udea.sitas.model.Role;
import com.udea.sitas.repository.AccountRepository;
import com.udea.sitas.repository.RoleRepository;

@Service
public class RegisterService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    public List<Person> findAllAccounts() {
        return accountRepository.findAll();
    }

    public Person createAccount(Person account) {
        Set<Role> defaultRoles = new HashSet<>(Arrays.asList(roleRepository.findById(0L).orElseThrow())); // default is client
        account.setRoles(defaultRoles);
        return accountRepository.save(account);
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

}
