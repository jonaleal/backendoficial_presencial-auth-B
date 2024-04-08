package com.udea.sitas.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.udea.sitas.model.Person;
import com.udea.sitas.repository.AccountRepository;

@Service
public class LoginService {
    private AccountRepository accountRepository;

    public Optional<Person> getAccount(Long id) {
        return accountRepository.findById(id);
    }
}
