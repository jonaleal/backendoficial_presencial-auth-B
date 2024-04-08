package com.udea.sitas.controller;

import com.udea.sitas.model.Person;
import com.udea.sitas.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/Login")
public class LoginController {
    @Autowired
    private LoginService service;
    @GetMapping("/{id}")
    public ResponseEntity<Person> getAccountById(@PathVariable("id") Long id) {
        Person account = service.getAccount(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la cuenta con el ID proporcionado: " + id));
        return ResponseEntity.ok(account);
    }
}
