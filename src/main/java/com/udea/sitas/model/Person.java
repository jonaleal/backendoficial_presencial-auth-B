package com.udea.sitas.model;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "PERSON", uniqueConstraints = @UniqueConstraint(columnNames = "EMAIL"))
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqAccounts")
    @SequenceGenerator(name = "seqAccounts", allocationSize = 1, sequenceName = "SEQ_ACCOUNTS")
    private Long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String genre;

    private LocalDate birthdate;

    private String phoneNumber;

    private String country;

    private String state;

    private String city;

    private String address;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    @ManyToMany
    @JoinTable(name = "PERSON_ROLE", joinColumns = @JoinColumn(name = "USER_ID", nullable = false), inverseJoinColumns = @JoinColumn(name = "ROLE_ID", nullable = false))
    private Set<Role> roles;

}
