package com.udea.sitas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.udea.sitas.model.Person;

@Repository
public interface AccountRepository extends JpaRepository<Person, Long> {

}
