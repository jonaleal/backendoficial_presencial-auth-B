package com.udea.sitas.controller;

import com.udea.sitas.model.Person;
import com.udea.sitas.service.UpdateInfoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/updateinfo")
public class UpdateInfoController {
    @Autowired
    private UpdateInfoService service;
    @PutMapping("/{id}")
    public Person updateAccount(@PathVariable("id") Long id, @Valid @RequestBody Person account) {
        return service.updateAccount(account);
    }
}
