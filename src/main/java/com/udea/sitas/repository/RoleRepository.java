package com.udea.sitas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udea.sitas.model.Role;


public interface RoleRepository extends JpaRepository<Role, Long> {
}
