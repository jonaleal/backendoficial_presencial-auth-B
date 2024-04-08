package com.udea.sitas.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "ROLE")
public class Role {

    @Id
    private Long id;

    @NotBlank
    private String name;

    private String description;

    @ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL)
    private Set<Person> users;
}
