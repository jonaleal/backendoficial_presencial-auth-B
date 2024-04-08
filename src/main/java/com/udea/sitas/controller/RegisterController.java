package com.udea.sitas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udea.sitas.model.Person;
import com.udea.sitas.service.RegisterService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private RegisterService service;

    @PostMapping
    public ResponseEntity<Person> createAccount(@Valid @RequestBody Person personInfo) {
        Person p = service.createAccount(personInfo); // TODO: check if email exists
        return ResponseEntity.status(HttpStatus.CREATED).body(p);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Person>> list() {
        List<Person> accounts = service.findAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Person> deleteAccount(@PathVariable("id") Long id) {
        service.deleteAccount(id);
        return ResponseEntity.ok().build();
    }
}
